package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.ListRepository;
import com.example.demo.domain.Category;
import com.example.demo.domain.Item;

@Service
public class ItemService {

    @Autowired
    private ListRepository listRepository;
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> topPageList() {
        List<Item> itemList = listRepository.topPageList();
        return itemList;
    }

    public Page<Item> pageSetting(List<Item> itemList, int page, int size) throws IllegalArgumentException {
        Page<Item> itemPages = Page.empty();// からのページリストを作る
        if (itemList == null || itemList.isEmpty()) {
            return itemPages;
        }

        int start = page * size;
        int end = Math.min(itemList.size(), start + size);// endを計算し、リストのサイズを超えないように制限

        List<Item> subItemList = itemList.subList(start, end);
        itemPages = new PageImpl<>(subItemList, PageRequest.of(page, size), itemList.size());

        return itemPages;
    }

    /**
     * 検索結果（フォーム）
     * 
     * @param itemName
     * @param brandName
     * @param parentCategory
     * @param chCategory
     * @return
     */
    public List<Item> findItems(String itemName, String brandName, String parentCategory, String chCategory) {
        String itemInfo = itemName + brandName + parentCategory + chCategory;
        if (itemInfo.equals("")) {// 何も検索条件に入れない場合
            return null;
        }
        List<Item> itemList = listRepository.findByItemNameCategoryBrand(itemName, brandName, parentCategory,
                chCategory);
        return itemList;
    }

    /**
     * id検索
     */
    public Item findById(int id) {
        return listRepository.findById(id);
    }

    public List<Category> parentCategoryList() {
        return listRepository.parenCategories();
    }

    public List<Category> chCategoryList(String parentCategory) {
        return listRepository.childCategories(parentCategory);
    }

    public List<Category> grandChCategories(String parentCategory, String chCategory){
        return listRepository.grandChildCategories(parentCategory, chCategory);
    }

    public void itemAdd(Item item){
         itemRepository.add(item);
    }
    public int getCategoryId(String parent ,String child ,String grandChild){
        return itemRepository.getCategoryId(parent, child, grandChild);
    }
    public void ietmEdit(Item item){
        itemRepository.edidItem(item);
    }

}
