package eu.uberdust.formatter;

import com.sun.syndication.feed.module.georss.GeoRSSModule;
import com.sun.syndication.feed.module.georss.SimpleModuleImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.Coordinate;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:06 PM
 */
public class GeoRssFormatter implements Formatter {
    private static final Logger LOGGER = Logger.getLogger(GeoRssFormatter.class);

    private static final GeoRssFormatter instance = new GeoRssFormatter();

    public static GeoRssFormatter getInstance() {
        return instance;
    }


    public String describeNode(final Node node, final String requestURL, final String requestURI, final String nodeDescription, final Position nodePos) throws NotImplementedException {
        final String baseUrl = requestURL.replace(requestURI, "");

        final Testbed testbed = node.getSetup().getTestbed();

        final String syndEntryLink = new StringBuilder().append(baseUrl).append("/uberdust/rest/testbed/")
                .append(testbed.getId()).append("/node/").append(node.getName()).toString();

        // set up feed and entries
        final SyndFeed feed = new SyndFeedImpl();

        feed.setFeedType("rss_2.0");
        feed.setTitle(node.getId() + " GeoRSS feed");
        feed.setDescription(testbed.getDescription());
        feed.setLink(requestURL);
        final List<SyndEntry> entries = new ArrayList<SyndEntry>();

        // set entry's title,link and publishing date
        final SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(node.getName());
        entry.setLink(syndEntryLink);
        entry.setPublishedDate(new Date());

        // set entry's description (HTML list)
        final SyndContent description = new SyndContentImpl();
        final StringBuilder descriptionBuffer = new StringBuilder();
        descriptionBuffer.append("<p>").append(nodeDescription).append("</p>");
        descriptionBuffer.append("<ul>");
        for (NodeCapability capability : NodeCapabilityControllerImpl.getInstance().list(node)) {
            descriptionBuffer.append("<li>").append(capability.getCapability().getName()).append(":");
            if (capability.getLastNodeReading().getStringReading() != null) {
                descriptionBuffer.append(capability.getLastNodeReading().getReading()).append(",")
                        .append(capability.getLastNodeReading().getStringReading());
            } else {
                descriptionBuffer.append(capability.getLastNodeReading().getReading());
            }
            descriptionBuffer.append("</li>");
        }
        descriptionBuffer.append("</ul>");
        description.setType("text/html");
        description.setValue(descriptionBuffer.toString());
        entry.setDescription(description);


        // set the GeoRSS module and add it
        final GeoRSSModule geoRSSModule = new SimpleModuleImpl();
        if (testbed.getSetup().getCoordinateType().equals("Absolute")) {
            com.sun.syndication.feed.module.georss.geometries.Position position =
                    new com.sun.syndication.feed.module.georss.geometries.Position();

            position.setLatitude(nodePos.getX());
            position.setLongitude(nodePos.getY());
            geoRSSModule.setPosition(position);
        } else {

            // convert testbed origin from long/lat position to xyz if needed
            final Origin origin = testbed.getSetup().getOrigin();

            final Coordinate originCoordinate = new Coordinate((double) origin.getX(), (double) origin.getY(),
                    (double) origin.getZ(), (double) origin.getPhi(), (double) origin.getTheta());
            final Coordinate properOrigin = Coordinate.blh2xyz(originCoordinate);

            // convert node position from xyz to long/lat

            final Coordinate nodeCoordinate = new Coordinate((double) nodePos.getX(),
                    (double) nodePos.getY(), (double) nodePos.getZ());
            final Coordinate rotated = Coordinate.rotate(nodeCoordinate, properOrigin.getPhi());
            final Coordinate absolute = Coordinate.absolute(properOrigin, rotated);
            final Coordinate coordinate = Coordinate.xyz2blh(absolute);
            geoRSSModule.setPosition(new com.sun.syndication.feed.module.georss.geometries.Position(coordinate.getX(), coordinate.getY()));

        }
        entry.getModules().add(geoRSSModule);
        entries.add(entry);

        // add entries to feed
        feed.setEntries(entries);

        // the feed output goes to response
        final SyndFeedOutput output = new SyndFeedOutput();
        final StringWriter writer = new StringWriter();
        try {
            output.output(feed, writer);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (FeedException e) {
            LOGGER.error(e);
        }
        return writer.toString();
    }

    @Override
    public String formatTestbed(final Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatCapabilities(final List<Capability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodes(final List<Node> nodes) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLinks(final List<Link> links) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLastReadings(final List<LastNodeReading> lastNodeReadings,
                                     final List<LastLinkReading> lastLinkReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
