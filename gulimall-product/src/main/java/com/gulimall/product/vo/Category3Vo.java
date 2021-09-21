package com.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author aqiang9  2020-08-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category3Vo{
    private String id;
    private String name;
    private String category2Id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(String category2Id) {
        this.category2Id = category2Id == null ? null : category2Id.trim();
    }
}
