package hollowsoft.cangamemaker.view.cycle;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Tag;
import hollowsoft.cangamemaker.view.BaseAppCompatActivity;

public class CreateCycleActivity extends BaseAppCompatActivity {

    private static final int MIN_CHAR_NAME = 3;

    @Length(min = MIN_CHAR_NAME, trim = true, messageResId = R.string.validation_name)
    @Bind(R.id.create_task_activity_edit_text_name)
    EditText editTextName;

    @Bind(R.id.spinner)
    Spinner spinnerTag;

    private Validator validator;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.cycle);

        final List<Tag> tagList = Tag.listAll(Tag.class);

        final String[] tagArray = new String[tagList.size()];

        for (int i = 0; i < tagList.size(); i++) {
            tagArray[i] = tagList.get(i).getName();
        }

        spinnerTag.setAdapter(new ArrayAdapter<>(this,
                R.layout.tag_item, R.id.tag_name,
                tagArray));

        spinnerTag.setSelection(0);

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

    @OnClick(R.id.button_next)
    public void onAdd() {
        validator.validate();
    }


    private class ValidationHandler implements Validator.ValidationListener {

        @Override
        public void onValidationSucceeded() {

            final Bundle bundle = new Bundle();

            bundle.putString("name", editTextName.getText().toString());
            bundle.putLong("idTag", spinnerTag.getSelectedItemPosition() + 1);

            navigateTo(SelectTaskActivity.class, bundle);
        }

        @Override
        public void onValidationFailed(final List<ValidationError> errorList) {

            for (final ValidationError error : errorList) {

                final String message = error.getCollatedErrorMessage(CreateCycleActivity.this);

                final View view = error.getView();

                if (view instanceof EditText) {

                    ((EditText) view).setError(message);
                }
            }
        }
    }
}
