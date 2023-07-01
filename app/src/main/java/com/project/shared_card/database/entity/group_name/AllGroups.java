package com.project.shared_card.database.entity.group_name;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group.GroupWithUser;

import java.util.List;

public class AllGroups implements Parcelable {
        @Embedded
        public GroupNameEntity groupName;
        @Relation(parentColumn = "id",entityColumn = "group_name_id",entity = GroupEntity.class)
        public List<GroupWithUser> groupEntities;

    public AllGroups() {
    }

    protected AllGroups(Parcel in) {
    }

    public static final Creator<AllGroups> CREATOR = new Creator<AllGroups>() {
        @Override
        public AllGroups createFromParcel(Parcel in) {
            return new AllGroups(in);
        }

        @Override
        public AllGroups[] newArray(int size) {
            return new AllGroups[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
