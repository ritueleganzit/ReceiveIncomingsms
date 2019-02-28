package com.receiveincomingsms;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by eleganz on 11/2/19.
 */

public class SMSListener extends BroadcastReceiver {
    private String condata;
    private String number,phone;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        Object[] pdus = new Object[0];
        if (data != null) {
            pdus = (Object[]) data.get("pdus"); // the pdus key will contain the newly received SMS
        }

        if (pdus != null) {
            for (Object pdu : pdus) { // loop through and pick up the SMS of interest
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);

                // your custom logic to filter and extract the OTP from relevant SMS - with regex or any other way.

                String msg=smsMessage.getMessageBody();
                phone = smsMessage.getDisplayOriginatingAddress();
                Log.d("receiver",msg);
                if (msg.equalsIgnoreCase("normal"))
                {
                    AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
/*
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                            && !notificationManager.isNotificationPolicyAccessGranted()) {
                        Intent intent1 = new Intent(
                                android.provider.Settings
                                        .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        context.startActivity(intent1);
                    }*/
                }


                    if ((containsIgnoreCase(msg,"#"))==true) {
                        ContentResolver contentResolver = context.getContentResolver();


                        String[] items = msg.split(("#"));
                        for (String item : items) {
                            Log.d("hhhhh", item);
                            condata = item;
                        }
                        Cursor cursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                                new String[]{condata}, null);

                        while (cursor.moveToNext()) {
                            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                        }

                        if (number == null) {


                        } else {
                            Log.d("hhhhh", number);
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phone, null, "" + number, null, null);

                        }
                    }
                }

        }
    }

    public static boolean containsIgnoreCase(final String str, final String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int len = searchStr.length();
        final int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

}
