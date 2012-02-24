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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:06 PM
 */
public class JsonFormatter implements Formatter {
    private final static Logger LOGGER = Logger.getLogger(JsonFormatter.class);

    private static JsonFormatter instance = null;

    public static JsonFormatter getInstance() {
        if (instance == null) {
            instance = new JsonFormatter();
        }
        return instance;
    }

    public String formatTestbed(final Testbed testbed) throws NotImplementedException {
        LOGGER.info("formatTestbed");
        throw new NotImplementedException();
    }

    @Override
    public String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        LOGGER.info("formatNodeReading");

        throw new NotImplementedException();
    }

    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException {
        LOGGER.info("formatTestbeds");

        throw new NotImplementedException();
    }

    @Override
    public String formatCapabilities(final List<Capability> capabilities) throws NotImplementedException {
        LOGGER.info("formatCapabilities");
        final JSONArray jsonArray = new JSONArray();
        try {

            // iterate over capabilities
            for (Capability capability : capabilities) {

                JSONObject linkJson = new JSONObject();
                linkJson.put("capabilityName", capability.getName());

                jsonArray.put(linkJson);
            }
        } catch (JSONException e) {
            LOGGER.error(e);
        }
        return jsonArray.toString();
    }

    @Override
    public String formatNodes(final List<Node> nodes) throws NotImplementedException {
        LOGGER.info("formatNodes");
        throw new NotImplementedException();
    }

    public String formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException {
        LOGGER.info("formatNodeReadings");
        throw new NotImplementedException();
    }

    @Override
    public String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        LOGGER.info("formatUniqueLastNodeReadings");
        throw new NotImplementedException();
    }

    @Override
    public String formatLinks(final List<Link> links) throws NotImplementedException {
        LOGGER.info("formatLinks");
        throw new NotImplementedException();
    }

    @Override
    public String formatLastReadings(final List<LastNodeReading> lastNodeReadings,
                                     final List<LastLinkReading> lastLinkReadings) throws NotImplementedException {
        LOGGER.info("formatLastReadings");
        throw new NotImplementedException();
    }
}
