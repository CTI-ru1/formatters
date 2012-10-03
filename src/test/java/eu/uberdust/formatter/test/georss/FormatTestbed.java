package eu.uberdust.formatter.test.georss;


import eu.uberdust.formatter.GeoRssFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Position;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatTestbed {
    private static Logger LOGGER = Logger.getLogger(FormatTestbed.class);

    public static void main(final String[] argv) {
        String baseurl = "http://uberdust.cti.gr/rest/testbed/1/node/urn:wisebed:ctitestbed:virtual:room:0.I.2/";


        String newBase = baseurl.replaceAll("rest*", "rest");
        String nodez = baseurl.substring(baseurl.indexOf("/node/"));
        System.out.println(newBase);
        System.out.println(nodez);

        if (true) return;

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // look up testbed
            final Testbed testbed = TestbedControllerImpl.getInstance().getByID(Integer.parseInt("1"));
            if (testbed == null) {
                // if no testbed is found throw exception
            }

            final List<Node> nodes = NodeControllerImpl.getInstance().list(testbed.getSetup());
            final List<Node> nodesList = new ArrayList<Node>();
            final Map<Node, String> descriptionMap = new HashMap<Node, String>();
            final Map<Node, List<NodeCapability>> capabilityMap = new HashMap<Node, List<NodeCapability>>();
            final Map<Node, Position> originMap = new HashMap<Node, Position>();
            for (final Node node : nodes) {
                if (node.getName().contains("virtual")) {
                    continue;
                }
                String desc = null;
                List<NodeCapability> caps;
                Position origins;
                try {
                    desc = NodeControllerImpl.getInstance().getDescription(node);
                    caps = NodeCapabilityControllerImpl.getInstance().list(node);
                    origins = NodeControllerImpl.getInstance().getPosition(node);
                    if ((desc == null) || (caps == null) || (origins == null)) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }

                nodesList.add(node);
                descriptionMap.put(node, desc);
                capabilityMap.put(node, caps);
                originMap.put(node, origins);

            }

            String output = "";

            try {
                output = GeoRssFormatter.getInstance().describeTestbed(testbed,
                        "requestURL",
                        "requestURI",
                        nodesList, descriptionMap, capabilityMap, originMap);
            } catch (NotImplementedException e) {
                output = e.getMessage();
            }
            System.out.println(output);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}

