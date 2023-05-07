package com.project.shared_card.activity.converter;

import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.target.FullTarget;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public static List<Product> FromProductEntityToProductModel(List<FullProduct> check){
        List<Product> checks = new ArrayList<>();

        for (FullProduct item:check) {
            checks.add(new Product(
                    item.product.getProductName(),
                    item.category.getName(),
                    item.product.getProductCount(),
                    DateConverter.FromLongDateToLocalDateTime(item.product.getDateFirst()),
                    item.creator.getName(),
                    item.metric.getName(),
                    item.product.getStatus(),
                    item.product));
        }
        return checks;
    }
    public static List<Target> FromTargetEntityToTargetModel(List<FullTarget> check){
        List<Target> checks = new ArrayList<>();
        for (FullTarget item:check) {
            checks.add(new Target(
                    item.target.getTargetName(),
                    item.category.getName(),
                    item.target.getPrice(),
                    DateConverter.FromLongDateToLocalDateTime(item.target.getDateFirst()),
                    item.creator.getName(),
                    item.currency.getName(),
                    item.target.getStatus(),
                    item.target));
        }
        return checks;
    }
    public static ProductEntity FromProductEntityToProductModel(long id, Product product){
        ProductEntity productEntity =  new ProductEntity();
        productEntity.setId(id);
        productEntity.setProductName(product.getName());
        return null;
    }
}
