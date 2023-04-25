package com.project.shared_card.retrofit.model;

public class TheGroupId {
    private long id;
    private int status;

    public long getId() {
        return id;}

    public void setId(long id) {
        this.id = id;}

    public int getStatus() {
        return status;}

    public void setStatus(int status) {
        this.status = status;}

    public TheGroupId() {}

    public TheGroupId(long id, int status) {
        this.id = id;
        this.status = status;}

    @Override
    public String toString() {
        return "TheGroupId{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
