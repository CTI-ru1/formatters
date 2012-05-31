package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityController;
import eu.wisebed.wisedb.controller.LinkCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeCapabilityController;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class ShowTestbedStatusLinksControllerTest {
    private static Logger LOGGER = Logger.getLogger(FormatLastLinkReadings.class);
    private static NodeCapabilityController nodeCapabilityManager;
    private static TestbedController testbedManager;
    private static LinkCapabilityController linkCapabilityManager;


    public static void main(final String[] argv) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            testbedManager = TestbedControllerImpl.getInstance();
            nodeCapabilityManager = NodeCapabilityControllerImpl.getInstance();
            linkCapabilityManager = LinkCapabilityControllerImpl.getInstance();
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
        // look up testbed
        final Testbed testbed = testbedManager.getByID(1);

        LOGGER.info("got testbed " + testbed);

        // get a list of link statistics from testbed
        final List<LinkCapability> linkCapabilities = linkCapabilityManager.list(testbed.getSetup());

        final String response = HtmlFormatter.getInstance().formatLastLinkReadings(linkCapabilities);
        System.out.println(response);
    }
}

