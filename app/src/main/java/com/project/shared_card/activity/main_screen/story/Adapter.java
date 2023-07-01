package com.project.shared_card.activity.main_screen.story;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.database.entity.story.model.History;
import com.project.shared_card.databinding.CellProductStoryBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<History> histories;
    private boolean SORTED_PRODUCT = true;
    private boolean SORTED_USER = true;
    private boolean SORTED_CATEGORY = true;
    private boolean SORTED_DATE = true;


    public Adapter() {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CellProductStoryBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.cell_product_story,
                        parent, false);
        return new ViewHolder(binding);
    }

    public void setHistories(List<History> histories_) {
        if(histories==null) {
            histories = histories_;
            notifyItemRangeInserted(0, histories.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return histories.size();
                }

                @Override
                public int getNewListSize() {
                    return histories_.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return histories.get(oldItemPosition).getDataLast().equals(histories_.get(newItemPosition).getDataLast());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return histories.get(oldItemPosition).getDataLast().equals(histories_.get(newItemPosition).getDataLast());
                }
            });
            histories = histories_;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindin.setHistory(histories.get(position));
        holder.bindin.textProduct.setSelected(true);
        holder.bindin.metric.setSelected(true);
        holder.bindin.buyer.setSelected(true);
        holder.bindin.category.setSelected(true);
        holder.bindin.dateOfBuy.setSelected(true);
        holder.bindin.shop.setSelected(true);
        holder.bindin.price.setSelected(true);
        holder.bindin.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return histories==null ? 0 : histories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final CellProductStoryBinding bindin;
        public ViewHolder(@NonNull CellProductStoryBinding binding) {
            super(binding.getRoot());
            this.bindin = binding;
        }
    }
    public void sorted(int mode){
        switch (mode){
            case 1:
                if(SORTED_PRODUCT) {
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getProduct))
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = false;
                }
                else {
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getProduct).reversed())
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = true;
                }
                notifyDataSetChanged();
                break;
            case 2:
                if(SORTED_DATE) {
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getDataLast))
                            .collect(Collectors.toList());
                    SORTED_DATE =false;
                }
                else{
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getDataLast).reversed())
                            .collect(Collectors.toList());
                    SORTED_DATE =true;
                }
                notifyDataSetChanged();
                break;
            case 3:
                if(SORTED_CATEGORY) {
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getCategory))
                            .collect(Collectors.toList());
                    SORTED_CATEGORY= false;
                }
                else{
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getCategory).reversed())
                            .collect(Collectors.toList());
                    SORTED_CATEGORY= true;
                }
                notifyDataSetChanged();
                break;
            case 4:
                if(SORTED_USER) {
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getBuyer))
                            .collect(Collectors.toList());
                    SORTED_USER = false;
                }
                else{
                    histories = histories.stream()
                            .sorted(Comparator.comparing(History::getBuyer).reversed())
                            .collect(Collectors.toList());
                    SORTED_USER = true;
                }
                notifyDataSetChanged();
                break;
        }
    }
}
