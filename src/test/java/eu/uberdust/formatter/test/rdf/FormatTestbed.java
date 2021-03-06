package eu.uberdust.formatter.test.rdf;


import com.hp.hpl.jena.rdf.model.Model;
import eu.uberdust.formatter.RdfFormatter;
import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.TestbedControllerImpl;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatTestbed {
    private static Logger LOGGER = Logger.getLogger(FormatTestbed.class);

    public static void main(final String[] argv) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {


            final Testbed testbed= TestbedControllerImpl.getInstance().getByID(1);

            Model output = null;
            output = (Model) RdfFormatter.getInstance().formatTestbed(testbed);

            String answer = "";
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                output.write(bos, "RDF/XML");
                answer = bos.toString();
                System.out.println(answer);
            } catch (Exception e) {
                LOGGER.info("Error in dumping to rdf file: " + e);
            }

            LOGGER.info(answer);


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

