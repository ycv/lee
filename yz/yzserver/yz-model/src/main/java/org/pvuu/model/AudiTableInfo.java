package org.pvuu.model;

import java.io.Serializable;

/**
 * @author lzb
 */
public class AudiTableInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
