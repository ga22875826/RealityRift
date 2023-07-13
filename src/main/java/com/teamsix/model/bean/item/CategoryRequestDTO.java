package com.teamsix.model.bean.item;

public class CategoryRequestDTO {
    private String name;
    private Integer parentid;
    private Integer id;
    
    public CategoryRequestDTO() {
        super();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getParentid() {
        return parentid;
    }
    
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
}

