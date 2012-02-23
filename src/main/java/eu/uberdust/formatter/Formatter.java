package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Testbed;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:13 PM
 */
public interface Formatter {

    public String format(final Testbed testbed) throws NotImplementedException;

    public String format(final List<Testbed> testbeds) throws NotImplementedException;
}
