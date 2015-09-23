package hollowsoft.cangamemaker.view.cycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Cycle;
import hollowsoft.cangamemaker.view.BaseFragment;

public class CycleFragment extends BaseFragment {

    @Bind(R.id.list_view)
    ListView listView;

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        getSupportActionBar().setTitle(R.string.cycle_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {

        final View view = inflater.inflate(R.layout.fragment_cycle, viewGroup, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final List<Cycle> cycleList = Cycle.listAll(Cycle.class);

        listView.setAdapter(new CycleAdapter(getActivity(), cycleList));
    }

    @OnClick(R.id.button_add)
    public void onAdd() {
        navigateTo(CreateCycleActivity.class);
    }
}
