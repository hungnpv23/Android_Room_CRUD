package com.example.practicepe_roomdb.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.practicepe_roomdb.dataEntity;

import java.util.List;
@Dao
public interface dataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData (dataEntity data);

    @Update
    void updateData (dataEntity data);

    @Delete
    void deleteData(dataEntity data);

    @Query("select * from data")
    List<dataEntity> getDataList();

    @Query("select * from data where Id= :id")
    List<dataEntity> checkData(String id);

}
