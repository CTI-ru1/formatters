package eu.uberdust.formatter.exception;

/**
 * Exception that reports a method that is not still implemented.
 *
 * @author amaxilat
 */
public class NotImplementedException extends Exception {
    @Override
    public String getMessage() {
        return "this method is not implemented!";
    }
}

