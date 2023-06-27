package com.example.practicepe_roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "data")
public class dataEntity implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String Id;
    @ColumnInfo
    private String Name;
    @ColumnInfo
    private String Address;
    @ColumnInfo
    private String AvgScore;

    public dataEntity() {

    }

    public dataEntity(String id, String name, String address, String avgScore) {
        Id = id;
        Name = name;
        Address = address;
        AvgScore = avgScore;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAvgScore() {
        return AvgScore;
    }

    public void setAvgScore(String avgScore) {
        AvgScore = avgScore;
    }
}
