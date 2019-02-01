package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "pizza",
        foreignKeys = @ForeignKey(
                entity = BaseEntity.class,
                parentColumns = "base__id",
                childColumns = "pizza__base_id"
        ),
        indices = @Index("pizza__base_id")
)
public class PizzaEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pizza__id")
    public long id;
    @ColumnInfo(name = "pizza__name")
    public String name;
    @ColumnInfo(name = "pizza__is_custom")
    public boolean isCustom;
    @ColumnInfo(name = "pizza__base_id")
    public long baseId;
    @ColumnInfo(name = "pizza__is_deleted")
    public boolean isDeleted;

    public PizzaEntity() {
    }

    @Ignore
    public PizzaEntity(String name, long baseId) {
        this.name = name;
        this.baseId = baseId;
        this.isCustom = true;
    }
}
