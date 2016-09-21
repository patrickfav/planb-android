package at.favre.lib.planb.recover;

import java.util.regex.Pattern;

import at.favre.lib.planb.BuildConfig;

public interface BuildTypeMatcher {

    boolean matches(String candidate);

    class DefaultEquals implements BuildTypeMatcher {
        private String buildType;
        private boolean caseSensitive;

        public DefaultEquals(String buildType) {
            this(buildType, true);
        }

        public DefaultEquals(String buildType, boolean caseSensitive) {
            this.buildType = buildType;
            this.caseSensitive = caseSensitive;
        }

        @Override
        public boolean matches(String candidate) {
            if (caseSensitive) {
                return buildType.equals(candidate);
            } else {
                return buildType.equals(candidate.toLowerCase());
            }
        }
    }

    class DefaultRegex implements BuildTypeMatcher {
        private String buildTypeRegex;

        public DefaultRegex(String buildTypeRegex) {
            this.buildTypeRegex = buildTypeRegex;
        }

        @Override
        public boolean matches(String candidate) {
            return Pattern.matches(buildTypeRegex, candidate);
        }
    }

    class Debug implements BuildTypeMatcher {
        @Override
        public boolean matches(String candidate) {
            return BuildConfig.DEBUG;
        }
    }

    class NonDebug implements BuildTypeMatcher {
        @Override
        public boolean matches(String candidate) {
            return !BuildConfig.DEBUG;
        }
    }
}
