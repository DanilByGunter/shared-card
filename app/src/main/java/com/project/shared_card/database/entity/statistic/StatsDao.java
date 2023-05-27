package com.project.shared_card.database.entity.statistic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.project.shared_card.database.entity.statistic.model.Price;
import com.project.shared_card.database.entity.statistic.model.Stats;

import java.util.List;

@Dao
public interface StatsDao {

    @Query("select category as name, count(category) as count, sum(price) as summa from (" +
            "select category_product.name as category, date_last, price from product " +
            "inner join category_product on category_id = category_product.id " +
            "where status ==2  and user_name_buyer_id = :id_user and group_name_id = :id_group " +
            " union " +
            "select category_target.name as category, date_last, price from target " +
            "inner join category_target on category_id = category_target.id " +
            "where status ==2 and user_name_buyer_id = :id_user and group_name_id = :id_group) " +
            "where date_last > :days " +
            "group by category ")
    LiveData<List<Stats>> getCategoriesStats(Long days, Long id_user, Long id_group);


    @Query("select category as name, count(category) as count, sum(price) as summa from (" +
            "select category_product.name as category, price, date_last from product " +
            "inner join category_product on category_id = category_product.id " +
            "where status ==2  and group_name_id = :id_group " +
            " union " +
            "select category_target.name as category, price, date_last from target " +
            "inner join category_target on category_id = category_target.id " +
            "where status ==2 and group_name_id = :id_group) " +
            "where date_last > :days " +
            "group by category ")
    LiveData<List<Stats>> getGeneralCategoriesCount(Long days, Long id_group);

    @Query("select shop as name, count(shop) as count, sum(price) as summa  from (" +
            "select shop_product.name as shop, price, date_last from product " +
            "inner join shop_product on shop_id = shop_product.id " +
            "where status ==2  and group_name_id = :id_group " +
            " union " +
            "select shop_target.name as shop, price, date_last from target " +
            "inner join shop_target on shop_id = shop_target.id " +
            "where status ==2 and group_name_id = :id_group) " +
            "where date_last > :days " +
            "group by shop ")
    LiveData<List<Stats>> getShopStats(Long days, Long id_group);

    @Query("select price, date_last as date from (" +
            "select price, date_last from product " +
            "where status ==2  and group_name_id = :id_group " +
            " union " +
            "select price, date_last from target " +
            "where status ==2 and group_name_id = :id_group) " +
            "where date_last > :days " +
            "order By date_last")
    LiveData<List<Price>> getSpending(Long days, Long id_group);

    @Query("select price, date_last as date from (" +
            "select price, date_last from product " +
            "where status ==2 " +
            " union " +
            "select price, date_last from target " +
            "where status ==2) " +
            "where date_last > :days " +
            "order By date_last")
    LiveData<List<Price>> getSpending(Long days);
}
