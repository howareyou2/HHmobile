
package com.example.a4cutdiary;

public class Album {

    private int id;
    private String diary;
    private byte[] image;

    public Album(String diary, byte[] image, int id) {
        this.diary = diary;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

