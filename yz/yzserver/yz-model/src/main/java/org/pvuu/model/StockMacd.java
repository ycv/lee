package org.pvuu.model;

// 日线macd数据查询
public class StockMacd extends AudiTableInfo {
    /**
     * MACD的5种金叉买入形态
     * 
     * 1、金叉后上行下调再反弹： DIF与DEA金叉后，随股价的上行而上行，而后，随股价的回调而下行。当主力洗盘时，股价回调，
     * 而DIF线回调到MACD线0值附近时，DIF线反转向上，使形成了金叉后上行下调再反弹形态，此时是买入的机会。
     * 
     * 2、零轴下金叉后死叉再次金叉：DIF在零轴之以下金叉DEA线以后，并没有上穿零轴或上穿一点就回到零轴之下，然后向下死叉DEA，
     * 几天以后再次金叉DEA线。该形态为股价在下跌探底之后，抛盘穷尽之时呈现的底部形态，应该理解为见底反弹信号，可择机入市。
     * 
     */
    private Integer id;
    private String code;
    private String market;
    private String date;

    // MACD数据
    private Double dea;
    private Double macd;
    private Double dif;

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

    public Double getDea() {
        return dea;
    }

    public void setDea(Double dea) {
        this.dea = dea;
    }

    public Double getMacd() {
        return macd;
    }

    public void setMacd(Double macd) {
        this.macd = macd;
    }

    public Double getDif() {
        return dif;
    }

    public void setDif(Double dif) {
        this.dif = dif;
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
