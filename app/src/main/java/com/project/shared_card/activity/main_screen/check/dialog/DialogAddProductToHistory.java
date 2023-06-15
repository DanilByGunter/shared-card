package com.project.shared_card.activity.main_screen.check.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.callback.ButtonClickCallback;
import com.project.shared_card.activity.main_screen.check.tabs.current.CurrentListViewModel;
import com.project.shared_card.activity.main_screen.check.tabs.target.TargetListViewModel;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.target.TargetEntity;
import com.project.shared_card.databinding.DialogAddProductToHistoryBinding;

public class DialogAddProductToHistory extends DialogFragment {


    private ProductEntity productEntity;
    private TargetEntity targetEntity;
    private DialogAddProductToHistoryBinding binding;
    private DialogToHistoryViewModel viewModel;
    private CurrentListViewModel currentListViewModel;
    private TargetListViewModel targetListViewModel;
    private static final String KEY_NAME = "name";
    private static final String KEY_PRODUCT = "product";
    private static final String KEY_TARGET = "target";
    private String name;
    ButtonClickCallback callback = () -> {
        if (binding.dialogEditPrice.getText().toString().equals(""))
            return;
        if (productEntity != null) {
            currentListViewModel = new ViewModelProvider(this).get(CurrentListViewModel.class);
            currentListViewModel.updateProductForDialog(productEntity,
                    binding.dialogEditPrice.getText().toString(),
                    binding.dialogShop.getSelectedItemPosition() + 1,
                    binding.dialogCurrency.getSelectedItemPosition() + 1);
        } else {
            targetListViewModel = new ViewModelProvider(this).get(TargetListViewModel.class);
            targetListViewModel.updateTargetForDialog(targetEntity,
                    binding.dialogEditPrice.getText().toString(),
                    binding.dialogShop.getSelectedItemPosition() + 1,
                    binding.dialogCurrency.getSelectedItemPosition() + 1);
        }
        getDialog().dismiss();
    };

    public DialogAddProductToHistory() {
    }

    public static DialogAddProductToHistory newInstance(ProductEntity entity) {
        DialogAddProductToHistory dialog = new DialogAddProductToHistory();
        Bundle args = new Bundle();
        args.putString(KEY_NAME, entity.getProductName());
        args.putParcelable(KEY_PRODUCT, entity);
        dialog.setArguments(args);
        return dialog;
    }

    public static DialogAddProductToHistory newInstance(TargetEntity entity) {
        DialogAddProductToHistory dialog = new DialogAddProductToHistory();
        Bundle args = new Bundle();
        args.putString(KEY_NAME, entity.getTargetName());
        args.putParcelable(KEY_TARGET, entity);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(KEY_NAME);
        productEntity = getArguments().getParcelable(KEY_PRODUCT);
        targetEntity = getArguments().getParcelable(KEY_TARGET);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_add_product_to_history, container, false);
        binding.dialogProduct.setText(name);
        binding.dialogProduct.setSelected(true);
        binding.setCallback(callback);
        binding.executePendingBindings();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DialogToHistoryViewModel.class);

        viewModel.getCurrency().observe(getViewLifecycleOwner(), strings -> {
            binding.dialogCurrency.setAdapter(new AdapterForSpinner(getContext(), strings));
        });

        if (productEntity != null) {
            viewModel.getShopProduct().observe(getViewLifecycleOwner(), strings -> {
                binding.dialogShop.setAdapter(new AdapterForSpinner(getContext(), strings));
            });
        } else {
            viewModel.getShopTarget().observe(getViewLifecycleOwner(), strings -> {
                binding.dialogShop.setAdapter(new AdapterForSpinner(getContext(), strings));
            });
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
