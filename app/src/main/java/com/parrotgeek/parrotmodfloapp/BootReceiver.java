package com.parrotgeek.parrotmodfloapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {

    private boolean isAdminUser(Context context) {
        UserHandle uh = Process.myUserHandle();
        UserManager um = (UserManager) context.getSystemService(Context.USER_SERVICE);
        return (um != null) && (um.getSerialNumberForUser(uh) == 0);
    }

    public void onReceive(Context arg0, Intent arg1) {
        if (isAdminUser(arg0)) {
            Intent intent = new Intent(arg0, MyService.class);
            arg0.stopService(intent);
            arg0.startService(intent);
            if (arg1.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
                Log.d("BootReceiver", "reenable icon");
                PackageManager pm = arg0.getPackageManager();
                pm.setComponentEnabledSetting(new ComponentName(arg0, MainActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            }
            Log.i("BootReceiver", "started");
        } else {
            Log.i("BootReceiver", "not first user, so disable myself");
            PackageManager pm = arg0.getPackageManager();
            pm.setComponentEnabledSetting(new ComponentName(arg0, MainActivity.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);

        }
    }
}