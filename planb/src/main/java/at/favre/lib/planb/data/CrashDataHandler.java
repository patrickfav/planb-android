package at.favre.lib.planb.data;

import java.util.List;


public interface CrashDataHandler {
    CrashData getLatest();

    List<CrashData> getAll();

    boolean hasUnhandledCrash();

    void persistCrashData(CrashData cd);

    void clear();
}
