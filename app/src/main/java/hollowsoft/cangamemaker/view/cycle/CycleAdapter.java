package hollowsoft.cangamemaker.view.cycle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Cycle;
import hollowsoft.cangamemaker.model.Tag;

public class CycleAdapter extends ArrayAdapter<Cycle> {

    public CycleAdapter(final Context context, final List<Cycle> cycleList) {
        super(context, 0, cycleList);
    }

    public static class ViewHolder {
        TextView textViewName;
        TextView textViewTag;
        RatingBar ratingBar;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup viewGroup) {
        View view = convertView;

        ViewHolder viewHolder;

        if (view == null) {

            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_cycle, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.textViewName = (TextView) view.findViewById(R.id.cycle_name);
            viewHolder.textViewTag = (TextView) view.findViewById(R.id.cycle_tag);
            viewHolder.ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

            final Drawable drawable = viewHolder.ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#186cb7"), PorterDuff.Mode.SRC_ATOP);

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        final Cycle cycle = getItem(position);

        viewHolder.textViewName.setText(cycle.getName());

        final Tag tag = Tag.findById(Tag.class, cycle.getTagId());

        viewHolder.textViewTag.setText(tag.getName());

        viewHolder.ratingBar.setRating(cycle.getScore() / cycle.getPlayed());

        return view;
    }
}
