package hollowsoft.cangamemaker.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hollowsoft.cangamemaker.R;
import hollowsoft.cangamemaker.model.Cycle;
import hollowsoft.cangamemaker.model.Step;
import hollowsoft.cangamemaker.model.Task;
import hollowsoft.cangamemaker.utility.Constants;

public class PlayActivity extends BaseActivity implements Runnable {

    private final long time = 5000;

    @Bind(R.id.box_text)
    RelativeLayout boxText;

    @Bind(R.id.play_activity_text_view)
    TextView textViewName;

    @Bind(R.id.play_activity_image_view)
    ImageView imageViewImage;

    @Bind(R.id.play_activity_video_view)
    VideoView videoView;

    private final StateHandler stateHandler = new StateHandler();
    private List<Task> taskList = new ArrayList<>();

    private MediaPlayer mediaPlayer;

    private final Handler handler = new Handler();

    private boolean remove;

    private Cycle cycle;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_play);

        ButterKnife.bind(this);

        final boolean isCycle = getIntent().getBooleanExtra(Constants.Intent.CYCLE, false);

        final long id = getIntent().getLongExtra(Constants.Intent.ID, Long.MIN_VALUE);

        if (isCycle) {

            cycle = Cycle.findById(Cycle.class, id);

            cycle.setPlayed(cycle.getPlayed() + 1);

            final String[] taskArray = cycle.getListTask().split(",");

            for (final String idString : taskArray) {

                final Task task = Task.findById(Task.class, Long.parseLong(idString));

                taskList.add(task);
            }

        } else {

            taskList = new ArrayList<>(1);

            taskList.add(Task.findById(Task.class, id));
        }

        stateHandler.setStep(Step.IMAGE);
        stateHandler.setTask(taskList.get(0));

        stateHandler.getTask().init();

        remove = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (stateHandler.getStep() == Step.IMAGE) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    final String path = stateHandler.getTask().getStep(Step.IMAGE);

                    setPic(path);

                    handler.postDelayed(PlayActivity.this, time);
                }

            }, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(this);

        release();

        finish();
    }

    private void release() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
            videoView = null;
        }
    }

    @Override
    public void run() {

        if (!remove) {

            if (stateHandler.getStep() == Step.IMAGE) {

                textViewName.setText(stateHandler.getTask().getStep(Step.TEXT));

                boxText.setVisibility(View.VISIBLE);

                stateHandler.setStep(Step.TEXT);

                handler.postDelayed(this, time);

            } else if (stateHandler.getStep() == Step.TEXT) {

                //final String path = Environment.getExternalStorageDirectory().getPath();

                final Uri uri = Uri.parse(stateHandler.getTask().getStep(Step.AUDIO));

                onPlayAudio(uri);

                stateHandler.setStep(Step.AUDIO);

            } else if (stateHandler.getStep() == Step.AUDIO) {

                imageViewImage.setVisibility(View.GONE);
                boxText.setVisibility(View.GONE);

                final Uri uri = Uri.parse(stateHandler.getTask().getStep(Step.VIDEO));

                videoView.setVisibility(View.VISIBLE);

                videoView.setVideoURI(uri);

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(final MediaPlayer mp) {
                        handler.postDelayed(PlayActivity.this, 3000);
                    }
                });

                videoView.start();

                stateHandler.setStep(Step.VIDEO);

            } else if (stateHandler.getStep() == Step.VIDEO) {

                Toast.makeText(this, R.string.pec_finished, Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    }

    public void onPlayAudio(final Uri uri) {

       mediaPlayer = MediaPlayer.create(PlayActivity.this, uri);

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mediaPlayer.stop();
                mediaPlayer.release();

                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                mediaPlayer.stop();

                handler.postDelayed(PlayActivity.this, time);
            }
        });

        mediaPlayer.start();
    }

    public class StateHandler {

        private int current;
        private Step step;
        private Task task;

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public Step getStep() {
            return step;
        }

        public void setStep(Step step) {
            this.step = step;
        }

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }
    }

    private void setPic(final String path) {
        // Get the dimensions of the View
        int targetW = imageViewImage.getWidth();
        int targetH = imageViewImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        imageViewImage.setImageBitmap(bitmap);
    }

    @OnClick(R.id.button_ok)
    public void onOk() {
        handler.removeCallbacks(this);

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
            videoView = null;
        }

        remove = true;

        checkPoints();

        release();

        Toast.makeText(this, R.string.pec_finished_success, Toast.LENGTH_SHORT).show();

        finish();
    }

    private void checkPoints() {

        float score = 0;

        if (stateHandler.getStep() == Step.IMAGE) {
            score = 5;

        } else if (stateHandler.getStep() == Step.TEXT) {
            score = 4;

        } else if (stateHandler.getStep() == Step.AUDIO) {
            score = 3;

        } else if (stateHandler.getStep() == Step.VIDEO) {
            score = 2;
        }

        cycle.setScore(cycle.getScore() + score);

        cycle.save();
    }
}
