package com.example.manifest_declared_broadcasts_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by aruna on 1/21/18.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations

    private  int lastState = TelephonyManager.CALL_STATE_IDLE;
    private  Date callStartTime;
    private  boolean isIncoming;
    private  String savedNumber;  //because the passed incoming is only valid in ringing


    @Override
    public void onReceive(Context context, Intent intent) {

        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            Toast.makeText(context,"Ringing State Number is -"+number,Toast.LENGTH_SHORT).show();
        }
        if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
            Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
        }



    }

    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    public void onCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                Log.e("Number",savedNumber.toString());

                Toast.makeText(context,"Number", Integer.parseInt(savedNumber.toString()));
//                onIncomingCallReceived(context, number, callStartTime);
                break;
        }
        lastState = state;
    }
}
