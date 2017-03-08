package com.example.andreykochetkov.rk.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

import java.io.IOException;

import ru.mail.weather.lib.*;

public class NewsService extends IntentService {

    public static final String ACTION_NEW_NEWS = "action_new_news";
    public final static int NEWS_SUCCESS_ACTION = 1;
    public final static int NEWS_ERROR_ACTION = 0;


    public NewsService() {
        super("NewsService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(ACTION_NEW_NEWS);
        String topic = Storage.getInstance(this).loadCurrentTopic();
        try {
            News news = new NewsLoader().loadNews(topic);
            Storage.getInstance(this).saveNews(news);
            if (receiver != null) {
                receiver.send(NEWS_SUCCESS_ACTION, null);
            }
        } catch (IOException e) {
            if (receiver != null) {
                receiver.send(NEWS_ERROR_ACTION, null);
            }
        }
    }
}