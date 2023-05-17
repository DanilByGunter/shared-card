package com.project.shared_card.activity.main_screen.check.tabs.current;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.story.model.History;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private final LayoutInflater inflater;
    public List<Product> checks;
    public List<Product> checksFilter;
    private int countSelectedItems;
    ImplDB db;
    private boolean SORTED_PRODUCT = true;
    private boolean SORTED_USER = true;
    private boolean SORTED_CATEGORY = true;
    private boolean SORTED_DATE = true;

    public ProductAdapter(Context context, List<Product> checks) {
        this.inflater = LayoutInflater.from(context);
        this.checks = checks;
        checksFilter = checks;
        db = new ImplDB(context);
        addCountSelectedItems(checks);
    }

    public void update(List<Product> products) {
        checks = products;
        checksFilter = products;
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
        holder.nameProduct.setSelected(true);
        holder.category.setSelected(true);
        holder.user.setSelected(true);
        holder.count.setSelected(true);
        holder.date.setSelected(true);
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
                    if(holder.getBindingAdapterPosition()!=checks.size()-countSelectedItems-1)
                        notifyItemMoved(holder.getBindingAdapterPosition(), checks.size()-1);
                    countSelectedItems++;

                } else {
                    holder.product.setStatus(0);
                    db.product().update(holder.product);
                    if(holder.getBindingAdapterPosition()>checks.size()-countSelectedItems){
                        notifyItemMoved(holder.getBindingAdapterPosition(), checks.size()-countSelectedItems);
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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint==null || constraint.length() ==0){
                    filterResults.values = checksFilter;
                    filterResults.count = checksFilter.size();
                }
                else{
                    String search = constraint.toString().toLowerCase();
                    List<Product> list = new ArrayList<>();
                    for(Product product:checksFilter){
                        if(product.getName().toLowerCase().contains(search)){
                            list.add(product);
                        }
                    }
                    filterResults.values =list;
                    filterResults.count = list.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                checks = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public void sorted(int mode){
        switch (mode){
            case 1:
                if(SORTED_PRODUCT) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getName))
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = false;
                }
                else {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getName).reversed())
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = true;
                }
                notifyDataSetChanged();
                break;
            case 2:
                if(SORTED_DATE) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getDate))
                            .collect(Collectors.toList());
                    SORTED_DATE =false;
                }
                else{
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getDate).reversed())
                            .collect(Collectors.toList());
                    SORTED_DATE =true;
                }
                notifyDataSetChanged();
                break;
            case 3:
                if(SORTED_CATEGORY) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getCategory))
                            .collect(Collectors.toList());
                    SORTED_CATEGORY= false;
                }
                else{
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getCategory).reversed())
                            .collect(Collectors.toList());
                    SORTED_CATEGORY= true;
                }
                notifyDataSetChanged();
                break;
            case 4:
                if(SORTED_USER) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getNameCreator))
                            .collect(Collectors.toList());
                    SORTED_USER = false;
                }
                else{
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Product::getNameCreator).reversed())
                            .collect(Collectors.toList());
                    SORTED_USER = true;
                }
                notifyDataSetChanged();
                break;
        }
        checksFilter = checks;
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
