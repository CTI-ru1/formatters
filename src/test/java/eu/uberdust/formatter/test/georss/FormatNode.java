package eu.uberdust.formatter.test.georss;


import eu.uberdust.formatter.GeoRssFormatter;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatNode {
    private static Logger LOGGER = Logger.getLogger(FormatNode.class);

    public static void main(final String[] argv) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // look up testbed
            final Testbed testbed = TestbedControllerImpl.getInstance().getByID(1);
            if (testbed == null) {
                return;
            }

            final List<Node> nodes = NodeControllerImpl.getInstance().list(testbed.getSetup());
            final Map<Node, String> descriptionMap = new HashMap<Node, String>();
            final Map<Node, List<NodeCapability>> capabilityMap = new HashMap<Node, List<NodeCapability>>();
            final Map<Node, Position> positionMap = new HashMap<Node, Position>();
            for (final Node node : nodes) {
                LOGGER.debug(node);
                descriptionMap.put(node, NodeControllerImpl.getInstance().getDescription(node));
                capabilityMap.put(node, NodeCapabilityControllerImpl.getInstance().list(node));
                positionMap.put(node, NodeControllerImpl.getInstance().getPosition(node));
            }

            String output = "";


            output = GeoRssFormatter.getInstance().describeTestbed(testbed,
                    "test",
                    "test",
                    nodes, descriptionMap, capabilityMap, positionMap);
            LOGGER.info(output);


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

