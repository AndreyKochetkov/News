package com.example.andreykochetkov.rk.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.andreykochetkov.rk.NewsResultReceiver;


public class ServiceHelper {

    private static final ServiceHelper instance = new ServiceHelper();

    private NewsResultReceiver resultReceiver = new NewsResultReceiver(new Handler());

    private ServiceHelper() {
    }

    public static ServiceHelper getInstance() {
        return instance;
    }

    public void requestNews(Context context) {
        Intent intent = new Intent(context, NewsService.class);
        intent.putExtra(NewsService.ACTION_NEW_NEWS, resultReceiver);
        context.startService(intent);
    }

    public void setCallback(Callback callback) {
        resultReceiver.setCallback(callback);
    }

    public interface Callback {
        void onNewsLoad(int code);
    }

}