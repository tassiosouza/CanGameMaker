package hollowsoft.cangamemaker.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.model.Parent;
import hollowsoft.cangamemaker.model.Patient;
import hollowsoft.cangamemaker.model.Profile;
import hollowsoft.cangamemaker.model.Setting;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.BaseAppCompatActivity;
import hollowsoft.cangamemaker.view.MainActivity;

public class ProfileActivity extends BaseAppCompatActivity implements OnProfileListener {

    @Bind(R.id.patient_list_activity_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        final List<Profile> profileList = Profile.listAll(Profile.class);

        for (final Profile profile : profileList) {

            profile.setPatient(Patient.findById(Patient.class, profile.getId()));
            profile.getPatient().setParent(Parent.findById(Parent.class, profile.getId()));
            profile.setSetting(Setting.findById(Setting.class, profile.getId()));
        }

        recyclerView.setAdapter(new ProfileAdapter(this, profileList));
    }

    @Override
    public void onProfileSelect(final Profile profile) {

        if (new PrefManager(this).put(Constants.Pref.PROFILE_ID, profile.getId())) {

            navigateTo(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                           Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    @OnClick(R.id.patient_list_activity_button_add)
    public void onAdd() {
        navigateTo(CreateProfileActivity.class);
    }
}
