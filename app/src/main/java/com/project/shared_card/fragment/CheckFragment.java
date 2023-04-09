package com.project.shared_card.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.UsersEntity;

import org.w3c.dom.Text;

import java.util.List;


public class CheckFragment extends Fragment {



    public CheckFragment() {
    }

    public static CheckFragment newInstance() {
        CheckFragment fragment = new CheckFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = view.findViewById(R.id.check_text);
        ImplDB db = new ImplDB(getContext());
        LiveData<List<UsersEntity>> entity =  db.getUsersRepository().allUsers();
        entity.observe(getViewLifecycleOwner(), new Observer<List<UsersEntity>>() {
            @Override
            public void onChanged(List<UsersEntity> usersEntities) {
                    String all = "";
                    for(UsersEntity user: usersEntities){
                        all += String.valueOf(user.getId());
                        all+=" ";
                        all+=user.getName();
                        all+=" ";
                        all+= user.getPhoto();
                        all+="\n";
                    }
                    text.setText(all);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }
}