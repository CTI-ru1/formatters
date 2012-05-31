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
import eu.wisebed.wisedb.model.NodeCapability;
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
public class ShowTestbedStatusNodesControllerTest {
    private static Logger LOGGER = Logger.getLogger(FormatLastLinkReadings.class);
    private static TestbedController testbedManager;
    private static NodeCapabilityController nodeCapabilityManager;
    private static LinkCapabilityController linkCapabilityManager;


    public static void main(final String[] argv) {
        System.out.println("started");

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        System.out.println("connected");
        try {
            testbedManager = TestbedControllerImpl.getInstance();
            nodeCapabilityManager = NodeCapabilityControllerImpl.getInstance();
            linkCapabilityManager = LinkCapabilityControllerImpl.getInstance();
            System.out.println("getInstance");
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

        System.out.println("got testbed " + testbed);

        // get a list of link statistics from testbed
        final List<NodeCapability> nodeCapabilities= nodeCapabilityManager.list(testbed.getSetup());

        final String response = HtmlFormatter.getInstance().formatLastNodeReadings(nodeCapabilities);
        System.out.println(response);
    }
}

