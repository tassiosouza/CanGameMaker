package hollowsoft.cangamemaker.view.task;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Step;
import hollowsoft.cangamemaker.model.Task;
import hollowsoft.cangamemaker.utility.DialogBuilder;
import hollowsoft.cangamemaker.utility.FileUtility;
import hollowsoft.cangamemaker.view.BaseAppCompatActivity;

public class CreateTaskActivity extends BaseAppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 5747;
    private static final int REQUEST_CODE_AUDIO = 1235;
    private static final int REQUEST_CODE_VIDEO = 1234;

    @Length(min = 3, trim = true, messageResId = R.string.validation_name)
    @Bind(R.id.create_task_activity_edit_text_name)
    TextView textViewName;

    @Bind(R.id.create_task_activity_linear_layout_name)
    LinearLayout linearLayoutName;

    @Bind(R.id.create_task_relative_layout_asset)
    RelativeLayout relativeLayoutAsset;

    @Bind(R.id.card_view_text)
    TextView textViewText;

    @Bind(R.id.task_image_view_image)
    ImageView imageViewImage;

    @Bind(R.id.task_video_image)
    ImageView imageViewVideo;

    @Bind(R.id.image_status_text)
    ImageView imageViewStatusText;

    @Bind(R.id.image_status_image)
    ImageView imageViewStatusImage;

    @Bind(R.id.image_status_sound)
    ImageView imageViewStatusSound;

    @Bind(R.id.image_status_video)
    ImageView imageViewStatusVideo;

    private boolean inAsset;

    String mCurrentPhotoPath;

    private final Task task = new Task();

    private Validator validator;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_create_task);

        validator = new Validator(this);
        validator.setValidationListener(new ValidationHandler());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            if (inAsset) {
                handlerAnimation();

            } else {
                onBackPressed();
            }

        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {

        if (inAsset) {
            handlerAnimation();

        } else {
            super.onBackPressed();
        }
    }

    private class ValidationHandler implements Validator.ValidationListener {

        @Override
        public void onValidationSucceeded() {

            final Animation slideIn = AnimationUtils.loadAnimation(CreateTaskActivity.this, R.anim.slide_in_left);

            slideIn.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(final Animation animation) {
                    relativeLayoutAsset.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    inAsset = true;

                    linearLayoutName.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {

                }
            });

            final Animation slideOut = AnimationUtils.loadAnimation(CreateTaskActivity.this, R.anim.slide_out_left);

            relativeLayoutAsset.startAnimation(slideIn);
            linearLayoutName.startAnimation(slideOut);
        }

        @Override
        public void onValidationFailed(final List<ValidationError> errorList) {

            for (final ValidationError error : errorList) {

                final String message = error.getCollatedErrorMessage(CreateTaskActivity.this);

                final View view = error.getView();

                if (view instanceof EditText) {

                    ((EditText) view).setError(message);
                }
            }
        }
    }

    @OnClick(R.id.create_task_button_next)
    public void onNext() {

        validator.validate();
    }

    private void handlerAnimation() {

        final Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

        slideIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
                linearLayoutName.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                inAsset = false;

                relativeLayoutAsset.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }
        });

        final Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        linearLayoutName.startAnimation(slideIn);
        relativeLayoutAsset.startAnimation(slideOut);
    }

    @OnClick(R.id.create_task_activity_fab_text)
    public void onAddText() {

        final String name = getString(R.string.hint_name);

        new DialogBuilder(this).load()
                .title(R.string.inform_hint)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputMaxLength(25)
                .input(name, textViewText.getText().toString(), false, new MaterialDialog.InputCallback() {

                            @Override
                            public void onInput(final MaterialDialog dialog, final CharSequence charSequence) {

                                imageViewStatusText.setImageResource(R.drawable.icon_success);

                                textViewText.setText(charSequence);

                                task.putStep(Step.TEXT, charSequence.toString());
                            }

                }).show();
    }

    File imageFile;

    @OnClick(R.id.create_task_activity_fab_image)
    public void onAddImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
           // Create the File where the photo should go

            imageFile = new File(Environment.getExternalStorageDirectory() + File.separator
                    + "photo_" + new Date().getTime() + "image.jpg");

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(imageFile));

            startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE);
        }
    }

    @OnClick(R.id.create_task_activity_fab_audio)
    public void onAddAudio() {

        final Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, REQUEST_CODE_AUDIO);
    }

    @OnClick(R.id.create_task_activity_fab_video)
    public void onAddVideo() {

        startActivityForResult(new Intent(MediaStore.ACTION_VIDEO_CAPTURE), REQUEST_CODE_VIDEO);
    }

    private Uri audioUri;

    private File videoFile;

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {

        if (requestCode == REQUEST_CODE_IMAGE) {

            task.putStep(Step.IMAGE, imageFile.getAbsolutePath());

            mCurrentPhotoPath = imageFile.getAbsolutePath();

            setPic();

            imageViewStatusImage.setImageResource(R.drawable.icon_success);

        } else if (requestCode == REQUEST_CODE_AUDIO) {

            if (resultCode == RESULT_OK) {

                audioUri = intent.getData();

                if (audioUri != null) {
                    task.putStep(Step.AUDIO, audioUri.toString());

                    imageViewStatusSound.setImageResource(R.drawable.icon_success);
                }
            }

        } else if (requestCode == REQUEST_CODE_VIDEO) {

            if (resultCode == RESULT_OK) {

                try {

                    AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(intent.getData(), "r");

                    videoFile = new File(Environment.getExternalStorageDirectory() + File.separator
                            + "video" + new Date().getTime() + "video.mp4");

                    FileInputStream stream = videoAsset.createInputStream();

                    if (FileUtility.save(videoFile, stream)) {

                        task.putStep(Step.VIDEO, videoFile.getAbsolutePath());

                        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(),
                                MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);

                        imageViewVideo.setImageBitmap(thumbnail);

                        imageViewStatusVideo.setImageResource(R.drawable.icon_success);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageViewImage.getWidth();
        int targetH = imageViewImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageViewImage.setImageBitmap(bitmap);
    }

    @OnClick(R.id.create_task_button_save)
    public void onSave() {

        for (final Step step : Step.values()) {

            if (task.getStep(step) == null) {
                Toast.makeText(this, R.string.you_need, Toast.LENGTH_SHORT).show();

                return;
            }
        }

        task.setName(textViewName.getText().toString());
        task.save();

        finish();
    }
}
