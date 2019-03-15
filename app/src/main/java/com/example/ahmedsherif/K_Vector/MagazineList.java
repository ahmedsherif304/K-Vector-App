package com.example.ahmedsherif.K_Vector;

import java.net.URL;

public class MagazineList {
    String title;
    URL url;
    MagazineList(String s,URL u){
        title=s;
        url=u;
    }
    void setTitle(String s){title=s;}
    void setUrl(URL u){url=u;}
    URL getUrl(){return url;}
    String getTitle(){return title;}
}
