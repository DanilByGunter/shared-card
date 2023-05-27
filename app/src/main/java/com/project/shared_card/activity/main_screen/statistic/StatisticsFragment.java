package com.project.shared_card.activity.main_screen.statistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.converter.StatsConverter;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyBarView;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyLineView;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieHelper;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieView;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.statistic.model.Price;
import com.project.shared_card.database.entity.statistic.model.Stats;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    FrameLayout backgroundFirstPie;
    FrameLayout backgroundSecondPie;
    ConstraintLayout backgroundGraphic;
    TextView textFirstHist;
    TextView textSecondHist;
    FrameLayout backgroundFirstHist;
    FrameLayout backgroundSecondHist;
    CustomScrollView verticalScroll;



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
            textFirstHist.setText("Траты по категориям");
            textSecondHist.setText("Траты по магазинам");


            switch (spinner.getSelectedItemPosition()){
                case 0:
                    days = DateConverter.FromNowDiffDateToLong(7L);
                case 1:
                    days = DateConverter.FromNowDiffDateToLong(30L);
                case 2:
                    days = DateConverter.FromNowDiffDateToLong(91L);
                case 3:
                    days = DateConverter.FromNowDiffDateToLong(365L);
                case 4:
                    days = DateConverter.FromNowDiffDateToLong(999L);
                default:
                    days = DateConverter.FromNowDiffDateToLong(7L);
            }
            System.out.println(spinner.getSelectedItemPosition());
            linearData(days);
            shopsStats(days);
            categoritsStats(days);
        } else{
            textFirstPie.setText("Личные траты по категориям");
            textSecondPie.setText("Общие траты по категориям");
            switch (spinner.getSelectedItemPosition()){
                case 0:
                    days = DateConverter.FromNowDiffDateToLong(7L);
                case 1:
                    days = DateConverter.FromNowDiffDateToLong(30L);
                case 2:
                    days = DateConverter.FromNowDiffDateToLong(91L);
                case 3:
                    days = DateConverter.FromNowDiffDateToLong(365L);
                case 4:
                    days = DateConverter.FromNowDiffDateToLong(999L);
                default:
                    days = DateConverter.FromNowDiffDateToLong(7L);
            }
        }
    }

    private void shopsStats(Long days){
        Map<String, Integer> names_count = new HashMap<>();
        Map<String, Integer> names_sum = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> data_count = new ArrayList<>();
        ArrayList<Integer> data_summ = new ArrayList<>();
        db.stats().getShopStats(days, id_user).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
            @Override
            public void onChanged(List<Stats> stats) {
                for (Stats stat: stats) {
                    names_count.put(stat.getName(), stat.getCount());
                    names_sum.put(stat.getName(), stat.getSumma());
                }
                System.out.println(names_count);
                System.out.println(names_sum);
                Map<String, Integer> sorted_names_count = StatsConverter.sortedDict(names_count);
                Map<String, Integer> sorted_names_sum = StatsConverter.sortedDict(names_sum);
                if (sorted_names_count.size() < 16){
                    for (int i = 0; i != sorted_names_count.size(); i++) {
                        data_count.add((int) sorted_names_count.values().toArray()[i]);
                        data_summ.add((int) sorted_names_sum.values().toArray()[i]);
                        names.add((String) sorted_names_sum.keySet().toArray()[i]);
                    }
                } else {
                    int summarize_count = 0;
                    int summarize_summ = 0;
                    for (int i = 0; i != sorted_names_count.size(); i++) {
                        if (i < 15){
                            data_count.add((int) sorted_names_count.values().toArray()[i]);
                            data_summ.add((int) sorted_names_sum.values().toArray()[i]);
                            names.add((String) sorted_names_sum.keySet().toArray()[i]);
                        } else {
                            summarize_count = summarize_count + (int) sorted_names_count.values().toArray()[i];
                            summarize_summ = summarize_summ + (int) sorted_names_sum.values().toArray()[i];
                        }
                    }
                    names.add("Другие");
                    data_count.add(summarize_count);
                    data_summ.add(summarize_summ);
                }
                if (sorted_names_count.size() != 0){
                    createPie(getView(), R.id.secondPie, data_count, names);
                    createHist(getView(), R.id.secondHist, data_summ, names);
                    visibility(View.VISIBLE, true);
                }
            }
        });
    }
    private void categoritsStats(Long days){
        Map<String, Integer> names_count = new HashMap<>();
        Map<String, Integer> names_sum = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> data_count = new ArrayList<>();
        ArrayList<Integer> data_summ = new ArrayList<>();
        db.stats().getCategoriesStats(days, id_user, Long.valueOf(id_group)).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
            @Override
            public void onChanged(List<Stats> stats) {
                for (Stats stat: stats) {
                    names_count.put(stat.getName(), stat.getCount());
                    names_sum.put(stat.getName(), stat.getSumma());
                }
                Map<String, Integer> sorted_names_count = StatsConverter.sortedDict(names_count);
                Map<String, Integer> sorted_names_sum = StatsConverter.sortedDict(names_sum);
                if (sorted_names_count.size() < 16){
                    for (int i = 0; i != sorted_names_count.size(); i++) {
                        data_count.add((int) sorted_names_count.values().toArray()[i]);
                        data_summ.add((int) sorted_names_sum.values().toArray()[i]);
                        names.add((String) sorted_names_sum.keySet().toArray()[i]);
                    }
                } else {
                    int summarize_count = 0;
                    int summarize_summ = 0;
                    for (int i = 0; i != sorted_names_count.size(); i++) {
                        if (i < 15){
                            data_count.add((int) sorted_names_count.values().toArray()[i]);
                            data_summ.add((int) sorted_names_sum.values().toArray()[i]);
                            names.add((String) sorted_names_sum.keySet().toArray()[i]);
                        } else {
                            summarize_count = summarize_count + (int) sorted_names_count.values().toArray()[i];
                            summarize_summ = summarize_summ + (int) sorted_names_sum.values().toArray()[i];
                        }
                    }
                    names.add("Другие");
                    data_count.add(summarize_count);
                    data_summ.add(summarize_summ);
                }
                if (sorted_names_count.size() != 0){
                    createPie(getView(), R.id.firstPie, data_count, names);
                    createHist(getView(), R.id.firstHist, data_summ, names);
                    visibility(View.VISIBLE, true);
                }
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
                if (sorted_names_data.size() == 0){
                    textSecondPie.setText("Будущие общие траты по категориям");
                } else if (sorted_names_data.size() <  16){
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        names.add(value.getKey());
                        data.add(value.getValue());
                    }
                    createPie(getView(), R.id.secondPie, data, names);
                } else {
                    int counter = 0;
                    int summarize = 0;
                    for (Map.Entry<String, Integer> value : sorted_names_data.entrySet()) {
                        if (counter < 15){
                            names.add(value.getKey());
                            data.add(value.getValue());
                            counter++;
                        } else {
                            summarize = summarize + value.getValue();
                        }
                    }
                    names.add("Другие");
                    data.add(summarize);
                    createPie(getView(), R.id.secondPie, data, names);
                }
            }
        });
    }
    private void linearData(Long days){
        Map<LocalDate, Integer> data_dict = new HashMap<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        db.stats().getSpending(days).observe(getViewLifecycleOwner(), new Observer<List<Price>>() {
            @Override
            public void onChanged(List<Price> prices) {
                for (Price price: prices) {
                    LocalDate date = DateConverter.FromLongDateToLocalDateTime(price.getDate()).toLocalDate();
                    if (data_dict.get(date) == null){
                        data_dict.put(date, 0);
                    }
                    data_dict.computeIfPresent(date, (k, v) -> v + price.getPrice());
                }
                Map<LocalDate, Integer> date_value = StatsConverter.sortedDate(data_dict);
                if (date_value.size() == 0){
                    textTitle.setText("Будущая статистика по тратам");
                }  else if (date_value.size() > 7){
                    int step = date_value.size()/7;
                    int tmp = (int) date_value.values().toArray()[0];
                    for (int i = 1; i != date_value.size(); i++){
                        if (i % step != 0){
                            int valueForIKey = (int) date_value.values().toArray()[i];
                            tmp = tmp + valueForIKey;
                        } else {
                            data.add(tmp);
                            dates.add(String.valueOf(date_value.keySet().toArray()[i]));
                            tmp = (int) date_value.values().toArray()[i];
                        }
                        if (i == date_value.size()-1) {
                            data.add(tmp);
                            dates.add(String.valueOf(date_value.keySet().toArray()[i]));}
                        createLinear(dates, data, getView());
                    }
                } else {
                    for (Map.Entry<LocalDate, Integer> value : date_value.entrySet()) {
                        data.add(value.getValue());
                        dates.add(String.valueOf(value.getKey()));
                    }
                    createLinear(dates, data, getView());
                }
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

    private void createHist(View view, int id, ArrayList<Integer> data, ArrayList<String> names){
        MyBarView barView = (MyBarView) view.findViewById(id);

        barView.setBottomTextList(names);
        barView.setDataList(data, data.get(0));
    }

    private void visibility(int visible, boolean mode){
        backgroundFirstPie.setVisibility(visible);
        backgroundSecondPie.setVisibility(visible);
        backgroundGraphic.setVisibility(visible);
        backgroundFirstHist.setVisibility(visible);
        backgroundSecondHist.setVisibility(visible);
        verticalScroll.setEnableScrolling(mode);
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
        textFirstHist = view.findViewById(R.id.titleFirstHist);
        textSecondHist = view.findViewById(R.id.titleSecondHist);

        backgroundFirstPie = view.findViewById(R.id.backgroundFirstPie);
        backgroundSecondPie = view.findViewById(R.id.backgroundSecondPie);
        backgroundFirstHist = view.findViewById(R.id.backgroundFirstHist);
        backgroundSecondHist = view.findViewById(R.id.backgroundSecondHist);
        backgroundGraphic = view.findViewById(R.id.backgroundGraphic);
        verticalScroll = view.findViewById(R.id.verticalScroll);

        visibility(View.INVISIBLE, false);
    }
}
