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
public class RdfFormatter implements Formatter {

    private static RdfFormatter instance = null;

    public static RdfFormatter getInstance() {
        if (instance == null) {
            instance = new RdfFormatter();
        }
        return instance;
    }

    public RdfFormatter() {

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
