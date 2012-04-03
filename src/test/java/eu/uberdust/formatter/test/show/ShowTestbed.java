package eu.uberdust.formatter.test.show;

import eu.uberdust.formatter.HtmlFormatter;
import eu.uberdust.formatter.TextFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.*;
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
 * User: akribopo
 * Date: 4/2/12
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowTestbed {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ShowTestbed.class);


    public static void main(String[] args) {
        LOGGER.info("showTestbedStatusController(...)");

        // a specific testbed is requested by testbed Id
        int testbedId = 2;

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        LOGGER.info("showTestbedStatusController(...)");

        final long start = System.currentTimeMillis();

        long start1 = System.currentTimeMillis();


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
            refData.put("lastLinkReadings", HtmlFormatter.getInstance().formatLinkCapabilities(linkCapabilities));
            LOGGER.info("--------- format link Capabilites: " + (System.currentTimeMillis() - start1));
        } catch (NotImplementedException e) {
            LOGGER.error(e);
        }

        refData.put("time", String.valueOf((System.currentTimeMillis() - start)));
        LOGGER.info("--------- " + String.valueOf((System.currentTimeMillis() - start)));
        tx.commit();

    }
}
