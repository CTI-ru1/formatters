package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;


/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects into SensorML format.
 * <p>
 * SensorML: Version 1.0.1 <br/>
 * <br/>
 * Version 1.0 SensorML was approved as an OGC Technical Specification on June 23, 2007. A corrigendum provided minor corrections on October 25, 2007.<br/>
 * In Version 1.0, SWE Common and SensorML were combined in the same specification. SWE Common defines the common data types and data aggregates used throughout all SWE specifications (excluding TML). In later versions, SWE Common will be defined as a separate specification.<br/>
 * <br/>
 * Version 1.0.1 of SensorML is defined by the following two documents:<br/>
 * Specification Document (V1.0) - OGC 07-000<br/>
 * Corrigendum Document  (V1.0.1) - OGC 07-122r2<br/>
 * <br/>
 * SensorML is defined by and is dependent on the following four schema:<br/>
 * SensorML 1.0.1 (namespace: http://schemas.opengis.net/sensorML/1.0.1)<br/>
 * SWE Common 1.0.1 (namespace: http://schemas.opengis.net/sweCommon/1.0.1)<br/>
 * GML 3.1.1 (namespace: http://schemas.opengis.net/gml/3.1.1)<br/>
 * IC:ISM 2.0 (namespace: http://schemas.opengis.net/ic/2.0)<br/>
 * <br/>
 * see:<br/> <a href="http://www.opengeospatial.org/standards/sensorml">OpenGIS Sensor Model Language (SensorML)</a><br/>
 * <a href="http://www.opengeospatial.org/standards/sensorml">OpenGIS Sensor Model Language (SensorML)</a>
 * <a href="http://schemas.opengis.net/sensorML/">Official SensorML schemas</a>
 * <a href="http://www.ogcnetwork.net/SensorML">SensorML</a>
 * </p>
 * @author antoniou
 */
//
// TODO: Reference which version of SensorML, which of O&M, which ontology...
//
public class SensorMLFormatter implements Formatter {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(SensorMLFormatter.class);
    /**
     * Singleton Instance.
     */
    private static SensorMLFormatter instance = new SensorMLFormatter();
    /**
     * Base Url to use with url links.
     */
    private static String baseUrl = "";

    /**
     * Returns a {@link SensorMLFormatter} instance.
     *
     * @return the {@link SensorMLFormatter} instance.
     */
    public static SensorMLFormatter getInstance() {
        return instance;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        SensorMLFormatter.baseUrl = baseUrl;
    }

    @Override
    public Object formatTestbed(Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public Object formatNode(Node node) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLink(Link link) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodeReading(NodeReading nodeReading) throws NotImplementedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
    public Object formatNodeReadings(List<NodeReading> nodeReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLinkReadings(List<LinkReading> linkReadings) throws NotImplementedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
    @Override
    public String formatVirtualNodes(List<Node> nodes) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
