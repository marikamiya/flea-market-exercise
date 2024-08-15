package com.example.demo.Repository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;

@Repository
public class ListRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Item> IT_ROW_MAPPER = (rs, i) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setBrand(rs.getString("brand"));
        item.setCategory(rs.getString("name_all"));
        item.setCondition(rs.getInt("condition"));
        item.setPrice(rs.getInt("price"));
        item.setDescription(rs.getString("description"));
        return item;
    };

    private static final RowMapper<Category> CG_ROW_MAPPER = (rs, i) -> {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setCategoryName(rs.getString("name"));
        category.setParentId(rs.getInt("parent_id"));
        category.setNameAll(rs.getString("name_all"));
        return category;
    };

    /**
     * 商品30件表示
     */
    public List<Item> topPageList() {
        String sql = """
                SELECT i.id ,i.name ,i.brand ,i.condition ,i.price ,i.category, i.description, c.name_all
                FROM items i
                   INNER JOIN category c
                    ON i.category = c.id
                    where i.deleted_at is NULL
                    order by i.name
                    limit 30
                    """;
        List<Item> itemList = template.query(sql, IT_ROW_MAPPER);
        return itemList;
    }

    /**
     * IDから検索
     */
    public Item findById(int id) {
        String sql = """
                SELECT i.NAME,
                       i.id,
                       i.category,
                       i.brand,i.CONDITION, i.price, i.description,
                       c1.name AS category_name,
                       c1.parent_id AS category_parent_id,
                       c1.name_all AS name_all,
                       c2.name AS child_category_name,
                       c2.parent_id AS child_category_parent_id,
                       c3.name AS parent_category_name
                FROM items i
                LEFT JOIN category c1 ON i.category = c1.id
                LEFT JOIN category c2 ON c1.parent_id = c2.id
                LEFT JOIN category c3 ON c2.parent_id = c3.id
                 WHERE i.id = :id;
                                """;
        SqlParameterSource pram = new MapSqlParameterSource().addValue("id", id);
        Item item = template.queryForObject(sql, pram, IT_ROW_MAPPER);
        return item;
    }

    /**
     * 商品検索
     */
    public List<Item> findByItemNameCategoryBrand(String itemName, String brandName, String parentCategory,
            String childCategory) {
        String sql = """
                SELECT i.NAME, i.id,
                        i.category,
                        i.brand,i.CONDITION, i.price, i.description,
                        c1.name AS category_name,
                        c1.parent_id AS category_parent_id,
                        c1.name_all AS name_all,
                        c2.name AS child_category_name,
                        c2.parent_id AS parent_category_parent_id,
                        c3.name AS parent_category_name
                            FROM items i
                            LEFT JOIN category c1 ON i.category = c1.id
                            LEFT JOIN category c2 ON c1.parent_id = c2.id
                            LEFT JOIN category c3 ON c2.parent_id = c3.id
                             WHERE i.name LIKE :itemName AND i.brand LIKE :brandName AND c3.name LIKE :parentCategory AND c2.name LIKE :childCategory AND i.deleted_at is null
                                order by i.id
                    """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", "%" + itemName + "%")
                .addValue("brandName", "%" + brandName + "%")
                .addValue("parentCategory", "%" + parentCategory + "%")
                .addValue("childCategory", "%" + childCategory + "%");
        List<Item> itemList = template.query(sql, param, IT_ROW_MAPPER);
        if (itemList.size() == 0) {
            return null;
        }
        return itemList;
    }

    /**
     * pearentカテゴリ全件リスト
     */
    public List<Category> parenCategories() {
        String sql = """
                SELECT * FROM category
                    WHERE
                        parent_id IS  NULL
                    AND
                        name_all IS  NULL;
                """;
        List<Category> pCategoryList = template.query(sql, CG_ROW_MAPPER);
        return pCategoryList;
    }

    /**
     * parentカテゴリからchildカテゴリリスト表示
     */
    public List<Category> childCategories(String parentCategory) {
        String sql = """
                SELECT *
                    FROM category ch_cg
                    LEFT JOIN category pa_cg ON ch_cg.parent_id = pa_cg.id
                    WHERE pa_cg.name = :parentCategory;
                """;
        SqlParameterSource pram = new MapSqlParameterSource()
                .addValue("parentCategory", parentCategory);
        List<Category> childCategoryList = template.query(sql, pram, CG_ROW_MAPPER);
        return childCategoryList;
    }

    /**
     * grandChildカテゴリリスト表示
     */
    public List<Category> grandChildCategories(String parentCategory, String childCategory) {
        String sql = """
                SELECT  *
                    FROM category g_ch_category
                    LEFT JOIN category ch_category ON g_ch_category.parent_id = ch_category.id
                    LEFT JOIN category pa_category ON ch_category.parent_id = pa_category.id
                    WHERE pa_category.name = :parentCategory AND ch_category.name = :childCategory;
                """;
        SqlParameterSource pram = new MapSqlParameterSource()
                .addValue("parentCategory", parentCategory).addValue("childCategory", childCategory);
        List<Category> grandChildCategories = template.query(sql, pram, CG_ROW_MAPPER);
        return grandChildCategories;
    }
}
