package com.example.demo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.Service.ItemService;
import com.example.demo.domain.Category;
import com.example.demo.domain.Item;

import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/list")
public class ListController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private HttpSession session;

    private static final int VIEW_SIZE = 30;

    @RequestMapping("")
    public String list(Integer page) {

        if (page == null) {
            page = 0;
        }
        
        List<Item> itemList = itemService.topPageList();
        Page<Item> itemPage = itemService.pageSetting(itemList, page, VIEW_SIZE);
        session.setAttribute("itemPage", itemPage);

        List<Category> parentCategoryList = itemService.parentCategoryList();
        session.setAttribute("parentCategoryList", parentCategoryList);
        return "list";
    }

    /**
     * 商品検索
     * @param itemName  アイテム名
     * @param brandName　ブランド名
     * @param parentCategory　親カテゴリ名
     * @param chaildCategory　子カテゴリ名
     * @param page　ページ数
     * @return　listページ
     */
    @RequestMapping("/search")
    public String serch(String itemName, String brandName, String parentCategory,
            @RequestParam(value = "childCategory", defaultValue = "") String chaildCategory, Integer page) {
        
        List<Item> itemList ;
        Page<Item> itemPage ;

        if (page == null) {
            page = 0;
            itemList = itemService.findItems(itemName, brandName, parentCategory, chaildCategory);
            session.setAttribute("itemList", itemList);
        }else{
            page++;
            
            itemList =  (List<Item>)session.getAttribute("itemList");
            
        }
        itemPage = itemService.pageSetting(itemList, page, VIEW_SIZE);
        session.setAttribute("itemPage", itemPage);
        
       
        return "list";
    }


    /**
     * ページ検索
     */
    @RequestMapping("/pageSearch")
    public String pageSearch(Integer page){ 
        
        page--;
        List<Item> itemList =  (List<Item>)session.getAttribute("itemList");
        try{
        Page<Item> itemPage = itemService.pageSetting(itemList, page, VIEW_SIZE);
        session.setAttribute("itemPage", itemPage);
        }catch(IllegalArgumentException e){
            System.out.println("ページ番号の誤り");
            e.printStackTrace();     
            return "list";
        }
       
        return "list";

    }



    /**
     * 非同期処理（プルダウン連動）
     */
    @ResponseBody
    @PostMapping("/serchChildCategory")
    public List<Category> searchChild(String parentCategory) {

        List<Category> childCategories = itemService.chCategoryList(parentCategory);
        return childCategories;
    }


    


    /**
     * ID検索
     * 
     * @param id　商品id
     * @return 詳細ページ
     */
    @RequestMapping("/detail")
    public String detail(Model model, Integer id) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);

        return "detail";
    }
    //childcategoryの情報を保持して表示をしてみて！
    //詳細ページの戻る時に直前のページに戻る方法を履歴ない時にも表示するところ未完
    //

}
