package hollowsoft.cangamemaker.view.cycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Cycle;
import hollowsoft.cangamemaker.model.Task;
import hollowsoft.cangamemaker.view.BaseAppCompatActivity;
import hollowsoft.cangamemaker.view.MainActivity;

public class SelectTaskActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.list_view)
    ListView listView;

    private String name;
    private Long idTag;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.select_task);

        final Intent intent = getIntent();

        name = intent.getStringExtra("name");
        idTag = intent.getLongExtra("idTag", 0);

        final List<Task> taskList = Task.listAll(Task.class);

        listView.setAdapter(new TaskSelectAdapter(this, taskList));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {

        final SparseBooleanArray itemSelectArray = listView.getCheckedItemPositions();

        if (itemSelectArray.get(position)) {
            view.setBackgroundColor(getResources().getColor(R.color.grey_light));

        } else {
            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

    @OnClick(R.id.button_save)
    public void onSave() {

        final SparseBooleanArray itemSelectArray = listView.getCheckedItemPositions();

        for (int i = 0; i < itemSelectArray.size(); i++) {

            if (itemSelectArray.valueAt(i)) {

                final StringBuilder builder = new StringBuilder();

                for (i = 0; i < itemSelectArray.size(); i++) {

                    if (itemSelectArray.valueAt(i)) {

                        final Task task = (Task) listView.getItemAtPosition(i);

                        builder.append(task.getId());

                        if (i < itemSelectArray.size() - 1) {
                            builder.append(",");
                        }
                    }
                }

                final Cycle cycle = new Cycle();

                cycle.setName(name);
                cycle.setTagId(idTag);
                cycle.setListTask(builder.toString());

                cycle.save();

                navigateTo(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);

                return;
            }

        }

        Toast.makeText(this, R.string.one_task, Toast.LENGTH_SHORT).show();
    }
}
