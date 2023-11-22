package org.pvuu.model;

import java.io.Serializable;

public class StockInfo implements Serializable {
    private Integer id;
    // 总市值，亿元
    private Double all_value;
    // 委差（手）
    // 对于委差的值来说，正数是比较好的，并且越大越好。委差为正，价格上升的可能性就大；反之，下降的可能性就大
    // 如果正数对买方来说是强势的那么涨价的可能性会相对更大。而负数意味着卖方是强势的，也就意味着有可能降低。
    private Double appointDiff;

    // 委比%
    // 股票委比为正数说明场内买盘较强，且数值越大，买盘就越强劲，买方力量较强，股票会涨。
    // 股票委比为负数说明抛盘较强，且数值越大，卖盘就越强劲，卖方力量强，股票会跌。
    private Double appointRate;

    // 换手率
    // 价格在高位，换手率高，一般为庄家出货所致;在低位换手率高的话;一般为庄家进货造成;;;;;; 长期换手率低的股票，价格又在低位属冷门股碰不得!
    // 个股低位换手率度过活跃期后,日换手率大于7%,且持续超过10%时,表明筹码正在急剧换手,个股将会出现急剧飙升行情。此时将是投资者最佳的操作时机
    // 持续长时间出现高换手率。高换手率出现后,说明短线流动性放 大、市场关注度提高,股价有望向上发展
    // 低位的高换手率,一种令人激动的洗盘,表明主力建仓的机率大
    // 当一只股票的换手率非常高时，股票价格通常不是在大涨就是在大跌
    private String turnover;

    // 振幅
    // 个股振幅越大，说明主力资金介入的程度就越深，反之，就越小
    private Double swing;

    // 成交量（手）
    // 持续上涨中，成交量也越来越大，随着价格抬升，买卖双方分歧剧烈，越来越多的持有者在卖出股票，这时在追涨的时候就要格外的注意了；
    // 成交量与趋势有关，下跌趋势的情况下，成交量会减少，买卖双方分歧不大，未来继续下跌的几率很大
    private Double tradeNum;
    private String tradeNumStr;

    // 成交金额(万元)
    // 成交量上涨，成交金额下降时，则代表股价在低位交易的多，此时股价通常处于下滑趋势
    // 反之，则代表股价在高位交易的多，此时股价通常处于上升趋势。
    private Double tradeAmount;
    private String tradeAmountStr;

    // 市净率
    // 市净率在3~10之间的股票，这是一个合理的范围
    // 市净率越低，股票后续上涨空间较大，市净率越高价格泡沫越大，上涨空间较小，下跌回调概率越大
    private Double pb;

    // 市盈率
    // 当市盈率为0~13时，说明该公司股票价值被低估;当市盈率为14~20时，说明该公司的股票价值处于正常水平;当市盈率为21~28时，股票价值被高估
    private Double pe;

    // 量比
    // 如果股票量比的数值越小，说明资金的流入越少，市场活跃度越低。
    // 相反，如果股票量比的数值越大，说明当天个股流入的资金越多，从侧面反映出市场活跃度越高
    // 当量比处于2.5到10的时候，说明是放量的情况。当股价长期处于低位的时候，出现大放量突破，量比达到5到10，代表着上涨的空间巨大，预示着主力即将拉升一波股价。
    // 如果在盘中量比曲线与分时线方向一致，且同时向上，说明股价上涨都是有成交量支持的，此时可以小仓位介入。
    // 如果分时线下跌，量比曲线指标上涨，则说明该个股在盘中放量杀跌，此时不可贸然介入。
    // 但当分时线上涨，量比曲线下跌，说明此时上涨已经不需要成交量来配合，预示该个股上涨强势。
    // 如果量比达到5到10倍就属于剧烈放量，如果这样的放量是出现在股价的底部的时候，那很有可能后市上涨的概率比较大，如果说一只股票在涨幅已经很大的情况下出现这么大的量比，
    // 那很有可能就是主力出货，我们一定要高度警惕。
    // 量比如果达到10倍甚至20倍的时候，一般可以考虑进行反向操作，如果在涨势中出现这种情况，说明大概率是要构筑头部，相反，如果说某只股票在跌势中出现这样的极端放量，那很可能是建仓的大好时机

