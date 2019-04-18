package com.example.ahmedsherif.K_Vector;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.ID;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.TEXT;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    String pos;
    DatabaseHelper2 databaseHelper2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent2 = getIntent();//changing the intent
        databaseHelper2=new DatabaseHelper2(this);
        textView = findViewById(R.id.t);
        pos = intent2.getStringExtra("ID");
        if (isNetworkAvailable(this)) {
            String s = intent2.getStringExtra("URL");//getting the string from the other activity
            new ArticleAsyncTask().execute(s);//executing parallel thread
        }
        else
        {
            Cursor data=databaseHelper2.GetData();
            if (data.getCount()==0)
                showToast("Please connect to the internet");
            else
            {
                boolean f=false;
                while(data.moveToNext())
                {
                    String id = data.getString(data.getColumnIndexOrThrow(ID));
                    if (id.equals(pos)) {
                        f=true;
                        String txt = data.getString(data.getColumnIndexOrThrow(TEXT));
                        textView.setText(txt);
                    }
                }
                if (!f)
                {
                    showToast("For sorry i didn't save the article yet");
                }
            }
        }
    }

    class ArticleAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return new Network(databaseHelper2).getArticle(strings[0],pos);//calling method get article in Network class
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);//setting the text to the text view
        }
    }
    public void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public boolean isNetworkAvailable(Context context) {
        //boolean function to check the internet connection
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
