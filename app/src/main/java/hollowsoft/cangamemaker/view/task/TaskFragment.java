package hollowsoft.cangamemaker.view.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Task;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.BaseFragment;
import hollowsoft.cangamemaker.view.PlayActivity;

public class TaskFragment extends BaseFragment implements TaskListener {

    @Bind(R.id.task_fragment_recycler_view)
    RecyclerView recyclerView;

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        getSupportActionBar().setTitle(R.string.task_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {

        final View view = inflater.inflate(R.layout.fragment_task, viewGroup, false);

        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final List<Task> taskList = Task.listAll(Task.class);

        recyclerView.setAdapter(new TaskAdapter(taskList, this));
    }

    @OnClick(R.id.task_fragment_button_add)
    public void onAdd() {
        navigateTo(CreateTaskActivity.class);
    }

    @Override
    public void onTaskSelect(final Task task) {

        new MaterialDialog.Builder(getActivity())
                .items(R.array.op_menu)
                .itemsCallback(new MaterialDialog.ListCallback() {

                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (which == 0) {

                            final Intent intent = new Intent(getActivity(), PlayActivity.class);

                            intent.putExtra(Constants.Intent.ID, task.getId());

                            startActivity(intent);

                        } else if (which == 1) {

                            task.delete();

                            final List<Task> taskList = Task.listAll(Task.class);

                            recyclerView.setAdapter(new TaskAdapter(taskList, TaskFragment.this));
                        }
                        else if (which == 2) {

                            final Intent intent = new Intent(getActivity(), CreateTaskActivity.class);

                            intent.putExtra(Constants.Intent.ID, task.getId());

                            startActivity(intent);
                        }
                    }
                }).show();

    }
}
