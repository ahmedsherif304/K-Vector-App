package com.example.ahmedsherif.K_Vector;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
            Intent intent2 = getIntent();//changing the intent
            textView= findViewById(R.id.t);
            String s = intent2.getStringExtra("URL");//getting the string from the other activity
            new ArticleAsyncTask().execute(s);//executing parallel thread
        }

    class ArticleAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return new Network().getArticle(strings[0]);//calling method get article in Network class
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);//setting the text to the text view
        }
    }
}
