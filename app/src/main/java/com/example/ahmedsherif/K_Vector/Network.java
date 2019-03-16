package com.example.ahmedsherif.K_Vector;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;


public class Network {
    private String api="http://kvectorfoundation.com/k19/magazine/",jsonstring;
    private Scanner scanner;
    private ArrayList<String> titles,urls;
    private MagazineList  magazinelist;
    private HttpURLConnection httpURLConnection;
    private JSONArray jsonArray;
    private JSONObject jsonObject;


    MagazineList getMagazineList(){
        try {
            Log.i("lolo","beg2");
            titles=new ArrayList<>();
            urls=new ArrayList<>();
            URL url=new URL(api); ///Converting string into useful URL
            Log.i("lolo","url");
            Log.i("lolo","url connected");
            httpURLConnection=(HttpURLConnection) url.openConnection();///opening the connection with website
            Log.i("lolo","http url");
            httpURLConnection.setRequestMethod("GET"); //////choosing the method to connect which is GET to get data from the URL
            Log.i("lolo","GET");
            InputStream inputStream;
            Log.i("lolo","input stream 1");
            inputStream=httpURLConnection.getInputStream();//getting the data from the website
            Log.i("lolo","get input stream 2");
            scanner =new Scanner(inputStream,"utf-8").useDelimiter("\\A");//changing this data into string using scanner "\\A" means all the data
            Log.i("lolo","scanner");
            jsonstring=scanner.hasNext()? scanner.next():"";//changing the scanner into string ""if it has any data""
            Log.i("lolo","scanner to json");
            jsonArray=new JSONArray(jsonstring);// converting the string into json array
            Log.i("lolo","json array");
            for (int i=0;i<jsonArray.length();i++){ ///getting objects of the array
                Log.i("lolo","For Loop");
                jsonObject=jsonArray.getJSONObject(i); //getting the  object
                titles.add(jsonObject.getString("title")); ///adding the object with key title into the titles array
                urls.add(jsonObject.getString("url")); //adding the object with key url to urls array
            }
            magazinelist=new MagazineList(titles,urls);///making new object of  Magazine List and adding titles and urls to it
            httpURLConnection.disconnect();///closing the connection with the website
            Log.i("lolo","Disconnect");
        }
        ////catching some excepted errors
        catch (Exception e){
            Log.i("lolo ","message "+e.getMessage());
        }
        return magazinelist; ///returning the information to the main activity
    }
}

