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
import com.project.shared_card.databinding.DialogCreateProductBinding;

public class DialogAddProduct extends DialogFragment {
    DialogCreateProductBinding binding;
    DialogAddProductViewModel viewModel;
    private CurrentListViewModel currentListViewModel;
    private TargetListViewModel targetListViewModel;
    int page;
    static final String KEY_PAGE = "page";
    ButtonClickCallback callback = () -> {
        if (!binding.dialogCountOfProduct.getText().toString().equals("") && !binding.dialogEditTextProduct.getText().toString().equals("")) {
            if (page == 0) {
                currentListViewModel = new ViewModelProvider(this).get(CurrentListViewModel.class);
                currentListViewModel.addProducts(binding.dialogEditTextProduct.getText().toString(),
                        binding.dialogCountOfProduct.getText().toString(),
                        binding.dialogMetric.getSelectedItemPosition() + 1,
                        binding.dialogTextCategory.getSelectedItemPosition() + 1);
            } else {
                targetListViewModel = new ViewModelProvider(this).get(TargetListViewModel.class);
                targetListViewModel.addTarget(binding.dialogEditTextProduct.getText().toString(),
                        binding.dialogCountOfProduct.getText().toString(),
                        binding.dialogMetric.getSelectedItemPosition() + 1,
                        binding.dialogTextCategory.getSelectedItemPosition() + 1);
            }
            getDialog().dismiss();
        }
    };

    public DialogAddProduct() {
    }

    public static DialogAddProduct newInstance(int page) {
        DialogAddProduct dialog = new DialogAddProduct();
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE, page);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        page = getArguments().getInt(KEY_PAGE);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_product, container, false);

        binding.setCallback(callback);

        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DialogAddProductViewModel.class);

        if (page == 0) {
            viewModel.getCategoryProduct().observe(this, strings -> {
                binding.dialogTextCategory.setAdapter(new AdapterForSpinner(getContext(), strings));
            });
            viewModel.getMetric().observe(this, strings -> {
                binding.dialogMetric.setAdapter(new AdapterForSpinner(getContext(), strings));
            });
            binding.dialogTextAddProduct.setText(getString(R.string.dialog_add_product));
            binding.dialogEditTextProduct.setHint(getString(R.string.dialog_text_hint_porduct));
            binding.dialogCountOfProduct.setHint(getString(R.string.dialog_text_hint_count));
        } else {
            viewModel.getCategoryTarget().observe(this, strings -> {
                binding.dialogTextCategory.setAdapter(new AdapterForSpinner(getContext(), strings));
            });
            viewModel.getCurrency().observe(this, strings -> {
                binding.dialogMetric.setAdapter(new AdapterForSpinner(getContext(), strings));
            });
            binding.dialogTextAddProduct.setText(getString(R.string.dialog_add_target));
            binding.dialogEditTextProduct.setHint(getString(R.string.dialog_text_hint_target));
            binding.dialogCountOfProduct.setHint(getString(R.string.dialog_text_hint_currency));
        }
    }
}
