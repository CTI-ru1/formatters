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
import java.util.Map;

/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects to GeoRss Feeds.
 *
 * @author amaxilat
 */
public class GeoRssFormatter implements Formatter {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(GeoRssFormatter.class);
    /**
     * Singleton Instance.
     */
    private static GeoRssFormatter instance = new GeoRssFormatter();
    /**
     * Base Url to use with url links.
     */
    private static String baseUrl = "";

    /**
     * Returns a {@link GeoRssFormatter} instance.
     *
     * @return the {@link GeoRssFormatter} instance.
     */
    public static GeoRssFormatter getInstance() {
        return instance;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public final String describeNode(final Node node, final String requestURL, final String requestURI,
                                     final String nodeDescription, final Position nodePos)
            throws NotImplementedException {
        final String baseUrl = requestURL.replace(requestURI, "");

        final Testbed testbed = node.getSetup().getTestbed();

        final String syndEntryLink = new StringBuilder().append(baseUrl).append("/uberdust/rest/testbed/")
                .append(testbed.getId()).append("/node/").append(node.getName()).toString();

        // set up feed and entries
        final SyndFeed feed = new SyndFeedImpl();
        final Date nowDate = new Date();

        feed.setFeedType("rss_2.0");
        feed.setTitle(node.getName() + " GeoRSS feed");
        feed.setDescription(testbed.getDescription());
        feed.setLink(requestURL);
        final List<SyndEntry> entries = new ArrayList<SyndEntry>();

        // set entry's title,link and publishing date
        final SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(node.getName());
        entry.setLink(syndEntryLink);
        entry.setPublishedDate(nowDate);

        // set entry's description (HTML list)
        final SyndContent description = new SyndContentImpl();
        final StringBuilder descriptionBuffer = new StringBuilder();
        descriptionBuffer.append("<p>").append(nodeDescription).append("</p>");
        descriptionBuffer.append("<ul>");
        for (NodeCapability capability : NodeCapabilityControllerImpl.getInstance().list(node)) {
            descriptionBuffer.append("<li>").append(capability.getCapability().getName()).append(":");
            if (capability.getLastNodeReading().getStringReading() == null) {
                descriptionBuffer.append(capability.getLastNodeReading().getReading());
            } else {
                descriptionBuffer.append(capability.getLastNodeReading().getReading()).append(",")
                        .append(capability.getLastNodeReading().getStringReading());
            }
            descriptionBuffer.append("</li>");
        }
        descriptionBuffer.append("</ul>");
        description.setType("text/html");
        description.setValue(descriptionBuffer.toString());
        entry.setDescription(description);


        // set the GeoRSS module and add it
        final GeoRSSModule geoRSSModule = new SimpleModuleImpl();
        if ("Absolute".equals(testbed.getSetup().getCoordinateType())) {
            final com.sun.syndication.feed.module.georss.geometries.Position position =
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
            geoRSSModule.setPosition(new com.sun.syndication.feed.module.georss.geometries.Position(
                    coordinate.getX(), coordinate.getY()));

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
    public final String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                        final List<Node> nodes, final Map<Node, String> descriptionMap,
                                        final Map<Node, List<NodeCapability>> capabilityMap,
                                        final Map<Node, Origin> originMap) throws NotImplementedException {

        final String baseUrl = requestURL.replace(requestURI, "");

        LOGGER.info("baseUrl : " + baseUrl);

        final SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(testbed.getName() + " GeoRSS");
        feed.setLink(requestURL);
        feed.setDescription(testbed.getDescription());
        final List<SyndEntry> entries = new ArrayList<SyndEntry>();

// convert testbed origin from long/lat position to xyz if needed
        Coordinate properOrigin = null;
        final Coordinate originCoordinate = new Coordinate();
        if (!(testbed.getSetup().getCoordinateType().equals("Absolute"))) {
// determine testbed origin by the type of coordinates given
            final Origin origin = testbed.getSetup().getOrigin();
            originCoordinate.setX((double) origin.getX());
            originCoordinate.setY((double) origin.getY());
            originCoordinate.setZ((double) origin.getZ());
            originCoordinate.setPhi((double) origin.getPhi());
            originCoordinate.setTheta((double) origin.getTheta());
            properOrigin = Coordinate.blh2xyz(originCoordinate);
        }


        final Date nowDate = new Date();
        for (final Node node : nodes) {
            final SyndEntry entry = new SyndEntryImpl();
            final SyndContent description = new SyndContentImpl();
            final GeoRSSModule geoRSSModule = new SimpleModuleImpl();
            final Coordinate nodeCoordinate = new Coordinate();

            // set entry's title,link and publishing date
            entry.setTitle(node.getName());
            entry.setLink(new StringBuilder().append(baseUrl).append("/rest/testbed/")
                    .append(testbed.getId()).append("/node/").append(node.getName()).toString());
            entry.setPublishedDate(nowDate);

            // set entry's description (HTML list)
            final StringBuilder descriptionBuffer = new StringBuilder();
            descriptionBuffer.append("<p>").append(descriptionMap.get(node)).append("</p>");
            descriptionBuffer.append("<p><a href=\"").append(baseUrl).append("/uberdust/rest/testbed/")
                    .append(testbed.getId()).append("/node/").append(node.getName()).append("/georss").append("\">")
                    .append("GeoRSS feed").append("</a></p>");
            descriptionBuffer.append("<ul>");
            for (final NodeCapability capability : capabilityMap.get(node)) {
                if (capability.getLastNodeReading().getReading() != null) {
                    descriptionBuffer.append("<li>").append(capability.getCapability().getName()).append(":")
                            .append(capability.getLastNodeReading().getReading()).append("</li>");
                } else if (capability.getLastNodeReading().getStringReading() != null) {
                    descriptionBuffer.append("<li>").append(capability.getCapability().getName()).append(":")
                            .append(capability.getLastNodeReading().getStringReading()).append("</li>");
                }

            }
            descriptionBuffer.append("</ul>");
            description.setType("text/html");
            description.setValue(descriptionBuffer.toString());
            entry.setDescription(description);


            // set the GeoRSS module and add it to entry
            if ("Absolute".equals(testbed.getSetup().getCoordinateType())) {
                geoRSSModule.setPosition(new com.sun.syndication.feed.module.georss.geometries.Position(
                        originMap.get(node).getX(), originMap.get(node).getY()));
            } else {
                // convert node position from xyz to long/lat

                Origin npos;
                try {
                    npos = originMap.get(node);
                } catch (NullPointerException e) {
                    npos = testbed.getSetup().getOrigin();
                }

                nodeCoordinate.setX((double) npos.getX());
                nodeCoordinate.setY((double) npos.getY());
                nodeCoordinate.setZ((double) npos.getZ());

                final Coordinate rotated = Coordinate.rotate(nodeCoordinate, properOrigin.getPhi());
                final Coordinate absolute = Coordinate.absolute(properOrigin, rotated);
                final Coordinate nodePosition = Coordinate.xyz2blh(absolute);
                geoRSSModule.setPosition(new com.sun.syndication.feed.module.georss.geometries.Position(
                        nodePosition.getX(), nodePosition.getY()));
            }
            entry.getModules().add(geoRSSModule);
            entries.add(entry);
        }

        // add entries to feed
        feed.setEntries(entries);

// the feed output goes to response
        final SyndFeedOutput output = new SyndFeedOutput();
        StringWriter writer = new StringWriter();
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
    public String showTestbed(Testbed testbed, List<Node> nodes, List<Link> links, List<Capability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeNodeCapabilities(List<NodeCapability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatTestbed(final Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNode(Node node) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLink(Link link) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatTestbeds(final List<Testbed> testbeds, final Map<String, Long> nodesCount,
                                       final Map<String, Long> linksCount) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLinkCapabilities(final List<LinkCapability> linkCapabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatCapability(Testbed testbed, Capability capability) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatCapabilities(Testbed testbed, List<Capability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatNodes(final List<Node> nodes) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatLinks(final List<Link> links) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatLastReadings(final List<LastNodeReading> lastNodeReadings,
                                           final List<LastLinkReading> lastLinkReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
