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

import java.time.LocalDateTime;
import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private List<Target> checks;
    private long idGroup;

    public TargetAdapter(Context context,List<Target> checks,long idGroup) {
        this.inflater = LayoutInflater.from(context);
        this.checks =checks;
        this.idGroup = idGroup;
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
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Target check = checks.get(position);
        holder.name.setText(check.getName());
        holder.category.setText(check.getCategory());
        holder.price.setText(String.valueOf(check.getPrice())+ " " + check.getCurrency());
        LocalDateTime date = check.getDate();
        String visualDate =date.getDayOfMonth() +"."+ date.getMonthValue()+" "+ date.getHour() + ":" + date.getMinute();
        holder.date.setText(visualDate);
        holder.user.setText(check.getNameCreator());
        holder.select.setChecked(check.getStatus());
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, category, price, date, user;
        final CheckBox select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.text_product);
            this.category = itemView.findViewById(R.id.text_category);
            this.price = itemView.findViewById(R.id.text_count_or_sell);
            this.date = itemView.findViewById(R.id.text_date);
            this.user = itemView.findViewById(R.id.text_user);
            this.select = itemView.findViewById(R.id.checkbox);
        }
    }
}
