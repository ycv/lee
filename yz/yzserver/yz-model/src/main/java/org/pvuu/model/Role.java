package org.pvuu.model;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

    private static final long serialVersionUID = 8111233872811285796L;
    private Integer id;

    private String name;

    private String nameZh;

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
        this.name = name == null ? null : name.trim();
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}