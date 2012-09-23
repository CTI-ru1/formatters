package eu.uberdust.formatter.test.rdf;


import eu.uberdust.formatter.RdfFormatter;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatNodeReading {
    private static Logger LOGGER = Logger.getLogger(FormatNodeReading.class);

    public static void main(final String[] argv) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            final Node node = NodeControllerImpl.getInstance().getByName("urn:wisebed:ctitestbed:0x712");
            Capability capability = CapabilityControllerImpl.getInstance().getByID("urn:wisebed:node:capability:light");
            List<NodeReading> capabilities = NodeReadingControllerImpl.getInstance().listNodeReadings(node, capability, 1);
//            Capability roomCapability = CapabilityControllerImpl.getInstance().getByID("room");
//            capabilities.add(NodeReadingControllerImpl.getInstance().listNodeReadings(node, roomCapability, 1).get(0));

            String output = null;
            output = (String) RdfFormatter.getInstance().formatNodeReadings(capabilities);

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

