package org.pvuu.model;

public class StockRsi extends AudiTableInfo {
    private Integer id;

    // 当6日RSI低于20时，行情进入超卖状态。这是看涨买入信号;当6日RSI超过80时，行情进入超买状态。这是看跌卖出信号。
    // 当6日RSI在低位向上穿越12日RSI时，形成RSI低位金叉。这是看涨买入信号。当6日RSI在高位向下穿越12日RSI时，形成RSI高位死叉。这是看跌卖出信号。
    // 当6日RSI连续两次下跌到同一位置获得支撑反弹时，形成RSI的双重底形态。这是看涨买入信号。
    // 当6日RSI连续两次上涨到同一位置遇到阻力回落时，形成RSI的双重顶形态。这是看跌卖出信号。
    // 如果股价连创新低的同时RSI指标没有创新低，就形成RSI指标底背离。底背离是看涨买入信号。
    // 如果股价连创新高的同时RSI指标不能创新高，就形成RSI指标顶背离。顶背离是看跌卖出信号。

    // 股票代码
    private String code;

    // 6日黄线
    private Double rsi6;
    // 12日绿线
    private Double rsi12;
    // 24日蓝线
    private Double rsi24;

    // 日期
    private String date;
    private String createTime;
    private String updateTime;

    private String beginDate;
    private String endDate;

    private String name;
    private String market;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Double getRsi6() {
        return rsi6;
    }

    public void setRsi6(Double rsi6) {
        this.rsi6 = rsi6;
    }

    public Double getRsi12() {
        return rsi12;
    }

    public void setRsi12(Double rsi12) {
        this.rsi12 = rsi12;
    }

    public Double getRsi24() {
        return rsi24;
    }

    public void setRsi24(Double rsi24) {
        this.rsi24 = rsi24;
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
