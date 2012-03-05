package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Implements the {@link Formatter} Interface and converts Wisedb Objects to Html.
 *
 * @author amaxilat
 */
public class HtmlFormatter implements Formatter {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(HtmlFormatter.class);
    /**
     * Singleton Instance.
     */
    private static HtmlFormatter instance = new HtmlFormatter();
    /**
     * Base Url to use with url links.
     */
    private static String baseUrl = "";
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
    public void setBaseUrl(final String baseUrl) {
        LOGGER.debug("baseUrl=" + baseUrl);
        this.baseUrl = baseUrl;
    }

    public HtmlFormatter() {
        LOGGER.debug("new instance");
    }

    @Override
    public final String formatTestbed(final Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }


    @Override
    public String formatNode(final Node node) throws NotImplementedException {

        LOGGER.info("formatNode");

        final StringBuilder output = new StringBuilder();
        output.append(S_TABLE);
        output.append(S_ROW).append(tdCell("Node ID")).append(tdCell(urlLink(
                "/rest/testbed/" + node.getSetup().getId() + "/node/" + node.getName()
                , node.getName()
        ))).append(E_ROW);
        output.append(S_ROW).append(tdCell("GeoRSS Feed"))
                .append(tdCell(urlLink("/rest/testbed/" + node.getSetup().getId() + "/node/" + node.getName() + "/georss"
                        , "GeoRSS Feed"
                )))
                .append(tdCell("<a href='http://maps.google.com/maps?q="
                        + baseUrl
                        + "/rest/testbed/" + node.getSetup().getId()
                        + "/node/" + node.getName()
                        + "/georss' > View on Google Maps </a>"
                )).append(E_ROW);
        output.append(S_ROW).append(tdCell("KML Feed"))
                .append(tdCell(urlLink("/rest/testbed/" + node.getSetup().getId() + "/node/" + node.getName() + "/kml"
                        , "Kml Feed"
                )))
                .append(tdCell("<a href='http://maps.google.com/maps?q="
                        + baseUrl
                        + "/rest/testbed/" + node.getSetup().getId()
                        + "/node/" + node.getName()
                        + "/kml' > View on Google Maps </a>"
                )).append(E_ROW);
        output.append(S_ROW).append(tdCell("Rdf description"))
                .append(tdCell(urlLink("/rest/testbed/" + node.getSetup().getId() + "/node/" + node.getName() + "/rdf"
                        , "Rdf Description"
                ))).append(E_ROW);


        output.append(E_TABLE);

        return output.toString();
    }

    @Override
    public String formatLink(final Link link) throws NotImplementedException {
        final StringBuilder output = new StringBuilder();
        output.append(S_TABLE);
        output.append(S_ROW).append(tdCell(urlLink(
                "/rest/testbed/" + link.getSetup().getId()
                        + "/link/" + link.getSource().getName() + "/" + link.getTarget().getName()
                , link.getSource().getName() + "," + link.getTarget().getName()
        )));
        output.append(E_ROW);
        output.append(S_ROW).append(tdCell("Source ID")).append(tdCell(urlLink(
                "/rest/testbed/" + link.getSource().getSetup().getId() + "/node/" + link.getSource().getName()
                , link.getSource().getName())
        )).append(E_ROW);
        output.append(S_ROW).append(tdCell("Target ID")).append(tdCell(urlLink(
                "/rest/testbed/" + link.getSource().getSetup().getId() + "/node/" + link.getTarget().getName()
                , link.getTarget().getName())
        )).append(E_ROW);
        output.append(E_TABLE);
        return output.toString();
    }

