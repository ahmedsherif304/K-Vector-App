package com.example.ahmedsherif.K_Vector;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.COLUMN_NAME_URL;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> titles, urls;
    ArrayAdapter<String> adapter;
    ListView L;
    Intent intent;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper=new DatabaseHelper(this);
        intent = new Intent(this, Main2Activity.class);
        L = findViewById(R.id.l1);
        titles = new ArrayList<>();
        urls = new ArrayList<>();
        if (!isNetworkAvailable(this))
        {
            // to check whether the internet is connected or not
            showToast("There is no internet connection ");
            Cursor data=databaseHelper.GetData();
            if (data.getCount()==0)//if it has no elements so No data is saved
                showToast("i Don't have any data please connect to the internet");
            else {
                while (data.moveToNext())//if it has more data
                {//insert this data in the array lists
                    String Title = data.getString(data.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
                    titles.add(Title);
                    String URL=data.getString(data.getColumnIndexOrThrow(COLUMN_NAME_URL));
                    urls.add(URL);
                }
                data.close();//releasing the cursor from it is data
                adapter = new ArrayAdapter<>(this, R.layout.listview,titles);//attaching the list view with the array list titles
                L.setAdapter(adapter);
                L.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    ////when clicking in any item it gives it's position to use it to get the url
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intent.putExtra("ID", String.valueOf(position));//putting the position of the url into the intent
                        intent.putExtra("URL",urls.get(position));
                        startActivity(intent);///starting the new intent
                    }
                });
            }
        }


            else {
                MagazineAsyncTask magazineAsyncTask = new MagazineAsyncTask();//creating new async task to perform in background
                magazineAsyncTask.execute(); //executing the parallel thread
                adapter = new ArrayAdapter<>(this, R.layout.listview, titles);//attaching the list view with the array list titles
                L.setAdapter(adapter);
                L.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    ////when clicking in any item it gives it's position to use it to get the url
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            intent.putExtra("URL", urls.get(position));//putting the url string into the intent
                            intent.putExtra("ID", String.valueOf(position));//putting the position of the url into the intent
                            // showToast(String.valueOf(position));
                            startActivity(intent);///starting the new intent
                    }
                });
        }
    }


    class MagazineAsyncTask extends AsyncTask<Void, Void, MagazineList> {


        @Override
        protected MagazineList doInBackground(Void... voids) {//working in a thread parallel to the ui thread
            return new Network(databaseHelper).getMagazineList();
        }

        @Override
        protected void onPostExecute(MagazineList magazineList) {
            super.onPostExecute(magazineList);
            adapter.clear();//releasing the data in the adapter to prevent the duplicates
            adapter.addAll(magazineList.getTitle());///adding the titles to preview
            urls = magazineList.getUrl();///getting the urls array

        }
    }

    public boolean isNetworkAvailable(Context context) {
        //boolean function to check the internet connection
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


}