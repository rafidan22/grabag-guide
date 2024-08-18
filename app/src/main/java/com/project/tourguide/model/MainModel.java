package com.project.tourguide.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MainModel implements Parcelable {

    private String uid;
    private String judul;
    private String kategori;
    private String deskripsi;
    private String image;

    public MainModel(){}

    protected MainModel(Parcel in) {
        uid = in.readString();
        judul = in.readString();
        kategori = in.readString();
        deskripsi = in.readString();
        image = in.readString();
    }

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(judul);
        parcel.writeString(kategori);
        parcel.writeString(deskripsi);
        parcel.writeString(image);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
