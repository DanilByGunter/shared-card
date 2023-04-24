package com.project.shared_card.retrofit.model;

import java.util.Arrays;

public class User {
    private long id;
    private String name;
    private byte[] photo;

    public User(String name, byte[] photo) {
        this.name = name;
        this.photo = photo;
    }

    public long getId_user() {
        return id;}

    public void setId_user(long id) {
        this.id = id;}

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public byte[] getPhoto() {
        return photo;}

    public void setPhoto(byte[] photo) {
        this.photo = photo;}

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
