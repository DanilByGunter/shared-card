package com.project.shared_card.activity.main_screen.check.tabs;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.callback.AdapterCallback;
import com.project.shared_card.activity.main_screen.check.tabs.model.Cell;
import com.project.shared_card.databinding.CellProductBinding;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.ViewHolder> {
    public List<Cell> checks;
    public List<Cell> checksFilter;
    private boolean SORTED_PRODUCT = true;
    private boolean SORTED_USER = true;
    private boolean SORTED_CATEGORY = true;
    private boolean SORTED_DATE = true;
    private AdapterCallback callback;

    public AdapterForRecyclerView(AdapterCallback callback) {
        this.callback = callback;
    }

    public void update(List<Cell> products) {
        if (checks == null) {
            checks = products;
            checksFilter = products;
            notifyItemRangeInserted(0, checks.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return checks.size();
                }

                @Override
                public int getNewListSize() {
                    return products.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return TextUtils.equals(checks.get(oldItemPosition).getName(), products.get(newItemPosition).getName());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return checks.get(oldItemPosition).getStatus() == products.get(newItemPosition).getStatus() &&
                            TextUtils.equals(checks.get(oldItemPosition).getName(), products.get(newItemPosition).getName());
                }
            });
            checks = products;
            checksFilter = products;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public AdapterForRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CellProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cell_product, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForRecyclerView.ViewHolder holder, int position) {
        holder.binding.setCell(checks.get(position));
        holder.binding.textProduct.setSelected(true);
        holder.binding.textCategory.setSelected(true);
        holder.binding.textUser.setSelected(true);
        holder.binding.textCountOrSell.setSelected(true);
        holder.binding.textDate.setSelected(true);
        holder.binding.checkbox.setOnClickListener(v -> callback.onClick(v, holder.getBindingAdapterPosition()));

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return checks == null ? 0 : checks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final CellProductBinding binding;

        public ViewHolder(@NonNull CellProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void sorted(int mode) {
        switch (mode) {
            case 1:
                if (SORTED_PRODUCT) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getName))
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = false;
                } else {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getName).reversed())
                            .collect(Collectors.toList());
                    SORTED_PRODUCT = true;
                }
                notifyDataSetChanged();
                break;
            case 2:
                if (SORTED_DATE) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getDate))
                            .collect(Collectors.toList());
                    SORTED_DATE = false;
                } else {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getDate).reversed())
                            .collect(Collectors.toList());
                    SORTED_DATE = true;
                }
                notifyDataSetChanged();
                break;
            case 3:
                if (SORTED_CATEGORY) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getCategory))
                            .collect(Collectors.toList());
                    SORTED_CATEGORY = false;
                } else {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getCategory).reversed())
                            .collect(Collectors.toList());
                    SORTED_CATEGORY = true;
                }
                notifyDataSetChanged();
                break;
            case 4:
                if (SORTED_USER) {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getNameCreator))
                            .collect(Collectors.toList());
                    SORTED_USER = false;
                } else {
                    checks = checks.stream()
                            .sorted(Comparator.comparing(Cell::getNameCreator).reversed())
                            .collect(Collectors.toList());
                    SORTED_USER = true;
                }
                notifyDataSetChanged();
                break;
        }
        checksFilter = checks;
    }
}
