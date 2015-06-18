package poc.cip.cgs.com.cip_poc;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 *
 */
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
