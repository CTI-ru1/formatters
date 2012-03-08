package eu.uberdust.formatter.test;

import eu.uberdust.formatter.HtmlFormatter;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeReadingControllerImpl;
import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetNodeReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetNodeReading.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            final List<NodeReading> readings = NodeReadingControllerImpl.getInstance().list();
            if (readings != null) {

                LOGGER.info(HtmlFormatter.getInstance().formatNodeReadings(readings));

            } else {
                LOGGER.error("readings list is null");
            }
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
