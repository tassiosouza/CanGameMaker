package hollowsoft.cangamemaker.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class BaseAppCompatActivity extends AppCompatActivity implements ViewListener, Navigate {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            throw new IllegalStateException("The actionBar cannot be null.");
        }

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setContentView(final int layout) {
        super.setContentView(layout);

        onSetContentView();
    }

    @Override
    public void onSetContentView() {
        ButterKnife.bind(this);
    }

    @Override
    public void navigateTo(final Class<? extends Activity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @Override
    public void navigateTo(final Class<? extends Activity> activityClass, final int flags) {

        final Intent intent = new Intent(this, activityClass);
        intent.setFlags(flags);

        startActivity(intent);
    }

    @Override
    public void navigateTo(final Class<? extends Activity> activityClass, final Bundle bundle) {

        final Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void navigateTo(final Class<? extends Activity> activityClass, final int flags, final Bundle bundle) {

        final Intent intent = new Intent(this, activityClass);
        intent.setFlags(flags);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void navigateForResult(final Class<? extends Activity> activityClass, final int requestCode) {
        startActivityForResult(new Intent(this, activityClass), requestCode);
    }

    @Override
    public void navigateForResult(final Class<? extends Activity> activityClass, final int requestCode,
                                  final int flags) {

        final Intent intent = new Intent(this, activityClass);
        intent.setFlags(flags);

        startActivityForResult(intent, requestCode);
    }

    @Override
    public void navigateForResult(final Class<? extends Activity> activityClass, final int requestCode,
                                  final Bundle bundle) {

        final Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);

        startActivityForResult(intent, requestCode);
    }

    @Override
    public void navigateForResult(final Class<? extends Activity> activityClass, final int requestCode,
                                  final int flags, final Bundle bundle) {

        final Intent intent = new Intent(this, activityClass);
        intent.setFlags(flags);
        intent.putExtras(bundle);

        startActivityForResult(intent, requestCode);
    }
}
