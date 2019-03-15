package com.example.ahmedsherif.K_Vector;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> magazines;
    ArrayAdapter<String> adapter;
    ListView L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        magazines=new ArrayList<>();
        L=findViewById(R.id.l1);






        adapter=new ArrayAdapter<String>(this,R.layout.listview,magazines);
        L.setAdapter(adapter);
    }



    class MagazineAsyncTask extends AsyncTask<Void,Void,MagazineList>{


        @Override
        protected MagazineList doInBackground(Void... voids) {
            return new Network().getMagazineList();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(MagazineList magazineList) {
            super.onPostExecute(magazineList);
            adapter.clear();
            adapter.addAll(magazines);
        }
    }
}
