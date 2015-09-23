package hollowsoft.cangamemaker.view.tag;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.manager.PrefManager;
import hollowsoft.cangamemaker.model.Patient;
import hollowsoft.cangamemaker.model.Tag;
import hollowsoft.cangamemaker.utility.Constants;
import hollowsoft.cangamemaker.view.BaseAppCompatActivity;

public class CreateTagActivity extends BaseAppCompatActivity {

    private static final int MIN_CHAR_NAME = 3;

    @Length(min = MIN_CHAR_NAME, trim = true, messageResId = R.string.validation_name)
    @Bind(R.id.create_tag_activity_edit_text_name)
    EditText editTextName;

    private Validator validator;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_create_tag);

        validator = new Validator(this);
        validator.setValidationListener(new ValidationHandler());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();

        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @OnClick(R.id.create_tag_activity_button_create)
    public void onCreate() {

        final List<Tag> tagList = Tag.find(Tag.class, "name = ?", editTextName.getText().toString());

        if (tagList.isEmpty()) {
            validator.validate();

        } else {
            Toast.makeText(this, R.string.tag_already_exits, Toast.LENGTH_SHORT).show();
        }
    }

    private class ValidationHandler implements Validator.ValidationListener {

        @Override
        public void onValidationSucceeded() {

            final String name = editTextName.getText().toString();

            final Integer id = new PrefManager(CreateTagActivity.this).get(Constants.Pref.PROFILE_ID);
            final Patient patient = Patient.findById(Patient.class, id.longValue());

            final Tag tag = new Tag(name, patient);

            tag.save();

            finish();
        }

        @Override
        public void onValidationFailed(final List<ValidationError> errorList) {

            for (final ValidationError error : errorList) {

                final String message = error.getCollatedErrorMessage(CreateTagActivity.this);

                final View view = error.getView();

                if (view instanceof EditText) {

                    ((EditText) view).setError(message);
                }
            }
        }
    }
}
