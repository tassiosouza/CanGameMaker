package hollowsoft.cangamemaker.view.tag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.model.Patient;
import hollowsoft.cangamemaker.model.Tag;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.BaseFragment;

public class TagFragment extends BaseFragment {

    @Bind(R.id.tag_fragment_recycler_view)
    RecyclerView recyclerView;

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        getSupportActionBar().setTitle(R.string.tag_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {

        final View view = inflater.inflate(R.layout.fragment_tag, viewGroup, false);

        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final Integer id = new PrefManager(getActivity()).get(Constants.Pref.PROFILE_ID);
        final Patient patient = Patient.findById(Patient.class, id.longValue());

        final List<Tag> tagList = Tag.find(Tag.class, "patient = ?", patient.getId().toString());

        recyclerView.setAdapter(new TagAdapter(tagList));
    }

    @OnClick(R.id.tag_fragment_button_add)
    public void onAdd() {
        navigateTo(CreateTagActivity.class);
    }
}
