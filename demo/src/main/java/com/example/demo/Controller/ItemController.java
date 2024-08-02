package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Service.ItemService;
import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.form.AddForm;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    HttpSession session;
    @Autowired
    ItemService itemService;
    @Autowired
    ListController listController;

    @RequestMapping("")
    public String index(){
        List<Category> parentCategoryList = itemService.parentCategoryList();
        session.setAttribute("parentCategoryList", parentCategoryList);
        return "add";
    }
    /** 
     * アイテム編集ページ表示
     */
    @RequestMapping("editPage")
    public String editPage(Integer id, Model model){

        Item item = itemService.findById(id);
        List<Category> parenCategoryList = itemService.parentCategoryList();
        session.setAttribute("parenCategoryList", parenCategoryList);
        model.addAttribute("item", item);
        return "edit";
    }
    /** 
     * アイテム編集 
     */
    @RequestMapping("edit")
    public String edit(AddForm form){
       
        int id = getCategoryId(form);
        Item item = new Item();
        BeanUtils.copyProperties(form, item);
        item.setCategoryId(id);
        itemService.ietmEdit(item);
        return listController.list(null);

    }

    /**
     * アイテム追加 
     */
    @RequestMapping("add")
    public String add(AddForm form){
        int id = getCategoryId(form);
        Item item = new Item();
        BeanUtils.copyProperties(form, item);       
        item.setCategoryId(id);
        itemService.itemAdd(item);
        return listController.list(null);
    }
    /**
     * フォームからカテゴリIDを取得
     */
    public int getCategoryId(AddForm form){
        String parent = form.getParentCategory();
        String child = form.getChildCategory();
        String grandChild = form.getGrandChildCategory();
        int id = itemService.getCategoryId(parent, child, grandChild);
        return id;
    }
    /** 
     * 非同期処理（プルダウン連動）
     * grandChildカテゴリリスト表示
    */
    @ResponseBody
    @PostMapping("/searchGrandChildCategory")
    public List<Category> searchGrandChild(String parentCategory, String childCategory){
        return itemService.grandChCategories(parentCategory, childCategory);
    }

}
