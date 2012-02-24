package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;

import java.util.List;
import java.util.Map;

/**
 * RdfFormatter Class.
 * Implements the Formatter interface to present input to RDF format.
 */
public class RdfFormatter implements Formatter {

    private static final RdfFormatter instance = new RdfFormatter();

    public static RdfFormatter getInstance() {
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

    @Override
    public String describeTestbed(Testbed testbed, String requestURL, String requestURI, List<Node> nodes, Map<Node, String> descriptionMap, Map<Node, List<NodeCapability>> capabilityMap, Map<Node, Origin> originMap) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
