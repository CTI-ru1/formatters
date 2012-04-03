package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class ShowTestbedStatusControllerTest {
    private static Logger LOGGER = Logger.getLogger(FormatLastLinkReadings.class);

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


        // a specific testbed is requested by testbed Id
        int testbedId;
        try {
            testbedId = 1;

        } catch (NumberFormatException nfe) {

        }

        // look up testbed
        final Testbed testbed = TestbedControllerImpl.getInstance().getByID(1);
        if (testbed == null) {
            // if no testbed is found throw exception
        }


        // get testbed nodes
        List<NodeCapability> nodeReadings = NodeCapabilityControllerImpl.getInstance().list(testbed.getSetup());

        Node node = nodeReadings.get(0).getNode();
        while (true) {
            LOGGER.info("reading");
            nodeReadings = NodeCapabilityControllerImpl.getInstance().list(testbed.getSetup());
            LOGGER.info("read");

            for (NodeCapability nodeReading : nodeReadings) {
                if (nodeReading.getNode().equals(node)) {
                    LOGGER.info(nodeReading.getCapability().getName() + " = " + nodeReading.getLastNodeReading().getReading() + " @ " + nodeReading.getLastNodeReading().getTimestamp());
                } else {
                    break;
                }

            }
            LOGGER.info("==============================");
            try {
                Thread.sleep(5000);
                try {
                    NodeReadingControllerImpl.getInstance().insertReading("urn:wisebed:ctitestbed:0x14d4", "x", (double) 81, null, new Date(System.currentTimeMillis()));
                } catch (UnknownTestbedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }
}

