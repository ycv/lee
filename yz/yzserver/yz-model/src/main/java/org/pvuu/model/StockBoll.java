package org.pvuu.model;

// 布林带boll数据查询
public class StockBoll extends AudiTableInfo {
    private Integer id;
    private String code;
    private String market;
    private String date;

    private Double UPPER;
    private Double MID;
    private Double LOWER;

    private String createTime;
    private String updateTime;

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

    public Double getUPPER() {
        return UPPER;
    }

    public void setUPPER(Double UPPER) {
        this.UPPER = UPPER;
    }

    public Double getMID() {
        return MID;
    }

    public void setMID(Double MID) {
        this.MID = MID;
    }

    public Double getLOWER() {
        return LOWER;
    }

    public void setLOWER(Double LOWER) {
        this.LOWER = LOWER;
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
}
