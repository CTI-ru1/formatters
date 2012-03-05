package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;

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
    private static RdfFormatter instance = new RdfFormatter();
    /**
     * Base Url to use with url links.
     */
    private static String baseUrl = "";

    /**
     * Returns a {@link RdfFormatter} instance.
     *
     * @return the {@link RdfFormatter} instance.
     */
    public static RdfFormatter getInstance() {
        return instance;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
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
    public String formatLinkCapabilities(List<LinkCapability> linkCapabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatCapability(final Testbed testbed, final Capability capability) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatCapabilities(final Testbed testbed, final List<Capability> capabilities)
            throws NotImplementedException {
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
                                           final List<LastLinkReading> lastLinkReadings)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String describeNode(final Node node, final String requestURL, final String requestURI,
                                     final String nodeDescription, final Position nodePos)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                        final List<Node> nodes, final Map<Node, String> descriptionMap,
                                        final Map<Node, List<NodeCapability>> capabilityMap,
                                        final Map<Node, Origin> originMap) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String showTestbed(Testbed testbed, List<Node> nodes, List<Link> links, List<Capability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeNodeCapabilities(List<NodeCapability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
