package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.NodeCapability;
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
        LOGGER.info("showTestbedStatusController(...)");

        final long start = System.currentTimeMillis();

        long start1 = System.currentTimeMillis();

        // a specific testbed is requested by testbed Id
        int testbedId = 4;

        LOGGER.info("--------- Get Testbed id: " + (System.currentTimeMillis() - start1));
        start1 = System.currentTimeMillis();

        // look up testbed
        final Testbed testbed = TestbedControllerImpl.getInstance().getByID(testbedId);
        
        LOGGER.info("got testbed " + testbed);

        LOGGER.info("--------- Get Testbed: " + (System.currentTimeMillis() - start1));



        start1 = System.currentTimeMillis();
        // get a list of node last readings from testbed
        final List<NodeCapability> nodeCapabilities = NodeCapabilityControllerImpl.getInstance().list(testbed.getSetup());
        LOGGER.info("--------- list nodeCapabilities: " + (System.currentTimeMillis() - start1));

        start1 = System.currentTimeMillis();
        String nodeCaps;
        try {
            nodeCaps = HtmlFormatter.getInstance().formatLastNodeReadings(nodeCapabilities);
        } catch (NotImplementedException e) {
            nodeCaps = "";
        }
        LOGGER.info("--------- format last node readings: " + (System.currentTimeMillis() - start1));

        start1 = System.currentTimeMillis();
        // get a list of link statistics from testbed
        final List<LinkCapability> linkCapabilities = LinkCapabilityControllerImpl.getInstance().list(testbed.getSetup());
        LOGGER.info("--------- List link capabilities: " + (System.currentTimeMillis() - start1));


        // Prepare data to pass to jsp
        final Map<String, Object> refData = new HashMap<String, Object>();
        refData.put("testbed", testbed);
        refData.put("lastNodeReadings", nodeCaps);


        try {
            start1 = System.currentTimeMillis();
            refData.put("lastLinkReadings", HtmlFormatter.getInstance().formatLastLinkReadings(linkCapabilities));
            LOGGER.info("--------- format link Capabilites: " + (System.currentTimeMillis() - start1));
        } catch (NotImplementedException e) {
            LOGGER.error(e);
        }
//
//        LOGGER.info("--------- Total time: " + (System.currentTimeMillis() - start));
//        refData.put("time", String.valueOf((System.currentTimeMillis() - start)));
//        LOGGER.info("prepared map");
//


    }
}

