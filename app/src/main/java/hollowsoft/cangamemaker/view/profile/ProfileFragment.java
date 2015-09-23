package hollowsoft.cangamemaker.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.model.Gender;
import hollowsoft.cangamemaker.model.Parent;
import hollowsoft.cangamemaker.model.Patient;
import hollowsoft.cangamemaker.model.Profile;
import hollowsoft.cangamemaker.model.Setting;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.BaseFragment;

public class ProfileFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    private static final int MIN_CHAR_NAME = 3;
    private static final int MIN_CHAR_MOBILE = 10;

    @Length(min = MIN_CHAR_NAME, trim = true, messageResId = R.string.validation_name)
    @Bind(R.id.create_profile_activity_edit_text_patient_name)
    TextView textViewPatientName;

//    @Bind(R.id.create_profile_activity_spinner_gender)
//    Spinner spinnerGender;

    @Bind(R.id.radio_button_male)
    RadioButton radioButtonMale;

    @Bind(R.id.radio_button_female)
    RadioButton radioButtonFemale;

    @NotEmpty(messageResId = R.string.validation_not_empty)
    @Bind(R.id.create_profile_activity_edit_text_birth_date)
    TextView textViewBirthDate;

    @Length(min = MIN_CHAR_NAME, trim = true, messageResId = R.string.validation_name)
    @Bind(R.id.create_profile_activity_edit_text_parent_name)
    TextView textViewParentName;

    @Length(min = MIN_CHAR_MOBILE, trim = true, messageResId = R.string.validation_mobile)
    @Pattern(regex = "[0-9\\.\\-\\s+\\/()]+" , messageResId = R.string.validation_mobile)
    @Bind(R.id.create_patient_activity_edit_text_mobile)
    TextView textViewMobile;

    @Bind(R.id.create_patient_activity_button_create)
    Button buttonSave;

    private Calendar calendar;

    private Validator validator;

    private final Profile profile = new Profile();


    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        getSupportActionBar().setTitle(R.string.edit_profile);
    }

    private void update(final Patient patient, Parent parent) {

        textViewPatientName.setText(patient.getName());

        if (patient.getGender() == Gender.MALE) {
            radioButtonMale.setChecked(true);

        } else {
            radioButtonFemale.setChecked(true);
        }

        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        calendar.setTime(patient.getBirthDate());

        textViewBirthDate.setText(calendar.get(Calendar.DAY_OF_MONTH)
                + "/" + calendar.get(Calendar.MONTH) + "/"
                + calendar.get(Calendar.YEAR));

        textViewParentName.setText(parent.getName());

        textViewMobile.setText(parent.getMobile());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup viewGroup, final Bundle bundle) {

        final View view = inflater.inflate(R.layout.activity_create_patient, viewGroup, false);

        ButterKnife.bind(this, view);

        buttonSave.setText(R.string.save);

        radioButtonMale.setChecked(true);

        validator = new Validator(this);
        validator.setValidationListener(new ValidationHandler());

        Patient patient = Patient.findById(Patient.class, 1L);

        Parent parent = Patient.findById(Parent.class, 1L);

        update(patient, parent);

        return view;
    }

    @OnClick(R.id.create_profile_activity_edit_text_birth_date)
    public void onDate() {

        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        final DatePickerDialog datePicker = DatePickerDialog.newInstance(this,

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int month, int day) {

        month++;

        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        calendar.set(year, month, day);

        textViewBirthDate.setText(day + "/" + month + "/" + year);
    }

    @OnClick(R.id.create_patient_activity_button_create)
    public void onCreate() {
        validator.validate();
    }

    private class ValidationHandler implements Validator.ValidationListener {

        @Override
        public void onValidationSucceeded() {

            final Patient patient = new Patient();

            patient.setName(textViewPatientName.getText().toString());

            Gender gender = radioButtonMale.isChecked() ? Gender.MALE : Gender.FEMALE;

            patient.setGender(gender);

            patient.setBirthDate(calendar.getTime());

            final String name = textViewParentName.getText().toString();
            final String mobile = textViewMobile.getText().toString();

            profile.save();
            patient.save();

            new Parent(name, mobile).save();
            new Setting(Setting.TIME).save();

            new PrefManager(getActivity()).put(Constants.Pref.PROFILE_ID, profile.getId());

            Toast.makeText(getActivity(), R.string.profile_saved, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValidationFailed(final List<ValidationError> errorList) {

            for (final ValidationError error : errorList) {

                final String message = error.getCollatedErrorMessage(getActivity());

                final View view = error.getView();

                if (view instanceof EditText) {

                    ((EditText) view).setError(message);
                }
            }
        }
    }
}
