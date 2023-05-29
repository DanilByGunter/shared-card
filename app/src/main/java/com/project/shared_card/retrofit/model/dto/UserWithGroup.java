package com.project.shared_card.retrofit.model.dto;

import com.project.shared_card.retrofit.model.TheGroupId;

public class UserWithGroup {
    private long id_group;
    private String name_group;
    private TheGroupId user;

    public UserWithGroup(long idGroup, String nameGroup, TheGroupId user) {
        this.id_group = idGroup;
        this.name_group = nameGroup;
        this.user = user;

    }

    public UserWithGroup() {
    }

    public TheGroupId getUser() {
        return user;
    }

    public void setUser(TheGroupId user) {
        this.user = user;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }

    public long getId_group() {
        return id_group;
    }

    public void setId_group(long id_group) {
        this.id_group = id_group;
    }

}
