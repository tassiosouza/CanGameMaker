package hollowsoft.cangamemaker.view.cycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Task;

public class TaskSelectAdapter extends ArrayAdapter<Task> {

    public TaskSelectAdapter(final Context context, final List<Task> taskList) {
        super(context, 0, taskList);
    }

    public static class ViewHolder {
        TextView textViewName;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup viewGroup) {
        View view = convertView;

        ViewHolder viewHolder;

        if (view == null) {

            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_select_task, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.textViewName = (TextView) view.findViewById(R.id.text_view_name);

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        final Task task = getItem(position);

        viewHolder.textViewName.setText(task.getName());

        return view;
    }
}
