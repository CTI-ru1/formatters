package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeCapabilityControllerImpl;
import eu.wisebed.wisedb.controller.NodeControllerImpl;
import eu.wisebed.wisedb.controller.SetupControllerImpl;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatNodeCapabilities {
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
        final Setup setup = SetupControllerImpl.getInstance().getByID(1);
        Node node = NodeControllerImpl.getInstance().getByName("urn:wisebed:ctitestbed:virtual:0.I.9");
        final List<NodeCapability> capabilities = NodeCapabilityControllerImpl.getInstance().list(node);
        LOGGER.info(HtmlFormatter.getInstance().describeNodeCapabilities(capabilities));

    }
}

