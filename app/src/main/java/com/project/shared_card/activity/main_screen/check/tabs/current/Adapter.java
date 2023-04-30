package com.project.shared_card.activity.main_screen.check.tabs.current;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Check;

import java.time.LocalDateTime;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private final LayoutInflater inflater;
    private List<Check> checks;

    public Adapter(Context context,List<Check> checks) {
        this.inflater = LayoutInflater.from(context);
        this.checks = checks;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cell_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Check check = checks.get(position);
        holder.nameProduct.setText(check.getName());
        holder.category.setText(check.getCategory());
        holder.count.setText(String.valueOf(check.getCount())+ " " + check.getMetric());
        LocalDateTime date = check.getDate();
        String visualDate =date.getDayOfMonth() +"."+ date.getMonthValue()+" "+ date.getHour() + ":" + date.getMinute();
        holder.date.setText(visualDate);
        holder.user.setText(check.getNameCreator());
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameProduct, category, count, date, user;
        final Button select;
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
