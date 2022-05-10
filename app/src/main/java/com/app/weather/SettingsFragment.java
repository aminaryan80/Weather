package com.app.weather;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        SwitchPreference switchPreference = findPreference("dark_mode");
        assert switchPreference != null;
        switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            setOnSwitchChangeListener(!switchPreference.isChecked());
            return true;
        });
    }

    public static void setOnSwitchChangeListener(boolean darkMode) {
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }
    }
}