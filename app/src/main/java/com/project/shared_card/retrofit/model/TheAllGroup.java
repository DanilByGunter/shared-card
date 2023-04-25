package com.project.shared_card.retrofit.model;

import java.util.Arrays;

public class TheAllGroup {
    private long id;
    private String name;
    private byte[] photo;
    public long getId() {
        return id;}

    public void setId(long id) {
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
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
