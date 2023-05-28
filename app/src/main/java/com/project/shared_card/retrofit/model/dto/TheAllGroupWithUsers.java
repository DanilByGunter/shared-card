package com.project.shared_card.retrofit.model.dto;

import com.project.shared_card.retrofit.model.TheAllGroup;

import java.util.List;

public class TheAllGroupWithUsers {
    private TheAllGroup all_group;
    private List<UsersGroup> users;

    public TheAllGroupWithUsers() {
    }

    public TheAllGroupWithUsers(TheAllGroup all_group, List<UsersGroup> users) {
        this.all_group = all_group;
        this.users = users;
    }

    public TheAllGroup getAllGroup() {
        return all_group;
    }

    public void setAllGroup(TheAllGroup all_group) {
        this.all_group = all_group;
    }

    public List<UsersGroup> getUsers() {
        return users;
    }

    public void setUsers(List<UsersGroup> users) {
        this.users = users;
    }
}
