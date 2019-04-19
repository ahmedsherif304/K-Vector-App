package com.example.ahmedsherif.K_Vector;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        if (isNetworkAvailable(this)) {
            DataAsyncTask dataAsyncTask = new DataAsyncTask();//creating new async task to perform in background
            dataAsyncTask.execute(); //executing the parallel thread
        }
    }


    class DataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            new Network(databaseHelper).SaveData();
            return null;
        }
    }

    public boolean isNetworkAvailable(Context context) {
        //boolean function to check the internet connection
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void Magazines(View v){//to start magazines intent
        intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }
    public void Academic(View v){

        showBuilder("Sorry,","It is Not Completed yet");
    }
    public void Events(View v){

        showBuilder("Sorry,","It is Not Completed yet");
    }
    public void Juniors(View v){

        showBuilder("Sorry,","It is Not Completed yet");
    }

    public void about(View v){
        showBuilder("About Us","\n" +
                "\n" +
                "K-vector is a student activity at Cairo university , Faculty of Engineering.\n" +
                "\n" +
                "We have a certain vision to build a generation that can build first car’s factory in Egypt which manufacture cars from A to Z , Make a change in R&D of artificial intelligence development & Build the first skyscraper in Egypt.\n");
    }

    public void showBuilder(String title,String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(s);
        builder.show();
    }
}