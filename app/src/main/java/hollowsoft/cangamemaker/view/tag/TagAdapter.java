package hollowsoft.cangamemaker.view.tag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Tag;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private final List<Tag> tagList;

    public TagAdapter(final List<Tag> tagList) {
        this.tagList = tagList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tag_name)
        TextView textViewName;

        public ViewHolder(final View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {

        final View view = LayoutInflater.from(viewGroup.getContext())
                                        .inflate(R.layout.item_tag, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Tag tag = tagList.get(position);

        viewHolder.textViewName.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }
}