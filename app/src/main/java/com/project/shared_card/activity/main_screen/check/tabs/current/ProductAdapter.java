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
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private List<Product> checks;
    private long idGroup;

    public ProductAdapter(Context context, List<Product> checks,long idGroup) {
        this.inflater = LayoutInflater.from(context);
        this.checks = checks;
        this.idGroup = idGroup;
    }
    public void update(List<Product> products){
        checks = products;
        notifyDataSetChanged();
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
        holder.nameProduct.setText(check.getName());
        holder.category.setText(check.getCategory());
        holder.count.setText(String.valueOf(check.getCount())+ " " + check.getMetric());
        LocalDateTime date = check.getDate();
        String visualDate =date.getDayOfMonth() +"."+ date.getMonthValue()+" "+ date.getHour() + ":" + date.getMinute();
        holder.date.setText(visualDate);
        holder.user.setText(check.getNameCreator());
        holder.select.setChecked(check.getStatus());
        //todo
//        holder.select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(holder.select.isChecked()){
//                    ImplDB db = new ImplDB(inflater.getContext());
//                    db.product().get(holder.getAdapterPosition()+1).observe((LifecycleOwner) inflater.getContext(), new Observer<ProductEntity>() {
//                        @Override
//                        public void onChanged(ProductEntity product) {
//                            product.setStatus(true);
//                            db.product().update(product);
//                        }
//                    });
//                }
//                else {
//                    ImplDB db = new ImplDB(inflater.getContext());
//                    db.product().get(holder.getAdapterPosition() + 1).observe((LifecycleOwner) inflater.getContext(), new Observer<ProductEntity>() {
//                        @Override
//                        public void onChanged(ProductEntity product) {
//                            product.setStatus(false);
//                            db.product().update(product);
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameProduct, category, count, date, user;
        final CheckBox select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameProduct = itemView.findViewById(R.id.text_product);
            this.category = itemView.findViewById(R.id.text_category);
            this.count = itemView.findViewById(R.id.text_count_or_sell);
            this.date = itemView.findViewById(R.id.text_date);
            this.user = itemView.findViewById(R.id.text_user);
            this.select = itemView.findViewById(R.id.checkbox);
        }
    }
}
