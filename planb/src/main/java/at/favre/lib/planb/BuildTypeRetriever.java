package at.favre.lib.planb;

/**
 * Created by PatrickF on 17.09.2016.
 */
public interface BuildTypeRetriever {
    String retrieve();

    class DefaultAndroidGradleRetriever implements BuildTypeRetriever {

        @Override
        public String retrieve() {
            return BuildConfig.BUILD_TYPE;
        }
    }
}
