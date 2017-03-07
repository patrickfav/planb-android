package at.favre.lib.planb.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import at.favre.lib.planb.exceptions.MockRuntimeException;

/**
 * Util for handling crashes and exceptions
 */
public class CrashUtil {

    /**
     * Throws a mock runtime exception for testing
     */
    public static void crash() {
        throw new MockRuntimeException();
    }

    /**
     * Prints the stacktrace of a throwable to a string
     *
     * @param t
     * @return the stacktrace as string
     */
    public static String printStacktrace(Throwable t) {
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
