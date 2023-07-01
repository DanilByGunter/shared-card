package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.MainActivityViewModel;
import com.project.shared_card.activity.main_screen.check.callback.ButtonClickCallback;
import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.databinding.DialogJoinGroupBinding;

import java.util.List;

public class DialogGroupJoin extends DialogFragment {
    DialogJoinGroupBinding binding;
    List<AllGroups> allGroups;
    DialogGroupJoinViewModel viewModel;

    public static final String KEY_GROUP = "ALL_GROUP";

    ButtonClickCallback clickReady = () -> {
        if(!binding.dialogEditName.getText().toString().equals("")){
            String idGroup = binding.dialogEditName.getText().toString().toLowerCase().split("#")[0];
            for(char s:idGroup.toCharArray())
                if(!Character.isDigit(s)){
                    Toast toast = Toast.makeText(getContext(),"Некоректный id",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
            long id_group = Long.parseLong(idGroup);
            String name = binding.dialogEditName.getText().toString().split("#")[1];

            boolean flag = true;
            for(AllGroups allGroup:allGroups)
                if(allGroup.groupName.getId() ==id_group)
                    flag=false;

            if(flag){
                viewModel.addUserInGroup(id_group,name);
            }
            else{
                Toast toast = Toast.makeText(getContext(),"Группа уже добавлена",Toast.LENGTH_SHORT);
                toast.show();
            }
            getDialog().dismiss();
        }
    };

    public DialogGroupJoin() {

    }
    public static DialogGroupJoin getInstance(List<AllGroups> allGroups){
        Bundle arg = new Bundle();
        arg.putParcelable(KEY_GROUP, (Parcelable) allGroups);
        DialogGroupJoin dialog = new DialogGroupJoin();
        dialog.setArguments(arg);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            allGroups = getArguments().getParcelableArrayList(KEY_GROUP,AllGroups.class);
        }
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
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_join_group,container,false);
        binding.dialogJoin.setOnClickListener((v) -> clickReady.onClick());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DialogGroupJoinViewModel.class);
    }

}
