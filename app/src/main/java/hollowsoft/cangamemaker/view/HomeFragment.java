package hollowsoft.cangamemaker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Cycle;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.cycle.CycleAdapter;

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.box_hint)
    LinearLayout linearLayout;

    @Bind(R.id.list_view)
    ListView listView;

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        getSupportActionBar().setTitle(R.string.menu_home_home);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {

        final View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final List<Cycle> cycleList = Cycle.listAll(Cycle.class);

        if (cycleList.isEmpty()) {
            linearLayout.setVisibility(View.VISIBLE);

        } else {

            linearLayout.setVisibility(View.GONE);

            listView.setAdapter(new CycleAdapter(getActivity(), cycleList));
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {

        final Cycle cycle = (Cycle) adapterView.getItemAtPosition(position);

        final Intent intent = new Intent(getActivity(), PlayActivity.class);

        intent.putExtra(Constants.Intent.ID, cycle.getId());
        intent.putExtra(Constants.Intent.CYCLE, true);

        startActivity(intent);
    }
}
