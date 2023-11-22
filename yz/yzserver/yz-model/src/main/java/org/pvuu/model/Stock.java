package org.pvuu.model;

public class Stock extends AudiTableInfo {
    // 第一步、9:25竞价结束后，对量比进行排行，看前30名，涨幅在4%以下的股票；
    // 第二步、选择流通股本数量较小的，最好在3亿以下的股票，中小板佳；
    // 第三步、选择之前换手率连续多日在3%以下或连续几日平均换手率在3%以下的个股；
    // 第四步、选择之前多日成交量较为均衡或有涨停未放量现象的个股【之前一直无量涨停的个股除外】
    private Integer id;

    // 股票名称
    private String name;

    // 1为上市，其他下市
    private String state;

    // 市场简写。支持 sh、sz、hk
    private String market;
    private String jys;

    // 流通股本，万股
    private String currcapital;

    // 上市日期
    private String listing_date;
    private String retCode;
    private String stockType;
    private String remark;
    // 股票代码
    private String code;
    private String api_code;

    // 总股本，万股
    private String totalcapital;
    // 四季度净利润（亿元）
    private String profit_four;

    // 每股净资产（元）
    private String mgjzc;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private String createTime;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private String updateTime;

    public String getJys() {
        return jys;
    }

    public void setJys(String jys) {
        this.jys = jys;
    }

    public String getApi_code() {
        return api_code;
    }

    public void setApi_code(String api_code) {
        this.api_code = api_code;
    }

    public String getListing_date() {
        return listing_date;
    }

    public void setListing_date(String listing_date) {
        this.listing_date = listing_date;
    }

    public String getProfit_four() {
        return profit_four;
    }

    public void setProfit_four(String profit_four) {
        this.profit_four = profit_four;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCurrcapital() {
        return currcapital;
    }

    public void setCurrcapital(String currcapital) {
        this.currcapital = currcapital;
    }

    public String getTotalcapital() {
        return totalcapital;
    }

    public void setTotalcapital(String totalcapital) {
        this.totalcapital = totalcapital;
    }

    public String getMgjzc() {
        return mgjzc;
    }

    public void setMgjzc(String mgjzc) {
        this.mgjzc = mgjzc;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }
}
