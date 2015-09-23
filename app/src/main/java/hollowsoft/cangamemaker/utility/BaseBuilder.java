package hollowsoft.cangamemaker.utility;

import android.content.Context;

public class BaseBuilder {

    private final Context context;

    public BaseBuilder(final Context context) {

        if (context == null) {
            throw new IllegalArgumentException("The context cannot be null.");
        }

        this.context = context;
    }

    public final Context getContext() {
        return context;
    }
}
