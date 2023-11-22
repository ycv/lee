package org.pvuu.model;

public class StockSar {
    private Integer id;
    private String code;
    private String market;
    private String date;
    private String createTime;
    private String updateTime;
    private Double sar_stock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Double getSar_stock() {
        return sar_stock;
    }

    public void setSar_stock(Double sar_stock) {
        this.sar_stock = sar_stock;
    }
}
