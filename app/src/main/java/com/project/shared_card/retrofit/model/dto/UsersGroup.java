package com.project.shared_card.retrofit.model.dto;



import java.util.Arrays;

public class UsersGroup {

    private Long id;

    private String name;

    private byte[] photo;

    private int status;

    public UsersGroup() {}

    public UsersGroup(Long id, String name, byte[] photo, int status) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.status = status;
    }

    public long getId() {
        return id;}

    public void setId(Long id) {
        this.id = id;}

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public byte[] getPhoto() {
        return photo;}

    public void setPhoto(byte[] photo) {
        this.photo = photo;}

    public int getStatus() {
        return status;}

    public void setStatus(int status) {
        this.status = status;}

    @Override
    public String toString() {
        return "UsersGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", status=" + status +
                '}';
    }
}