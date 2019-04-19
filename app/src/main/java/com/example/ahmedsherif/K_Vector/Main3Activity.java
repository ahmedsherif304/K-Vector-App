package com.example.ahmedsherif.K_Vector;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.COLUMN_NAME_URL;

public class Main3Activity extends AppCompatActivity {

    ArrayList<String> titles, urls;
    ArrayAdapter<String> adapter;
    ListView L;
    Intent intent;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        titles=new ArrayList<>();
        urls=new ArrayList<>();
        databaseHelper=new DatabaseHelper(this);
        L = findViewById(R.id.l1);
        intent = new Intent(this, Main2Activity.class);
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
                        startActivity(intent);///starting the new intent "Activity2"
                    }
                });
            }



    }

    public void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void Home(View v){
        intent =new Intent (this,MainActivity.class);
        startActivity(intent);
    }


}
