package com.renteasy.reciever;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by RUPESH on 9/10/2016.
 */
public class ProductReciever extends ResultReceiver {

    private Receiver mReceiver;

    public ProductReciever(Handler handler) {
        super(handler);

    }
    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
