package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.target.TargetEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder> implements Filterable {
    private final LayoutInflater inflater;
    public List<Target> checks;
    public List<Target> checksFilter;
    private ImplDB db;
    int countSelectedItems;
    private boolean SORTED_PRODUCT = true;
    private boolean SORTED_USER = true;
    private boolean SORTED_CATEGORY = true;
    private boolean SORTED_DATE = true;

    public TargetAdapter(Context context,List<Target> checks) {
        this.inflater = LayoutInflater.from(context);
        this.checks =checks;
        checksFilter = checks;
        db = new ImplDB(context);
        addCountSelectedItems(checks);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cell_product, parent, false);
        return new ViewHolder(view);
    }
    public void update(List<Target> checks){
        this.checks = checks;
        checksFilter = checks;
        notifyDataSetChanged();
        addCountSelectedItems(checks);
    }
    void addCountSelectedItems(List<Target> targets){
        countSelectedItems=0;
        for (Target target: targets) {
            if(target.getStatus()==1)
                countSelectedItems++;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Target check = checks.get(position);
        holder.setTarget(check.getEntity());
        holder.name.setSelected(true);
        holder.category.setSelected(true);
        holder.user.setSelected(true);
        holder.price.setSelected(true);
        holder.date.setSelected(true);
        holder.name.setText(check.getName());
        holder.category.setText(check.getCategory());
        holder.price.setText(String.valueOf(check.getPrice())+ " " + check.getCurrency());
        LocalDateTime date = check.getDate();
        String visualDate =date.getDayOfMonth() +"."+ date.getMonthValue()+" "+ date.getHour() + ":" + date.getMinute();
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
                    holder.target.setStatus(1);
                    db.target().update(holder.target);
                    if(holder.getBindingAdapterPosition()!=checks.size()-countSelectedItems-1)
                        notifyItemMoved(holder.getBindingAdapterPosition(), checks.size()-1);
                    countSelectedItems++;

                } else {
                    holder.target.setStatus(0);
                    db.target().update(holder.target);
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
                    List<Target> list = new ArrayList<>();
                    for(Target target:checksFilter){
                        if(target.getName().toLowerCase().contains(search)){
                            list.add(target);
                        }
                    }
                    filterResults.values =list;
                    filterResults.count = list.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                checks = (List<Target>) results.values;
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
                            .sorted(Comparator.comparing(Target::getName))
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = false;
                }
                else {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getName).reversed())
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = true;
                }
                notifyDataSetChanged();
                break;
            case 2:
                if(SORTED_DATE) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getDate))
                            .collect(Collectors.toList());
                    SORTED_DATE =false;
                }
                else{
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getDate).reversed())
                            .collect(Collectors.toList());
                    SORTED_DATE =true;
                }
                notifyDataSetChanged();
                break;
            case 3:
                if(SORTED_CATEGORY) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getCategory))
                            .collect(Collectors.toList());
                    SORTED_CATEGORY= false;
                }
                else{
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getCategory).reversed())
                            .collect(Collectors.toList());
                    SORTED_CATEGORY= true;
                }
                notifyDataSetChanged();
                break;
            case 4:
                if(SORTED_USER) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getNameCreator))
                            .collect(Collectors.toList());
                    SORTED_USER = false;
                }
                else{
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Target::getNameCreator).reversed())
                            .collect(Collectors.toList());
                    SORTED_USER = true;
                }
                notifyDataSetChanged();
                break;
        }
        checksFilter = checks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, category, price, date, user;
        final CheckBox select;
        TargetEntity target;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.text_product);
            this.category = itemView.findViewById(R.id.text_category);
            this.price = itemView.findViewById(R.id.text_count_or_sell);
            this.date = itemView.findViewById(R.id.text_date);
            this.user = itemView.findViewById(R.id.text_user);
            this.select = itemView.findViewById(R.id.checkbox);
        }

        public TargetEntity getTarget() {
            return target;
        }

        public void setTarget(TargetEntity target) {
            this.target = target;
        }
    }
}