    @Override
    public final String formatCapability(final Testbed testbed, final Capability capability)
            throws NotImplementedException {
        final StringBuilder output = new StringBuilder();
        output.append(S_TABLE);
        output.append(S_ROW).append(headerCell("Capability ID"));
        output.append(headerCell(urlLink(
                "/rest/testbed/" + testbed.getId() + "/capability/" + capability.getName(),
                capability.getName()
        ))).append(E_ROW);

        output.append(S_ROW).append(tdCell("Capability Semantic Description"));
        output.append(tdCell(capability.getDescription())).append(E_ROW);
        output.append(S_ROW).append(headerCell("List All Readings for the capability in:")).append(E_ROW);

        output.append(S_ROW).append(tdCell(urlLink(
                "/rest/testbed/" + testbed.getId() + "/capability/" + capability.getName() + "/tabdelimited"
                , "Tab Delimited Format"
        ))).append(E_ROW);


        return output.toString();
    }

    @Override
    public final String formatTestbeds(final List<Testbed> testbeds, final Map<String, Long> nodesCount,
                                       final Map<String, Long> linksCount) throws NotImplementedException {
        final StringBuilder output = new StringBuilder();

        output.append("<p> Available Testbeds: (" + testbeds.size() + ") </p>");

        output.append(S_TABLE);

        int count = 1;
        for (final Testbed testbed : testbeds) {
            output.append(S_ROW).append(tdCell(String.valueOf(count)));
            output.append(tdCell(urlLink(
                    "/rest/testbed/" + testbed.getId()
                    , testbed.getName()
            )));
            output.append(tdCell("Nodes (" + nodesCount.get(testbed.getName()) + ")"));
            output.append(tdCell("Links (" + linksCount.get(testbed.getName()) + ")"));
            output.append(E_ROW);
            count++;

        }
        output.append(E_TABLE);
        return output.toString();
    }

    @Override
    public final String formatNodeReadings(final List<NodeReading> nodeReadings) {
        final StringBuilder output = new StringBuilder();

        output.append("<table id=\"information\"").append(S_ROW);
        output.append(headerCell("Timestamp"));
        output.append(headerCell("Double Reading"));
        output.append(headerCell("String Reading"));
        output.append(E_ROW);

        for (final NodeReading nr : nodeReadings) {
            output.append(S_ROW);
            output.append(tdCell(nr.getTimestamp().toString()));
            output.append(tdCell(nr.getReading().toString()));
            output.append(tdCell(nr.getStringReading()));
            output.append(E_ROW);
        }

        output.append(E_TABLE);
        return output.toString();
    }

