package com.project.shared_card.activity.database.entity.check.target;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "target")
public class TargetEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "group_name_id")
    private long groupNameId;
    @ColumnInfo(name = "category_id")
    private long categoryId;
    @ColumnInfo(name = "target_name")
    private String targetName;
    @ColumnInfo(name = "currency_id")
    private long currencyId;
    @ColumnInfo(name = "user_name_creator_id")
    private long userNameCreatorId;
    @ColumnInfo(name = "user_name_buyer_id")
    private long userNameBuyerId;
    @ColumnInfo(name = "date_first")
    private long dateFirst;
    @ColumnInfo(name = "date_last")
    private long dateLast;
    private int price;
    private int status;
    @ColumnInfo(name = "shop_id")
    private long shopId;

    public TargetEntity() {
    }

    public TargetEntity(long groupNameId, long categoryId, String targetName, long currencyId, long userNameCreatorId, long dateFirst, int price,int status) {
        this.groupNameId = groupNameId;
        this.categoryId = categoryId;
        this.targetName = targetName;
        this.currencyId = currencyId;
        this.userNameCreatorId = userNameCreatorId;
        this.dateFirst = dateFirst;
        this.price = price;
        this.status=status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupNameId() {
        return groupNameId;
    }

    public void setGroupNameId(long groupNameId) {
        this.groupNameId = groupNameId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public long getUserNameCreatorId() {
        return userNameCreatorId;
    }

    public void setUserNameCreatorId(long userNameCreatorId) {
        this.userNameCreatorId = userNameCreatorId;
    }

    public long getUserNameBuyerId() {
        return userNameBuyerId;
    }

    public void setUserNameBuyerId(long userNameBuyerId) {
        this.userNameBuyerId = userNameBuyerId;
    }

    public long getDateFirst() {
        return dateFirst;
    }

    public void setDateFirst(long dateFirst) {
        this.dateFirst = dateFirst;
    }

    public long getDateLast() {
        return dateLast;
    }

    public void setDateLast(long dateLast) {
        this.dateLast = dateLast;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
}
