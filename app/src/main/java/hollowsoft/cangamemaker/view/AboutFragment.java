package hollowsoft.cangamemaker.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hollowsoft.cangamemaker.R;

public class AboutFragment extends BaseFragment {

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        getSupportActionBar().setTitle(R.string.menu_home_about);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {
        return inflater.inflate(R.layout.fragment_about, viewGroup, false);
    }
}
