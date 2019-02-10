package k36tee.agent.Pref;

import android.content.Context;

/**
 * Created by myron echenim  on 8/14/16.
 */
public class AppPref {
    public static final String AGENT_EMAIL = "k36tee.agent.agent.Pref.AGENT_EMAIL";
    public static final String AGENT_NAME = "k36tee.agent.agent.Pref.AGENT_NAME";
    public static final String CURRENT_BALANCE = "k36tee.agent.agent.Pref.CURRENT_BALANCE";
    public static final String HAS_LOGIN = "k36tee.agent.agent.Pref.HAS_LOGIN";
    public static final String AGENT_LAT = "k36tee.agent.agent.Pref.AGENT_LAT";
    public static final String AGENT_LNG = "k36tee.agent.agent.Pref.AGENT_LNG";

    public void setStringValue(Context context, String key, String value) {
        context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).edit().putString(AppPref.class.getName() + '.' + key, value).commit();
    }


    public String getStringValue(Context context, String key) {
        return context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).getString(AppPref.class.getName() + "." + key, "");
    }

    public void setBooleanValue(Context context, String key, boolean value) {
        context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).edit().putBoolean(AppPref.class.getName() + '.' + key, value).commit();

    }


    public boolean getBooleanValue(Context context, String key) {
        return context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).getBoolean(AppPref.class.getName() + "." + key, false);

    }

    public void setLongValue(Context context, String key, long value) {
        context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).edit().putLong(AppPref.class.getName() + '.' + key, value).commit();

    }


    public long getLongValue(Context context, String key) {
        return context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).getLong(AppPref.class.getName() + "." + key, 0l);

    }

    public void setFloatValue(Context context, String key, float value) {
        context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).edit().putFloat(AppPref.class.getName() + '.' + key, value).commit();

    }


    public float getFloatValue(Context context, String key) {
        return context.getSharedPreferences(AppPref.class.getName(), Context.MODE_PRIVATE).getFloat(AppPref.class.getName() + "." + key, 0l);

    }
}
