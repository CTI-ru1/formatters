package eu.uberdust.formatter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.model.*;
import eu.wisebed.wiseml.model.WiseML;
import eu.wisebed.wiseml.model.scenario.Scenario;
import eu.wisebed.wiseml.model.scenario.Timestamp;
import eu.wisebed.wiseml.model.setup.Data;
import eu.wisebed.wiseml.model.trace.Trace;
import eu.wisebed.wiserdf.WiseML2RDF;
import eu.wisebed.wiserdf.rdfNodeExporter;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.LinkedList;
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
    private final String uri = "http://www.wisebed.eu/wiseml2rdf/";

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(RdfFormatter.class);


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
    public final Object formatTestbed(final Testbed testbed) throws NotImplementedException {


        //create new wiseml
        WiseML wiseML = new WiseML();
        wiseML.setVersion("1.0");
        wiseML.setXmlns("http://wisebed.eu/ns/wiseml/1.0");


        //init wisemlTRACE
        Trace trace = new Trace();
        List<Object> traceitemList = new LinkedList<Object>();
        Timestamp timestamp = new Timestamp();
        timestamp.setValue("0");
        traceitemList.add(timestamp);
        trace.setChildren(traceitemList);
        wiseML.setTrace(trace);
        LOGGER.info(testbed);
        LOGGER.info(testbed.getSetup());

        List<Node> wisedbNodes = NodeControllerImpl.getInstance().list(testbed.getSetup());
        List<eu.wisebed.wiseml.model.setup.Node> wisemlNodes = new LinkedList<eu.wisebed.wiseml.model.setup.Node>();
        for (Node wisedbNode : wisedbNodes) {
            eu.wisebed.wiseml.model.setup.Node wisemlNode = initWisemlNode(wisedbNode, null);
            wisemlNodes.add(wisemlNode);
        }

        //init wisemlSETUP
        eu.wisebed.wiseml.model.setup.Setup setup;
        setup = initSetup(testbed.getSetup());
        setup.setNodes(wisemlNodes);
        wiseML.setSetup(setup);

        //init wisemlSCENARIO
        wiseML.setScenario(new Scenario());


        //create a converter
        WiseML2RDF ml2RDF = new WiseML2RDF(wiseML);
        Model wiseModel;
        wiseModel = ModelFactory.createDefaultModel();
        wiseModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        wiseModel.setNsPrefix("wiserdf", uri);
        //generate RDF
        ml2RDF.exportRDF(wiseModel, uri);

        return wiseModel;
//        String answer = "";
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            wiseModel.write(bos, "RDF/XML");
//            answer = bos.toString();
//            System.out.println(answer);
//        } catch (Exception e) {
//            LOGGER.info("Error in dumping to rdf file: " + e);
//        }
//        return answer;

    }

    @Override
    public Object formatNode(Node wisedbNode) throws NotImplementedException {

        //create new wiseml
        WiseML wiseML = new WiseML();
        wiseML.setVersion("1.0");
        wiseML.setXmlns("http://wisebed.eu/ns/wiseml/1.0");

        //init wisemlNODE
        eu.wisebed.wiseml.model.setup.Node wisemlNode = initWisemlNode(wisedbNode, null);

        //init wisemlTRACE
        Trace trace = initTrace(wisedbNode, wisemlNode, null);
        wiseML.setTrace(trace);

        //init wisemlSETUP
        eu.wisebed.wiseml.model.setup.Setup setup;
        setup = initSetup(wisedbNode.getSetup());
        List<eu.wisebed.wiseml.model.setup.Node> nodeList = new LinkedList<eu.wisebed.wiseml.model.setup.Node>();
        nodeList.add(wisemlNode);
        setup.setNodes(nodeList);
        wiseML.setSetup(setup);

        //init wisemlSCENARIO
        wiseML.setScenario(new Scenario());


        //create a converter
        WiseML2RDF ml2RDF = new WiseML2RDF(wiseML);
        Model wiseModel;
        wiseModel = ModelFactory.createDefaultModel();
        wiseModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        wiseModel.setNsPrefix("wiserdf", uri);
        //generate RDF
        ml2RDF.exportRDF(wiseModel, uri);

        return wiseModel;
//        String answer = "";
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            wiseModel.write(bos, "RDF/XML");
//            answer = bos.toString();
//            System.out.println(answer);
//        } catch (Exception e) {
//            LOGGER.info("Error in dumping to rdf file: " + e);
//        }
//        return answer;


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
    public final String formatTestbeds(final List<Testbed> testbeds, final Map<String, Long> nodesCount,
                                       final Map<String, Long> linksCount) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final Object formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException {


        rdfNodeExporter ndf = new rdfNodeExporter("http://uberdust.cti.gr/rest/testbed/1");

        Node wisedbNode = nodeReadings.get(0).getCapability().getNode();
        List<Capability> capabilityList = new LinkedList<Capability>();
        capabilityList.add(nodeReadings.get(0).getCapability().getCapability());
        eu.wisebed.wiseml.model.setup.Node wisemlNode = initWisemlNode(wisedbNode, capabilityList);

        Data d = new Data();
        d.setKey(nodeReadings.get(0).getCapability().getCapability().getDescription());

        if (nodeReadings.get(0).getReading() == null) {
            d.setValue(nodeReadings.get(0).getStringReading());
        } else {
            d.setValue(nodeReadings.get(0).getReading().toString());
        }

        DateTime ts = new DateTime(nodeReadings.get(0).getTimestamp());
        String room = nodeReadings.get(1).getStringReading();
        return ndf.exportNode(wisemlNode, d, ts, room);
    }

    @Override
    public String formatLinkReadings(List<LinkReading> linkReadings) throws NotImplementedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String formatLastNodeReadings(List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        return null;
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
                                        final Map<Node, Position> originMap) throws NotImplementedException {
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

    private Trace initTrace(Node wisedbNode, eu.wisebed.wiseml.model.setup.Node wisemlNode, List<Capability> caps) {
        Trace trace = new Trace();

        List<Object> children = new LinkedList<Object>();

        List<NodeCapability> wisedbCapabilityList = null;
        if (caps != null) {
            for (Capability cap : caps) {
                wisedbCapabilityList.add(NodeCapabilityControllerImpl.getInstance().getByID(wisedbNode, cap));
            }
        } else {
            wisedbCapabilityList = NodeCapabilityControllerImpl.getInstance().list(wisedbNode);
        }

        for (NodeCapability nodeCapability : wisedbCapabilityList) {


            LastNodeReading lnr = nodeCapability.getLastNodeReading();
            Timestamp timestamp = new Timestamp();
            LinkedList<eu.wisebed.wiseml.model.setup.Node> nodelist = new LinkedList<eu.wisebed.wiseml.model.setup.Node>();

            List<Data> dataList = new LinkedList<Data>();
            Data capData = new Data();

            capData.setKey(lnr.getNodeCapability().getCapability().getName());

            LOGGER.info("Adding : " + lnr);
            if (lnr.getReading() != null) {

                capData.setValue(lnr.getReading().toString());
            } else {
                capData.setValue(lnr.getStringReading());
            }

            dataList.add(capData);
            wisemlNode.setData(dataList);
            nodelist.add(wisemlNode);


            timestamp.setValue(String.valueOf(lnr.getTimestamp().getTime() / 1000));
            children.add(timestamp);
            children.add(wisemlNode);
        }


        children.add(wisemlNode);

//        trace.setTimestamp(children);
        trace.setChildren(children);
        trace.setId(0);
        return trace;
    }


    private Trace initTraceWithReadings(Node wisedbNode, eu.wisebed.wiseml.model.setup.Node wisemlNode, List<NodeReading> readings) {
        Trace trace = new Trace();

        List<Object> children = new LinkedList<Object>();

        List<NodeCapability> wisedbCapabilityList = null;

        for (NodeReading lnr : readings) {


            Timestamp timestamp = new Timestamp();
            LinkedList<eu.wisebed.wiseml.model.setup.Node> nodelist = new LinkedList<eu.wisebed.wiseml.model.setup.Node>();

            List<Data> dataList = new LinkedList<Data>();
            Data capData = new Data();

            capData.setKey(lnr.getCapability().getCapability().getName());

            LOGGER.info("Adding : " + lnr);
            if (lnr.getReading() != null) {

                capData.setValue(lnr.getReading().toString());
            } else {
                capData.setValue(lnr.getStringReading());
            }

            dataList.add(capData);
            wisemlNode.setData(dataList);
            nodelist.add(wisemlNode);


            timestamp.setValue(String.valueOf(lnr.getTimestamp().getTime() / 1000));
            children.add(timestamp);
            children.add(wisemlNode);
        }


        children.add(wisemlNode);

//        trace.setTimestamp(children);
        trace.setChildren(children);
        trace.setId(0);
        return trace;
    }

    public eu.wisebed.wiseml.model.setup.Node initWisemlNode(Node wisedbNode, List<Capability> ncaps) {

        eu.wisebed.wiseml.model.setup.Node wisemlNode = new eu.wisebed.wiseml.model.setup.Node();
        wisemlNode.setId(wisedbNode.getName());


        List<Capability> wisedbCapabilityList = ncaps;
        if (ncaps == null) {
            wisedbCapabilityList = CapabilityControllerImpl.getInstance().list(wisedbNode);
        }
        List<eu.wisebed.wiseml.model.setup.Capability> wisemlCapabilityList = new LinkedList<eu.wisebed.wiseml.model.setup.Capability>();
        for (Capability capability : wisedbCapabilityList) {

            eu.wisebed.wiseml.model.setup.Capability wisemlCapability = new eu.wisebed.wiseml.model.setup.Capability();
            wisemlCapability.setName(capability.getName());
            wisemlCapability.setDatatype(capability.getDatatype());
            wisemlCapability.setDefaultvalue(capability.getDefaultvalue());
            wisemlCapability.setDescription(capability.getDescription());
            wisemlCapability.setUnit(capability.getUnit());
            wisemlCapabilityList.add(wisemlCapability);

        }
        wisemlNode.setCapabilities(wisemlCapabilityList);
        return wisemlNode;
    }


    private eu.wisebed.wiseml.model.setup.Setup initSetup(Setup nodeSetup) {
        eu.wisebed.wiseml.model.setup.Setup setup = new eu.wisebed.wiseml.model.setup.Setup();
        setup.setDescription(nodeSetup.getDescription());
//        eu.wisebed.wiseml.model.setup.TimeInfo timeInfo = new eu.wisebed.wiseml.model.setup.TimeInfo();
//        timeInfo.setDuration(setup.getTimeinfo().getDuration());
//        timeInfo.setStart(setup.getTimeinfo().getStart());
//        timeInfo.setEnd(setup.getTimeinfo().getEnd());
//        timeInfo.setUnit(setup.getTimeinfo().getUnit());
//        setup.setTimeinfo(timeInfo);
        setup.setCoordinateType(nodeSetup.getCoordinateType());
        return setup;
    }

}
