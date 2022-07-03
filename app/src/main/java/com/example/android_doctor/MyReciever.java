package com.example.android_doctor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import androidx.annotation.RequiresApi;

public class MyReciever extends BroadcastReceiver {

    public static SMSlistener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage smsMessage;

        if (Build.VERSION.SDK_INT >= 19) { //KITKAT
            SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            smsMessage = msgs[0];
        } else {
            Object pdus[] = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");

            smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0],format);
        }
        String messageBody = smsMessage.getMessageBody();
        String senderNo = smsMessage.getDisplayOriginatingAddress();

        mListener.messageReceived(messageBody, senderNo);

    }
    public static void bindListener(SMSlistener listener) {
        mListener = listener;
    }
}
