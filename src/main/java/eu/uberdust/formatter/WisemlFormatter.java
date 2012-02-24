package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Position;
import eu.wisebed.wisedb.model.Testbed;

import java.util.List;

/**
 * WisemlFormatter Class.
 * Implements the Formatter interface to present input to WiseMl format.
 */
public class WisemlFormatter implements Formatter {

    private static final WisemlFormatter instance = new WisemlFormatter();

    public static WisemlFormatter getInstance() {
        return instance;
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

    @Override
    public String describeNode(Node node, String requestURL, String requestURI, String nodeDescription, Position nodePos) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
