package com.project.shared_card.activity.main_screen.check.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.target.TargetEntity;

public class DialogAddProductToHistory {
    Context context;
    public TextView product;
    public Dialog dialog;
    public EditText price;
    public Spinner shop;
    public Spinner currency;
    public Button ready;
    public ProductEntity productEntity;
    public TargetEntity targetEntity;
    public DialogAddProductToHistory(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_product_to_history);
        this.context = context;
        product = dialog.findViewById(R.id.dialog_product);
        price = dialog.findViewById(R.id.dialog_edit_price);
        shop = dialog.findViewById(R.id.dialog_shop);
        ready = dialog.findViewById(R.id.dialog_btn_add);
        currency = dialog.findViewById(R.id.dialog_currency);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
