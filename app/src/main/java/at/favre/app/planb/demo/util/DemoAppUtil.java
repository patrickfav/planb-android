package at.favre.app.planb.demo.util;


import java.lang.reflect.MalformedParameterizedTypeException;
import java.nio.BufferOverflowException;
import java.nio.channels.IllegalSelectorException;
import java.nio.charset.IllegalCharsetNameException;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

public class DemoAppUtil {
    public static void throwRandomRuntimeException() {
        String random = "planb:" + UUID.randomUUID().toString();
        RuntimeException[] exceptions = new RuntimeException[]{
                new IllegalStateException("This is a test exception because the sate " + random),
                new IllegalArgumentException("Wrong argument test exception" + random),
                new RuntimeException("This is a test exception " + random),
                new IllegalSelectorException(),
                new IndexOutOfBoundsException("A test index exception " + random),
                new ClassCastException("A test class cast exception " + random),
                new NoSuchElementException("A test no such element exception " + random),
                new MalformedParameterizedTypeException(),
                new BufferOverflowException(),
                new EmptyStackException(),
                new NullPointerException("This is not a real nullpointer " + random),
                new SecurityException("This is not a real security exception " + random),
                new ArithmeticException("This is not a real arithmetic exception " + random),
                new IllegalThreadStateException("This is a test exception with threads " + random),
                new IllegalCharsetNameException("Charset is wrong test exception " + random),
                new IllegalMonitorStateException("This is a test exception with illegal monitor " + random)};

        throw exceptions[new Random().nextInt(exceptions.length)];
    }
}
