package com.project.shared_card.activity.main_screen.story;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.activity.main_screen.story.model.History;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    private final LayoutInflater inflater;
    private List<History> histories;
    private List<History> historiesFilter;


    public Adapter(Context context,List<History> histories) {
        this.inflater = LayoutInflater.from(context);
        this.histories = histories;
        historiesFilter = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cell_product_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = histories.get(position);
        holder.productName.setText(history.getProduct());
        holder.productName.setSelected(true);
        holder.metric.setText(history.getCount() + " " + history.getMetric());
        holder.metric.setSelected(true);
        holder.buyer.setText(history.getBuyer());
        holder.buyer.setSelected(true);
        holder.category.setText(history.getCategory());
        holder.category.setSelected(true);
        LocalDateTime dateLast = history.getDataLast();
        String dateLastString = dateLast.getMonthValue()+":"+dateLast.getDayOfMonth()+" "+ dateLast.getHour()+":"+dateLast.getMinute();
        holder.datelast.setText(dateLastString);
        holder.datelast.setSelected(true);
        holder.shop.setText(history.getShop());
        holder.shop.setSelected(true);
        holder.price.setText(String.valueOf(history.getPrice())+ " " + history.getCurrency());
        holder.price.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint==null || constraint.length() ==0){
                    filterResults.values = historiesFilter;
                    filterResults.count = historiesFilter.size();
                }
                else{
                    String search = constraint.toString().toLowerCase();
                    List<History> list = new ArrayList<>();
                    for(History history:historiesFilter){
                        if(history.getProduct().toLowerCase().contains(search)){
                            list.add(history);
                        }
                    }
                    filterResults.values =list;
                    filterResults.count = list.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                histories = (List<History>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,price,metric,category,shop,buyer,datelast;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_product);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            shop = itemView.findViewById(R.id.shop);
            metric = itemView.findViewById(R.id.metric);
            buyer = itemView.findViewById(R.id.buyer);
            datelast = itemView.findViewById(R.id.date_of_buy);
        }
    }
}
