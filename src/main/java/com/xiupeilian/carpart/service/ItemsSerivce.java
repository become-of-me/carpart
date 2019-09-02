package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Items;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemsSerivce {
    List<Items> findItemsByQueryVo(Items items);

    List<Items> findItemsByUserId(Integer id);

    void deleteItemById(Integer id);

    void addItem(Items items, MultipartFile file);

    Items findItemById(Integer id);

    void updateItemById(Items items, MultipartFile file);
}
