package com.project.shared_card.activity.converter;

import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public static List<Product> FromCheckEntityToCheckModel(List<FullProduct> check){
        List<Product> checks = new ArrayList<>();
        for (FullProduct item:check) {
            checks.add(new Product(
                    item.product.getProductName(),
                    item.category.getName(),
                    item.product.getProductCount(),
                    DateConverter.FromLongDateToLocalDateTime(item.product.getDateFirst()),
                    item.creator.getName(),
                    item.metric.getName(),
                    item.product.isStatus()));
        }
        return checks;
    }
    public static ProductEntity FromCheckEntityToCheckModel(long id,Product product){
        ProductEntity productEntity =  new ProductEntity();
        productEntity.setId(id);
        productEntity.setProductName(product.getName());
        return null;
    }
}
