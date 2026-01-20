package com.viewnext.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.viewnext.data.local.entity.FacturaEntity;

import java.util.List;

@Dao
public interface FacturaDao {
    @Query("SELECT * FROM facturas")
    LiveData<List<FacturaEntity>> getFacturas();

    @Query("SELECT * FROM facturas")
    List<FacturaEntity> getFacturasDirect();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FacturaEntity> facturas);

    @Query("DELETE FROM facturas")
    void deleteAll();
}
