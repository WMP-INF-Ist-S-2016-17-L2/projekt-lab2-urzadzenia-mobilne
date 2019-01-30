package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "base"
)
public class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "base__id")
    public long id;
    @ColumnInfo(name = "base__name")
    public String name;

    public BaseEntity() {
    }

    @Ignore
    public BaseEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public BaseEntity(String name) {
        this.name = name;
    }
}