    // 量比≦0.5成交量萎缩到极致，变盘随时发生。
    // 量比0.8--1.5成交量处于正常水平。
    // 量比1.5--2.5温和放量，将会延续原有趋势。
    // 量比2.5--5明显放量，可以采取相应行动了。
    // 量比5--10放巨量表现，趋势已到末期。
    // 量比>10极端放量，趋势已经到默契，可以考虑反向操作。

    // 短线操作中，股价首次放量上涨，量比指标不宜超过“5”;股价连续放量上涨，量比指标不宜超过“3”，否则需要提防主力出逃
    private Double liangbi;

    // 外盘
    // 外盘：在成交量中以主动性叫买价格成交的数量，所谓主动性叫买； 外盘指的是投资者主动买入股票并且成交的数量
    // 股价经过较长时间的数浪下跌,股价处于较低价位,成交量极度萎缩。然后,成交量开始温和放量,当日外盘数量增加,大于内盘数量,此时股价将可能上涨。
    // 股价经过较长时间的数浪上涨,股价处于较高价位,成交量增大到不能再继续增加,当日内盘数量放大,大于外盘数量,此时股价将可能下跌。
    private Double waipan;

    // 内盘
    // 内盘：在成交量中以主动性叫卖价格成交的数量，所谓主动性叫卖； 内盘指的是投资者主动卖出股票并且成交的数量。
    private Double neipan;

    // 神奇九转指标 lowNine
    private Double buy4_m;
    // 神奇九转指标 highNine
    private Double buy4_n;

    private String neipanStr;
    private String waipanStr;

    // 买一报价(元)
    private Double buy1_m;
    // 买一数量(手)
    private Double buy1_n;
    // 买二报价元
    private Double buy2_m;

    private Double buy5_m;
    private Double buy5_n;
    // 流通市值(亿元)
    private Double circulation_value;
    // 昨日收盘价（上个交易日收盘价）
    private Double closePrice;

    // 代码
    private String code;
    // 流通股本（万股）
    private Double currcapital;
    // 日期
    private String date;
    // 跌涨金额
    private Double diff_money;
    // 跌涨幅度
    private Double diff_rate;
    // 跌停价
    private Double downLimit;
    // 涨停价
    private Double highLimit;
    // 52周最大价
    private Double max52;
    // 52周最低价
    private Double min52;
    // 当前价格
    private Double nowPrice;
    // 今日开盘价（最近交易日开盘价）
    private Double openPrice;

    // 卖一报价
    private Double sell1_m;
    // 卖一数量（手）
    private Double sell1_n;
    private Double sell2_m;
    private Double sell2_n;
    private Double sell3_m;
    private Double sell3_n;
    private Double sell4_m;
    private Double sell4_n;
    private Double sell5_m;
    private Double sell5_n;

    // 1正常状态 非1停盘
    private String state;

    // 今日最高价（最近交易日最高价）
    private Double todayMax;
    // 今日最低价(最近交易日最低价)
    private Double todayMin;
    // 总股本
    private Double totalcapital;

    // 均价
    private Double junjia;
    private Double dtpe;
    private Double jtpe;

    // 年初至今 涨幅%
    private Double zjzhangfu;

    // // 每股净资产
    private Double yestodayClosePrice;
    // 刷新时间
    private String time;
    private String remark;
    // 股票名称
    private String name;
    // 市场
    private String market;

    // 5日内 涨幅% 63
    private Double zhangfu5;
    // 10日内 涨幅% 69
    private Double zhangfu10;

