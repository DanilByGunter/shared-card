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
import com.project.shared_card.activity.main_screen.statistic.graphics.MyLineView;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieHelper;
import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieView;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.statistic.model.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


        if (id_group.equals("-1")){
            textTitle.setText("Статистика расходов");
            textFirstPie.setText("Траты по категориям");
            db.stats().getCategoriesCount(7L, id_user).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
                @Override
                public void onChanged(List<Stats> stats) {
                    for (Stats stat: stats) {
                        System.out.println(stat.getName() + " " + stat.getCount());
                    }
                }
            });
            textSecondPie.setText("Траты по магазинам");
            db.stats().getShopsCount(7L, id_user).observe(getViewLifecycleOwner(), new Observer<List<Stats>>() {
                @Override
                public void onChanged(List<Stats> stats) {
                    for (Stats stat: stats) {
                        System.out.println(stat.getName() + " " + stat.getCount());
                    }
                }
            });
        } else{
            textFirstPie.setText("Личные траты по категориям");
            textSecondPie.setText("Общие траты по категориям");
        }


        ArrayList<String> names = new ArrayList<String>();
        names.add("first");
        names.add("second");
        names.add("third");
        names.add("forth");
        names.add("fifth");
        names.add("sixth");
        names.add("seventh");
        names.add("eight");
        names.add("ninth");
        names.add("tenth");

        ArrayList<Integer> data = new ArrayList<Integer>();
        data.add(40);
        data.add(20);
        data.add(10);
        data.add(50);
        data.add(30);
        data.add(80);
        data.add(70);
        data.add(60);
        data.add(90);
        data.add(100);

        createLinear(names,data, view);
        createPie(view, R.id.firstPie, data);
        createPie(view, R.id.secondPie, data);
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

    private void createPie(View view, int id, ArrayList<Integer> data){
        MyPieView pieView = (MyPieView) view.findViewById(id);
        ArrayList<MyPieHelper> pieHelperArrayList = percentageConvert(data);

        pieView.setDate(pieHelperArrayList);
        pieView.selectedPie(2);
        pieView.showPercentLabel(true);
    }

    private ArrayList<MyPieHelper> percentageConvert(ArrayList<Integer> data){
        double summa = 0.0;
        for (Integer value: data) {
            summa += value;}

        ArrayList<MyPieHelper> pieHelperArrayList = new ArrayList<MyPieHelper>();
        for (Integer value: data) {
            double per = value / summa * 100;
            pieHelperArrayList.add(new MyPieHelper((int)Math.round(per)));}
        return pieHelperArrayList;
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