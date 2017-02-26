package at.favre.app.planb.demo.exceptions;

import android.support.annotation.NonNull;

import java.util.Map;

import at.favre.lib.planb.interfaces.ICrashExceptionData;

public class MockAdditionalDataException extends RuntimeException implements ICrashExceptionData {
    private final Map<String, String> additionalData;

    public MockAdditionalDataException(String message, Map<String, String> additionalData) {
        super(message);
        this.additionalData = additionalData;
    }

    @NonNull
    @Override
    public Map<String, String> getAdditionalExceptionData() {
        return additionalData;
    }
}
