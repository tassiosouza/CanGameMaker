package hollowsoft.cangamemaker.utility;

import android.content.Context;
import android.graphics.Bitmap;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileUtility {

    private static final String TAG = FileUtility.class.getSimpleName();

    private static final int QUALITY = 100;

    private FileUtility() {

    }

    public static boolean save(final File directory, final Bitmap bitmap) {

        try {

            final FileOutputStream stream = new FileOutputStream(directory);

            bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, stream);

            stream.close();

        } catch (final IOException e) {
            Logger.logError(TAG, e.getMessage());

            return false;
        }

        return true;
    }

    public static File getDirectory(final Context context, final String name) {
        return new File(context.getFilesDir(), name);
    }

    public static boolean createDirectory(final Context context, final String name) {
        return new File(context.getFilesDir(), name).mkdir();
    }

    public static File createFile(final Context context, final String name) {
        return new File(context.getFilesDir(), name);
    }

    public static boolean save(final Context context, final String name, final FileInputStream inputStream) {

        try {

            final FileOutputStream output = context.openFileOutput(name, Context.MODE_PRIVATE);

            output.write(IOUtils.toByteArray(inputStream));

            output.close();

        } catch (final FileNotFoundException e) {
            Logger.logError(TAG, e.getMessage());

            return false;

        } catch (final IOException e) {
            Logger.logError(TAG, e.getMessage());

            return false;
        }

        return true;
    }

    public static boolean save(File file, final FileInputStream inputStream) {

        try {

            final FileOutputStream output = new FileOutputStream(file);

            output.write(IOUtils.toByteArray(inputStream));

            output.close();

        } catch (final FileNotFoundException e) {
            Logger.logError(TAG, e.getMessage());

            return false;

        } catch (final IOException e) {
            Logger.logError(TAG, e.getMessage());

            return false;
        }

        return true;
    }
}
