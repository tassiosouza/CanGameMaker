package hollowsoft.cangamemaker.utility;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import hollowsoft.cangamemaker.R;

public final class DialogBuilder extends BaseBuilder {

    public DialogBuilder(final Context context) {
        super(context);
    }

    public MaterialDialog.Builder load() {

        return new MaterialDialog.Builder(getContext())
                                 .titleColorRes(android.R.color.black)
                                 .contentColorRes(R.color.translucent_black_dark)
                                 .negativeColorRes(R.color.primary)
                                 .positiveColorRes(R.color.primary);
    }
}
