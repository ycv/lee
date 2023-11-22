package org.pvuu.model;

// 龙虎榜
public class StockDragonTiger extends AudiTableInfo {

    // 总 买入额（万元）
    private Double buyAmount;
    private String buyAmountStr;
    // 涨跌幅%
    private Double chg;
    private String chgStr;
    // 收盘价
    private Double close;
    // 总卖出额(万元)
    private Double sellAmount;
    private String sellAmountStr;
    // 龙虎榜成交额（万元）
    private Double topAmount;
    // 换手率
    private Double turnover;
    private String turnoverStr;
    // 日期
    private String endDate;
    private String name;
    // 上榜原因
    private String reason;
    private String code;
    private String thsCode;
    private String market;
    private String createTime;
    private String updateTime;

    public String getTurnoverStr() {
        return turnoverStr;
    }

    public void setTurnoverStr(String turnoverStr) {
        this.turnoverStr = turnoverStr;
    }

    public String getChgStr() {
        return chgStr;
    }

    public void setChgStr(String chgStr) {
        this.chgStr = chgStr;
    }

    public String getBuyAmountStr() {
        return buyAmountStr;
    }

    public void setBuyAmountStr(String buyAmountStr) {
        this.buyAmountStr = buyAmountStr;
    }

    public String getSellAmountStr() {
        return sellAmountStr;
    }

    public void setSellAmountStr(String sellAmountStr) {
        this.sellAmountStr = sellAmountStr;
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

    public String getThsCode() {
        return thsCode;
    }

    public void setThsCode(String thsCode) {
        this.thsCode = thsCode;
    }

    public Double getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(Double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Double getChg() {
        return chg;
    }

    public void setChg(Double chg) {
        this.chg = chg;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Double getTopAmount() {
        return topAmount;
    }

    public void setTopAmount(Double topAmount) {
        this.topAmount = topAmount;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}
