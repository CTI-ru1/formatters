package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
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
public class ListTestbedsControllerTest {
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
        LOGGER.info("listTestbedsController(...)");


        final long start = System.currentTimeMillis();

        // testbed list
        final List<Testbed> testbeds = TestbedControllerImpl.getInstance().list();

        final Map<String, Long> nodesCount = TestbedControllerImpl.getInstance().countNodes();
        final Map<String, Long> linksCount = TestbedControllerImpl.getInstance().countLinks();


        // Prepare data to pass to jsp
        final Map<String, Object> refData = new HashMap<String, Object>();

        try {
            refData.put("text", HtmlFormatter.getInstance().formatTestbeds(testbeds, nodesCount, linksCount));
        } catch (NotImplementedException e) {
            LOGGER.error(e);
        }

        refData.put("time", String.valueOf((System.currentTimeMillis() - start)));


    }
}

