/*
 * SPDX-FileCopyrightText: 2021 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "GoogleParts";
    // A country where 5 GHz and 6 GHz hotspots and Wi-Fi repeater are allowed.
    private static final String WIFI_COUNTRY_CODE = "GB";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "Received boot completed intent");
        EuiccDisabler.enableOrDisableEuicc(context);
        overrideWifiCountryCode(context);
    }

    private static void overrideWifiCountryCode(Context context) {
        final WifiManager wifiManager = context.getSystemService(WifiManager.class);
        if (wifiManager == null) {
            return;
        }
        try {
            wifiManager.setOverrideCountryCode(WIFI_COUNTRY_CODE);
        } catch (SecurityException | UnsupportedOperationException e) {
            Log.e(TAG, "Failed to override Wi-Fi country code", e);
        }
    }
}
