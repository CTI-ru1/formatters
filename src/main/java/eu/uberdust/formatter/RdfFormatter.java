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
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import java.io.IOException;
import java.util.HashMap;
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
    private Map<String, String> conversionMap;

    public RdfFormatter() {
        conversionMap = new HashMap<String, String>();
        conversionMap.put("Centigrades", "http://dbpedia.org/page/Celsius");
        conversionMap.put("Fahrenheit", "http://dbpedia.org/page/Fahrenheit");
        conversionMap.put("Lux", "http://dbpedia.org/page/Lux");
        conversionMap.put("%", "http://dbpedia.org/page/Percentage");
        conversionMap.put("m", "http://dbpedia.org/page/Metre");
    }

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

    private JSONArray wrapValue(String str) {
        JSONArray array = new JSONArray();
        array.put(str);
        return array;
    }

    private JSONArray wrapValue(JSONArray str) {
        JSONArray array = new JSONArray();
        array.put(str);
        return array;
    }

    @Override
    public String formatNodeReading(NodeReading nodeReading) throws NotImplementedException {

        /*
           {
           "tsproperties":[["id123","id456","id789","id101"]],
           //"base_ov_name":["http://www.example1.org/ov/"],
           ///"observed_property":["temperature"],
           //"uri":["http://www.example.org/device/remotea12b"],
           //"base_datetime":["12-08-28T19:03Z"],
           //"author":[{"surname":["Theodoridis"],"firstname":["Evangelos"]}],
           //"uom":["centigrade"],
           //"base_name":["http://www.example2.org/device/"],
           //"observation_values" : [1,2,3]

           }
        */


        String response = null;
        try {
            JSONObject json = new JSONObject();
            JSONArray authorDetails = new JSONArray();
            JSONObject surnameobj = new JSONObject();
            surnameobj.put("surname", "Chatzigiannakis");
            JSONObject nameobj = new JSONObject();
            nameobj.put("firstname", "Ioannis");

            authorDetails.put(surnameobj);
            authorDetails.put(nameobj);

            json.put("author", authorDetails);

            json.put("observed_property", wrapValue(nodeReading.getCapability().getCapability().getName().substring(
                    nodeReading.getCapability().getCapability().getName().lastIndexOf(":") + 1)));
            json.put("uom", wrapValue(nodeReading.getCapability().getCapability().getUnit()));
            json.put("base_datetime", wrapValue(nodeReading.getTimestamp().toString()));
            //http://uberdust.cti.gr/rest/testbed/1/node/urn:wisebed:ctitestbed:0x2df/capability/urn:wisebed:node:capability:light/rdf/rdf+xml
            json.put("uri", "http://uberdust.cti.gr/rest/testbed/"
                    + nodeReading.getCapability().getNode().getSetup().getId()
                    + "/node/" + nodeReading.getCapability().getNode().getName()
                    + "/capability/" + nodeReading.getCapability().getCapability().getName()
                    + "/rdf/rdf+xml");
            json.put("base_ov_name", wrapValue(""));
            json.put("base_name", wrapValue(""));
            json.put("ts_properties", wrapValue(wrapValue("").toString()));
            //http://uberdust.cti.gr/rest/testbed/1/node/urn:wisebed:ctitestbed:0x2df/capability/urn:wisebed:node:capability:light/rdf/rdf+xml/limit/1
            json.put("observation_values", wrapValue(
                    "http://uberdust.cti.gr/rest/testbed/"
                            + nodeReading.getCapability().getNode().getSetup().getId()
                            + "/node/" + nodeReading.getCapability().getNode().getName()
                            + "/capability/" + nodeReading.getCapability().getCapability().getName()
                            + "/rdf/rdf+xml/limit/1"

            ));


            json.put("location-coords", wrapValue(nodeReading.getCapability().getNode().getSetup().getOrigin().getX() + "_" + nodeReading.getCapability().getNode().getSetup().getOrigin().getY()));


            JSONArray vals = new JSONArray();

//            LOGGER.info(json.toString());
//
//            for (int i = 0; i < nodeReadings.size(); i++) {
//                if (nodeReadings.get(i).getReading() != null) {
//                    vals.put(nodeReadings.get(i).getReading());
//                } else {
//                    vals.put(nodeReadings.get(i).getStringReading());
//                }
//            }
//
//            json.put("values", vals);

            LOGGER.info(json.toString());


            ClientResource cr = new ClientResource("http://localhost:8182/ld4s/device/");
            StringRepresentation stringRep = new StringRepresentation(json.toString());
            stringRep.setMediaType(MediaType.APPLICATION_JSON);
            Representation resp = cr.post(stringRep);

            List<Preference<MediaType>> accepted = new LinkedList<Preference<MediaType>>();
            accepted.add(new Preference<MediaType>(MediaType.APPLICATION_RDF_XML));
            cr.getClientInfo().setAcceptedMediaTypes(accepted);

            Status status = cr.getStatus();
            if (status.isError()) {
                LOGGER.error(status.getCode() + " " + status.getDescription());
                return status.getCode() + " " + status.getDescription();
            } else {
                return resp.getText();
            }

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return response;
    }

    @Override
    public final String formatTestbeds(final List<Testbed> testbeds, final Map<String, Long> nodesCount,
                                       final Map<String, Long> linksCount) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final Object formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException {

        if ((nodeReadings == null) || (nodeReadings.size() == 0)) {
            return "No values provided";
        }

        String response = null;
        try {
            JSONObject json = new JSONObject();
            json.put("uri", "http://uberdust.cti.gr/rest/testbed/"
                    + nodeReadings.get(0).getCapability().getNode().getSetup().getId()
                    + "/node/" + nodeReadings.get(0).getCapability().getNode().getName());
            json.put("context", "");
            json.put("resource_time", nodeReadings.get(0).getTimestamp().getTime());

            JSONArray vals = new JSONArray();

            LOGGER.info(json.toString());

            for (int i = 0; i < nodeReadings.size(); i++) {
                if (nodeReadings.get(i).getReading() != null) {
                    vals.put(nodeReadings.get(i).getReading());
                } else {
                    vals.put(nodeReadings.get(i).getStringReading());
                }
            }

            json.put("values", vals);

            LOGGER.info(json.toString());


            ClientResource cr = new ClientResource("http://localhost:8182/ld4s/ov/");
            Representation resp = cr.post(json.toString(), MediaType.APPLICATION_RDF_XML);
            Status status = cr.getStatus();
            if (status.isError()) {
                LOGGER.error(status.getCode() + " " + status.getDescription());
                return status.getCode() + " " + status.getDescription();
            } else {
                return resp.getText();
            }

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return response;
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

        //{
        // "systems":[["http://www.example.org/device/2","http://www.example.org/device/1"]],
        // "algorithms":[["http://www.example.org/alg/45","http://www.example.org/alg/18"]],
        // "worn_by":[[ [{"surname":["Hausenblas"],"firstname":["Michael"]}], [{"surname":["Amaxilatis"],"firstname":["Dimitrios"]}] ]],
        // "owners":[[ [{"surname":["Leggieri"],"firstname":["Myriam"]}], [{"surname":["Hauswirth"],"firstname":["Manfred"]}] ]],
        // "author":[{"surname":["Theodoridis"],"firstname":["Evangelos"]}],
        // "location-name":["Patras"],
        // "location-coords":["38.24444_21.73444"]},
        // "start_range":["5800"], "end_range":["10321"],
        // }

        String response = null;
        try {
            JSONObject json = new JSONObject();
            JSONArray authorDetails = new JSONArray();
            JSONObject surnameobj = new JSONObject();
            surnameobj.put("surname", wrapValue("Chatzigiannakis"));
            JSONObject nameobj = new JSONObject();
            nameobj.put("firstname", wrapValue("Ioannis"));

            authorDetails.put(surnameobj);
            authorDetails.put(nameobj);

            json.put("author", authorDetails);

            JSONArray vals = new JSONArray();

            for (NodeCapability capability : capabilities) {
                vals.put("http://uberdust.cti.gr/rest/testbed/"
                        + capability.getNode().getSetup().getId()
                        + "/node/" + capability.getNode().getName()
                        + "/capability/" + capability.getCapability().getName()
                        + "/rdf/rdf+xml"
                );
            }


            json.put("systems", wrapValue(
                    vals
            ));
            json.put("location-coords", wrapValue(capabilities.get(0).getNode().getSetup().getOrigin().getX() + "_" + capabilities.get(0).getNode().getSetup().getOrigin().getY()));


//            LOGGER.info(json.toString());
//
//            for (int i = 0; i < nodeReadings.size(); i++) {
//                if (nodeReadings.get(i).getReading() != null) {
//                    vals.put(nodeReadings.get(i).getReading());
//                } else {
//                    vals.put(nodeReadings.get(i).getStringReading());
//                }
//            }
//
//            json.put("values", vals);

            LOGGER.info(json.toString());


            ClientResource cr = new ClientResource("http://localhost:8182/ld4s/tpp/");
            Representation resp = cr.post(json.toString(), MediaType.APPLICATION_RDF_XML);
            List<Preference<MediaType>> accepted = new LinkedList<Preference<MediaType>>();
            accepted.add(new Preference<MediaType>(MediaType.APPLICATION_RDF_XML));
            cr.getClientInfo().setAcceptedMediaTypes(accepted);

            Status status = cr.getStatus();
            if (status.isError()) {
                LOGGER.error(status.getCode() + " " + status.getDescription());
                return status.getCode() + " " + status.getDescription();
            } else {
                return resp.getText();
            }

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return response;

    }


    public String describeNodeCapability(List<NodeCapability> capabilities) throws NotImplementedException {

        //{
        // "systems":[["http://www.example.org/device/2","http://www.example.org/device/1"]],
        //- "algorithms":[["http://www.example.org/alg/45","http://www.example.org/alg/18"]],
        //- "worn_by":[[ [{"surname":["Hausenblas"],"firstname":["Michael"]}], [{"surname":["Amaxilatis"],"firstname":["Dimitrios"]}] ]],
        //- "owners":[[ [{"surname":["Leggieri"],"firstname":["Myriam"]}], [{"surname":["Hauswirth"],"firstname":["Manfred"]}] ]],
        //+ "author":[{"surname":["Theodoridis"],"firstname":["Evangelos"]}],
        // "location-name":["Patras"], "location-coords":["38.24444_21.73444"]},
        // "start_range":["5800"],
        // "end_range":["10321"],
        // }

        String response = null;
        try {
            JSONObject json = new JSONObject();

            json.put("author", generateAuthorDetails());

            JSONArray vals = new JSONArray();

            for (NodeCapability capability : capabilities) {
                vals.put("http://uberdust.cti.gr/rest/testbed/"
                        + capability.getNode().getSetup().getId()
                        + "/node/" + capability.getNode().getName()
                        + "/capability/" + capability.getCapability().getName()
                        + "/rdf/rdf+xml"
                );
            }
            json.put("tpproperties", wrapValue(
                    vals
            ));

//            json.put("algorithms", new JSONArray(new JsonArray()));
//            json.put("owners", new JSONArray(new JsonArray()));
//            json.put("worn_by", new JSONArray(new JsonArray()));
//            json.put("location-name", wrapValue("Patras"));
            json.put("base_datetime", wrapValue("0"));
            json.put("base_name", wrapValue(""));
//            json.put("end_range", wrapValue("0"));


//            json.put("location-coords", generateSensorCoordinates(capabilities.get(0).getNode().getSetup().getOrigin().getX(), capabilities.get(0).getNode().getSetup().getOrigin().getY()));

            LOGGER.info(json.toString());


            ClientResource cr = new ClientResource("http://localhost:8182/ld4s/platform/");
            Representation resp = cr.post(json);
            List<Preference<MediaType>> accepted = new LinkedList<Preference<MediaType>>();
            accepted.add(new Preference<MediaType>(MediaType.APPLICATION_RDF_XML));
            cr.getClientInfo().setAcceptedMediaTypes(accepted);

            Status status = cr.getStatus();
            if (status.isError()) {
                LOGGER.error(status.getCode() + " " + status.getDescription());
                return status.getCode() + " " + status.getDescription();
            } else {
                return resp.getText();
            }

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return response;

    }

    private JSONArray generateSensorCoordinates(final Float x, final Float y) {
        JSONArray coordinates = wrapValue(x + "_" + y);
        return coordinates;
    }

    private JSONArray generateAuthorDetails() throws JSONException {

        JSONArray authorDetails = new JSONArray();
        JSONObject surnameobj = new JSONObject();
        surnameobj.put("surname", wrapValue("Chatzigiannakis"));
        JSONObject nameobj = new JSONObject();
        nameobj.put("firstname", wrapValue("Ioannis"));

        authorDetails.put(surnameobj);
        authorDetails.put(nameobj);
        return authorDetails;
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
        wisemlNode.setId("sad");


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
            wisemlCapability.setUnit(convert(capability.getUnit()));
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


    private String convert(final String input) {
        if (conversionMap.containsKey(input) == true) {
            return conversionMap.get(input);
        } else {
            return input;
        }

    }

    @Override
    public String formatVirtualNodes(List<Node> nodes) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
