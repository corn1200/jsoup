package com.example.jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String htmlPageUrl = "https://trends.google.com/trends/?geo=KR";
    private TextView textViewHtmlDocument;
    private String htmlContentInStringFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHtmlDocument = findViewById(R.id.textView);
        textViewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        Button htmlTitleButton = findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements links = doc.select(".list-item-title");

                for (Element link : links) {
                    htmlContentInStringFormat += link.text() + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textViewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }

}