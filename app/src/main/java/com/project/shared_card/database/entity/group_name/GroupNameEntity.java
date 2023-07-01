package com.project.shared_card.database.entity.group_name;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_name")
public class GroupNameEntity implements Parcelable {
    @PrimaryKey()
    private long id;
    private String name;

    private byte[] photo;

    public GroupNameEntity(long id, String name, byte[] photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;

    }

    protected GroupNameEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            photo = in.readBlob();
        }
    }

    public static final Creator<GroupNameEntity> CREATOR = new Creator<GroupNameEntity>() {
        @Override
        public GroupNameEntity createFromParcel(Parcel in) {
            return new GroupNameEntity(in);
        }

        @Override
        public GroupNameEntity[] newArray(int size) {
            return new GroupNameEntity[size];
        }
    };

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeBlob(photo);
    }
}
