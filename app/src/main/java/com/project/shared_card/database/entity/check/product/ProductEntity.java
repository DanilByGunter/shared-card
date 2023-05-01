package com.project.shared_card.database.entity.check.product;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//,
//        foreignKeys = {
//@ForeignKey(entity = UserNameEntity.class,parentColumns = "id",childColumns ="user_name_creator_id"),
//@ForeignKey(entity = UserNameEntity.class,parentColumns = "id",childColumns ="user_name_buyer_id"),
//@ForeignKey(entity = GroupNameEntity.class,parentColumns = "id",childColumns ="group_name_id"),
//@ForeignKey(entity = CategoriesEntity.class,parentColumns = "id",childColumns ="category_id"),
//@ForeignKey(entity = MetricsEntity.class,parentColumns = "id",childColumns ="metric_id"),
//@ForeignKey(entity = GroupEntity.class,
//        parentColumns = {"user_name_id","group_name_id"},childColumns = {"user_name_creator_id", "group_name_id"}),
//        }
@Entity(tableName = "product")
public class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "group_name_id")
    private long groupNameId;
    @ColumnInfo(name = "category_id")
    private long categoryId;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "product_count")
    private int productCount;
    @ColumnInfo(name = "metric_id")
    private long metricId;
    @ColumnInfo(name = "user_name_creator_id")
    private long userNameCreatorId;
    @ColumnInfo(name = "user_name_buyer_id")
    private long userNameBuyerId;
    @ColumnInfo(name = "date_first")
    private long dateFirst;
    @ColumnInfo(name = "date_last")
    private long dateLast;
    private int price;
    @ColumnInfo(name = "shop_id")
    private long shopId;
    private boolean status;
    public ProductEntity() {
    }

    public ProductEntity(long groupNameId, long userNameCreatorId, String productName, int productCount, long categoryId, long metricId, long dateFirst, long shopId, boolean status) {
        this.groupNameId = groupNameId;
        this.userNameCreatorId = userNameCreatorId;
        this.productName = productName;
        this.productCount = productCount;
        this.categoryId = categoryId;
        this.metricId = metricId;
        this.dateFirst = dateFirst;
        this.shopId = shopId;
        this.status = status;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getMetricId() {
        return metricId;
    }

    public void setMetricId(long metricId) {
        this.metricId = metricId;
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

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public boolean isStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }
}
