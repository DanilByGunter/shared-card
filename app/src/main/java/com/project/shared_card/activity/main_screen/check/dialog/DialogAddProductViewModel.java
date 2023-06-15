package com.project.shared_card.activity.main_screen.check.dialog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.database.ImplDB;

import java.util.List;

public class DialogAddProductViewModel extends AndroidViewModel {
    private final ImplDB implDB;
    private final LiveData<List<String>> currency;
    private final LiveData<List<String>> categoryProduct;
    private final LiveData<List<String>> categoryTarget;
    private final LiveData<List<String>> metric;

    public DialogAddProductViewModel(@NonNull Application application) {
        super(application);

        implDB = ((BasicApp) application).getRepository();

        metric = implDB.metric().getAll();
        currency = implDB.currency().getAll();
        categoryProduct = implDB.category_product().getAll();
        categoryTarget = implDB.category_target().getAll();
    }

    public LiveData<List<String>> getMetric() {
        return metric;
    }

    public LiveData<List<String>> getCurrency() {
        return currency;
    }

    public LiveData<List<String>> getCategoryProduct() {
        return categoryProduct;
    }

    public LiveData<List<String>> getCategoryTarget() {
        return categoryTarget;
    }


}
