package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:13 PM
 */
public interface Formatter {
    static final String TAB = "\t";
    static final String NEW_LINE = "\n";
    static final String NULL = "null";


    public String formatTestbed(final Testbed testbed) throws NotImplementedException;

    public String formatNodeReading(final LastNodeReading nodeReading) throws NotImplementedException;

    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException;

    public String formatCapabilities(final List<Capability> capabilities) throws NotImplementedException;

    public String formatNodes(final List<Node> nodes) throws NotImplementedException;

    public String formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException;

    public String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities) throws NotImplementedException;

    public String formatLinks(final List<Link> links) throws NotImplementedException;

    public String formatLastReadings(final List<LastNodeReading> lastNodeReadings, final List<LastLinkReading> lastLinkReadings) throws NotImplementedException;

    public String describeNode(final Node node, final String requestURL, final String requestURI,
                               final String nodeDescription, final Position nodePos) throws NotImplementedException;

    public String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                  final List<Node> nodes, final Map<Node, String> descriptionMap,
                                  final Map<Node, List<NodeCapability>> capabilityMap,
                                  final Map<Node, Origin> originMap) throws NotImplementedException;

}
