package ru.romazanov.testapp1.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AuthEntity.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract MyDao dao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}
