package org.pvuu.model;

// 乖离率BIAS指标
public class StockBias extends AudiTableInfo {
    private Integer id;
    private String code;
    private String market;
    private Double bias1;
    private Double bias2;
    private Double bias3;
    private String date;
    private String api_code;
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

    public Double getBias1() {
        return bias1;
    }

    public void setBias1(Double bias1) {
        this.bias1 = bias1;
    }

    public Double getBias2() {
        return bias2;
    }

    public void setBias2(Double bias2) {
        this.bias2 = bias2;
    }

    public Double getBias3() {
        return bias3;
    }

    public void setBias3(Double bias3) {
        this.bias3 = bias3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApi_code() {
        return api_code;
    }

    public void setApi_code(String api_code) {
        this.api_code = api_code;
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
