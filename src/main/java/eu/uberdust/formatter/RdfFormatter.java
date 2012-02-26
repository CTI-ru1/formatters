package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Position;
import eu.wisebed.wisedb.model.Testbed;

import java.util.List;
import java.util.Map;

/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects to Rdf Objects.
 *
 * @author amaxilat
 */
public class RdfFormatter implements Formatter {
    /**
     * Singleton Instance.
     */
    private static final RdfFormatter instance = new RdfFormatter();

    /**
     * Returns a {@link RdfFormatter} instance.
     *
     * @return the {@link RdfFormatter} instance.
     */
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
    public String describeNode(final Node node, final String requestURL, final String requestURI,
                               final String nodeDescription, final Position nodePos) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                  final List<Node> nodes, final Map<Node, String> descriptionMap,
                                  final Map<Node, List<NodeCapability>> capabilityMap,
                                  final Map<Node, Origin> originMap) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
