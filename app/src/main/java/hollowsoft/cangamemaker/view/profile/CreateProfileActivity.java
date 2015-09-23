package hollowsoft.cangamemaker.view.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.model.Gender;
import hollowsoft.cangamemaker.model.Parent;
import hollowsoft.cangamemaker.model.Patient;
import hollowsoft.cangamemaker.model.Profile;
import hollowsoft.cangamemaker.model.Setting;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.BaseAppCompatActivity;
import hollowsoft.cangamemaker.view.MainActivity;

public class CreateProfileActivity extends BaseAppCompatActivity implements DatePickerDialog.OnDateSetListener {

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

    private Calendar calendar;

    private Validator validator;

    private final Profile profile = new Profile();

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_create_patient);

        final String[] genderArray = getResources().getStringArray(R.array.gender);

//        spinnerGender.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line,
//                genderArray));
//
//        spinnerGender.setSelection(0);

        radioButtonMale.setChecked(true);

        validator = new Validator(this);
        validator.setValidationListener(new ValidationHandler());
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

            new PrefManager(CreateProfileActivity.this).put(Constants.Pref.PROFILE_ID, profile.getId());

            navigateTo(MainActivity.class);
        }

        @Override
        public void onValidationFailed(final List<ValidationError> errorList) {

            for (final ValidationError error : errorList) {

                final String message = error.getCollatedErrorMessage(CreateProfileActivity.this);

                final View view = error.getView();

                if (view instanceof EditText) {

                    ((EditText) view).setError(message);
                }
            }
        }
    }
}
