package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.main_screen.check.tabs.current.CurrentListViewModel;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.target.FullTarget;
import com.project.shared_card.database.entity.check.target.TargetEntity;

import java.util.List;

public class TargetListViewModel extends AndroidViewModel {
    private final LiveData<List<FullTarget>> target;
    private final MutableLiveData<CharSequence> query;
    private final ImplDB implDB;
    private final SharedPreferences settings;
    private String groupId;

    public TargetListViewModel(@NonNull Application application) {
        super(application);
        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = settings.getString(getApplication().getString(R.string.key_for_select_group_id), "no_id");
        implDB =  ((BasicApp) application).getRepository();
        query = new MutableLiveData<>();
        query.setValue("");
        target = Transformations.switchMap(query, new Function<CharSequence, LiveData<List<FullTarget>>>() {
            @Override
            public LiveData<List<FullTarget>> apply(CharSequence input) {
                if(input.equals("")){
                    return implDB.target().getAllForCheck(Long.valueOf(groupId));
                }
                else{
                    return implDB.target().getAllForCheckQuery(Long.valueOf(groupId),input+"%");
                }
            }
        });
    }

    public LiveData<List<FullTarget>> getTarget() {
        return target;
    }

    public void deleteTarget(TargetEntity entity){
        implDB.target().delete(entity);
    }

    public void updateTargetForDialog(TargetEntity entity, String price, int shop, int currency){
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
        implDB.target().update(entity);
    }
    public void updateTargetIsChecked(TargetEntity entity){
        entity.setStatus(1);
        implDB.target().update(entity);
    }
    public void updateTargetIsUnchecked(TargetEntity entity){
        entity.setStatus(0);
        implDB.target().update(entity);
    }
    public void addTarget(String name, String price, int currency, int category){
        TargetEntity entity = new TargetEntity();
        entity.setTargetName(name);
        entity.setPrice(Integer.parseInt(price));
        entity.setCurrencyId(currency);
        entity.setCategoryId(category);
        entity.setDateFirst(DateConverter.FromNowDateToLong());
        entity.setGroupNameId(Long.parseLong(groupId));

        if (groupId.equals(getApplication().getString(R.string.me_id))) {
            entity.setUserNameCreatorId(Long.parseLong(groupId));
        } else {
            String userId = settings.getString(getApplication().getString(R.string.key_for_me_id_server), "no id");
            entity.setUserNameCreatorId(Long.parseLong(userId));
        }
        implDB.target().add(entity);
    }

    public void setQuery(CharSequence query){

        this.query.setValue(query);
    }
}
