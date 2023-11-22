package org.pvuu.controller.market;

import static org.pvuu.utils.tools.DateUtils.getDateByParam;
import static org.pvuu.utils.tools.DateUtils.getWorkDateListByBeginEndDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.pvuu.model.*;
import org.pvuu.service.MarketService;
import org.pvuu.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/market")
public class MarketController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 涨跌幅
    private int diffRateTemp = 9;

    @Autowired
    MarketService marketService;

    @Autowired
    StockService stockService;

    /**
     * 列表
     * 
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/getMarketDataByUrl")
    public RespPageBean getMarketDataByUrl(@RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @PostMapping("/getMarketData")
    public RespPageBean getMarketData(@RequestBody Stock stock) {
        return stockService.getStockByPage(stock);
    }

    /**
     * 统计管理 -> 龙虎榜单 -> 龙虎榜列表数据
     */
    @PostMapping("/getStockDragonTigerList")
    public RespPageBean getStockDragonTigerList(@RequestBody StockDragonTiger stockDragonTiger) {
        logger.warn("getStockDragonTigerList0002 : " + JSON.toJSONString(stockDragonTiger));
        return stockService.getStockDragonTigerListByParam(stockDragonTiger);
    }

    /**
     * 统计管理 -> 股票RSI列表 -> 列表数据
     */
    @PostMapping("/getStockRsiList")
    public RespPageBean getStockRsiList(@RequestBody StockRsi stockRsi) {
        logger.warn("getStockRsiList3112 : " + JSON.toJSONString(stockRsi));
        return stockService.getStockRsiList(stockRsi);
    }

    @GetMapping("/getStockInfo")
    public void getStockInfo() {
        logger.warn("getStockInfo10022 : " + JSON.toJSONString(126));
    }

    /**
     * 全量历史记录列表
     * 
     * 统计管理 -> 全量历史记录 -> 列表查询
     * 
     * @param stockInfo
     * @return
     */
    @PostMapping("/getStockInfoRecord")
    public RespPageBean getStockInfoRecord(@RequestBody StockInfo stockInfo) {
        logger.warn("getStockInfoRecord0412 : " + JSON.toJSONString(stockInfo));
        return stockService.getStockInfoListByPage(stockInfo);
    }

    /**
     * 全量历史记录列表 指标列表数据
     *
     * @param stockRsiParam
     * @return
     */
    @PostMapping("/getStockChartDataList")
    public RespBean getStockChartDataList(@RequestBody StockRsi stockRsiParam) {
        return RespBean.ok("加载指标数据成功 !", stockService.getStockChartDataByParam(stockRsiParam));
    }

    /**
     * 统计管理 -> 全量历史记录 -> 统计数据
     */
    @GetMapping("/getActStockDate")
    public RespBean getActStockDate(@RequestParam(defaultValue = "") String code) {
        // MACD，KDJ和BOLL指标。 成交量（如果当前成交量明显较大，说明该股票正处活跃期，短线交易的机会也更多）
        // 换手率（换手率越高短线的交易机会也越多）
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String endDay = df.format(System.currentTimeMillis());
        // String beginDate = getDateByParam(endDay, "month", -2);
        String beginDate = getDateByParam(endDay, "day", -1);
        logger.warn("getActStockDate0009 : " + beginDate + "  ==  " + endDay);
        List<String> workDateList = getWorkDateListByBeginEndDay(beginDate, endDay);
        logger.warn("getActStockDate0010 : " + JSON.toJSONString(workDateList));
        // 接口说明：查询当日是不是交易日
        // workDateList = marketService.freeGetDateData(workDateList);
        List<String> skName = new ArrayList<>();
        List<Stock> sk = stockService.getAllStocksByStateAndCodePre();
        // stockwr stockcci stockkdj stockboll stockma stockmacd stockrsi stockbias
        for (Stock s : sk) {
            skName.add(s.getMarket() + "" + s.getCode());
            // // 日线wr数据查询
            // marketService.freeGetWrDataByDate(s.getCode(), "2022-10-17");
            // // // 日线cci数据查询
            // marketService.freeGetCciDataByDate(s.getCode(), "2022-10-19", "14");
            // marketService.freeGetCciDataByDate(s.getCode(), "2022-10-19", "88");
            // // 日线kdj数据查询
            // marketService.freeGetKdjDataByDate(s.getCode(), "2022-10-19");
            // 5，10，20日ma均线数据查询
            // marketService.freeGetMaData(s.getCode(), "2022-10-19");
            // // 日线macd数据查询
            // marketService.freeGetMacdDataByCode(s.getCode(), "2022-10-19");
            // // // RSI指标
            // marketService.freeGetRSIData(s.getCode(), "2022-10-19");
            // // // 布林带boll数据查询
            // marketService.freeGetBollDataByDate(s.getCode(), "2022-10-18");
            // // 乖离率BIAS指标
            // marketService.freeGetBiasDataByDate(s.getCode(), "2022-10-18");

            // 神奇九转指标
            // marketService.freeGetNineTurn(s.getCode(), "2022-09-22");
        }
        String codeTemp = "";
        for (int j = 1; j <= skName.size(); j++) {
            codeTemp += skName.get(j - 1) + ",";
            if ((j > 1 && j % 10 == 0) || (j == skName.size())) {
                codeTemp = codeTemp.substring(0, codeTemp.length() - 1);
                // logger.warn("getActStockDate0011 : " + JSON.toJSONString(codeTemp));
                // marketService.freeTencentGetStockInfoHistoryBatch(codeTemp);
                codeTemp = "";
            }
        }

        // 腾讯股票实时数据接口 沪深MACD数据查询 001330
        // marketService.getStockMacData("001330", "20220901", "20220910");
        // 腾讯股票实时数据接口 免费
        // marketService.freeTencentGetStockInfoHistoryBatch("");

        // 同花顺所有A股列表数据查询 抛物线SAR指标
        // marketService.freeGetSarDataByDate("000675", "2022-09-13");

        //// 指标
        // 同花顺所有A股列表数据查询 日线wr数据查询
        // marketService.freeGetWrDataByDate("600671", "2022-09-99");
        // 同花顺所有A股列表数据查询 日线cci数据查询
        // marketService.freeGetCciDataByDate("000810", "2022-10-18", "14");
        // marketService.freeGetCciDataByDate("600243", "2022-10-18", "88");
        // 同花顺所有A股列表数据查询 日线macd数据查询
        // marketService.freeGetMacdDataByCode("002043", "2022-09-02");
        // 同花顺所有A股列表数据查询 布林带boll数据查询
        // marketService.freeGetBollDataByDate("002043", "2022-09-01");
        // 同花顺所有A股列表数据查询 日线kdj数据查询
        // marketService.freeGetKdjDataByDate("600258", "2022-10-18");
        // 同花顺所有A股列表数据查询 乖离率BIAS指标
        // marketService.freeGetBiasDataByDate("002043", "2022-09-01");
        // 同花顺所有A股列表数据查询 5，10，20日ma均线数据查询
        // marketService.freeGetMaData("002043", "2022-09-01");
        // 同花顺所有A股列表数据查询 RSI指标
        // marketService.freeGetRSIData("600446", "2022-09-28");
        //// 指标end

        // 同花顺神奇九转指标
        // marketService.freeGetNineTurn("001331", "2022-09-15");
        // 同花顺所有A股列表数据查询 A股列表数据查询
        // marketService.freeGetAllStockData();
        // 同花顺所有A股列表数据查询 龙虎榜查询
        // marketService.freeGetDragonTigerData("2022-10-17");

        // 3332
        logger.warn("getActStockDate00000 : " + JSON.toJSONString(sk.size()));
        // 000593 001236 002169 001222 001330
        // marketService.getStockRSIData(code, beginDate, endDay);
        return RespBean.ok(code + " 数据统计完成！！！");
    }

}
