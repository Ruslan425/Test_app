package ru.romazanov.testapp1.db;

import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

@androidx.room.Dao
public interface MyDao {

    @Query("SELECT * FROM authentity")
    Flowable<List<AuthEntity>> getAll();

    @Insert
    void insert(AuthEntity... authEntities);
}
