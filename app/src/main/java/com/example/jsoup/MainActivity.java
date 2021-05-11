package com.example.jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Document doc = null;

        try {
            doc = doc = Jsoup.connect("https://trends.google.co.kr/trends/?geo=KR").get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Elements elementData = doc.select(".list-item-title");
    }
}