package at.favre.lib.planb.exceptions;

public class MockRuntimeException extends RuntimeException {
    public MockRuntimeException() {
        super("This is a simple mock exception that will be thrown on purpose. This is NOT a bug or error of the app.");
    }
}