    private String createTime;
    private String updateTime;

    private String beginDate;
    private String endDate;
    // beginDate~endDate 相隔天数
    private Integer diffDayNum;

    private Integer page;
    private Integer size;

    public String getNeipanStr() {
        return neipanStr;
    }

    public String getWaipanStr() {
        return waipanStr;
    }

    public void setNeipanStr(String neipanStr) {
        this.neipanStr = neipanStr;
    }

    public void setWaipanStr(String waipanStr) {
        this.waipanStr = waipanStr;
    }

    public Double getJunjia() {
        return junjia;
    }

    public void setJunjia(Double junjia) {
        this.junjia = junjia;
    }

    public Double getDtpe() {
        return dtpe;
    }

    public void setDtpe(Double dtpe) {
        this.dtpe = dtpe;
    }

    public Double getJtpe() {
        return jtpe;
    }

    public void setJtpe(Double jtpe) {
        this.jtpe = jtpe;
    }

    public Double getZjzhangfu() {
        return zjzhangfu;
    }

    public void setZjzhangfu(Double zjzhangfu) {
        this.zjzhangfu = zjzhangfu;
    }

    public Double getWaipan() {
        return waipan;
    }

    public void setWaipan(Double waipan) {
        this.waipan = waipan;
    }

    public Double getNeipan() {
        return neipan;
    }

    public void setNeipan(Double neipan) {
        this.neipan = neipan;
    }

    public Double getLiangbi() {
        return liangbi;
    }

    public void setLiangbi(Double liangbi) {
        this.liangbi = liangbi;
    }

    public Double getZhangfu5() {
        return zhangfu5;
    }

    public void setZhangfu5(Double zhangfu5) {
        this.zhangfu5 = zhangfu5;
    }

    public Double getZhangfu10() {
        return zhangfu10;
    }

    public void setZhangfu10(Double zhangfu10) {
        this.zhangfu10 = zhangfu10;
    }

    public String getTradeAmountStr() {
        return tradeAmountStr;
    }

    public void setTradeAmountStr(String tradeAmountStr) {
        this.tradeAmountStr = tradeAmountStr;
    }

    public String getTradeNumStr() {
        return tradeNumStr;
    }

    public void setTradeNumStr(String tradeNumStr) {
        this.tradeNumStr = tradeNumStr;
    }

    public Integer getDiffDayNum() {
        return diffDayNum;
    }

