package com.project.shared_card.activity.converter;

import com.project.shared_card.activity.main_screen.statistic.graphics.MyPieHelper;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsConverter {
    public static ArrayList<MyPieHelper> percentageConvert(ArrayList<Integer> data){
        double summa = 0.0;
        for (Integer value: data) {
            summa += value;}

        ArrayList<MyPieHelper> pieHelperArrayList = new ArrayList<MyPieHelper>();
        for (Integer value: data) {
            double per = value / summa * 100;
            pieHelperArrayList.add(new MyPieHelper((float) per));}
        return pieHelperArrayList;
    }

    public static Map<String, Integer> sortedDict(Map<String, Integer> data){
        Map<String, Integer> sortedMap = data.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        return sortedMap;
    }
    public static Map<LocalDate, Integer> sortedDate(Map<LocalDate, Integer> data){
        Map<LocalDate, Integer> sortedMap = data.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> (int) -e.getKey().getLong(ChronoField.DAY_OF_YEAR)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));

        return sortedMap;
    }
}
