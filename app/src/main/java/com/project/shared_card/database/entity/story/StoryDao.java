package com.project.shared_card.database.entity.story;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.project.shared_card.database.entity.story.model.History;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("select product, currency,category, shop, buyer,date_first,date_last,price,count,metric from (" +
            "select product_name as product,currency.name as currency,category_product.name as category, shop_product.name as shop, user_name.name as buyer,date_first,date_last,price,product_count as count,metric.name as metric from product " +
            "inner join currency on currency_id = currency.id " +
            "inner join category_product on category_id = category_product.id " +
            "inner join user_name on user_name_buyer_id = user_name.id " +
            "inner join metric on metric_id = metric.id " +
            "inner join shop_product on shop_id = shop_product.id " +
            "where status ==2" +
            " union " +
            "select target_name as name,currency.name as currency,category_target.name as category,shop_target.name as shop,  user_name.name as buyer,date_first,date_last,price,null,null from target " +
            "inner join currency on currency_id = currency.id " +
            "inner join category_target on category_id = category_target.id " +
            "inner join user_name on user_name_buyer_id = user_name.id " +
            "inner join shop_target on shop_id = shop_target.id " +
            "where status ==2)" +
            "order By date_last Desc")
    LiveData<List<History>> getAll();
}
