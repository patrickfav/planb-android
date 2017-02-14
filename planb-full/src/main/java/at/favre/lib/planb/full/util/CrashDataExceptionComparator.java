package at.favre.lib.planb.full.util;

import java.util.Comparator;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.util.CrashDataUtil;

public class CrashDataExceptionComparator implements Comparator<CrashData> {
    @Override
    public int compare(CrashData o1, CrashData o2) {
        return CrashDataUtil.getClassNameForException(o1.throwableClassName).compareTo(CrashDataUtil.getClassNameForException(o2.throwableClassName));
    }
}
