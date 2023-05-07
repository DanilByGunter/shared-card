package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.target.TargetEntity;

import java.time.LocalDateTime;
import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    public List<Target> checks;
    private ImplDB db;
    private int countSelectedItems;

    public TargetAdapter(Context context,List<Target> checks) {
        this.inflater = LayoutInflater.from(context);
        this.checks =checks;
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
        holder.name.setText(check.getName());
        holder.category.setText(check.getCategory());
        holder.price.setText(String.valueOf(check.getPrice())+ " " + check.getCurrency());
        LocalDateTime date = check.getDate();
        String visualDate =date.getDayOfMonth() +"."+ date.getMonthValue()+" "+ date.getHour() + ":" + date.getMinute();
        holder.date.setText(visualDate);
        holder.user.setText(check.getNameCreator());
        if(check.getStatus()==0){
            holder.select.setChecked(false);
        }
        else{
            holder.select.setChecked(true);
        }
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.select.isChecked()) {
                    holder.target.setStatus(1);
                    db.target().update(holder.target);
                    if(holder.getAdapterPosition()!=checks.size()-countSelectedItems-1)
                        notifyItemMoved(holder.getAdapterPosition(), checks.size()-1);
                    countSelectedItems++;
                } else {
                    holder.target.setStatus(0);
                    db.target().update(holder.target);
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
