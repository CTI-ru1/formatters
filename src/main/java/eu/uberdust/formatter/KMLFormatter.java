package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects into KML format for Google Earth and Google Maps
 *
 * @author antoniou
 */
//
// TODO: Check compatibility with most recent Google Earth. Also with most recent Google Maps
// TODO: Reference KML version
//
public class KMLFormatter implements Formatter {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(KMLFormatter.class);
    /**
     * Singleton Instance.
     */
    private static KMLFormatter instance = new KMLFormatter();

    /**
     * Base Url to use with url links.
     */
    private static String baseUrl = "";

    /**
     * Returns a {@link KMLFormatter} instance.
     *
     * @return the {@link KMLFormatter} instance.
     */
    public static KMLFormatter getInstance() {
        return instance;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        KMLFormatter.baseUrl = baseUrl;
    }

    @Override
    public String formatTestbed(Testbed testbed) throws NotImplementedException {
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
    public String formatNodeReading(LastNodeReading lastNodeReading) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatTestbeds(List<Testbed> testbeds, Map<String, Long> nodesCount, Map<String, Long> linksCount) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLinkCapabilities(List<LinkCapability> linkCapabilities) throws NotImplementedException {
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
    public String formatNodes(List<Node> nodes) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodeReadings(List<NodeReading> nodeReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLastNodeReadings(List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String formatUniqueLastNodeReadings(List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLinks(List<Link> links) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLastReadings(List<LastNodeReading> lastNodeReadings, List<LastLinkReading> lastLinkReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeNode(Node node, String requestURL, String requestURI, String nodeDescription, Position nodePos) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeTestbed(Testbed testbed, String requestURL, String requestURI, List<Node> nodes, Map<Node, String> descriptionMap, Map<Node, List<NodeCapability>> capabilityMap, Map<Node, Position> originMap) throws NotImplementedException {
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
