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
 * An interface that contains all possible transformations of wisedb objects.
 *
 * @author amaxilat
 */
public interface Formatter {
    /**
     * the TAB literal.
     */
    final String TAB = "\t";
    /**
     * the NEW LINE literal.
     */
    final String NEW_LINE = "\n";
    /**
     * the NULL literal.
     */
    final String NULL = "null";

    /**
     * Formats a given {@link Testbed} object to the target format.
     *
     * @param testbed the {@link Testbed} to format.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatTestbed(final Testbed testbed) throws NotImplementedException;

    /**
     * Formats a given {@link LastNodeReading} object to the target format.
     *
     * @param lastNodeReading a {@link LastNodeReading} object.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatNodeReading(final LastNodeReading lastNodeReading) throws NotImplementedException;

    /**
     * Formats a given List of {@link Testbed} objects to the target format.
     *
     * @param testbeds a List of {@link Testbed} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatTestbeds(final List<Testbed> testbeds) throws NotImplementedException;

    /**
     * Formats a given List of {@link Capability} objects to the target format.
     *
     * @param capabilities a list of {@link Capability} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatCapabilities(final List<Capability> capabilities) throws NotImplementedException;

    /**
     * Formats a given List of {@link Node} objects to the target format.
     *
     * @param nodes a list of {@link Node} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatNodes(final List<Node> nodes) throws NotImplementedException;

    /**
     * Formats a given List of {@link NodeReading} objects to the target format.
     *
     * @param nodeReadings a list of {@link NodeReading} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatNodeReadings(final List<NodeReading> nodeReadings) throws NotImplementedException;

    /**
     * Formats a given List of {@link NodeCapability} objects to the target format.
     *
     * @param nodeCapabilities a List of {@link NodeCapability} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatUniqueLastNodeReadings(final List<NodeCapability> nodeCapabilities) throws NotImplementedException;

    /**
     * Formats a given List of {@link Link} objects to the target format.
     *
     * @param links a List of {@link Link} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatLinks(final List<Link> links) throws NotImplementedException;

    /**
     * Formats the given Lists of {@link LastNodeReading} and {@link LastLinkReading} objects to the target format.
     *
     * @param lastNodeReadings a List of {@link LastNodeReading} objects.
     * @param lastLinkReadings a List of {@link LastLinkReading} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String formatLastReadings(final List<LastNodeReading> lastNodeReadings, final List<LastLinkReading> lastLinkReadings) throws NotImplementedException;

    /**
     * Returns a description of the {@link Node} object in the target format.
     *
     * @param node            the {@link Node} objectto describe.
     * @param requestURL      the original request url.
     * @param requestURI      the original request uri.
     * @param nodeDescription the Description String of the {@link Node} object.
     * @param nodePos         the position of the {@link Node} object.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String describeNode(final Node node, final String requestURL, final String requestURI,
                               final String nodeDescription, final Position nodePos) throws NotImplementedException;

    /**
     * Returns a desctiption of the {@link Testbed} object in the target format.
     *
     * @param testbed        the {@link Testbed} object object to describe.
     * @param requestURL     the original request url.
     * @param requestURI     the original request uri.
     * @param nodes          the {@link Node} object that belong to the {@link Testbed}.
     * @param descriptionMap a map with the Description String of the {@link Node} objects.
     * @param capabilityMap  a map with the {@link NodeCapability} objects of the {@link Node} objects.
     * @param originMap      a map with the {@link Origin} objects of the {@link Node} objects.
     * @return a string containing the formatted object.
     * @throws NotImplementedException when not implemented the function informs the handler.
     */
    public String describeTestbed(final Testbed testbed, final String requestURL, final String requestURI,
                                  final List<Node> nodes, final Map<Node, String> descriptionMap,
                                  final Map<Node, List<NodeCapability>> capabilityMap,
                                  final Map<Node, Origin> originMap) throws NotImplementedException;

}
