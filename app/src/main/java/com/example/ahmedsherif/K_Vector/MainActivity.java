package com.example.ahmedsherif.K_Vector;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> titles,urls;
    ArrayAdapter<String> adapter;
    ListView L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("lolo","beg1");
        MagazineAsyncTask magazineAsyncTask=new MagazineAsyncTask();
        magazineAsyncTask.execute(); //executing the parallel thread
        Log.i("lolo","execute");
        titles=new ArrayList<>();
        Log.i("lolo","array list");
        L=findViewById(R.id.l1);



        adapter=new ArrayAdapter<String>(this,R.layout.listview,titles);
        Log.i("lolo","adapter");
        L.setAdapter(adapter);
    }



    class MagazineAsyncTask extends AsyncTask<Void,Void,MagazineList>{


        @Override
        protected MagazineList doInBackground(Void... voids) {//working in a thread parallel to the ui thread
            MagazineList magazineList=new Network().getMagazineList();
            return magazineList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(MagazineList magazineList) {
            super.onPostExecute(magazineList);
            adapter.clear();
            Log.i("lolo","clear");
            adapter.addAll(magazineList.getTitle());///adding the titles to preview
            urls=magazineList.getUrl();///getting the urls array

        }
    }
}
