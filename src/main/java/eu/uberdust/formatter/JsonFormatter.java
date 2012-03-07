package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects to Json Objects.
 *
 * @author amaxilat
 */
public class JsonFormatter implements Formatter {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(JsonFormatter.class);

    /**
     * Singleton Instance.
     */
    private static JsonFormatter instance = new JsonFormatter();
    /**
     * Base Url to use with url links.
     */
    private static String baseUrl = "";

    /**
     * Returns a {@link JsonFormatter} instance.
     *
     * @return the {@link JsonFormatter} instance.
     */
    public static JsonFormatter getInstance() {
        return instance;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public final String formatTestbed(final Testbed testbed)
            throws NotImplementedException {
        LOGGER.info("formatTestbed");
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
    public final String formatNodeReading(final LastNodeReading nodeReading)
            throws NotImplementedException {
        LOGGER.info("formatNodeReading");
        try {
            if (nodeReading == null) {
                return "";
            } else {
                final JSONObject json = new JSONObject();
                json.put("nodeId", nodeReading.getNodeCapability().getNode().getName());
                json.put("capabilityId", nodeReading.getNodeCapability().getCapability().getName());
                final JSONArray jsonArray = new JSONArray();
                // write on the HTTP response
                final JSONObject obj = new JSONObject();

                if (nodeReading.getTimestamp() != null) {
                    obj.put("timestamp", nodeReading.getTimestamp().getTime());
                    obj.put("reading", nodeReading.getReading() == null ? NULL : nodeReading.getReading());
                    obj.put("stringReading", nodeReading.getStringReading() == null ? NULL : nodeReading.getStringReading());
                    jsonArray.put(obj);
                }

                json.put("readings", jsonArray);
                return json.toString();
            }
        } catch (JSONException e) {
            LOGGER.error(e);
            return e.toString();
        }
    }

    @Override
    public final String formatTestbeds(final List<Testbed> testbeds, final Map<String, Long> nodesCount,
                                       final Map<String, Long> linksCount) throws NotImplementedException {
        LOGGER.info("formatTestbeds");

        final JSONArray jsonArray = new JSONArray();
        try {
            final JSONObject obj = new JSONObject();
            for (final Testbed testbed : testbeds) {
                obj.put("testbedId", testbed.getId());
                obj.put("testbedName", testbed.getName());
                jsonArray.put(obj);
            }
        } catch (JSONException e) {
            LOGGER.error(e);
        }
        return jsonArray.toString();
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
        LOGGER.info("formatCapabilities");
        final JSONArray jsonArray = new JSONArray();
        try {
            final JSONObject obj = new JSONObject();
            // iterate over capabilities
            for (final Capability capability : capabilities) {
                obj.put("capabilityName", capability.getName());
                jsonArray.put(obj);
            }
        } catch (JSONException e) {
            LOGGER.error(e);
        }
        return jsonArray.toString();
    }

    @Override
    public final String formatNodes(final List<Node> nodes)
            throws NotImplementedException {
        LOGGER.info("formatNodes");
        final JSONObject json = new JSONObject();
        try {
            final JSONArray jsonArray = new JSONArray();
            // write on the HTTP response
            for (final Node node : nodes) {
                jsonArray.put(node.getName());
            }
            json.put("nodes", jsonArray);
        } catch (JSONException e) {
            LOGGER.error(e);
        }
        return json.toString();
    }

    @Override
    public final String formatNodeReadings(final List<NodeReading> nodeReadings)
            throws NotImplementedException {
        LOGGER.info("formatNodeReadings");
        try {
            if (nodeReadings == null) {
                return "";
            } else {
                final JSONObject json = new JSONObject();
                json.put("nodeId", nodeReadings.get(0).getCapability().getNode().getName());
                json.put("capabilityId", nodeReadings.get(0).getCapability().getCapability().getName());
                final JSONArray jsonArray = new JSONArray();
                // write on the HTTP response
                final JSONObject obj = new JSONObject();
                for (final NodeReading reading : nodeReadings) {
                    if (reading.getTimestamp() != null) {
                        obj.put("timestamp", reading.getTimestamp().getTime());
                        obj.put("reading", reading.getReading() == null ? NULL : reading.getReading());
                        obj.put("stringReading", reading.getStringReading() == null ? NULL : reading.getStringReading());
                        jsonArray.put(obj);
                    }
                }
                json.put("readings", jsonArray);
                return json.toString();
            }
        } catch (JSONException e) {
            LOGGER.error(e);
            return e.toString();
        }
    }

    @Override
    public String formatLastNodeReadings(List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities)
            throws NotImplementedException {
        LOGGER.info("formatUniqueLastNodeReadings");
        final JSONObject allRooms = new JSONObject();

        final Map<String, Integer> uniqueReadings = new HashMap<String, Integer>();

        for (final NodeCapability nodeCapability : nodeCapabilities) {
            uniqueReadings.put(nodeCapability.getLastNodeReading().getStringReading(), 1);
        }

        try {
            for (final String room : uniqueReadings.keySet()) {
                final JSONArray nodesInRoom = new JSONArray();
                for (final NodeCapability nodeCapability : nodeCapabilities) {
                    if (nodeCapability.getLastNodeReading().getStringReading().equals(room)) {
                        nodesInRoom.put(nodeCapability.getNode().getName());
                    }
                }
                allRooms.put(room, nodesInRoom);
            }

        } catch (JSONException e) {
            LOGGER.error(e);
        }
        return allRooms.toString();
    }

    @Override
    public final String formatLinks(final List<Link> links)
            throws NotImplementedException {
        LOGGER.info("formatLinks");
        final JSONObject json = new JSONObject();

        try {
            final JSONArray jsonArray = new JSONArray();

            // write on the HTTP response
            for (final Link link : links) {
                final JSONObject linkJson = new JSONObject();
                linkJson.put("linkSource", link.getSource().getName());
                linkJson.put("linkTarget", link.getTarget().getName());
                jsonArray.put(linkJson);
            }

            json.put("links", jsonArray);
        } catch (JSONException e) {
            LOGGER.error(e);
        }
        return json.toString();
    }

    @Override
    public final String formatLastReadings(final List<LastNodeReading> lastNodeReadings,
                                           final List<LastLinkReading> lastLinkReadings)
            throws NotImplementedException {
        LOGGER.info("formatLastReadings");
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
                                        final Map<Node, Origin> originMap)
            throws NotImplementedException {
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
