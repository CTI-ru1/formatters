package eu.uberdust.formatter.test.html;


import eu.uberdust.formatter.HtmlFormatter;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:16 PM
 */
public class FormatNode {
    private static Logger LOGGER = Logger.getLogger(FormatNode.class);

    public static void main(final String[] argv) {

        try {

            final Node node = new Node();
            node.setId(1);
            node.setName("name");
            node.setSetup(new Setup());

            String output = HtmlFormatter.getInstance().formatNode(node);
            LOGGER.info(output);


        } catch (Exception e) {
            LOGGER.fatal(e);
            System.exit(-1);
        }

    }
}

