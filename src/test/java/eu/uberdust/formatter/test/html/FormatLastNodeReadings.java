package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import org.apache.log4j.BasicConfigurator;
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
public class FormatLastNodeReadings {
    private static Logger LOGGER = Logger.getLogger(FormatLastNodeReadings.class);

    public static void main(final String[] argv) {

        BasicConfigurator.configure();

        try {

            final List<NodeCapability> readings = new ArrayList<NodeCapability>();

            final Node node1 = new Node();
            node1.setId(1);
            node1.setName("1");
            final Node node2 = new Node();
            node2.setId(2);
            node2.setName("2");
            final Node node3 = new Node();
            node3.setId(3);
            node3.setName("3");


            for (int i = 0; i < 4; i++) {
                Capability capability = new Capability();
                capability.setName("cap" + i);
                NodeCapability cap = new NodeCapability();
                cap.setCapability(capability);
                cap.setNode(node1);


                LastNodeReading reading = new LastNodeReading();
                reading.setTimestamp(new Timestamp(i * 1000));
                reading.setReading((double) i);
                reading.setStringReading("abc");
                reading.setNodeCapability(cap);

                cap.setLastNodeReading(reading);

                readings.add(cap);
            }

            for (int i = 0; i < 5; i++) {
                Capability capability = new Capability();
                capability.setName("cap" + i);
                NodeCapability cap = new NodeCapability();
                cap.setCapability(capability);
                cap.setNode(node2);


                LastNodeReading reading = new LastNodeReading();
                reading.setTimestamp(new Timestamp(System.currentTimeMillis()));
                reading.setReading((double) i);
                reading.setStringReading("abc");
                reading.setNodeCapability(cap);

                cap.setLastNodeReading(reading);

                readings.add(cap);
            }

            for (int i = 0; i < 3; i++) {
                Capability capability = new Capability();
                capability.setName("cap" + i);
                NodeCapability cap = new NodeCapability();
                cap.setCapability(capability);
                cap.setNode(node3);


                LastNodeReading reading = new LastNodeReading();
                reading.setTimestamp(new Timestamp(i * 1000));
                reading.setReading((double) i);
                reading.setStringReading("abc");
                reading.setNodeCapability(cap);

                cap.setLastNodeReading(reading);

                readings.add(cap);
            }
            for (int i = 4; i < 6; i++) {
                Capability capability = new Capability();
                capability.setName("cap" + i);
                NodeCapability cap = new NodeCapability();
                cap.setCapability(capability);
                cap.setNode(node3);


                LastNodeReading reading = new LastNodeReading();
                reading.setTimestamp(new Timestamp(System.currentTimeMillis()));
                reading.setReading((double) i);
                reading.setStringReading("abc");
                reading.setNodeCapability(cap);

                cap.setLastNodeReading(reading);

                readings.add(cap);
            }

            String output = HtmlFormatter.getInstance().formatLastNodeReadings(readings);

            System.out.println(output);


        } catch (Exception e) {
            LOGGER.fatal(e);
            System.exit(-1);
        }

    }
}

