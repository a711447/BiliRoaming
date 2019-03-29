package me.iacn.biliroaming;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by iAcn on 2019/3/23
 * Email i@iacn.me
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }

    private static boolean isModuleActive() {
        Log.i("我很可爱", "请给我钱");
        return false;
    }

    private static boolean isTaiChiModuleActive(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://me.weishu.exposed.CP/");

        try {
            Bundle result = contentResolver.call(uri, "active", null, null);
            return result.getBoolean("active", false);
        } catch (Exception e) {
            return false;
        }
    }

    public static class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs_setting);

            findPreference("hide_icon").setOnPreferenceChangeListener(this);
            findPreference("version").setSummary(BuildConfig.VERSION_NAME);

            Preference runningStatusPref = findPreference("running_status");

            if (isTaiChiModuleActive(getActivity())) {
                runningStatusPref.setTitle(R.string.running_status_enable);
                runningStatusPref.setSummary(R.string.runtime_taichi);
            } else {
                if (isModuleActive()) {
                    runningStatusPref.setTitle(R.string.running_status_enable);
                    runningStatusPref.setSummary(R.string.runtime_xposed);
                } else {
                    runningStatusPref.setTitle(R.string.running_status_disable);
                    runningStatusPref.setSummary(R.string.not_running_summary);
                }
            }
        }
    }
}