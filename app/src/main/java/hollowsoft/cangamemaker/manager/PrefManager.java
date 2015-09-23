package hollowsoft.cangamemaker.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import hollowsoft.cangamemaker.utility.Logger;

public class PrefManager extends BaseManager implements IPref {

    private static final String TAG = PrefManager.class.getSimpleName();

    private static final String NAME = "MainPref";

    private ObjectMapper mapper;

    private SharedPreferences pref;

    public PrefManager(final Context context) {
        super(context);
    }

    private ObjectMapper getMapper() {

        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        return mapper;
    }

    private SharedPreferences getPref() {

        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        }

        return pref;
    }

    @Override
    public <T> T get(final String key) {

        final String json = getPref().getString(key, new String());

        try {

            return getMapper().readValue(json, new TypeReference<T>() {
            });

        } catch (IOException e) {
            Logger.logError(TAG, e.getMessage());
        }

        return null;
    }

    @Override
    public <T> boolean put(final String key, final T entity) {

        try {

            final String json = getMapper().writeValueAsString(entity);

            return getPref().edit()
                            .putString(key, json)
                            .commit();

        } catch (JsonProcessingException e) {
            Logger.logError(TAG, e.getMessage());
        }

        return false;
    }

    @Override
    public boolean remove(final String key) {

        return getPref().edit()
                        .remove(key)
                        .commit();
    }
}