    @Override
    public final String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String describeNodeCapabilities(final List<NodeCapability> capabilities) throws NotImplementedException {
        if (capabilities.isEmpty()) {
            return "<p style=\"color : red\">No capabilities found!</p>";
        } else {
            final StringBuilder output = new StringBuilder();
            output.append(S_TABLE);
            output.append(S_ROW).append(headerCell("Capabilities (" + capabilities.size() + ")"));
            final StringBuilder innerTable = new StringBuilder();
            innerTable.append(S_TABLE);
            for (final NodeCapability nCap : capabilities) {
                innerTable.append(S_ROW);
                innerTable.append(tdCell(urlLink(
                        "/rest/testbed/" + nCap.getNode().getSetup().getId()
                                + "/capability/" + nCap.getCapability().getName()
                        , nCap.getCapability().getName()
                )));
                final String basicUrl = "/rest/testbed/" + nCap.getNode().getSetup().getId()
                        + "/node/" + nCap.getNode().getName()
                        + "/capability/" + nCap.getCapability().getName();
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/html/limit/10"
                        , "HTML")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/tabdelimited/limit/10"
                        , "Tab Delimited")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/json/limit/10"
                        , "JSON")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/wiseml/limit/10"
                        , "WiseML")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/latestreading"
                        , "Last Reading Tab")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/latestreading/json"
                        , "Last Reading JSON")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/chart/limit/10"
                        , "Chart")));
                innerTable.append(tdCell(urlLink(
                        basicUrl + "/live"
                        , "Live")));
                innerTable.append(E_ROW);
            }
            innerTable.append(E_TABLE);
            output.append(tdCell(innerTable.toString())).append(E_ROW);
            output.append(E_TABLE);
            return output.toString();
        }
    }

    @Override
    public final String formatLinkCapabilities(final List<LinkCapability> linkCapabilities) throws NotImplementedException {
        if (linkCapabilities.isEmpty()) {
            return "<p style=\"color : red\">No capabilities found!</p>";
        } else {
            final StringBuilder output = new StringBuilder();
            output.append(S_TABLE);
            output.append(S_ROW).append(headerCell("Capabilities"));
            output.append(headerCell(String.valueOf(linkCapabilities.size()))).append(E_ROW);
            for (final LinkCapability capability : linkCapabilities) {

                output.append(S_ROW);
                output.append(tdCell(urlLink(
                        "/rest/testbed/" + capability.getLink().getSetup().getId()
                                + "/capability/"
                                + capability.getCapability().getName()
                        , capability.getCapability().getName())));
                output.append(E_ROW);
            }
            output.append(E_TABLE);
            return output.toString();
        }
    }

    @Override
    public final String formatCapabilities(final Testbed testbed, final List<Capability> capabilities)
            throws NotImplementedException {
        if (capabilities.isEmpty()) {
            return "<p style=\"color : red\">No capabilities found!</p>";
        } else {
            final StringBuilder output = new StringBuilder();
            output.append(S_TABLE);
            output.append(S_ROW).append(tdCell(
                    urlLink("/rest/testbed/" + testbed.getSetup().getId() + "/node", "Capabilities")
                            + " (" + capabilities.size() + ") ["
                            + urlLink("/rest/testbed/" + testbed.getSetup().getId() + "/capability/raw", "raw") + ", "
                            + urlLink("/rest/testbed/" + testbed.getSetup().getId() + "/capability/json", "json")
                            + "]"

            )).append(E_ROW);
            for (final Capability capability : capabilities) {

                output.append(S_ROW);
                output.append(tdCell(urlLink(
                        "/rest/testbed/" + testbed.getId() + "/capability/" + capability.getName()
                        , capability.getName())));
                output.append(E_ROW);
            }
            output.append(E_TABLE);
            return output.toString();
        }
    }


    @Override
    public final String formatNodes(final List<Node> nodes) throws NotImplementedException {
        LOGGER.info("formatNodes");


        final StringBuilder output = new StringBuilder();
        output.append(S_TABLE);
        if (nodes.isEmpty()) {
            return "<p style=\"color : red\">No nodes found!</p>";
        } else {
            output.append(S_ROW).append(tdCell(
                    urlLink("/rest/testbed/" + nodes.get(0).getSetup().getId() + "/node", "Nodes")
                            + " (" + nodes.size() + ") ["
                            + urlLink("/rest/testbed/" + nodes.get(0).getSetup().getId() + "/node/raw", "raw") + ", "
                            + urlLink("/rest/testbed/" + nodes.get(0).getSetup().getId() + "/node/json", "json")
                            + "]"

            )).append(E_ROW);
            for (final Node node : nodes) {
                output.append(S_ROW).append(tdCell(urlLink(
                        "/rest/testbed/" + node.getSetup().getTestbed().getId() + "/node/" + node.getName()
                        , node.getName()
                ))).append(E_ROW);
            }
        }
        output.append(E_TABLE);
        return output.toString();
    }


    @Override
    public final String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String formatLinks(final List<Link> links) throws NotImplementedException {
        final StringBuilder output = new StringBuilder();
        output.append(S_TABLE);
        if (links.isEmpty()) {
            return "<p style=\"color : red\">No links found!</p>";
        } else {
            output.append(S_ROW).append(tdCell(
                    urlLink("/rest/testbed/" + links.get(0).getSetup().getId() + "/link", "Links")
                            + " (" + links.size() + ") ["
                            + urlLink("/rest/testbed/" + links.get(0).getSetup().getId() + "/link/raw", "raw") + ", "
                            + urlLink("/rest/testbed/" + links.get(0).getSetup().getId() + "/link/json", "json")
                            + "]"

            )).append(E_ROW);
            for (final Link link : links) {
                output.append(S_ROW).append(tdCell(urlLink(
                        "/rest/testbed/" + link.getSetup().getTestbed().getId() + "/link/"
                                + link.getSource().getName() + "/" + link.getTarget().getName()
                        , "[" + link.getSource().getName() + "," + link.getTarget().getName() + "]"
                ))).append(E_ROW);
            }
        }
        output.append(E_TABLE);
        return output.toString();
    }

    @Override
    public final String formatLastReadings(final List<LastNodeReading> lastNodeReadings,
                                           final List<LastLinkReading> lastLinkReadings)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String describeNode(final Node node, final String requestURL, final String requestURI,
                                     final String nodeDescription, final Position nodePos)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                        final List<Node> nodes, final Map<Node, String> descriptionMap,
                                        final Map<Node, List<NodeCapability>> capabilityMap,
                                        final Map<Node, Origin> originMap) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public final String showTestbed(final Testbed testbed, List<Node> nodes, final List<Link> links,
                                    final List<Capability> capabilities) throws NotImplementedException {
        final StringBuilder output = new StringBuilder();
        output.append(S_TABLE);

        output.append(S_ROW).append(tdCell("Testbed ID"))
                .append(tdCell(String.valueOf(testbed.getId()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed Description"))
                .append(tdCell(String.valueOf(testbed.getDescription()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed Name"))
                .append(tdCell(String.valueOf(testbed.getName()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed URN prefix"))
                .append(tdCell(String.valueOf(testbed.getUrnPrefix()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed Timezone"))
                .append(tdCell(String.valueOf(testbed.getTimeZone().getDisplayName()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed URL"))
                .append(tdCell(String.valueOf(testbed.getUrl()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed SNAA URL"))
                .append(tdCell(String.valueOf(testbed.getSnaaUrl()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed RS URL"))
                .append(tdCell(String.valueOf(testbed.getRsUrl()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed Session Management URL "))
                .append(tdCell(String.valueOf(testbed.getSessionUrl()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed Federated Testbed"))
                .append(tdCell(String.valueOf(testbed.getFederated()))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed Status Page"))
                .append(tdCell(urlLink("/rest/testbed/" + testbed.getId() + "/status", "status page"))).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed GeoRSS feed"))
                .append(tdCell(urlLink(
                        "/rest/testbed/" + testbed.getId() + "/georss"
                        , "GeoRss Feed")
                        + "   <a href='http://maps.google.com/maps?q="
                        + baseUrl + "/rest/testbed/" + testbed.getId() + "/georss"
                        + "'>View On Google Maps</a>"
                )).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed KML feed"))
                .append(tdCell(urlLink(
                        "/rest/testbed/" + testbed.getId() + "/kml"
                        , "Kml Feed")
                        + "   <a href='http://maps.google.com/maps?q="
                        + baseUrl + "/rest/testbed/" + testbed.getId() + "/kml"
                        + "'>View On Google Maps</a>"
                )).append(E_ROW);
        output.append(S_ROW).append(tdCell("Testbed WiseML"))
                .append(tdCell(urlLink("/rest/testbed/" + testbed.getId() + "/wiseml", "WiseML"))).append(E_ROW);

        output.append(E_TABLE);
        output.append(S_TABLE);
        output.append(S_ROW);
        output.append("<td style=\"vertical-align:top\"> " + formatNodes(nodes) + "</td>");
        output.append("<td style=\"vertical-align:top\"> " + formatLinks(links) + "</td>");
        output.append("<td style=\"vertical-align:top\"> " + formatCapabilities(testbed, capabilities) + "</td>");
        output.append(E_ROW);
        output.append(E_TABLE);

        return output.toString();
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
    private String tdCell(final String contents) {
        final StringBuilder output = new StringBuilder();
        output.append(S_TD).append(contents).append(E_TD);
        return output.toString();
    }

    /**
     * Creates an HTML link tag.
     *
     * @param url  the url to point to.
     * @param name the text to display.
     * @return the complete tag.
     */
    private String urlLink(String url, String name) {
        final StringBuilder output = new StringBuilder();
        output.append("<a href='").append(baseUrl).append(url).append("'>").append(name).append("</a>");
        return output.toString();
    }

}
