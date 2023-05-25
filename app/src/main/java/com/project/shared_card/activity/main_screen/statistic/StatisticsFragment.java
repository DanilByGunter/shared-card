package com.project.shared_card.activity.main_screen.statistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.converter.StatsConverter;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyLineView;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieHelper;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieView;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.statistic.model.Price;
import com.project.shared_card.database.entity.statistic.model.Stats;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import im.dacer.androidcharts.LineView;


public class StatisticsFragment extends Fragment {
    ArrayList<String> dates;
    Spinner spinner;
    AdapterForSpinner adapter;
    ImplDB db;
    String id_group;
    Long id_user;
    TextView textTitle;
    TextView textFirstPie;
    TextView textSecondPie;
    private SharedPreferences settings;


    public StatisticsFragment() {
    }

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
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
        init(view);

        spinner.setAdapter(adapter);


        Long days;
        textTitle.setText("Статистика расходов");
        if (id_group.equals("-1")){
            textFirstPie.setText("Траты по категориям");
            textSecondPie.setText("Траты по магазинам");
            switch (spinner.getSelectedItemPosition()){
                case 0:
                    days = 7L;
                case 1:
                    days = 30L;
                case 2:
                    days = 91L;
                case 3:
                    days = 365L;
                case 4:
                    days = 999L;
                default:
                    days = 7L;
            }
            linearData(days);
            shopsPie(days);
            soloCategoriesPie(days);
        } else{
            textFirstPie.setText("Личные траты по категориям");
            textSecondPie.setText("Общие траты по категориям");
            switch (spinner.getSelectedItemPosition()){
                case 0:
                    days = 7L;
                case 1:
                    days = 30L;
                case 2:
                    days = 91L;
                case 3:
                    days = 365L;
                case 4:
                    days = 999L;
                default:
                    days = 7L;
            }
            linearData(days, Long.valueOf(id_group));
            soloCategoriesPie(days);
            generalCategoriesPie(days);
        }
    }

    private void shopsPie(Long days){
        Map<String, Integer> names_data = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();
        db.stats().getShopsCount(days, id_user).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
            @Override
            public void onChanged(List<Stats> stats) {
                for (Stats stat: stats) {
                    names_data.put(stat.getName(), stat.getCount());
                }
                Map<String, Integer> sorted_names_data = StatsConverter.sortedDict(names_data);
                if (sorted_names_data.size() <= 16){
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        names.add(value.getKey());
                        data.add(value.getValue());
                    }
                } else {
                    int counter = 0;
                    int summarize = 0;
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        if (counter < 16){
                            names.add(value.getKey());
                            data.add(value.getValue());
                            counter++;
                        } else {
                            summarize = summarize + value.getValue();
                        }
                    }
                    names.add("Другие");
                    data.add(summarize);
                }
                createPie(getView(), R.id.secondPie, data, names);
            }
        });
    }
    private void soloCategoriesPie(Long days){
        Map<String, Integer> names_data = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();
        db.stats().getCategoriesCount(days, id_user).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
            @Override
            public void onChanged(List<Stats> stats) {
                for (Stats stat: stats) {
                    names_data.put(stat.getName(), stat.getCount());
                }
                Map<String, Integer> sorted_names_data = StatsConverter.sortedDict(names_data);
                if (sorted_names_data.size() <= 16){
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        names.add(value.getKey());
                        data.add(value.getValue());
                    }
                } else {
                    int counter = 0;
                    int summarize = 0;
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        if (counter < 16){
                            names.add(value.getKey());
                            data.add(value.getValue());
                            counter++;
                        } else {
                            summarize = summarize + value.getValue();
                        }
                    }
                    names.add("Другие");
                    data.add(summarize);
                }
                createPie(getView(), R.id.firstPie, data, names);
            }
        });
    }
    private void generalCategoriesPie(Long days){
        Map<String, Integer> names_data = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();
        db.stats().getGeneralCategoriesCount(days, Long.valueOf(id_group)).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
            @Override
            public void onChanged(List<Stats> stats) {
                for (Stats stat: stats) {
                    names_data.put(stat.getName(), stat.getCount());
                }
                Map<String, Integer> sorted_names_data = StatsConverter.sortedDict(names_data);
                if (sorted_names_data.size() <= 16){
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        names.add(value.getKey());
                        data.add(value.getValue());
                    }
                } else {
                    int counter = 0;
                    int summarize = 0;
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        if (counter < 16){
                            names.add(value.getKey());
                            data.add(value.getValue());
                            counter++;
                        } else {
                            summarize = summarize + value.getValue();
                        }
                    }
                    names.add("Другие");
                    data.add(summarize);
                }
                createPie(getView(), R.id.secondPie, data, names);
            }
        });
    }
    private void linearData(Long days){
        Map<String, Integer> date_value = new HashMap<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        db.stats().getSpending(days).observe(getViewLifecycleOwner(), new Observer<List<Price>>() {
            @Override
            public void onChanged(List<Price> prices) {
                for (Price price: prices) {
                    LocalDate date = DateConverter.FromLongDateToLocalDateTime(price.getDate()).toLocalDate();
                    String str_date = String.valueOf(date);
                    if (date_value.get(str_date) == null){
                        date_value.put(str_date, 0);
                    }
                    date_value.computeIfPresent(str_date, (k, v) -> v + price.getPrice());
                }

                if (date_value.size() > 7){
                    int step = date_value.size()/7;
                    int tmp = (int) date_value.values().toArray()[0];
                    for (int i = 1; i != date_value.size(); i++){
                        if (i % step != 0){
                            int valueForIKey = (int) date_value.values().toArray()[i];
                            tmp = tmp + valueForIKey;
                        } else if (i == date_value.size()) {
                            data.add(tmp);
                            dates.add((String) date_value.keySet().toArray()[i]);
                        } else {
                            data.add(tmp);
                            dates.add((String) date_value.keySet().toArray()[i]);
                            tmp = (int) date_value.values().toArray()[i];
                        }
                    }
                } else {
                    for (Map.Entry<String, Integer> value : date_value.entrySet()) {
                        data.add(value.getValue());
                        dates.add(value.getKey());
                    }
                }
                Collections.reverse(data);
                Collections.reverse(dates);



                createLinear(dates, data, getView());
            }
        });
    }
    private void linearData(Long days, Long id){
        Map<String, Integer> date_value = new HashMap<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        db.stats().getSpending(days, id).observe(getViewLifecycleOwner(), new Observer<List<Price>>() {
            @Override
            public void onChanged(List<Price> prices) {

                for (Price price: prices) {
                    LocalDate date = DateConverter.FromLongDateToLocalDateTime(price.getDate()).toLocalDate();
                    String str_date = String.valueOf(date);
                    if (date_value.get(str_date) == null){
                        date_value.put(str_date, 0);
                    }
                    date_value.computeIfPresent(str_date, (k, v) -> v + price.getPrice());
                }
                if (date_value.size() > 8){
                    int step = date_value.size()/7;
                    int tmp = 0;
                    for (int i = 0; i != date_value.size(); i++){
                        if (i % step != 0){
                            int valueForIKey = (int) date_value.values().toArray()[i];
                            tmp = tmp + valueForIKey;
                        } else if (i == step) {
                            data.add(tmp);
                            dates.add((String) date_value.keySet().toArray()[i]);
                        } else {
                            data.add(tmp);
                            dates.add((String) date_value.keySet().toArray()[i]);
                            tmp = 0;
                        }
                    }
                } else {
                    for (Map.Entry<String, Integer> value : date_value.entrySet()) {
                        data.add(value.getValue());
                        dates.add(value.getKey());
                    }
                }
                createLinear(dates, data, getView());
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    private void createLinear(ArrayList<String> names, ArrayList<Integer> data, View view){
        MyLineView lineView = (MyLineView) view.findViewById(R.id.linearGraphic);
        lineView.setDrawDotLine(false);
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY);
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<ArrayList<Integer>>();
        dataLists.add(data);
        lineView.setBottomTextList(names);
        lineView.setColorArray(new int[]{Color.rgb(124, 223, 181)});
        lineView.setDataList(dataLists);
    }

    private void createPie(View view, int id, ArrayList<Integer> data, ArrayList<String> names){
        MyPieView pieView = (MyPieView) view.findViewById(id);
        ArrayList<MyPieHelper> pieHelperArrayList = StatsConverter.percentageConvert(data);

        pieView.setDate(pieHelperArrayList);
        pieView.addNames(names);
        pieView.showPercentLabel(true);
    }

    private void init(View view){
        dates = new ArrayList<String>(Arrays.asList("неделя", "месяц", "сезон", "год", "всё время"));
        spinner = view.findViewById(R.id.spinner_period);
        adapter = new AdapterForSpinner(getContext(), dates);

        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        id_group = settings.getString(getString(R.string.key_for_select_group_id),"XD");
        id_user = Long.valueOf(getString(R.string.me_id));
        db = new ImplDB(getContext());

        textTitle = view.findViewById(R.id.titleName);
        textFirstPie = view.findViewById(R.id.titleFirstPie);
        textSecondPie = view.findViewById(R.id.titleSecondPie);
    }
}