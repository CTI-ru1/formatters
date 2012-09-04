package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
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
public class FormatNodeReadings {
    private static Logger LOGGER = Logger.getLogger(FormatNodeCapabilities.class);

    public static void main(final String[] argv) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            runTest();

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

    private static void runTest() throws NotImplementedException {
        final long start = System.currentTimeMillis();
        Node node = NodeControllerImpl.getInstance().getByName("urn:wisebed:ctitestbed:virtual:0.I.1");
        Capability capability = CapabilityControllerImpl.getInstance().getByID("urn:wisebed:node:capability:pir");
        final List<NodeReading> readings = NodeReadingControllerImpl.getInstance().listNodeReadings(node, capability, 10);
        LOGGER.info(HtmlFormatter.getInstance().formatNodeReadings(readings));
        LOGGER.info((System.currentTimeMillis() - start));

    }
}

