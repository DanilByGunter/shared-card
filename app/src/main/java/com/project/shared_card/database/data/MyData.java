package com.project.shared_card.database.data;

import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;
import com.project.shared_card.database.entity.currency.CurrencyEntity;
import com.project.shared_card.database.entity.metrics.MetricsEntity;
import com.project.shared_card.database.entity.shop.product.ShopProductEntity;
import com.project.shared_card.database.entity.shop.target.ShopTargetEntity;

import java.util.Arrays;
import java.util.List;

public class MyData {
    public static List<MetricsEntity> getMetric() {
        return Arrays.asList(
                new MetricsEntity("шт"),
                new MetricsEntity("кг"),
                new MetricsEntity("г"),
                new MetricsEntity("л"),
                new MetricsEntity("мл"));
    }

    public static List<CurrencyEntity> getCurrency() {
        return Arrays.asList(
                new CurrencyEntity("₽"),
                new CurrencyEntity("$"),
                new CurrencyEntity("€"),
                new CurrencyEntity("₤"),
                new CurrencyEntity("₴"),
                new CurrencyEntity("₸"));
    }

    public static List<ShopProductEntity> getShopProduct() {
        return Arrays.asList(
                new ShopProductEntity("Аптека"),
                new ShopProductEntity("Азбука вкуса"),
                new ShopProductEntity("Ашан"),
                new ShopProductEntity("Бристоль"),
                new ShopProductEntity("Виктория"),
                new ShopProductEntity("Вкусвилл"),
                new ShopProductEntity("Красное и белое"),
                new ShopProductEntity("Лента"),
                new ShopProductEntity("Магнит"),
                new ShopProductEntity("Окей"),
                new ShopProductEntity("Перекрёсток"),
                new ShopProductEntity("Пятерочка"),
                new ShopProductEntity("Продуктовый"),
                new ShopProductEntity("Рынок"),
                new ShopProductEntity("Самокат"),
                new ShopProductEntity("Спортпит"),
                new ShopProductEntity("Фикспрайс"),
                new ShopProductEntity("Другое"));
    }

    public static List<ShopTargetEntity> getShopTarget() {
        return Arrays.asList(
                new ShopTargetEntity("Автотовары"),
                new ShopTargetEntity("Автоцентр"),
                new ShopTargetEntity("Гипермаркет"),
                new ShopTargetEntity("Детские товары"),
                new ShopTargetEntity("Зоомагазин"),
                new ShopTargetEntity("Интернет-магазин"),
                new ShopTargetEntity("Книжный магазин"),
                new ShopTargetEntity("Магазин канцтоваров"),
                new ShopTargetEntity("Магазин одежды"),
                new ShopTargetEntity("Мебельный"),
                new ShopTargetEntity("Музыкальный магазин"),
                new ShopTargetEntity("Онлайн площадка"),
                new ShopTargetEntity("Продовольственный"),
                new ShopTargetEntity("Рынок"),
                new ShopTargetEntity("Сексшоп"),
                new ShopTargetEntity("Спецмагазин"),
                new ShopTargetEntity("Строительные товары"),
                new ShopTargetEntity("Супермаркет"),
                new ShopTargetEntity("Художественный магазин"),
                new ShopTargetEntity("Цветочный магазин"),
                new ShopTargetEntity("Церковный магазин"),
                new ShopTargetEntity("Хобби-гипермаркет"),
                new ShopTargetEntity("Электротовары"));
    }

    public static List<CategoriesProductEntity> getCategoryProduct() {
        return Arrays.asList(
                new CategoriesProductEntity("Алкоголь"),
                new CategoriesProductEntity("Бытовые товары"),
                new CategoriesProductEntity("Готовая еда"),
                new CategoriesProductEntity("Грибы"),
                new CategoriesProductEntity("Зелень"),
                new CategoriesProductEntity("Крупы"),
                new CategoriesProductEntity("Лекарство"),
                new CategoriesProductEntity("Масло"),
                new CategoriesProductEntity("Молочные продукты"),
                new CategoriesProductEntity("Морепродукты"),
                new CategoriesProductEntity("Мясо"),
                new CategoriesProductEntity("Напитки"),
                new CategoriesProductEntity("Овощи"),
                new CategoriesProductEntity("Орехи"),
                new CategoriesProductEntity("Полуфабрикаты"),
                new CategoriesProductEntity("Рыба"),
                new CategoriesProductEntity("Сладости"),
                new CategoriesProductEntity("Снеки"),
                new CategoriesProductEntity("Фрукты"),
                new CategoriesProductEntity("Химия"),
                new CategoriesProductEntity("Хлебобулочные изделия"),
                new CategoriesProductEntity("Яичные продукты"),
                new CategoriesProductEntity("Другое"));
    }

    public static List<CategoriesTargetEntity> getCategoryTarget() {
        return Arrays.asList(
                new CategoriesTargetEntity("Быт"),
                new CategoriesTargetEntity("Дом"),
                new CategoriesTargetEntity("Досуг"),
                new CategoriesTargetEntity("Здоровье"),
                new CategoriesTargetEntity("Мебель"),
                new CategoriesTargetEntity("Одежда"),
                new CategoriesTargetEntity("Подарок"),
                new CategoriesTargetEntity("Продукты"),
                new CategoriesTargetEntity("Путешествие"),
                new CategoriesTargetEntity("Спорт"),
                new CategoriesTargetEntity("Транспорт"),
                new CategoriesTargetEntity("Творчество"),
                new CategoriesTargetEntity("Электроника"),
                new CategoriesTargetEntity("Другое"));
    }
}