    public void setDiffDayNum(Integer diffDayNum) {
        this.diffDayNum = diffDayNum;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAll_value() {
        return all_value;
    }

    public void setAll_value(Double all_value) {
        this.all_value = all_value;
    }

    public Double getAppointDiff() {
        return appointDiff;
    }

    public void setAppointDiff(Double appointDiff) {
        this.appointDiff = appointDiff;
    }

    public Double getAppointRate() {
        return appointRate;
    }

    public void setAppointRate(Double appointRate) {
        this.appointRate = appointRate;
    }

    public Double getBuy1_m() {
        return buy1_m;
    }

    public void setBuy1_m(Double buy1_m) {
        this.buy1_m = buy1_m;
    }

    public Double getBuy1_n() {
        return buy1_n;
    }

    public void setBuy1_n(Double buy1_n) {
        this.buy1_n = buy1_n;
    }

    public Double getBuy2_m() {
        return buy2_m;
    }

    public void setBuy2_m(Double buy2_m) {
        this.buy2_m = buy2_m;
    }

    public Double getBuy4_m() {
        return buy4_m;
    }

    public void setBuy4_m(Double buy4_m) {
        this.buy4_m = buy4_m;
    }

    public Double getBuy4_n() {
        return buy4_n;
    }

    public void setBuy4_n(Double buy4_n) {
        this.buy4_n = buy4_n;
    }

    public Double getBuy5_m() {
        return buy5_m;
    }

    public void setBuy5_m(Double buy5_m) {
        this.buy5_m = buy5_m;
    }

    public Double getBuy5_n() {
        return buy5_n;
    }

    public void setBuy5_n(Double buy5_n) {
        this.buy5_n = buy5_n;
    }

    public Double getCirculation_value() {
        return circulation_value;
    }

    public void setCirculation_value(Double circulation_value) {
        this.circulation_value = circulation_value;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getCurrcapital() {
        return currcapital;
    }

    public void setCurrcapital(Double currcapital) {
        this.currcapital = currcapital;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDiff_money() {
        return diff_money;
    }

    public void setDiff_money(Double diff_money) {
        this.diff_money = diff_money;
    }

    public Double getDiff_rate() {
        return diff_rate;
    }

    public void setDiff_rate(Double diff_rate) {
        this.diff_rate = diff_rate;
    }

    public Double getDownLimit() {
        return downLimit;
    }

    public void setDownLimit(Double downLimit) {
        this.downLimit = downLimit;
    }

    public Double getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(Double highLimit) {
        this.highLimit = highLimit;
    }

    public Double getMax52() {
        return max52;
    }

    public void setMax52(Double max52) {
        this.max52 = max52;
    }

    public Double getMin52() {
        return min52;
    }

    public void setMin52(Double min52) {
        this.min52 = min52;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getPb() {
        return pb;
    }

    public void setPb(Double pb) {
        this.pb = pb;
    }

    public Double getPe() {
        return pe;
    }

    public void setPe(Double pe) {
        this.pe = pe;
    }

    public Double getSell1_m() {
        return sell1_m;
    }

    public void setSell1_m(Double sell1_m) {
        this.sell1_m = sell1_m;
    }

    public Double getSell1_n() {
        return sell1_n;
    }

    public void setSell1_n(Double sell1_n) {
        this.sell1_n = sell1_n;
    }

    public Double getSell2_m() {
        return sell2_m;
    }

    public void setSell2_m(Double sell2_m) {
        this.sell2_m = sell2_m;
    }

    public Double getSell2_n() {
        return sell2_n;
    }

    public void setSell2_n(Double sell2_n) {
        this.sell2_n = sell2_n;
    }

    public Double getSell3_m() {
        return sell3_m;
    }

    public void setSell3_m(Double sell3_m) {
        this.sell3_m = sell3_m;
    }

    public Double getSell3_n() {
        return sell3_n;
    }

    public void setSell3_n(Double sell3_n) {
        this.sell3_n = sell3_n;
    }

    public Double getSell4_m() {
        return sell4_m;
    }

    public void setSell4_m(Double sell4_m) {
        this.sell4_m = sell4_m;
    }

    public Double getSell4_n() {
        return sell4_n;
    }

    public void setSell4_n(Double sell4_n) {
        this.sell4_n = sell4_n;
    }

    public Double getSell5_m() {
        return sell5_m;
    }

    public void setSell5_m(Double sell5_m) {
        this.sell5_m = sell5_m;
    }

    public Double getSell5_n() {
        return sell5_n;
    }

    public void setSell5_n(Double sell5_n) {
        this.sell5_n = sell5_n;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getSwing() {
        return swing;
    }

    public void setSwing(Double swing) {
        this.swing = swing;
    }

    public Double getTodayMax() {
        return todayMax;
    }

    public void setTodayMax(Double todayMax) {
        this.todayMax = todayMax;
    }

    public Double getTodayMin() {
        return todayMin;
    }

    public void setTodayMin(Double todayMin) {
        this.todayMin = todayMin;
    }

    public Double getTotalcapital() {
        return totalcapital;
    }

    public void setTotalcapital(Double totalcapital) {
        this.totalcapital = totalcapital;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Double tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public Double getYestodayClosePrice() {
        return yestodayClosePrice;
    }

    public void setYestodayClosePrice(Double yestodayClosePrice) {
        this.yestodayClosePrice = yestodayClosePrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
