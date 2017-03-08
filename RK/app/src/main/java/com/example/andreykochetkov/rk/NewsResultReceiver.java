package com.example.andreykochetkov.rk;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.andreykochetkov.rk.Service.ServiceHelper;

public class NewsResultReceiver extends ResultReceiver {

    private ServiceHelper.Callback callback;

    public NewsResultReceiver(final Handler handler) {
        super(handler);
    }

    public void setCallback(ServiceHelper.Callback t_callback) {
        this.callback = t_callback;
    }

    @Override
    protected void onReceiveResult(int code, Bundle data) {
        super.onReceiveResult(code, data);
        if (callback != null) {
            callback.onNewsLoad(code);
        }
    }
}
