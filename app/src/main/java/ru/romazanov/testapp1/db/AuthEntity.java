package ru.romazanov.testapp1.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AuthEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "code")
    public int code;

    @ColumnInfo(name = "auth_date")
    public String date;

    public AuthEntity(int code, String date) {
        this.code = code;
        this.date = date;
    }

    @Override
    public String toString() {
        return "AuthEntity{" +
                "uid=" + uid +
                ", code=" + code +
                ", date='" + date + '\'' +
                '}';
    }

    public String getCode() {
        return String.valueOf(code);
    }
}
