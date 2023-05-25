package com.project.shared_card.retrofit.model.dto;

public class UserWithGroup {
    private long id_group;
    private String name_group;
    private long id_user;

    public UserWithGroup(long idGroup, String nameGroup, long idUser) {
        this.id_group = idGroup;
        this.name_group = nameGroup;
        this.id_user = idUser;

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

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
}
