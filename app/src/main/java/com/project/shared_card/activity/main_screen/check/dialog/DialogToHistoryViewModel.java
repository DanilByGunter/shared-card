package com.project.shared_card.activity.main_screen.check.dialog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.database.ImplDB;

import java.util.List;

public class DialogToHistoryViewModel extends AndroidViewModel {
    private final ImplDB implDB;
    private final LiveData<List<String>> currency;
    private final LiveData<List<String>> shopProduct;
    private final LiveData<List<String>> shopTarget;


    public DialogToHistoryViewModel(@NonNull Application application) {
        super(application);
        implDB = ((BasicApp) application).getRepository();

        currency = implDB.currency().getAll();
        shopProduct = implDB.shop_product().getAll();
        shopTarget = implDB.shop_target().getAll();
    }

    public LiveData<List<String>> getCurrency() {
        return currency;
    }

    public LiveData<List<String>> getShopProduct() {
        return shopProduct;
    }

    public LiveData<List<String>> getShopTarget() {
        return shopTarget;
    }


}
