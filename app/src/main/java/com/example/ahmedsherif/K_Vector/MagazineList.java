package com.example.ahmedsherif.K_Vector;

import java.net.URL;
import java.util.ArrayList;

public class MagazineList {
    ArrayList<String> title;
    ArrayList<String> url;
    ArrayList<String> images;
    MagazineList(ArrayList<String> s, ArrayList<String> u){
        title=s;
        url=u;
    }
    void setTitle(ArrayList<String> s){title=s;}
    void setUrl(ArrayList<String> u){url=u;}
    ArrayList<String> getUrl(){return url;}
    ArrayList<String> getTitle(){return title;}
}
