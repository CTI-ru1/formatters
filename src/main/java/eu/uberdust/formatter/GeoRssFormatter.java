package eu.uberdust.formatter;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/23/12
 * Time: 10:06 PM
 */
public class GeoRssFormatter {

    private static GeoRssFormatter instance = null;

    public static GeoRssFormatter getInstance() {
        if (instance == null) {
            instance = new GeoRssFormatter();
        }
        return instance;
    }


}
