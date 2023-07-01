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
import com.project.shared_card.activity.main_screen.group.GroupFragmentViewModel;
import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.databinding.DialogEditProfileBinding;

public class DialogEditGroup extends DialogFragment {
    ActivityResultLauncher<String> getContent;
    DialogEditProfileBinding binding;
    GroupNameEntity group;
    public static final String KEY_GROUP = "group";
    ButtonClickCallback clickReady = () ->{
        GroupFragmentViewModel viewModel = new ViewModelProvider(this).get(GroupFragmentViewModel.class);
        if(!binding.dialogEditName.getText().toString().equals("")){
            viewModel.update(group.getId(),
                    binding.dialogEditName.getText().toString(),
                    binding.dialogImage.getDrawable());
            getDialog().dismiss();
        }

    };
    ButtonClickCallback clickOnPhoto = () -> {
        getContent.launch("image/*");
    };
    public DialogEditGroup() {

    }
    public static DialogEditGroup getInstance(GroupNameEntity group){
        Bundle args = new Bundle();
        args.putParcelable(KEY_GROUP,group);
        DialogEditGroup dialog = new DialogEditGroup();
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        group = (GroupNameEntity) getArguments().get(KEY_GROUP);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_edit_profile,container,false);

        binding.dialogReady.setOnClickListener(v -> clickReady.onClick());
        binding.dialogImage.setOnClickListener(v -> clickOnPhoto.onClick());

        binding.dialogEditName.setText(group.getName());
        binding.dialogImage.setImageBitmap(DbBitmapUtility.getImage(group.getPhoto()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onActivityResult);

    }
    private void onActivityResult(Uri result) {
       binding.dialogImage.setImageURI(result);
    }

}
