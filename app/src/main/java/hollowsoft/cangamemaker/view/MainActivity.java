package hollowsoft.cangamemaker.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final Class<? extends Fragment> MAIN_FRAGMENT = HomeFragment.class;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.main_activity_navigation_view_main_menu)
    NavigationView navigationView;

    @Bind(R.id.main_activity_drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    private final Map<String, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {

        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.setDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(this);

        addFragment(MAIN_FRAGMENT,
                MAIN_FRAGMENT.getSimpleName());
    }

    @Override
    protected void onPostCreate(final Bundle bundle) {
        super.onPostCreate(bundle);

        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        return drawerToggle.onOptionsItemSelected(menuItem) || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if (getCurrentFragment().getTag().equals(MAIN_FRAGMENT.getSimpleName())) {

            super.onBackPressed();

        } else {

            replaceFragment(MAIN_FRAGMENT,
                            MAIN_FRAGMENT.getSimpleName());
        }
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {

        drawerLayout.closeDrawer(GravityCompat.START);

        final HomeMenu homeMenu = HomeMenu.getBy(menuItem);

        if (false) {
        //if (homeMenu.getId() == HomeMenu.EXIT.getId()) {

            if (new PrefManager(this).remove(Constants.Pref.PROFILE_ID)) {

                final Intent intent = new Intent(this, ProfileActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }


        } else if (homeMenu.isFragment()) {

            if (!homeMenu.getTag().equals(getCurrentFragment().getTag())) {

                replaceFragment(homeMenu.getMenuClass(), homeMenu.getTag());
            }

        } else if (homeMenu.isActivity()) {
            startActivity(new Intent(this, homeMenu.getMenuClass()));
        }

        menuItem.setChecked(true);

        return true;
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.main_activity_frame_layout_fragment_container);
    }

    private void addFragment(final Class<? extends Fragment> fragmentClass, final String tag) {

        final Fragment fragment = Fragment.instantiate(this, fragmentClass.getName());

        getFragmentManager().beginTransaction()
                            .add(R.id.main_activity_frame_layout_fragment_container, fragment, tag)
                            .commit();

        fragmentMap.put(tag, fragment);
    }

    private void replaceFragment(final Class<?> fragmentClass, final String tag) {

        Fragment fragment = fragmentMap.get(tag);

        if (fragment == null) {

            fragment = Fragment.instantiate(this, fragmentClass.getName());

            fragmentMap.put(tag, fragment);
        }

        getFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_frame_layout_fragment_container, fragment, tag)
                            .commit();
    }
}
