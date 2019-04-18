package com.example.ahmedsherif.K_Vector;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.DATABASE_NAME;


public class Network {
    private String api="http://kvectorfoundation.com/k19/magazine/",jsonstring,htmltext;
    private Scanner scanner;
    private ArrayList<String> titles,urls;
    private MagazineList  magazinelist;
    private HttpURLConnection httpURLConnection;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private DatabaseHelper databaseHelper;
    private DatabaseHelper2 databaseHelpert;

    public Network(DatabaseHelper databaseHelper1){databaseHelper=databaseHelper1;}
    //a constructor to insert the data into the first database
    public Network(DatabaseHelper2 databaseHelper2){databaseHelpert=databaseHelper2;}
    //a constructor to insert the data into the second database
    String getArticle(String u,String pos){////the Same as magazine list but getting article
        try {
            URL url = new URL(u);
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream=httpURLConnection.getInputStream();
            scanner =new Scanner(inputStream).useDelimiter("\\A");
            jsonstring=scanner.hasNext()?scanner.next():"";
            jsonObject=new JSONObject(jsonstring);
            jsonstring= jsonObject.getString("data");
            jsonObject=new JSONObject(jsonstring);
            jsonstring= jsonObject.getString("body");
            httpURLConnection.disconnect();
            htmltext= Html.fromHtml(jsonstring).toString();///converting the html string into html to view as page
            databaseHelpert.insert(pos,htmltext);

        }
        catch (Exception e){
            Log.i("lol",e.getMessage());
        }
        return htmltext;
    }
    MagazineList getMagazineList(){
        try {
            titles=new ArrayList<>();
            urls=new ArrayList<>();
            URL url=new URL(api); ///Converting string into useful URL
            httpURLConnection=(HttpURLConnection) url.openConnection();///opening the connection with website
            httpURLConnection.setRequestMethod("GET"); //////choosing the method to connect which is GET to get data from the URL
            InputStream inputStream;
            inputStream=httpURLConnection.getInputStream();//getting the data from the website
            scanner =new Scanner(inputStream).useDelimiter("\\A");//changing this data into string using scanner "\\A" means all the data
            jsonstring=scanner.hasNext()? scanner.next():"";//changing the scanner into string ""if it has any data""
            jsonArray=new JSONArray(jsonstring);// converting the string into json array

            databaseHelper.renew();//READ THE COMMENT IN THE CLASS ITSELF
            for (int i=0;i<jsonArray.length();i++){ ///getting objects of the array
                jsonObject=jsonArray.getJSONObject(i); //getting the  object
                titles.add(jsonObject.getString("title")); ///adding the object with key title into the titles array
                urls.add(jsonObject.getString("url")); //adding the object with key url to urls array
                databaseHelper.insert(jsonObject.getString("title"),jsonObject.getString("url"));//inserting data to the database
            }
            magazinelist=new MagazineList(titles,urls);///making new object of  Magazine List and adding titles and urls to it
            httpURLConnection.disconnect();///closing the connection with the website
        }
        ////catching some excepted errors
        catch (Exception e){
            Log.i("lol ","message "+e.getMessage());
        }
        return magazinelist; ///returning the information to the main activity
    }
}

