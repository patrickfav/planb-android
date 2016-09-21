package at.favre.lib.planb.exceptions;

import android.os.Parcelable;

import java.util.Map;

public interface ICrashExceptionData {

    Map<String,String> getAdditionalExceptionData();
}
