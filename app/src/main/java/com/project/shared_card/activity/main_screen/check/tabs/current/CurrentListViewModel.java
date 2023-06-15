package com.project.shared_card.activity.main_screen.check.tabs.current;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;

import java.util.List;

public class CurrentListViewModel extends AndroidViewModel {
    private final LiveData<List<FullProduct>> products;
    private final MutableLiveData<CharSequence> query;
    private final ImplDB implDB;
    private final SharedPreferences settings;
    private String groupId;

    public CurrentListViewModel(@NonNull Application application) {
        super(application);
        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = settings.getString(getApplication().getString(R.string.key_for_select_group_id), "no_id");
        implDB = ((BasicApp) application).getRepository();
        query = new MutableLiveData<>();
        query.setValue("");
        products = Transformations.switchMap(query, new Function<CharSequence, LiveData<List<FullProduct>>>() {
            @Override
            public LiveData<List<FullProduct>> apply(CharSequence input) {
                if (input.equals("")) {
                    return implDB.product().getAllForCheck(Long.valueOf(groupId));
                } else {
                    return implDB.product().getAllForCheckQuery(Long.valueOf(groupId), input + "%");
                }
            }
        });


    }

    public LiveData<List<FullProduct>> getProducts() {
        return products;
    }

    public void addProducts(String name, String count, int metric, int category) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(name);
        entity.setProductCount(Integer.parseInt(count));
        entity.setMetricId(metric);
        entity.setCategoryId(category);
        entity.setDateFirst(DateConverter.FromNowDateToLong());
        entity.setGroupNameId(Long.parseLong(groupId));
        entity.setStatus(0);
        if (groupId.equals(getApplication().getString(R.string.me_id))) {
            entity.setUserNameCreatorId(Long.parseLong(groupId));
        } else {
            String userId = settings.getString(getApplication().getString(R.string.key_for_me_id_server), "no id");
            entity.setUserNameCreatorId(Long.parseLong(userId));
        }
        implDB.product().add(entity);
    }

    public void deleteProduct(ProductEntity entity) {
        implDB.product().delete(entity);
    }

    public void updateProductForDialog(ProductEntity entity, String price, int shop, int currency) {
        entity.setStatus(2);
        entity.setDateLast(DateConverter.FromNowDateToLong());
        entity.setPrice(Integer.parseInt(price));
        entity.setShopId(shop);
        entity.setCurrencyId(currency);

        if (!settings.getString(getApplication().getString(R.string.key_for_me_id_server), "no_id").equals("no_id")) {
            entity.setUserNameBuyerId(Long.parseLong(settings.getString(getApplication().getString(R.string.key_for_me_id_server), "no_id")));
        } else {
            entity.setUserNameBuyerId(Long.parseLong(getApplication().getString(R.string.me_id)));
        }
        implDB.product().update(entity);
    }

    public void updateProductIsChecked(ProductEntity entity) {
        entity.setStatus(1);
        implDB.product().update(entity);
    }

    public void updateProductIsUnchecked(ProductEntity entity) {
        entity.setStatus(0);
        implDB.product().update(entity);
    }

    public void setQuery(CharSequence query) {

        this.query.setValue(query);
    }
}

