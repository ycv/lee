package org.pvuu.service;

import static org.pvuu.utils.tools.DateUtils.getDateByParam;
import static org.pvuu.utils.tools.DateUtils.getWorkDateListByBeginEndDay;
import static org.pvuu.utils.tools.StringUtils.format5;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.logging.log4j.util.Strings;
import org.pvuu.mapper.*;
import org.pvuu.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

@Service
public class StockService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    StockMapper stockMapper;
    @Autowired
    StockInfoMapper stockInfoMapper;
    @Autowired
    StockRsiMapper stockRsiMapper;
    @Autowired
    StockDragonTigerMapper stockDragonTigerMapper;
    @Autowired
    StockBiasMapper stockBiasMapper;
    @Autowired
    StockBollMapper stockBollMapper;
    @Autowired
    StockKdjMapper stockKdjMapper;
    @Autowired
    StockMaMapper stockMaMapper;
    @Autowired
    StockMacdMapper stockMacdMapper;
    @Autowired
    StockCciMapper stockCciMapper;
    @Autowired
    StockWrMapper stockWrMapper;
    @Autowired
    StockSarMapper stockSarMapper;

    public List<Stock> getAllStocks() {
        return stockMapper.getAllStocks();
    }

    public List<Stock> getAllStocksByStateAndCodePre() {
        // 主板【沪市主板股票代码：600、601、603、605；深市主板股票代码：000开头】 沪市B股以900开头
        // 中小板【股票代码：002开头】 深市B股以200开头，中小板以002开头
        // 创业板【股票代码：300开头】
        // 科创板【股票代码：688开头】
        // String[] codePre = {"60", "00", "90", "20"};
        String[] codePre = {"60", "00"};
        String state = "1";
        return stockMapper.getAllStocksByStateAndCodePre(state, codePre);
    }

    public List<Stock> getStockCodeList() {
        String[] markets = {"sz", "sh"};
        String state = "1";
        return stockMapper.getStockCodeByStateAndMarket(markets, state);
    }

    public RespPageBean getStockDragonTigerListByParam(StockDragonTiger stockDragonTiger) {
        Integer page = stockDragonTiger.getPage() != null ? stockDragonTiger.getPage() : 1;
        Integer size = stockDragonTiger.getSize() != null ? stockDragonTiger.getSize() : 10;
        page = (page - 1) * size;

        List<StockDragonTiger> data =
            stockDragonTigerMapper.getStockDragonTigerListByParam(page, size, stockDragonTiger);

        if (!CollectionUtils.isEmpty(data)) {
            for (StockDragonTiger item : data) {
                item.setSellAmountStr(format5(item.getSellAmount() / 10000) + "万");
                item.setBuyAmountStr(format5(item.getBuyAmount() / 10000) + "万");
                item.setChgStr(format5(item.getChg()) + "%");
                item.setTurnoverStr(format5(item.getTurnover()) + "%");
            }
        }

        Long total = stockDragonTigerMapper.getStockDragonTigerTotal(stockDragonTiger);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        logger.warn("getStockDragonTigerListByParam0002 : " + total);
        return bean;
    }

    public RespPageBean getStockRsiList(StockRsi stockRsi) {
        Integer page = stockRsi.getPage() != null ? stockRsi.getPage() : 1;
        Integer size = stockRsi.getSize() != null ? stockRsi.getSize() : 10;
        page = (page - 1) * size;
        logger.warn("getStockRsiList0006 : " + JSON.toJSONString(stockRsi) + "-" + page + "-" + size);
        List<StockRsi> data = stockRsiMapper.getStockRsiListByPage(page, size, stockRsi);
        Long total = stockRsiMapper.getStockRsiTotal(stockRsi);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public RespPageBean getStockByPage(Stock stock) {
        Integer page = stock.getPage() != null ? stock.getPage() : 1;
        Integer size = stock.getSize() != null ? stock.getSize() : 10;
        String name = stock.getName() != null ? stock.getName().trim() : Strings.EMPTY;
        page = (page - 1) * size;
        String[] markets = {"sz", "sh"};
        String state = "1";

        List<Stock> data = stockMapper.getStockListByPage(page, size, markets, state, name);
        Long total = stockMapper.getStockTotal(markets, state, name);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public List<StockInfo> setDifferenceNumByStockDetailsList(List<StockInfo> sc2, int differenceNum) {
        List<StockInfo> stockInfos = new ArrayList<>();
        try {
            if (!CollectionUtils.isEmpty(sc2) && sc2.size() > 1) {
                for (int i = 0; i < sc2.size(); i++) {
                    if (i % 2 != 0) {
                        // Double differenceMoneyTemp = sc2.get(i - 1).getTradeAmount() - sc2.get(i).getTradeAmount();
                        // sc2.get(i).setDifferenceMoney(String.format("%.2f", differenceMoneyTemp));
                        // sc2.get(i).setDifferenceNum(sc2.get(i - 1).getTrade_num() - sc2.get(i).getTrade_num());
                    }

                    if (differenceNum > 0) {
                        // if (i % 2 != 0 && sc2.get(i).getDifferenceNum() > differenceNum) {
                        // stockInfos.add(sc2.get(i - 1));
                        // stockInfos.add(sc2.get(i));
                        // }
                    } else {
                        stockInfos.add(sc2.get(i));
                    }
                }
            }
            return stockInfos;
        } catch (Exception e) {
            return stockInfos;
        }
    }

    public List<Stock> getStockByCode(String code) {
        return stockMapper.getStockByCode(code);
    }

    public void addStockDragonTigerData(List<StockDragonTiger> stockDragonTigerList) {
        try {
            if (!CollectionUtils.isEmpty(stockDragonTigerList)) {
                for (StockDragonTiger sdt : stockDragonTigerList) {
                    sdt.setCreateTime(df.format(System.currentTimeMillis()));
                    sdt.setUpdateTime(df.format(System.currentTimeMillis()));
                    if (Strings.isNotEmpty(sdt.getThsCode())) {
                        String[] thsCodeTemp = sdt.getThsCode().trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sdt.setCode(thsCodeTemp[0]);
                            sdt.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                    }
                }
                Thread.sleep(360);// 毫秒数
                stockDragonTigerMapper.insertBatch(stockDragonTigerList);
            }
        } catch (Exception e) {
            logger.warn("addStockDragonTigerData9999err : " + JSON.toJSONString("err"));
            e.printStackTrace();
        }
    }

    public void addStockKdjData(StockKdj stockKdj) {
        stockKdj.setCreateTime(df.format(System.currentTimeMillis()));
        stockKdj.setUpdateTime(df.format(System.currentTimeMillis()));
        stockKdjMapper.insert(stockKdj);
    }

    public void addStockSarData(StockSar stockSar) {
        stockSar.setCreateTime(df.format(System.currentTimeMillis()));
        stockSar.setUpdateTime(df.format(System.currentTimeMillis()));
        stockSarMapper.insert(stockSar);
    }

    public void addStockWrData(StockWr StockWr) {
        StockWr.setCreateTime(df.format(System.currentTimeMillis()));
        StockWr.setUpdateTime(df.format(System.currentTimeMillis()));
        stockWrMapper.insert(StockWr);
    }

    public void updateStockCciData(StockCci stockCci) {
        stockCci.setUpdateTime(df.format(System.currentTimeMillis()));
        stockCciMapper.updateStockCciData(stockCci);
    }

    public void addStockCciData(StockCci stockCci) {
        stockCci.setCreateTime(df.format(System.currentTimeMillis()));
        stockCci.setUpdateTime(df.format(System.currentTimeMillis()));
        stockCciMapper.insert(stockCci);
    }

    public void addStockBollData(StockBoll stockBoll) {
        stockBoll.setCreateTime(df.format(System.currentTimeMillis()));
        stockBoll.setUpdateTime(df.format(System.currentTimeMillis()));
        stockBollMapper.insert(stockBoll);
    }

    public void addStockMaData(StockMa stockMa) {
        stockMa.setCreateTime(df.format(System.currentTimeMillis()));
        stockMa.setUpdateTime(df.format(System.currentTimeMillis()));
        stockMaMapper.insert(stockMa);
    }

    public void addStockRSIData(StockRsi stockRsi) {
        stockRsi.setCreateTime(df.format(System.currentTimeMillis()));
        stockRsi.setUpdateTime(df.format(System.currentTimeMillis()));
        stockRsiMapper.insert(stockRsi);
    }

    public void addStockMacdData(StockMacd stockMacd) {
        stockMacd.setCreateTime(df.format(System.currentTimeMillis()));
        stockMacd.setUpdateTime(df.format(System.currentTimeMillis()));
        stockMacdMapper.insert(stockMacd);
    }

    public void addStockBiasData(StockBias stockBias) {
        stockBias.setCreateTime(df.format(System.currentTimeMillis()));
        stockBias.setUpdateTime(df.format(System.currentTimeMillis()));
        stockBiasMapper.insert(stockBias);
    }

    public void addStockData(List<Stock> stockList) {

        List<Stock> productAddTemp = new ArrayList<>();
        List<Stock> productUpdateTemp = new ArrayList<>();
        try {
            for (Stock sk : stockList) {
                List<Stock> sList = this.getStockByCode(sk.getApi_code());
                if (CollectionUtils.isEmpty(sList)) {
                    sk.setCode(sk.getApi_code());
                    sk.setMarket(Strings.isNotEmpty(sk.getJys()) ? sk.getJys().toLowerCase() : "");
                    sk.setState("1");
                    sk.setStockType("A");
                    sk.setCreateTime(df.format(System.currentTimeMillis()));
                    sk.setUpdateTime(df.format(System.currentTimeMillis()));
                    productAddTemp.add(sk);
                    logger.warn("freeGetAllStockData5539 : " + JSON.toJSONString(sk));
                    // stockMapper.insert(sk);
                    // Thread.sleep(360);// 毫秒数
                    //
                    // logger.warn("updateupdateupdate11 : " + JSON.toJSONString(sk));
                    // } else {
                    // // update
                } else {
                    sk.setCode(sk.getApi_code());
                    sk.setState("1");
                    sk.setStockType("A");
                    sk.setMarket(Strings.isNotEmpty(sk.getJys()) ? sk.getJys().toLowerCase() : "");
                    sk.setUpdateTime(df.format(System.currentTimeMillis()));
                    productUpdateTemp.add(sk);
                    logger.warn("freeGetAllStockData1529 : " + JSON.toJSONString(sk));
                }
                Thread.sleep(15);// 毫秒数
            }
            Thread.sleep(360);// 毫秒数
            logger.warn("freeGetAllStockData1039 : " + JSON.toJSONString(productAddTemp.size()));
            logger.warn("freeGetAllStockData1139 : " + JSON.toJSONString(productUpdateTemp.size()));

            if (!CollectionUtils.isEmpty(productAddTemp)) {
                stockMapper.insertBatch(productAddTemp);
            }
            if (!CollectionUtils.isEmpty(productUpdateTemp)) {
                stockMapper.updateBatchByCode(productUpdateTemp);
            }

        } catch (Exception e) {
            logger.warn("addStockData9999 : " + JSON.toJSONString("err"));
            // InterruptedException
            e.printStackTrace();
        }

    }

    public void deleteStockByIds(List<Stock> stockList) {
        Integer[] ids = new Integer[stockList.size()];
        int j = 0;
        for (Stock sk : stockList) {
            if (Objects.equals("1", sk.getState())) {

            } else {
                ids[j] = sk.getId();
                j++;
            }
        }

        List<Integer> list1 = new ArrayList<>(Arrays.asList(ids));
        list1.removeAll(Collections.singleton(null));

        int[] res = list1.stream().mapToInt(Integer::intValue).toArray();
        int delNum = stockMapper.deleteStockByIds(res);
    }

    public void updateStockRsiData(StockRsi stockRsi) {
        try {
            stockRsi.setUpdateTime(df.format(System.currentTimeMillis()));
            Thread.sleep(10);
            logger.warn("updateStockRsiData0009 : " + JSON.toJSONString(stockRsi));
            stockRsiMapper.updateStockRsiDataByCodeAndDate(stockRsi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStockInfoData(StockInfo stockInfo) {
        try {
            stockInfo.setUpdateTime(df.format(System.currentTimeMillis()));
            Thread.sleep(70);
            logger.warn("updateStockInfoData0002 : " + JSON.toJSONString(stockInfo));
            stockInfoMapper.updateStockInfoByCodeAndDate(stockInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStockInfoData(List<StockInfo> stockInfoList) {

        List<StockInfo> productAddTemp = new ArrayList<>();
        try {
            for (StockInfo sk : stockInfoList) {
                sk.setCreateTime(df.format(System.currentTimeMillis()));
                sk.setUpdateTime(df.format(System.currentTimeMillis()));
                productAddTemp.add(sk);
            }
            Thread.sleep(400);// 毫秒数
            stockInfoMapper.insertStockInfoMapperBatch(productAddTemp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getStockChartDataByParam(StockRsi stockRsiParam) {
        if (Strings.isBlank(stockRsiParam.getBeginDate()) || Strings.isBlank(stockRsiParam.getEndDate())) {
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String endDay = df2.format(System.currentTimeMillis());
            stockRsiParam.setEndDate(endDay);
            // stockRsiParam.setBeginDate(this.getDateByParam(endDay, "month", -2));
            stockRsiParam.setBeginDate(getDateByParam(endDay, "day", -22));
        }
        logger.warn("getStockChartDataByParam3366 : " + JSON.toJSONString(stockRsiParam));
        List<String> workDateList =
            getWorkDateListByBeginEndDay(stockRsiParam.getBeginDate(), stockRsiParam.getEndDate());
        logger.warn("getStockChartDataByParam3367 : " + JSON.toJSONString(workDateList));
        Map stockChartMap = new HashMap();
        // RSI指标
        List<StockRsi> stockRsiList = stockRsiMapper.getStockChartDataByParam(stockRsiParam, workDateList);

        // 日期
        List<String> dateList = new ArrayList<>();
        // RSI指标
        List<Double> rsi6List = new ArrayList<>();
        List<Double> rsi12List = new ArrayList<>();
        List<Double> rsi24List = new ArrayList<>();
        // 成交金额
        List<Double> tradeAmount = new ArrayList<>();
        String stockName = "";
        if (!CollectionUtils.isEmpty(stockRsiList)) {
            for (StockRsi item : stockRsiList) {
                rsi6List.add(item.getRsi6());
                rsi12List.add(item.getRsi12());
                rsi24List.add(item.getRsi24());
                dateList.add((item.getDate() != null && item.getDate().length() > 5) ? item.getDate().substring(5)
                    : Strings.EMPTY);
                StockInfo sd = stockInfoMapper.getStockInfoByCodeAndDate(item.getCode(), item.getDate());
                // 成交金额(万元)
                if (!Objects.isNull(sd)) {
                    tradeAmount.add(Double.parseDouble(String.format("%.2f", sd.getTradeAmount() / 10000)));
                } else {
                    tradeAmount.add(0.0);
                }
            }
            List<Stock> stockNameInfo = stockMapper.getStockByCode(stockRsiList.get(0).getCode());
            // 股票名称
            if (!CollectionUtils.isEmpty(stockNameInfo)) {
                stockName = stockNameInfo.get(0).getName();
            }
        }
        stockChartMap.put("rsi6", rsi6List);
        stockChartMap.put("rsi12", rsi12List);
        stockChartMap.put("rsi24", rsi24List);
        stockChartMap.put("dateList", dateList);
        stockChartMap.put("tradeAmount", tradeAmount);
        stockChartMap.put("stockName", stockName);
        stockChartMap.put("beginYear", stockRsiParam.getBeginDate().substring(0, 4));

        // 日线wr数据
        List<StockWr> stockWrList = stockWrMapper.getStockWrChartDataByParam(stockRsiParam.getCode(), workDateList);
        List<Double> wr1List = new ArrayList<>();
        List<Double> wr2List = new ArrayList<>();
        if (!CollectionUtils.isEmpty(stockWrList)) {
            for (StockWr item : stockWrList) {
                wr1List.add(item.getWr1());
                wr2List.add(item.getWr2());
            }
        }
        stockChartMap.put("wr1", wr1List);
        stockChartMap.put("wr2", wr2List);

        // 日线cci
        List<StockCci> stockCciList = stockCciMapper.getStockCciChartDataByParam(stockRsiParam.getCode(), workDateList);
        List<Double> CciList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(stockCciList)) {
            for (StockCci item : stockCciList) {
                CciList.add(item.getCci());
            }
        }
        stockChartMap.put("cci", CciList);

        // 日线kdj数据
        List<StockKdj> stockKdjList = stockKdjMapper.getStockKdjChartDataByParam(stockRsiParam.getCode(), workDateList);
        List<Double> dList = new ArrayList<>();
        List<Double> jList = new ArrayList<>();
        List<Double> kList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(stockKdjList)) {
            for (StockKdj item : stockKdjList) {
                dList.add(item.getD());
                jList.add(item.getJ());
                kList.add(item.getK());
            }
        }
        stockChartMap.put("d", dList);
        stockChartMap.put("j", jList);
        stockChartMap.put("k", kList);

        //
        return stockChartMap;
    }

    public RespPageBean getStockInfoListByPage(StockInfo stockInfo) {
        Integer page = stockInfo.getPage() != null ? stockInfo.getPage() : 1;
        Integer size = stockInfo.getSize() != null ? stockInfo.getSize() : 10;
        page = (page - 1) * size;
        List<StockInfo> data = stockInfoMapper.getStockInfoListByPage(page, size, stockInfo);

        if (!CollectionUtils.isEmpty(data)) {
            for (StockInfo item : data) {
                item.setTradeNumStr(this.getDecimalFormat(item.getTradeNum(), 0));
                item.setNeipanStr(this.getDecimalFormat(item.getNeipan(), 0));
                item.setWaipanStr(this.getDecimalFormat(item.getWaipan(), 0));
                item.setTradeAmountStr(this.getDecimalFormat(item.getTradeAmount(), 0));
            }
        }
        Long total = stockInfoMapper.getStockInfoListTotal(stockInfo);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public void addStockByCodeAndMarket(String name, String code, String market) {

        try {
            Thread.sleep(20);// 毫秒数 111
            List<Stock> sList = stockMapper.getStockByCode(code);
            if (CollectionUtils.isEmpty(sList)) {
                Stock sk = new Stock();
                sk.setName(name);
                sk.setCode(code);
                sk.setMarket(market);
                sk.setCreateTime(df.format(System.currentTimeMillis()));
                sk.setUpdateTime(df.format(System.currentTimeMillis()));
                stockMapper.insert(sk);
                logger.warn("addStockByCodeAndMarket0001 : " + JSON.toJSONString("1"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStockRsiByCode(String code, String beginDate, String endDate) {
        int total = stockRsiMapper.deleteStockRsiByCode(code, beginDate, endDate);
        logger.warn("deleteStockRsiByCode0022 : " + JSON.toJSONString(total));
    }

    public void addStockRsiData(List<StockRsi> stockDetailsList) {
        List<StockRsi> productAddTemp = new ArrayList<>();
        try {
            for (StockRsi sk : stockDetailsList) {
                sk.setCreateTime(df.format(System.currentTimeMillis()));
                sk.setUpdateTime(df.format(System.currentTimeMillis()));
                productAddTemp.add(sk);
            }
            Thread.sleep(330);// 毫秒数
            logger.warn("addStockRsiData0002 : " + JSON.toJSONString(productAddTemp.size()));
            stockRsiMapper.insertStockRsiBatch(productAddTemp);

        } catch (Exception e) {
            // Exception InterruptedException
            e.printStackTrace();
        }
    }

    /**
     * 金额格式化 数字转换金额
     * 
     * @param dTemp
     * @return
     */
    public String getDecimalFormat(Double dTemp, int type) {
        String outStr = "";
        if (dTemp != null && !Objects.equals("", dTemp)) {
            DecimalFormat fmt = new DecimalFormat("##,###,###,###,###");
            if (Objects.equals(1, type)) {
                fmt = new DecimalFormat("##,###,###,###,###.00");
            }
            try {
                outStr = fmt.format(dTemp);
            } catch (Exception e) {
                outStr = String.valueOf(dTemp);
                e.printStackTrace();
            }
        }
        return outStr;
    }

}
