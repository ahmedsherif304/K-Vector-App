package com.example.ahmedsherif.K_Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> titles,urls;
    ArrayAdapter<String> adapter;
    ListView L;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            intent=new Intent(this,Main2Activity.class);
            MagazineAsyncTask magazineAsyncTask = new MagazineAsyncTask();
            magazineAsyncTask.execute(); //executing the parallel thread
            titles = new ArrayList<>();
            urls = new ArrayList<>();
            L = findViewById(R.id.l1);
            adapter = new ArrayAdapter<String>(this, R.layout.listview, titles);
            L.setAdapter(adapter);


            L.setOnItemClickListener(new AdapterView.OnItemClickListener() {////when clicking in any item it gives it's position to use it to get the url
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast toast = Toast.makeText(getApplicationContext(), urls.get(position), Toast.LENGTH_SHORT);
                    toast.show();
                    Log.i("lolo","Toast");
                    intent.putExtra("URL",urls.get(position));//putting the url string into the intent
                    Log.i("lolo","put extra");
                    startActivity(intent);///starting the new intent
                }
            });


        }
        catch (Exception e){
            Log.i("lolo",e.getMessage());

        }
    }



    class MagazineAsyncTask extends AsyncTask<Void,Void,MagazineList>{


        @Override
        protected MagazineList doInBackground(Void... voids) {//working in a thread parallel to the ui thread
            MagazineList magazineList=new Network().getMagazineList();
            return magazineList;
        }

        @Override
        protected void onPostExecute(MagazineList magazineList) {
            super.onPostExecute(magazineList);
            adapter.clear();
            adapter.addAll(magazineList.getTitle());///adding the titles to preview
            urls=magazineList.getUrl();///getting the urls array

        }
    }
}
