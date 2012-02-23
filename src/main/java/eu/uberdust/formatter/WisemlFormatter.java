package eu.uberdust.formatter;

import eu.uberdust.formatter.exception.NotImplementedException;
import eu.wisebed.wisedb.model.Testbed;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:06 PM
 */
public class WisemlFormatter implements Formatter {

    private static WisemlFormatter instance = null;

    public static WisemlFormatter getInstance() {
        if (instance == null) {
            instance = new WisemlFormatter();
        }
        return instance;
    }

    public WisemlFormatter() {

    }


    @Override
    public String format(Testbed testbed) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String format(List<Testbed> testbeds) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
