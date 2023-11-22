package org.pvuu.model;

public class StockWr extends AudiTableInfo {
    private Integer id;
    private String code;
    private String market;
    private String date;
    private String createTime;
    private String updateTime;
    private Double wr1;
    private Double wr2;

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

    public Double getWr1() {
        return wr1;
    }

    public void setWr1(Double wr1) {
        this.wr1 = wr1;
    }

    public Double getWr2() {
        return wr2;
    }

    public void setWr2(Double wr2) {
        this.wr2 = wr2;
    }
}
