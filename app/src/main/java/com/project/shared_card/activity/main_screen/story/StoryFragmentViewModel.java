package com.project.shared_card.activity.main_screen.story;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.story.model.History;

import java.util.List;

public class StoryFragmentViewModel extends AndroidViewModel {
    private final LiveData<List<History>> history;
    private final MutableLiveData<CharSequence> query;
    private final ImplDB implDB;
    private final SharedPreferences settings;
    private final String groupId;

    public StoryFragmentViewModel(@NonNull Application application) {
        super(application);

        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = settings.getString(getApplication().getString(R.string.key_for_select_group_id), "no_id");
        implDB =  ((BasicApp) application).getRepository();

        query = new MutableLiveData<>();
        query.setValue("");

        history = Transformations.switchMap(query, new Function<CharSequence, LiveData<List<History>>>() {
            @Override
            public LiveData<List<History>> apply(CharSequence input) {
                if(input.equals("")){
                    return implDB.story().getAll(Long.valueOf(groupId));
                }
                else{
                    return implDB.story().getAllForQuery(Long.valueOf(groupId),input+"%");
                }
            }
        });
    }

    public LiveData<List<History>> getHistory() {
        return history;
    }

    public void setQuery(CharSequence query){
        this.query.setValue(query);
    }
}
