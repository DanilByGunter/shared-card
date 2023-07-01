package com.project.shared_card.database;

import static com.project.shared_card.database.data.MyData.getCategoryProduct;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.project.shared_card.database.data.MyData;
import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;
import com.project.shared_card.database.entity.currency.CurrencyEntity;
import com.project.shared_card.database.entity.group_name.repository.ImplGroupNameRepository;
import com.project.shared_card.database.entity.categories.product.repository.ImplCategoriesProductRepository;
import com.project.shared_card.database.entity.categories.target.repository.ImplCategoriesTargetRepository;
import com.project.shared_card.database.entity.check.product.repository.ImplProductRepository;
import com.project.shared_card.database.entity.check.target.repository.ImplTargetRepository;
import com.project.shared_card.database.entity.currency.repository.ImplCurrencyRepository;
import com.project.shared_card.database.entity.group.repository.ImplGroupRepository;
import com.project.shared_card.database.entity.metrics.MetricsEntity;
import com.project.shared_card.database.entity.metrics.repository.ImplMetricsRepository;
import com.project.shared_card.database.entity.shop.product.ShopProductEntity;
import com.project.shared_card.database.entity.shop.product.repository.ImplShopProductRepository;
import com.project.shared_card.database.entity.shop.target.ShopTargetEntity;
import com.project.shared_card.database.entity.shop.target.repository.ImplShopTargetRepository;
import com.project.shared_card.database.entity.statistic.repository.ImplStatsRepository;
import com.project.shared_card.database.entity.story.repository.ImplStoryRepository;
import com.project.shared_card.database.entity.user_name.repository.ImplUserNameRepository;

import java.util.List;

public class ImplDB {
    private static ImplDB implDB;
    private AppDatabase db;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public ImplDB(Context context) {
//        this.db = Room.databaseBuilder(context,
//                        AppDatabase.class, "Sample.db")
//                .addCallback(new RoomDatabase.Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        List<CategoriesProductEntity> categoriesProduct = MyData.getCategoryProduct();
//                        List<CategoriesTargetEntity> categoriesTarget = MyData.getCategoryTarget();
//                        List<CurrencyEntity> currency = MyData.getCurrency();
//                        List<MetricsEntity> metric = MyData.getMetric();
//                        List<ShopProductEntity> shopProduct = MyData.getShopProduct();
//                        List<ShopTargetEntity> shopTarget = MyData.getShopTarget();
//                        insert(categoriesProduct,categoriesTarget,currency,metric,shopProduct,shopTarget);
//                        setDatabaseCreated();
//                    }
//                })
//                .build();
        this.db = Room.databaseBuilder(context, AppDatabase.class,"Sample.db").allowMainThreadQueries().build();
    }

    public MutableLiveData<Boolean> getmIsDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath("Sample.db").exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }


    private void insert(List<CategoriesProductEntity> categoriesProduct, List<CategoriesTargetEntity> categoriesTarget, List<CurrencyEntity> currency, List<MetricsEntity> metric, List<ShopProductEntity> shopProduct, List<ShopTargetEntity> shopTarget) {
        db.runInTransaction(() ->{
            db.getCategoriesProductDao().add(categoriesProduct);
            db.getCategoriesTargetDao().add(categoriesTarget);
            db.getCurrencyDao().add(currency);
            db.getMetricsDao().createMetric(metric);
            db.getShopProductDao().add(shopProduct);
            db.getShopTargetDao().add(shopTarget);
        });
    }

    public static ImplDB getInstance(Context context) {
        if(implDB==null){
            synchronized (ImplDB.class) {
                if(implDB==null)
                    implDB = new ImplDB(context);
            }
        }
        return implDB;
    }

    public AppDatabase getDb() {
        return db;
    }

    public ImplUserNameRepository user_name() {
        return new ImplUserNameRepository(db.getUserNameDao());
    }
    public ImplGroupNameRepository group_name(){
        return new ImplGroupNameRepository(db.getGroupNameDao());
    }
    public ImplGroupRepository group(){
        return new ImplGroupRepository(db.getGroupDao());
    }
    public ImplCategoriesProductRepository category_product(){
        return new ImplCategoriesProductRepository(db.getCategoriesProductDao());
    }
    public ImplShopProductRepository shop_product(){
        return new ImplShopProductRepository(db.getShopProductDao());
    }
    public ImplShopTargetRepository shop_target(){
        return new ImplShopTargetRepository(db.getShopTargetDao());
    }
    public ImplMetricsRepository metric(){
        return new ImplMetricsRepository(db.getMetricsDao());
    }
    public ImplProductRepository product(){
        return new ImplProductRepository(db.getProductDao());
    }
    public ImplTargetRepository target(){
        return new ImplTargetRepository(db.getTargetDao());
    }
    public ImplCategoriesTargetRepository category_target(){
        return new ImplCategoriesTargetRepository(db.getCategoriesTargetDao());
    }
    public ImplStatsRepository stats() {
        return new ImplStatsRepository(db.getStatsDao());
    }
    public ImplCurrencyRepository currency(){
        return  new ImplCurrencyRepository(db.getCurrencyDao());
    }
    public ImplStoryRepository story(){
        return new ImplStoryRepository(db.getStoryDao());
    }
}
