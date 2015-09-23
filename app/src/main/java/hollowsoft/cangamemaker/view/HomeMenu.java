package hollowsoft.cangamemaker.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.view.MenuItem;

import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.view.cycle.CycleFragment;
import hollowsoft.cangamemaker.view.profile.ProfileFragment;
import hollowsoft.cangamemaker.view.tag.TagFragment;
import hollowsoft.cangamemaker.view.task.TaskFragment;

public enum HomeMenu {

    HOME        (R.id.menu_home_home, HomeFragment.class),
    CYCLE       (R.id.menu_home_cycle, CycleFragment.class),
    TASK        (R.id.menu_home_task, TaskFragment.class),
    TAG         (R.id.menu_home_tag, TagFragment.class),
    //SETTING     (R.id.menu_home_setting, SettingFragment.class),
    PROFILE        (R.id.menu_home_profile, ProfileFragment.class),
    ABOUT       (R.id.menu_home_about, AboutFragment.class);
   // EXIT        (R.id.menu_home_exit, null);

    private final int id;
    private final Class<?> menuClass;

    HomeMenu(final int id, final Class<?> menuClass) {

        this.id = id;
        this.menuClass = menuClass;
    }

    public final int getId() {
        return id;
    }

    public final Class<?> getMenuClass() {
        return menuClass;
    }

    public final String getTag() {
        return menuClass.getSimpleName();
    }

    public final boolean isDialog() {
        return DialogFragment.class.isAssignableFrom(menuClass);
    }

    public final boolean isFragment() {
        return Fragment.class.isAssignableFrom(menuClass);
    }

    public final boolean isActivity() {
        return Activity.class.isAssignableFrom(menuClass);
    }

    public static HomeMenu getBy(final int id) {

        for (final HomeMenu menu : HomeMenu.values()) {

            if (menu.getId() == id) {
                return menu;
            }
        }

        throw new IllegalArgumentException("The HomeMenu has not found.");
    }

    public static HomeMenu getBy(final MenuItem menuItem) {

        for (final HomeMenu menu : HomeMenu.values()) {

            if (menu.getId() == menuItem.getItemId()) {
                return menu;
            }
        }

        throw new IllegalArgumentException("The HomeMenu has not found.");
    }
}
