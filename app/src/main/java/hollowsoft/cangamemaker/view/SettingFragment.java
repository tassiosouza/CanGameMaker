package hollowsoft.cangamemaker.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.ButterKnife;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.model.Profile;
import hollowsoft.cangamemaker.model.Setting;
import hollowsoft.cangamemaker.model.Step;
import hollowsoft.cangamemaker.utility.Constants;

public class SettingFragment extends BaseFragment {

    SeekBar seekBarText;

    SeekBar seekBarAudio;

    SeekBar seekBarVideo;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {

        final View view = inflater.inflate(R.layout.fragment_setting, viewGroup, false);

        ButterKnife.bind(this, view);

        final long id = new PrefManager(getActivity()).get(Constants.Pref.PROFILE_ID);

        final Setting setting = Profile.findById(Profile.class, id).getSetting();

        seekBarText.setProgress(setting.getSet(Step.TEXT));

        seekBarAudio.setProgress(setting.getSet(Step.AUDIO));

        seekBarVideo.setProgress(setting.getSet(Step.VIDEO));

        return view;
    }
}
