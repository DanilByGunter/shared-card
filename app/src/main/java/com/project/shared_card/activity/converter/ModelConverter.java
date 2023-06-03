package com.project.shared_card.activity.converter;

import android.content.Context;
import android.net.Uri;

import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.target.FullTarget;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;
import com.project.shared_card.retrofit.model.TheAllGroup;
import com.project.shared_card.retrofit.model.dto.UsersGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelConverter {
    public static GroupNameEntity FromServerGroupToEntity(TheAllGroup allGroup, String getFilesDir) {
        return new GroupNameEntity(allGroup.getId(), allGroup.getName(),allGroup.getPhoto());
    }
    public static List<UserNameEntity> FromServerUserToEntity(List<UsersGroup> usersGroups, String getFilesDir){
        List<UserNameEntity> users = new ArrayList<>();
        for(UsersGroup usersGroup: usersGroups){
            users.add(new UserNameEntity(usersGroup.getId(),usersGroup.getName(),usersGroup.getPhoto()));
        }
        return users;
    }

    public static void savePhoto(String getFilesDir, byte[] bytes, long id, Boolean directory) {

        try {
            FileOutputStream fos;
            if (directory) {
                File fileUser = new File(getFilesDir + "/user");
                if(!fileUser.exists())
                    fileUser.mkdir();
                fos = new FileOutputStream(getFilesDir + "/user/" +String.valueOf(id));
                fos.write(bytes);
            } else {
                File fileGroup = new File(getFilesDir + "/group");
                if(!fileGroup.exists())
                    fileGroup.mkdir();
                fos = new FileOutputStream(getFilesDir + "/group/" + String.valueOf(id));
                fos.write(bytes);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] getPhoto(Context context,String user_path){
        FileInputStream inputStream = null;
        byte[] photo = null;
        try {
             inputStream = new FileInputStream(user_path);
            //inputStream = context.getContentResolver().openInputStream(Uri.parse(user_path));
            photo = new byte[inputStream.available()];
            inputStream.read(photo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  photo;
    }

    public static List<Product> FromProductEntityToProductModel(List<FullProduct> check) {
        List<Product> checks = new ArrayList<>();

        for (FullProduct item : check) {
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

    public static List<Target> FromTargetEntityToTargetModel(List<FullTarget> check) {
        List<Target> checks = new ArrayList<>();
        for (FullTarget item : check) {
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



//    public static List<History> FromProductsEntityToHistory(List<FullProduct> fullProduct) {
//        List<History> histories = new ArrayList<>();
//        for (FullProduct fullProduct1 : fullProduct)
//            histories.add(new History(
//                    fullProduct1.product.getProductName(),
//                    fullProduct1.category.getName(),
//                    fullProduct1.shop.getName(),
//                    fullProduct1.buyer.getName(),
//                    DateConverter.FromLongDateToLocalDateTime(fullProduct1.product.getDateLast()),
//                    fullProduct1.product.getPrice(),
//                    fullProduct1.currency.getName(),
//                    String.valueOf(fullProduct1.product.getProductCount()),
//                    fullProduct1.metric.getName()));
//        return histories;
//    }
//    public static History FromProductEntityToHistory(FullProduct fullProduct) {
//        return new History(
//                fullProduct.product.getProductName(),
//                fullProduct.category.getName(),
//                fullProduct.shop.getName(),
//                fullProduct.buyer.getName(),
//                DateConverter.FromLongDateToLocalDateTime(fullProduct.product.getDateLast()),
//                fullProduct.product.getPrice(),
//                fullProduct.currency.getName(),
//                String.valueOf(fullProduct.product.getProductCount()),
//                fullProduct.metric.getName());
//    }
//    public static History FromTargetEntityToHistory(FullTarget fullTarget) {
//        return new History(
//                fullTarget.target.getTargetName(),
//                fullTarget.category.getName(),
//                fullTarget.shop.getName(),
//                fullTarget.buyer.getName(),
//                DateConverter.FromLongDateToLocalDateTime(fullTarget.target.getDateLast()),
//                fullTarget.target.getPrice(),
//                fullTarget.currency.getName(),
//                "",
//                ""
//                );
//    }
}
