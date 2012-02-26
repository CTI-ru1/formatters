package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Origin;
import eu.wisebed.wisedb.model.Position;
import eu.wisebed.wisedb.model.Testbed;

import java.util.List;
import java.util.Map;

/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects to Html.
 *
 * @author amaxilat
 */
public class HtmlFormatter implements Formatter {
    /**
     * Singleton Instance.
     */
    private static HtmlFormatter instance = new HtmlFormatter();
    /**
     * Starting tag for table.
     */
    private static final String S_TABLE = "<table>";
    /**
     * Closing tag for table.
     */
    private static final String E_TABLE = "</table>";
    /**
     * Starting tag for table row.
     */
    private static final String S_ROW = "<tr>";
    /**
     * Closing tag for table row.
     */
    private static final String E_ROW = "</tr>\n";
    /**
     * Starting tag for table header cell.
     */
    private static final String S_TH = "<th>";
    /**
     * Closing tag for table header cell.
     */
    private static final String E_TH = "</th>";
    /**
     * Starting tag for table body cell.
     */
    private static final String S_TD = "<td>";
    /**
     * Closing tag for table body cell.
     */
    private static final String E_TD = "</td>";

    /**
     * Returns a {@link HtmlFormatter} instance.
     *
     * @return the {@link HtmlFormatter} instance.
     */
    public static HtmlFormatter getInstance() {
        return instance;
    }

    @Override
    public String formatTestbed(final Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodeReadings(final List<NodeReading> nodeReadings) {
        final StringBuilder output = new StringBuilder();

        output.append("<table id=\"information\"").append(S_ROW);
        output.append(headerCell("Timestamp"));
        output.append(headerCell("Readings(" + nodeReadings.size() + ")"));
        output.append(E_ROW);

        for (final NodeReading nr : nodeReadings) {
            output.append(S_ROW);
            output.append(simpleCell(nr.getTimestamp().toString()));
            output.append(simpleCell(nr.getReading().toString()));
            output.append(simpleCell(nr.getStringReading()));
            output.append(E_ROW);
        }

        output.append(E_TABLE);
        return output.toString();
    }

    @Override
    public String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        throw new NotImplementedException();
    }


    @Override
    public String formatCapabilities(final List<Capability> capabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatNodes(final List<Node> nodes) throws NotImplementedException {
        throw new NotImplementedException();
    }


    @Override
    public String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLinks(final List<Link> links) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String formatLastReadings(final List<LastNodeReading> lastNodeReadings, final List<LastLinkReading> lastLinkReadings) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeNode(final Node node, final String requestURL, final String requestURI,
                               final String nodeDescription, final Position nodePos) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                  final List<Node> nodes, final Map<Node, String> descriptionMap,
                                  final Map<Node, List<NodeCapability>> capabilityMap,
                                  final Map<Node, Origin> originMap) throws NotImplementedException {
        throw new NotImplementedException();
    }

    /**
     * Creates an HTML table header cell with opening and closing tags.
     *
     * @param contents the text to add inside the cell.
     * @return the HTML complete tag.
     */
    private String headerCell(final String contents) {
        final StringBuilder output = new StringBuilder();
        output.append(S_TH).append(contents).append(E_TH);
        return output.toString();
    }

    /**
     * Creates an HTML table body cell with opening and closing tags.
     *
     * @param contents the text to add inside the cell.
     * @return the HTML complete tag.
     */
    private String simpleCell(final String contents) {
        final StringBuilder output = new StringBuilder();
        output.append(S_TD).append(contents).append(E_TD);
        return output.toString();
    }
}
