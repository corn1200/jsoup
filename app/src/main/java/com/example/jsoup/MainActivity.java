package com.example.jsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private String htmlPageUrl = "https://trends.google.co.kr/trends/trendingsearches/daily?geo=KR";
    private TextView textViewHtmlDocument;
    private String htmlContentInStringFormat;
    private WebView webView;
    private String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavascriptInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.Android.getHtml"
                        + "(document.getElementsByTagName('body')[0].innerHTML);");
            }
        });
        webView.loadUrl(htmlPageUrl);

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
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Parser.parse(source, htmlPageUrl);
                Elements links = doc.select("span[ng-repeat] span");

                for (Element link : links) {
                    htmlContentInStringFormat += link.text().trim() + "\n";
                }
            } catch (Exception e) {
                htmlContentInStringFormat += "error" + "\n";
                Log.d("error", "error");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textViewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }

    private class MyJavascriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            source = html;
        }
    }

    private class ExampleThread extends Thread {

    }
}