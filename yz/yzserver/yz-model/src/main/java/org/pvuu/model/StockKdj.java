package org.pvuu.model;

public class StockKdj extends AudiTableInfo {
    // KDJ指标和MACD指标。如果KDJ指标和MACD指标同时在0轴下方发出金叉信号，则可以半仓以上买入;
    // 如果KDJ指标和MACD指标同时在0轴上方，且在高位开始形成死叉，则要考虑卖出。
    /**
     * 1、超买区：K、D、J这三值在20以下为超卖区，是买入信号。
     *
     * 2、超卖区：K、D、J这三值在80以上为超买区，是卖出信号。
     *
     * 3、徘徊区:K、D、J这三值在20-80之间为徘徊区，宜观望。
     * 
     * 20-80为整理区,代表股价或指数维持震荡,; 0-20为超弱去,技术超卖,代表卖盘沉重,短线即将见底的标志,操作上可以逐步买入; 0或负值区域,代表指数或行情即将见底,可大胆买入。
     * 
     * K线向上突破D线时，为买进信号
     */
    private Integer id;
    private String code;
    private String market;
    private String date;
    private Double d;
    private Double j;
    private Double k;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getJ() {
        return j;
    }

    public void setJ(Double j) {
        this.j = j;
    }

    public Double getK() {
        return k;
    }

    public void setK(Double k) {
        this.k = k;
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
