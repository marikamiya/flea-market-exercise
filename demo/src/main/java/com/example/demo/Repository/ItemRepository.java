package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

@Repository
public class ItemRepository {

        @Autowired
        private NamedParameterJdbcTemplate template;

        private static final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

        /**
         * アイテム追加する
         */
        public void add(Item item) {
                String sql = """
                                    INSERT INTO items (name,price,category,brand,CONDITION,description)
                                    VALUES(:name ,:price ,:categoryId ,:brand ,:condition ,:description);
                                """;
                SqlParameterSource param = new MapSqlParameterSource()
                                .addValue("name", item.getName())
                                .addValue("price", item.getPrice())
                                .addValue("categoryId", item.getCategoryId())
                                .addValue("brand", item.getBrand())
                                .addValue("condition", item.getCondition())
                                .addValue("description", item.getDescription());
                template.update(sql, param);

        }

        /**
         * カテゴリidを習得する
         */
        public int getCategoryId(String parentCategory, String childCategory, String grandChildCategory) {
                String sql = """
                                SELECT grand_child.id
                                    FROM category grand_child
                                    LEFT JOIN category child ON grand_child.parent_id = child.id
                                    LEFT JOIN category parent ON child.parent_id = parent.id
                                        WHERE grand_child.name = :grandChildCategory AND child.name = :childCategory AND parent.name = :parentCategory ;
                                """;

                SqlParameterSource param = new MapSqlParameterSource()
                                .addValue("grandChildCategory", grandChildCategory)
                                .addValue("childCategory", childCategory)
                                .addValue("parentCategory", parentCategory);
                int id = template.queryForObject(sql, param, Integer.class);
                return id;
        }

        /**
         * アイテムを編集する
         */
         public void editItem(Item item) {
                String sql = """
                                UPDATE items
                                SET name = :name ,CONDITION = :condition ,category = :categoryId ,brand = :brand ,price = :price ,description = :description
                                WHERE id = :id;
                                """;
                SqlParameterSource param = new MapSqlParameterSource()
                        .addValue("id", item.getId())
                        .addValue("name", item.getName())
                        .addValue("price", item.getPrice())
                        .addValue("categoryId", item.getCategoryId())
                        .addValue("brand", item.getBrand())
                        .addValue("condition", item.getCondition())
                        .addValue("description", item.getCondition());
                template.update(sql, param);
        }

        /*
         * idでアイテムを削除する
         */
         public void itemDelete(int id){
                String sql = """
                                DELETE FROM items WHERE id = :id ;
                                """;
                SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
                template.update(sql, param);
         }
        


    
}
