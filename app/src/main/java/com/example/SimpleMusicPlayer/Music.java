package com.example.SimpleMusicPlayer;

import java.io.Serializable;

public class Music implements Serializable {
    String titleJgh;
    int id;
    String path;

    public Music(String nameJgh, int id, String path) {
        this.titleJgh = nameJgh;
        this.id = id;
        this.path = path;
    }

    public String getTitleJgh() {
        return titleJgh;
    }

    public int getId() {
        return id;
    }

    public String getMusicPah() {
        return path;
    }


}
