package com.gulimall.product.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class Category2Vo {
    private String id;
    private String name;
    private String category1Id;
    private List<Category3Vo> category3List ;


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

    public String getCategory1Id() {
        return category1Id;
    }

    public void setCategory1Id(String category1Id) {
        this.category1Id = category1Id == null ? null : category1Id.trim();
    }

    public List<Category3Vo> getCategory3List() {
        return category3List;
    }

    public void setCategory3List(List<Category3Vo> category3List) {
        this.category3List = category3List;
    }
}
