package hollowsoft.cangamemaker.view.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final TaskListener listener;
    private final List<Task> taskList;

    public TaskAdapter(final List<Task> taskList, TaskListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.task_name)
        TextView textViewName;

        @Bind(R.id.task_image)
        ImageView imageViewImage;

        public ViewHolder(final View view) {
            super(view);

            ButterKnife.bind(this, view);

            if (listener != null) {

                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        final Task task = taskList.get(getAdapterPosition());

                        listener.onTaskSelect(task);
                    }
                });
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {

        final View view = LayoutInflater.from(viewGroup.getContext())
                                        .inflate(R.layout.item_task, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Task task = taskList.get(position);

        viewHolder.textViewName.setText(task.getName());
        viewHolder.imageViewImage.setImageResource(R.drawable.icon_play);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
