package com.example.andreykochetkov.rk.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andreykochetkov.rk.Service.NewsService;
import com.example.andreykochetkov.rk.Service.ServiceHelper;
import com.example.andreykochetkov.rk.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.mail.weather.lib.*;


public class MainActivity extends AppCompatActivity implements ServiceHelper.Callback{

    private TextView topicTextView;
    private TextView contentTextView;
    private TextView dateTextView;

    private Storage storage;


    private final ServiceHelper serviceHelper = ServiceHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingsActivity = (Button) findViewById(R.id.btnSettings);
        settingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNews();
            }
        });
        findViewById(R.id.btnOk).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BackUpdate(true);
                    }
                }
        );
        findViewById(R.id.btnCancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BackUpdate(false);
                    }
                }
        );
        serviceHelper.setCallback(this);


    }

    @Override
    public void onNewsLoad(int code) {
        topicTextView.setText("все фигово");
        if (code == NewsService.NEWS_SUCCESS_ACTION) {
            News news = storage.getLastSavedNews();
            updateContent(news);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //загружаем текущий топик
        storage = Storage.getInstance(this);
        String currentTopic = storage.loadCurrentTopic();
        if (currentTopic == "") {
            currentTopic = Topics.ALL_TOPICS[0];
        }
        topicTextView = (TextView) findViewById(R.id.tvTopic);
        topicTextView.setText(currentTopic);

        //пытаемся загрузить новую новость
        updateNews();
        //берем последнюю сохраненную из базы
        News news = storage.getLastSavedNews();
        if (news != null) {
            updateContent(news);
        }


    }

    private void updateNews() {
        serviceHelper.requestNews(this);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }



    private void updateContent(News news) {
        String title = news.getTitle();
        String content = news.getBody();

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        topicTextView.setText(title);
        contentTextView.setText(content);
        dateTextView.setText(simpleDateFormat.format(news.getDate()));
    }

    private void BackUpdate(boolean update) {
        Intent intent = new Intent(this, NewsService.class);
        Scheduler scheduler = Scheduler.getInstance();
        if (update) {
            scheduler.schedule(this, intent, 60*1000L);
        }
        else {
            scheduler.unschedule(this, intent);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceHelper.setCallback(null);
    }
}
