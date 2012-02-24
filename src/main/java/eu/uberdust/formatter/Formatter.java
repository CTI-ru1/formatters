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

}
