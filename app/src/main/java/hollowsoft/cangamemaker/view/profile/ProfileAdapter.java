package hollowsoft.cangamemaker.view.profile;

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
import hollowsoft.cangamemaker.model.Profile;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private final List<Profile> profileList;

    private final OnProfileListener listener;

    public ProfileAdapter(final OnProfileListener listener, final List<Profile> profileList) {

        if (listener == null) {
            throw new IllegalArgumentException("The listener cannot be null.");
        }

        this.listener = listener;

        this.profileList = profileList;

//        Patient patient = new Patient();
//        patient.setName("Igor Morais");
//
//        profileList.add(patient);
//
//        Patient patient1 = new Patient();
//        patient1.setName("Igor Morais");
//
//        profileList.add(patient1);
//
//
//        Patient patient2 = new Patient();
//        patient2.setName("Igor Morais");
//
//        profileList.add(patient2);
//
//        Patient patient3 = new Patient();
//        patient3.setName("Igor Morais");
//
//        profileList.add(patient3);
//
//        Patient patient4 = new Patient();
//        patient4.setName("Igor Morais");
//
//        profileList.add(patient4);
//
//        Patient patient5 = new Patient();
//        patient5.setName("Igor Morais");
//
//        profileList.add(patient5);
//
//        Patient patient6 = new Patient();
//        patient6.setName("Igor Morais");
//
//        profileList.add(patient6);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.profile_name)
        TextView textViewName;

        @Bind(R.id.profile_photo)
        ImageView imageViewPhoto;

        public ViewHolder(final View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {

            final Profile profile = profileList.get(getAdapterPosition());

            listener.onProfileSelect(profile);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {

        final View view = LayoutInflater.from(viewGroup.getContext())
                                        .inflate(R.layout.item_profile, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Profile profile = profileList.get(position);

        viewHolder.textViewName.setText(profile.getPatient().getName());
        viewHolder.imageViewPhoto.setImageResource(R.drawable.image_avatar_small_1);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
