package at.favre.lib.planb.data;

public interface PersistenceStorage {

    void persistCrashData(CrashData cd);

    void clear();
}
