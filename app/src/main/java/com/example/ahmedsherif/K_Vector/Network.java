package com.example.ahmedsherif.K_Vector;


import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



public class Network {
    private String api="http://kvectorfoundation.com/k19/magazine/",jsonstring,htmltext;
    private Scanner scanner;
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
    void SaveData(){
        try {
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
                databaseHelper.insert(jsonObject.getString("title"),jsonObject.getString("url"));//inserting data to the database
            }
            httpURLConnection.disconnect();///closing the connection with the website
        }
        ////catching some excepted errors
        catch (Exception e){
            Log.i("lol ","message "+e.getMessage());
        }
    }
}

