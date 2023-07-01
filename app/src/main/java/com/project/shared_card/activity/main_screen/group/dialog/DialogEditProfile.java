package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.MainActivityViewModel;
import com.project.shared_card.activity.main_screen.check.callback.ButtonClickCallback;
import com.project.shared_card.databinding.DialogEditProfileBinding;

public class DialogEditProfile extends DialogFragment {
    ActivityResultLauncher<String> getContent;
    DialogEditProfileBinding binding;
    DialogEditViewModel viewModel;
    MainActivityViewModel activityViewModel;
    public DialogEditProfile() {

    }
    ButtonClickCallback clickReady = () ->{
        if(!binding.dialogEditName.getText().toString().equals("")){
            viewModel.save(binding.dialogImage.getDrawable(),
                    binding.dialogEditName.getText().toString());
            getDialog().dismiss();
        }

    };
    ButtonClickCallback clickOnPhoto = () -> {
        getContent.launch("image/*");
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static DialogEditProfile getInstance(){
        DialogEditProfile dialog = new DialogEditProfile();
        return dialog;
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
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_edit_profile,container,false);

        binding.dialogReady.setOnClickListener(v -> clickReady.onClick());
        binding.dialogImage.setOnClickListener(v -> clickOnPhoto.onClick());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DialogEditViewModel.class);
        activityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onActivityResult);

        getGroup();
    }
    private void onActivityResult(Uri result) {
       binding.dialogImage.setImageURI(result);
    }
    void getGroup(){
        activityViewModel.getGroup().observe(this,groupName -> {
            binding.dialogImage.setImageBitmap(DbBitmapUtility.getImage(groupName.getPhoto()));
            binding.dialogEditName.setText(groupName.getName());
        });
    }

}
