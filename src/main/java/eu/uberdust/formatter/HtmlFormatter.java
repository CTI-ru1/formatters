package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:06 PM
 */
public class HtmlFormatter implements Formatter {

    private static HtmlFormatter instance = new HtmlFormatter();
    private static final String S_TABLE = "<table>";
    private static final String E_TABLE = "</table>";
    private static final String S_ROW = "<tr>";
    private static final String E_ROW = "</tr>\n";
    private static final String S_TH = "<th>";
    private static final String E_TH = "</th>";
    private static final String S_TD = "<td>";
    private static final String E_TD = "</td>";

    public static HtmlFormatter getInstance() {
        return instance;
    }

    public String formatTestbed(final Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException {
        throw new NotImplementedException();
    }

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

    private String headerCell(final String cont) {
        final StringBuilder output = new StringBuilder();
        output.append(S_TH).append(cont).append(E_TH);
        return output.toString();
    }


    private String simpleCell(final String cont) {
        final StringBuilder output = new StringBuilder();
        output.append(S_TD).append(cont).append(E_TD);
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
}
