package org.pvuu.model;

public class StockCci extends AudiTableInfo {
    // cci指标是属于超买超卖指标里的顺势指标，是专门测量股价、外汇或者贵金属交易是否已超出常态分布范围。
    // 超买就是超过买入区，因此要卖掉股票。超卖就是超过卖出区域，此时可以买入。

    /**
     * 1、CCI 为正值时，视为多头市场；为负值时，视为空头市场；
     *
     * 2、常态行情时，CCI 波动于±100之间；强势行情，CCI 会超出±100 ；
     *
     * 3、CCI＞100 时，买进参考，直到CCI＜100 时，卖出参考；
     *
     * 4、CCI＜-100 时，减仓参考，直到CCI＞-100 时，回补参考。
     */
    // 1、当CCI曲线在远离+100线上方的高位时，如果CCI曲线的走势形成M头或三重顶等顶部反转形态，
    private Integer id;
    private String code;
    private String market;
    private String date;
    private String createTime;
    private String updateTime;
    private Double cci;
    private Double cci88;

    public Double getCci88() {
        return cci88;
    }

    public void setCci88(Double cci88) {
        this.cci88 = cci88;
    }

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

    public Double getCci() {
        return cci;
    }

    public void setCci(Double cci) {
        this.cci = cci;
    }
}
