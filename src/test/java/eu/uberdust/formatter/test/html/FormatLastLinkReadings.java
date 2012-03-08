package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatLastLinkReadings {
    private static Logger LOGGER = Logger.getLogger(FormatLastLinkReadings.class);

    public static void main(final String[] argv) {

        try {

            final List<LinkCapability> readings = new ArrayList<LinkCapability>();

            final Setup setup = new Setup();
            setup.setId(1);

            final Node node1 = new Node();
            node1.setId(1);
            node1.setName("1");
            node1.setSetup(setup);
            final Node node2 = new Node();
            node2.setId(2);
            node2.setName("2");
            node2.setSetup(setup);

            final Link link = new Link();
            link.setSetup(setup);
            link.setSource(node1);
            link.setTarget(node2);

            for (int i = 0; i < 4; i++) {
                Capability capability = new Capability();
                capability.setName("cap" + i);
                LinkCapability cap = new LinkCapability();
                cap.setCapability(capability);

                cap.setLink(link);


                LastLinkReading reading = new LastLinkReading();
                reading.setTimestamp(new Timestamp(i * 1000));
                reading.setReading((double) i);
                reading.setStringReading("abc");
                reading.setLinkCapability(cap);

                cap.setLastLinkReading(reading);

                readings.add(cap);
            }


            String output = HtmlFormatter.getInstance().formatLastLinkReadings(readings);

            LOGGER.info(output);


        } catch (Exception e) {
            LOGGER.fatal(e);
            e.printStackTrace();
            System.exit(-1);
        }

    }
}

