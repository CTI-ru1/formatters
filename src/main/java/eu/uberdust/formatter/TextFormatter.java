package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:06 PM
 */
public class TextFormatter implements Formatter {
    private static final Logger LOGGER = Logger.getLogger(TextFormatter.class);


    private static TextFormatter instance = new TextFormatter();

    public static TextFormatter getInstance() {
        return instance;
    }

    @Override
    public String formatTestbed(final Testbed testbed) throws NotImplementedException {
        LOGGER.info("formatTestbed");
        throw new NotImplementedException();
    }

    @Override
    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException {
        LOGGER.info("formatTestbeds");
        throw new NotImplementedException();
    }

    @Override
    public String formatLinks(final List<Link> links) throws NotImplementedException {
        LOGGER.info("formatLinks");

        final StringBuilder output = new StringBuilder();

        for (final Link link : links) {
            output.append("[").append(link.getSource().getName()).append(",");
            output.append(link.getTarget().getName()).append("]");
            output.append(NEW_LINE);
        }

        return output.toString();
    }

    @Override
    public String formatCapabilities(final List<Capability> capabilities) throws NotImplementedException {
        LOGGER.info("formatCapabilities");

        final StringBuilder output = new StringBuilder();

        for (final Capability capability : capabilities) {
            output.append(capability.getName());
            output.append(NEW_LINE);
        }

        return output.toString();
    }

    @Override
    public String formatLastReadings(final List<LastNodeReading> lastNodeReadings,
                                     final List<LastLinkReading> lastLinkReadings) throws NotImplementedException {
        LOGGER.info("formatLastReadings");

        final StringBuilder output = new StringBuilder();
        if (lastNodeReadings != null) {
            for (final LastNodeReading lnr : lastNodeReadings) {

                if (lnr.getTimestamp() != null) {
                    output.append(lnr.getNodeCapability().getNode().getName()).append(TAB);
                    output.append(lnr.getTimestamp().getTime()).append(TAB);
                    if (lnr.getReading() != null) {
                        output.append(lnr.getReading().toString()).append(TAB);
                    } else {
                        output.append(NULL).append(TAB);
                    }
                    if (lnr.getStringReading() != null) {
                        output.append(lnr.getStringReading()).append(TAB);
                    } else {
                        output.append(NULL).append(TAB);
                    }
                    output.append(NEW_LINE);
                }
            }
        }
        if (lastLinkReadings != null) {
            for (final LastLinkReading llr : lastLinkReadings) {
                output.append("[").append(llr.getLinkCapability().getLink().getSource().getName()).append(",");

                if (llr.getTimestamp() != null) {
                    output.append(llr.getLinkCapability().getLink().getTarget().getName()).append("]").append(TAB);
                    output.append(llr.getTimestamp().getTime()).append(TAB);
                    if (llr.getReading() != null) {
                        output.append(llr.getReading().toString()).append(TAB);
                    } else {
                        output.append(NULL).append(TAB);
                    }
                    if (llr.getStringReading() != null) {
                        output.append(llr.getStringReading()).append(TAB);
                    } else {
                        output.append(NULL).append(TAB);
                    }
                    output.append(NEW_LINE);
                }
            }
        }
        return output.toString();
    }

    @Override
    public String formatNodes(final List<Node> nodes) throws NotImplementedException {
        LOGGER.info("formatNodes");

        final StringBuilder output = new StringBuilder();

        for (final Node node : nodes) {
            output.append(node.getName());
            output.append(NEW_LINE);
        }

        return output.toString();
    }

    @Override
    public String formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException {
        LOGGER.info("formatNodeReadings");

        final StringBuilder output = new StringBuilder();

        for (final NodeReading nr : nodeReadings) {

            output.append(nr.getTimestamp().getTime()).append(TAB);
            if (nr.getReading() != null) {
                output.append(nr.getReading().toString()).append(TAB);
            }
            if (nr.getStringReading() != null) {
                output.append(nr.getStringReading()).append(TAB);
            }
            output.append(NEW_LINE);
        }

        return output.toString();
    }

    @Override
    public String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        LOGGER.info("formatNodeReading");

        final StringBuilder output = new StringBuilder();

        output.append(nodeReading.getTimestamp().getTime()).append(TAB);
        if (nodeReading.getReading() != null) {
            output.append(nodeReading.getReading().toString()).append(TAB);
        }
        if (nodeReading.getStringReading() != null) {
            output.append(nodeReading.getStringReading()).append(TAB);

            output.append(NEW_LINE);
        }

        return output.toString();
    }

    @Override
    public String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        LOGGER.info("formatUniqueLastNodeReadings");

        final StringBuilder output = new StringBuilder();

        final Map<String, Integer> uniqueRooms = new HashMap<String, Integer>();

        for (final NodeCapability nodeCapability : nodeCapabilities) {
            uniqueRooms.put(nodeCapability.getLastNodeReading().getStringReading(), 1);
        }

        for (final String room : uniqueRooms.keySet()) {
            output.append(room).append(NEW_LINE);
        }

        return output.toString();
    }
}
