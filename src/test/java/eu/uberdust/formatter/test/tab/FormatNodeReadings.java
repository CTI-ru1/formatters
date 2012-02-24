package eu.uberdust.formatter.test.tab;


import eu.uberdust.formatter.TextFormatter;
import eu.wisebed.wisedb.model.NodeReading;
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
public class FormatNodeReadings {
    private static Logger LOGGER = Logger.getLogger(FormatNodeReadings.class);

    public static void main(final String[] argv) {

        try {

            final List<NodeReading> readings = new ArrayList<NodeReading>();
            NodeReading reading = new NodeReading();
            for (int i = 0; i < 100; i++) {
                reading.setTimestamp(new Timestamp(i * 1000));
                reading.setReading((double) i);
                reading.setStringReading("abc");
                readings.add(reading);
            }

            final String output = TextFormatter.getInstance().formatNodeReadings(readings);

            LOGGER.info(output);


        } catch (Exception e) {
            LOGGER.fatal(e);
            System.exit(-1);
        }

    }
}
