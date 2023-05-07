package com.project.shared_card.activity.main_screen.check.tabs.current;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.ProductEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    public List<Product> checks;
    private int countSelectedItems;
    ImplDB db;

    public ProductAdapter(Context context, List<Product> checks) {
        this.inflater = LayoutInflater.from(context);
        this.checks = checks;
        db = new ImplDB(context);
        addCountSelectedItems(checks);
    }

    public void update(List<Product> products) {
        checks = products;
        notifyDataSetChanged();
        addCountSelectedItems(products);
    }
    void addCountSelectedItems(List<Product> products){
        countSelectedItems=0;
        for (Product product: products) {
            if(product.getStatus()==1)
                countSelectedItems++;
        }
    }
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cell_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product check = checks.get(position);
        holder.setProduct(check.getEntity());
        holder.nameProduct.setText(check.getName());
        holder.category.setText(check.getCategory());
        holder.count.setText(String.valueOf(check.getCount()) + " " + check.getMetric());
        LocalDateTime date = check.getDate();
        String visualDate = date.getDayOfMonth() + "." + date.getMonthValue() + " " + date.getHour() + ":" + date.getMinute();
        holder.date.setText(visualDate);
        holder.user.setText(check.getNameCreator());
        if(check.getStatus()==0){
            holder.select.setChecked(false);
        } else {
            holder.select.setChecked(true);
        }

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.select.isChecked()) {
                    holder.product.setStatus(1);
                    db.product().update(holder.product);
                    if(holder.getAdapterPosition()!=checks.size()-countSelectedItems-1)
                        notifyItemMoved(holder.getAdapterPosition(), checks.size()-1);
                    countSelectedItems++;

                } else {
                    holder.product.setStatus(0);
                    db.product().update(holder.product);
                    if(holder.getAdapterPosition()>checks.size()-countSelectedItems){
                        notifyItemMoved(holder.getAdapterPosition(), checks.size()-countSelectedItems);
                    }
                    countSelectedItems--;

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameProduct, category, count, date, user;
        final CheckBox select;
        ProductEntity product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameProduct = itemView.findViewById(R.id.text_product);
            this.category = itemView.findViewById(R.id.text_category);
            this.count = itemView.findViewById(R.id.text_count_or_sell);
            this.date = itemView.findViewById(R.id.text_date);
            this.user = itemView.findViewById(R.id.text_user);
            this.select = itemView.findViewById(R.id.checkbox);
        }

        public ProductEntity getProduct() {
            return product;
        }

        public void setProduct(ProductEntity product) {
            this.product = product;
        }
    }
}
