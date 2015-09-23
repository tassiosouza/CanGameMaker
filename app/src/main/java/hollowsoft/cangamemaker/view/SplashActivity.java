package hollowsoft.cangamemaker.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.profile.CreateProfileActivity;

public class SplashActivity extends BaseActivity implements Runnable {

    private static final long WAIT_TIME = 1500;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_splash);

        handler.postDelayed(this, WAIT_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeCallbacks(this);
    }

    @Override
    public void run() {

        if (new PrefManager(this).get(Constants.Pref.PROFILE_ID) == null) {

            navigateTo(CreateProfileActivity.class);

        } else {

            navigateTo(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                 Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }
}
