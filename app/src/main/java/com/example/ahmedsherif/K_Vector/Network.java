package com.example.ahmedsherif.K_Vector;

import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Network {
    String api="http://kvectorfoundation.com/k19/magazine";
    ArrayList<String> titles,urls;
    MagazineList magazinelist;
    HttpURLConnection httpURLConnection;
    MagazineList getMagazineList(){
        try {
            URL url=new URL(api);
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");


            for (int i=0;i<10;i++){



            }


            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Hiii", "getMagazineList: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return magazinelist;}
}

