package org.pvuu.service;

import java.io.IOException;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.pvuu.model.*;
import org.pvuu.utils.HttpUtil;
import org.pvuu.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class MarketService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // AppCode
    public String appcodeTemp = "81245430bbca42eaafa581d8ffa689e6";
    public String tokenTemp = "c20c37f5e278374546c0901ca6160435232c37544fa65f03";

    @Autowired
    StockService stockService;

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：日线kdj数据查询
     *
     */
    public void freeGetKdjDataByDate(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/kdj";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        queryParam.put("code", code);
        queryParam.put("calculationCycle", "100");
        queryParam.put("cycle", "9");
        queryParam.put("cycle1", "3");
        queryParam.put("cycle2", "3");
        queryParam.put("vipCycleFlag", "0");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetKdjDataByDate1112 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockKdj sk = new StockKdj();
                        sk.setDate(date);
                        if (Strings.isNotEmpty(data.getString("d"))) {
                            sk.setD(Double
                                .parseDouble(String.format("%.4f", JSONObject.parseArray(data.getString("d")).get(0))));
                        }
                        if (Strings.isNotEmpty(data.getString("j"))) {
                            sk.setJ(Double
                                .parseDouble(String.format("%.4f", JSONObject.parseArray(data.getString("j")).get(0))));
                        }
                        if (Strings.isNotEmpty(data.getString("k"))) {
                            sk.setK(Double
                                .parseDouble(String.format("%.4f", JSONObject.parseArray(data.getString("k")).get(0))));
                        }

                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sk.setCode(thsCodeTemp[0]);
                            sk.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        Thread.sleep(11);
                        stockService.addStockKdjData(sk);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetKdjDataByDate9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：抛物线SAR指标
     *
     */
    public void freeGetSarDataByDate(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/sar";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(9);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        queryParam.put("code", code);
        queryParam.put("cycle", "4");
        queryParam.put("limit", "10");
        queryParam.put("step", "2");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetSarDataByDate0001 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                logger.warn("freeGetSarDataByDate0002 : " + JSON.toJSONString(data));
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {

                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetSarDataByDate9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }

    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：日线wr数据查询
     *
     */
    public void freeGetWrDataByDate(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/wr";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(5);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        queryParam.put("code", code);
        queryParam.put("cycle1", "10");
        queryParam.put("cycle2", "6");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetWrDataByDate0001 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockWr sw = new StockWr();
                        sw.setDate(date);
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sw.setCode(thsCodeTemp[0]);
                            sw.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        if (Strings.isNotEmpty(data.getString("wr1"))) {
                            sw.setWr1(Double.parseDouble(String.format("%.4f", data.get("wr1"))));
                        }
                        if (Strings.isNotEmpty(data.getString("wr2"))) {
                            sw.setWr2(Double.parseDouble(String.format("%.4f", data.get("wr2"))));
                        }
                        Thread.sleep(33);
                        stockService.addStockWrData(sw);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetWrDataByDate9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：日线cci数据查询
     *
     */
    public void freeGetCciDataByDate(String code, String date, String cycle) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/cci";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        queryParam.put("code", code);
        queryParam.put("cycle", "14");
        queryParam.put("cycle", cycle);
        queryParam.put("vipCycleFlag", "0");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetCciDataByDate0001 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                logger.warn("freeGetCciDataByDate0002 : " + JSON.toJSONString(data));
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockCci sc = new StockCci();
                        sc.setDate(date);
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sc.setCode(thsCodeTemp[0]);
                            sc.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        if (Strings.isNotEmpty(data.getString("cci"))) {
                            if (Objects.equals("88", cycle)) {
                                sc.setCci88(Double.parseDouble(
                                    String.format("%.4f", JSONObject.parseArray(data.getString("cci")).get(0))));
                            } else {
                                sc.setCci(Double.parseDouble(
                                    String.format("%.4f", JSONObject.parseArray(data.getString("cci")).get(0))));
                            }
                        }
                        Thread.sleep(33);
                        if (Objects.equals("88", cycle)) {
                            stockService.updateStockCciData(sc);
                        } else {
                            stockService.addStockCciData(sc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetCciDataByDate9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：布林带boll数据查询
     *
     */
    public void freeGetBollDataByDate(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/boll";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        queryParam.put("code", code);
        queryParam.put("bandwidth", "2");
        queryParam.put("cycle", "26");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetBollDataByDate0006 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockBoll sb = new StockBoll();
                        if (Strings.isNotEmpty(data.getString("LOWER"))) {
                            sb.setLOWER(Double.parseDouble(String.format("%.4f", data.get("LOWER"))));
                        }
                        if (Strings.isNotEmpty(data.getString("MID"))) {
                            sb.setMID(Double.parseDouble(String.format("%.4f", data.get("MID"))));
                        }
                        if (Strings.isNotEmpty(data.getString("UPPER"))) {
                            sb.setUPPER(Double.parseDouble(String.format("%.4f", data.get("UPPER"))));
                        }
                        sb.setDate(date);
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sb.setCode(thsCodeTemp[0]);
                            sb.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }

                        Thread.sleep(11);
                        stockService.addStockBollData(sb);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetBollDataByDate9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：乖离率BIAS指标
     *
     */
    public void freeGetBiasDataByDate(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/bias";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        queryParam.put("code", code);
        queryParam.put("cycle1", "6");
        queryParam.put("cycle2", "12");
        queryParam.put("cycle3", "24");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetBiasDataByDate2113 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockBias sb = new StockBias();
                        if (Strings.isNotEmpty(data.getString("bias1"))) {
                            sb.setBias1(Double.parseDouble(String.format("%.4f", data.get("bias1"))));
                        }
                        if (Strings.isNotEmpty(data.getString("bias2"))) {
                            sb.setBias2(Double.parseDouble(String.format("%.4f", data.get("bias2"))));
                        }
                        if (Strings.isNotEmpty(data.getString("bias3"))) {
                            sb.setBias3(Double.parseDouble(String.format("%.4f", data.get("bias3"))));
                        }
                        sb.setDate(date);
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sb.setCode(thsCodeTemp[0]);
                            sb.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        Thread.sleep(11);
                        stockService.addStockBiasData(sb);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetBiasDataByDate9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }

    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：日线macd数据查询
     *
     */
    public void freeGetMacdDataByCode(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/macd";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(2);
        queryParam.put("token", tokenTemp);
        queryParam.put("code", code);
        queryParam.put("date", date);
        queryParam.put("cycle", "9");
        queryParam.put("longCycle", "26");
        queryParam.put("shortCycle", "12");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetMacdDataByCode1226 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockMacd sm = new StockMacd();
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sm.setCode(thsCodeTemp[0]);
                            sm.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        sm.setDate(date);
                        if (Strings.isNotEmpty(data.getString("dea"))) {
                            sm.setDea(Double.parseDouble(
                                String.format("%.4f", JSONObject.parseArray(data.getString("dea")).get(0))));
                        }
                        if (Strings.isNotEmpty(data.getString("dif"))) {
                            sm.setDif(Double.parseDouble(
                                String.format("%.4f", JSONObject.parseArray(data.getString("dif")).get(0))));
                        }
                        if (Strings.isNotEmpty(data.getString("macd"))) {
                            sm.setMacd(Double.parseDouble(
                                String.format("%.4f", JSONObject.parseArray(data.getString("macd")).get(0))));
                        }
                        stockService.addStockMacdData(sm);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetMacdDataByCode9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：RSI指标
     *
     */
    public void freeGetRSIData(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/rsi";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("token", tokenTemp);
        queryParam.put("code", code);
        queryParam.put("date", date);
        queryParam.put("cycle1", "6");
        queryParam.put("cycle2", "12");
        queryParam.put("cycle3", "24");
        queryParam.put("vipCycleFlag", "0");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetRSIData0122 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockRsi sr = new StockRsi();
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sr.setCode(thsCodeTemp[0]);
                            sr.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        sr.setDate(date);
                        if (Strings.isNotEmpty(data.getString("rsi1"))) {
                            sr.setRsi6(Double.parseDouble(
                                String.format("%.3f", JSONObject.parseArray(data.get("rsi1").toString()).get(0))));
                        }
                        if (Strings.isNotEmpty(data.getString("rsi2"))) {
                            sr.setRsi12(Double.parseDouble(
                                String.format("%.3f", JSONObject.parseArray(data.get("rsi2").toString()).get(0))));
                        }
                        if (Strings.isNotEmpty(data.getString("rsi3"))) {
                            sr.setRsi24(Double.parseDouble(
                                String.format("%.3f", JSONObject.parseArray(data.get("rsi3").toString()).get(0))));
                        }
                        Thread.sleep(11);
                        stockService.addStockRSIData(sr);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetRSIData9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：5，10，20日ma均线数据查询
     *
     */
    public void freeGetMaData(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/ma";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(4);
        queryParam.put("token", tokenTemp);
        queryParam.put("code", code);
        queryParam.put("date", date);
        // 周期,逗号分隔符必须为英文，默认值ma=5,10,20
        queryParam.put("ma", "5,10,20");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject dataObject = JSONObject.parseObject(s);
            logger.warn("freeGetMaData1212 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    if (Strings.isNotEmpty(data.getString("api_code"))) {
                        StockMa sm = new StockMa();
                        String[] thsCodeTemp = data.getString("api_code").trim().split("\\.");
                        if (Objects.equals(2, thsCodeTemp.length)) {
                            sm.setCode(thsCodeTemp[0]);
                            sm.setMarket(Strings.isNotEmpty(thsCodeTemp[1]) ? thsCodeTemp[1].toLowerCase() : "");
                        }
                        sm.setDate(date);
                        if (Strings.isNotEmpty(data.getString("ma5"))) {
                            sm.setMa5(Double.parseDouble(data.getString("ma5")));
                        }
                        if (Strings.isNotEmpty(data.getString("ma10"))) {
                            sm.setMa10(Double.parseDouble(data.getString("ma10")));
                        }
                        if (Strings.isNotEmpty(data.getString("ma20"))) {
                            sm.setMa20(Double.parseDouble(data.getString("ma20")));
                        }
                        Thread.sleep(11);// 毫秒数
                        stockService.addStockMaData(sm);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetMaData9999err : " + JSON.toJSONString("err " + code + "=" + date));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：查询当日是不是交易日
     *
     */
    public List<String> freeGetDateData(List<String> workDateList) {
        List<String> workDateListTemp = new ArrayList<>();
        try {
            String host = "https://stockapi.com.cn";
            String path = "/v1/base/tradeDate";
            String method = "GET";
            Map<String, String> headers = new HashMap<String, String>();
            Map<String, String> queryParam = new HashMap<String, String>(2);
            queryParam.put("token", tokenTemp);
            for (String tmp : workDateList) {
                queryParam.put("tradeDate", tmp);
                Thread.sleep(11);
                HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
                JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                    && Objects.equals(dataObject.get("msg").toString(), "success")) {
                    JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                    if (!data.isEmpty()) {
                        String dateTemp = data.getString("isTradeDate") != null ? data.getString("isTradeDate") : "";
                        if (Objects.equals(dateTemp, "1")) {
                            workDateListTemp.add(tmp);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetDateData9999err :   err" + JSON.toJSONString(workDateList));
            e.printStackTrace();
        }
        return workDateListTemp;
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：神奇九转指标
     *
     */
    public void freeGetNineTurn(String code, String date) {
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/nineTurn";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(2);
        queryParam.put("code", code);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject dataObject = JSONObject.parseObject(s);
            logger.warn("freeGetNineTurn00016 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    StockInfo si = new StockInfo();
                    si.setCode(code);
                    si.setDate(date);
                    si.setBuy4_m(0.0);
                    si.setBuy4_n(0.0);
                    if (Strings.isNotEmpty(data.getString("lowNine"))) {
                        si.setBuy4_m(Double.parseDouble(data.getString("lowNine")));
                    }
                    if (Strings.isNotEmpty(data.getString("highNine"))) {
                        si.setBuy4_n(Double.parseDouble(data.getString("highNine")));
                    }
                    stockService.updateStockInfoData(si);
                }
            } else {
                logger.warn("freeGetNineTurn00066 : " + JSON.toJSONString(dataObject));
            }
        } catch (Exception e) {
            logger.warn("freeGetNineTurn9999err : " + JSON.toJSONString("err"));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：龙虎榜查询
     *
     */
    public void freeGetDragonTigerData(String date) {
        List<StockDragonTiger> products = new ArrayList<>();
        String host = "https://stockapi.com.cn";
        String path = "/v1/base/dragonTiger";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(2);
        queryParam.put("token", tokenTemp);
        queryParam.put("date", date);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            JSONObject dataObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            logger.warn("freeGetDragonTigerData0026 : " + JSON.toJSONString(dataObject));
            if (Strings.isNotEmpty(dataObject.getString("msg")) && Strings.isNotEmpty(dataObject.getString("data"))
                && Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONArray data = JSONObject.parseArray(dataObject.get("data").toString());
                logger.warn("freeGetDragonTigerData0126 : " + JSON.toJSONString(data.size()));
                if (!data.isEmpty()) {
                    // 转换成对象
                    products = JSONArray.parseArray(data.toJSONString(), StockDragonTiger.class);
                }
                if (CollectionUtils.isEmpty(products)) {
                    logger.warn("freeGetDragonTigerData0009 : " + JSON.toJSONString("OVER"));
                } else {
                    stockService.addStockDragonTigerData(products);
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetDragonTigerData9999err : " + JSON.toJSONString("err"));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：所有A股列表数据查询
     *
     */
    public void freeGetAllStockData() {
        List<Stock> products = new ArrayList<>();
        String host = "https://stockapi.com.cn";
        String path = "/v1/base/all";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("token", tokenTemp);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject dataObject = JSONObject.parseObject(s);
            if (Objects.equals(dataObject.get("msg").toString(), "success")) {
                JSONArray data = JSONObject.parseArray(dataObject.get("data").toString());
                if (!data.isEmpty()) {
                    products = JSONArray.parseArray(data.toJSONString(), Stock.class);// 转换成对象
                }
                // 5020
                logger.warn("freeGetAllStockData2019 : " + JSON.toJSONString(data.size()));
                logger.warn("freeGetAllStockData2029 : " + JSON.toJSONString(products.size()));
                if (CollectionUtils.isEmpty(products)) {
                    logger.warn("freeGetAllStockData0009 : " + JSON.toJSONString("OVER"));
                } else {
                    stockService.addStockData(products);
                }
            }
        } catch (Exception e) {
            logger.warn("freeGetAllStockData0011 : " + JSON.toJSONString("err"));
            e.printStackTrace();
        }
    }

    /**
     * 同花顺，通达信，东方财富基金api接口大全macd，kdj，cci，威廉指标，神奇九转大全
     *
     *
     * 接口说明：RSI指标
     *
     */
    public void freeGetRsiData(String code, String date) {
        List<StockRsi> products = new ArrayList<>();
        String host = "https://stockapi.com.cn";
        String path = "/v1/quota/rsi";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> queryParam = new HashMap<String, String>(6);
        queryParam.put("code", code);
        queryParam.put("date", date);
        queryParam.put("cycle1", "6");
        queryParam.put("cycle2", "12");
        queryParam.put("cycle3", "24");
        queryParam.put("vipCycleFlag", "0");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, queryParam);
            // 获取返回的数据 转换成字符串
            String s = EntityUtils.toString(response.getEntity());
            // 转换成JSON格式
            JSONObject dataObject = JSONObject.parseObject(s);
            JSONObject data = JSONObject.parseObject(dataObject.get("data").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            if (!jo.isEmpty()) {
                StockRsi sr = new StockRsi();
                sr.setCode(code);
                sr.setDate(JSONObject.parseArray(jo.get("date").toString()).get(0).toString());
                sr.setRsi6(
                    Double.parseDouble(String.format("%.3f", JSONObject.parseArray(jo.get("rsi1").toString()).get(0))));
                sr.setRsi12(
                    Double.parseDouble(String.format("%.3f", JSONObject.parseArray(jo.get("rsi2").toString()).get(0))));
                sr.setRsi24(
                    Double.parseDouble(String.format("%.3f", JSONObject.parseArray(jo.get("rsi3").toString()).get(0))));
                products.add(sr);
            }
            if (CollectionUtils.isEmpty(products)) {
                logger.warn("freeGetRsiData0006 : " + JSON.toJSONString("OVER"));
            } else {
                stockService.deleteStockRsiByCode(code, date, date);
                stockService.addStockRsiData(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 万维易源-股票数据分析查询接口-历史股票数据接口-沪深港股-股票api查询
     *
     * 沪深MACD数据查询
     */
    public void getStockMacData(String code, String beginDay, String endDay) {
        List<StockRsi> products = new ArrayList<>();
        String host = "https://ali-stock.showapi.com";
        String path = "/macd";
        String method = "GET";
        String appcode = appcodeTemp;
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("code", code);
        querys.put("end", endDay);
        // 数据复权类型，qfq表示前复权、hfq表示后复权、bfq表示不复权，默认不复权
        querys.put("fqtype", "bfq");
        querys.put("start", beginDay);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式
            logger.warn("getStockMacData0113 : " + JSON.toJSONString(datas));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 万维易源-股票数据分析查询接口-历史股票数据接口-沪深港股-股票api查询
     * 
     * 沪深RSI数据查询
     */
    public void getStockRSIData(String code, String beginDay, String endDay) {
        List<StockRsi> products = new ArrayList<>();
        String host = "https://ali-stock.showapi.com";
        String path = "/rsi";
        String method = "GET";
        String appcode = appcodeTemp;
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("code", code);
        querys.put("end", endDay);
        // 数据复权类型，qfq表示前复权、hfq表示后复权、bfq表示不复权，默认不复权
        querys.put("fqtype", "bfq");
        querys.put("start", beginDay);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式
            logger.warn("getStockHistoryAA019 : " + JSON.toJSONString(datas));
            JSONObject data = JSONObject.parseObject(datas.get("showapi_res_body").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            JSONArray records = JSONObject.parseArray(jo.get("list").toString());
            if (!records.isEmpty()) {// 如果返回的数据不为空
                products = JSONArray.parseArray(records.toJSONString(), StockRsi.class);// 转换成对象
            }
            logger.warn("getStockList126 : " + JSON.toJSONString(products));
            if (CollectionUtils.isEmpty(products)) {
                logger.warn("getStockList2912 : " + JSON.toJSONString("OVER"));
            } else {
                stockService.deleteStockRsiByCode(code, beginDay, endDay);
                stockService.addStockRsiData(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 万维易源-股票数据分析查询接口-历史股票数据接口-沪深港股-股票api查询
     *
     * 沪深港股历史行情_日线_
     */
    public void getStockHistory(String beginDay, String endDay, String code) {

        String host = "https://ali-stock.showapi.com";
        String path = "/sz-sh-stock-history";
        String method = "GET";
        String appcode = appcodeTemp;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        // "2015-09-01"
        querys.put("begin", beginDay);
        // "2015-09-02"
        querys.put("end", endDay);
        querys.put("code", code);
        // qfq代表“前复权”，hfq代表“后复权”，bfq代表“不复权”，默认值为bfq
        // querys.put("type", "hfq");

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式

            logger.warn("getStockHistoryAA002 : " + JSON.toJSONString(datas));

            JSONObject data = JSONObject.parseObject(datas.get("showapi_res_body").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            JSONArray records = JSONObject.parseArray(jo.get("list").toString());

            if (!records.isEmpty()) {// 如果返回的数据不为空
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 万维易源-股票数据分析查询接口-历史股票数据接口-沪深港股-股票api查询
     *
     * 股票列表查询
     */
    public void getStockList001(String pageNum, String type) {
        List<Stock> products = new ArrayList<>();

        String host = "https://ali-stock.showapi.com";
        String path = "/stocklist";
        String method = "GET";
        String appcode = appcodeTemp;

        Map<String, String> headers = new HashMap<String, String>();

        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();

        // 市场简写。支持 sh、sz、hk
        querys.put("market", type);
        querys.put("page", pageNum);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式

            logger.warn("getStockList0010006 : " + JSON.toJSONString(datas));

            JSONObject data = JSONObject.parseObject(datas.get("showapi_res_body").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            JSONArray records = JSONObject.parseArray(jo.get("contentlist").toString());

            if (!records.isEmpty()) {// 如果返回的数据不为空
                products = JSONArray.parseArray(records.toJSONString(), Stock.class);// 转换成对象
            }

            logger.warn("getStockList1128 : " + JSON.toJSONString(products));

            if (CollectionUtils.isEmpty(products)) {
                logger.warn("getStockList999 : " + JSON.toJSONString("OVER"));
            } else {
                stockService.addStockData(products);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 万维易源-股票数据分析查询接口-历史股票数据接口-沪深港股-股票api查询
     * 
     * 大盘股指批量历史
     */
    public void getStockHistoryData(String stocksParam) {
        String host = "https://ali-stock.showapi.com";
        String path = "/stockIndex";
        String method = "GET";
        String appcode = appcodeTemp;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        // 每次最多10个编码。股指编码以逗号分隔 sh000001,sz399001,sz399005
        querys.put("stocks", stocksParam);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式

            logger.warn("getStockListAA092 : " + JSON.toJSONString(datas));

            JSONObject data = JSONObject.parseObject(datas.get("showapi_res_body").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            JSONArray records = JSONObject.parseArray(jo.get("indexList").toString());

            logger.warn("getStockList1618 : " + JSON.toJSONString(records));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 万维易源-股票数据分析查询接口-历史股票数据接口-沪深港股-股票api查询
     *
     * 批量查询历史参考数据
     */
    public void getStockInfoHistoryBatch(String stocksParam) {
        List<StockInfo> StockInfoProducts = new ArrayList<>();
        String host = "https://ali-stock.showapi.com";
        String path = "/batch-real-stockinfo";
        String method = "GET";
        String appcode = appcodeTemp;
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        // 是否需要返回4大股票指数（上证指数、深证成指、恒生指数、创业板指）。1为需要，0为不需要。
        querys.put("needIndex", "0");
        // 股票编码 。多个股票代码间以英文逗号分隔，最多输入20个代码。如果超出，系统只取前20个代码。
        // "sh601006,sh601007,sh601008,sh601009,sz000018,hk00941"
        querys.put("stocks", stocksParam);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式

            logger.warn("getStockListAA092 : " + JSON.toJSONString(datas));

            JSONObject data = JSONObject.parseObject(datas.get("showapi_res_body").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            JSONArray records = JSONObject.parseArray(jo.get("list").toString());

            if (!records.isEmpty()) {// 如果返回的数据不为空
                StockInfoProducts = JSONArray.parseArray(records.toJSONString(), StockInfo.class);// 转换成对象
            }
            logger.warn("getStockList161222811661356 : " + JSON.toJSONString(StockInfoProducts));
            if (CollectionUtils.isEmpty(StockInfoProducts)) {
                logger.warn("getStockList12444 : " + JSON.toJSONString("OVER"));
            } else {
                stockService.addStockInfoData(StockInfoProducts);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 腾讯股票实时数据接口 免费
     * 
     * @param stocksParam
     */
    public void freeTencentGetStockInfoHistoryBatch(String stocksParam) {
        List<StockInfo> StockInfoProducts = new ArrayList<>();
        try {
            String resultStr = HttpUtil.sendHttpGet("http://qt.gtimg.cn/q=" + stocksParam, null);
            logger.warn("freeGetStockInfoHistoryBatch00822 : " + resultStr);
            List<String> resultStrList = Arrays.asList(resultStr.split(";"));
            if (!CollectionUtils.isEmpty(resultStrList)) {
                for (String s : resultStrList) {
                    if (Strings.isNotEmpty(s) && Strings.isNotBlank(s)) {
                        StockInfo stockInfoObj = new StockInfo();
                        String resultStrTemp = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));

                        List<String> rl = Arrays.asList(resultStrTemp.split("~"));
                        if (rl.size() < 80) {
                            continue;
                        }
                        stockInfoObj.setState("1");
                        // 市场
                        stockInfoObj.setMarket(
                            rl.get(0) != null ? (Objects.equals("1", rl.get(0).trim()) ? "sh" : "sz") : Strings.EMPTY);
                        // 名字 1
                        stockInfoObj.setName(rl.get(1) != null ? rl.get(1).trim() : Strings.EMPTY);
                        // 代码 2
                        stockInfoObj.setCode(rl.get(2) != null ? rl.get(2).trim() : Strings.EMPTY);
                        // 当前价格 3
                        stockInfoObj.setNowPrice(rl.get(3) != null ? Double.parseDouble(rl.get(3)) : 0.0);
                        // 昨收 4
                        stockInfoObj.setClosePrice(rl.get(4) != null ? Double.parseDouble(rl.get(4)) : 0.0);
                        // 今开 5
                        stockInfoObj.setOpenPrice(rl.get(5) != null ? Double.parseDouble(rl.get(5)) : 0.0);
                        // 成交量（手）6 tradeNum
                        stockInfoObj.setTradeNum(rl.get(6) != null ? Double.parseDouble(rl.get(6)) : 0.0);
                        // 买一 9 buy1_m
                        stockInfoObj.setBuy1_m(rl.get(9) != null ? Double.parseDouble(rl.get(9)) : 0.0);
                        // 买一量（手）10 buy1_n
                        stockInfoObj.setBuy1_n(rl.get(10) != null ? Double.parseDouble(rl.get(10)) : 0.0);
                        // 买二11 buy2_m
                        stockInfoObj.setBuy2_m(rl.get(11) != null ? Double.parseDouble(rl.get(11)) : 0.0);

                        // 买四15
                        // 买四量（手）16
                        stockInfoObj.setBuy4_m(rl.get(15) != null ? Double.parseDouble(rl.get(15)) : 0.0);
                        stockInfoObj.setBuy4_n(rl.get(16) != null ? Double.parseDouble(rl.get(16)) : 0.0);
                        // 买五17
                        // 买五量（手）18
                        stockInfoObj.setBuy5_m(rl.get(17) != null ? Double.parseDouble(rl.get(17)) : 0.0);
                        stockInfoObj.setBuy5_n(rl.get(18) != null ? Double.parseDouble(rl.get(18)) : 0.0);
                        // 卖一19 sell1_m
                        // 卖一量（手）20 sell1_n
                        stockInfoObj.setSell1_m(rl.get(19) != null ? Double.parseDouble(rl.get(19)) : 0.0);
                        stockInfoObj.setSell1_n(rl.get(20) != null ? Double.parseDouble(rl.get(20)) : 0.0);
                        // 卖二21
                        // 卖二量（手）22
                        stockInfoObj.setSell2_m(rl.get(21) != null ? Double.parseDouble(rl.get(21)) : 0.0);
                        stockInfoObj.setSell2_n(rl.get(22) != null ? Double.parseDouble(rl.get(22)) : 0.0);
                        // 卖三23
                        // 卖三量（手）24
                        stockInfoObj.setSell3_m(rl.get(23) != null ? Double.parseDouble(rl.get(23)) : 0.0);
                        stockInfoObj.setSell3_n(rl.get(24) != null ? Double.parseDouble(rl.get(24)) : 0.0);
                        // 卖四25
                        // 卖四量（手）26
                        stockInfoObj.setSell4_m(rl.get(25) != null ? Double.parseDouble(rl.get(25)) : 0.0);
                        stockInfoObj.setSell4_n(rl.get(26) != null ? Double.parseDouble(rl.get(26)) : 0.0);
                        // 卖五27
                        // 卖五量（手）28
                        stockInfoObj.setSell5_m(rl.get(27) != null ? Double.parseDouble(rl.get(27)) : 0.0);
                        stockInfoObj.setSell5_n(rl.get(28) != null ? Double.parseDouble(rl.get(28)) : 0.0);

                        // 时间 30 20220902161427
                        StringBuffer stringBuilderDate =
                            new StringBuffer(rl.get(30) != null ? rl.get(30).substring(0, 8).trim() : Strings.EMPTY)
                                .insert(6, "-").insert(4, "-");
                        StringBuffer stringBuilderTime =
                            new StringBuffer(rl.get(30) != null ? rl.get(30).substring(8).trim() : Strings.EMPTY)
                                .insert(4, ":").insert(2, ":");

                        stockInfoObj.setDate(stringBuilderDate.toString());
                        stockInfoObj.setTime(stringBuilderTime.toString());

                        // 跌涨金额 31 diff_money
                        stockInfoObj.setDiff_money(rl.get(31) != null ? Double.parseDouble(rl.get(31)) : 0.0);
                        // 跌涨幅度% 32 diff_rate
                        stockInfoObj.setDiff_rate(rl.get(32) != null ? Double.parseDouble(rl.get(32)) : 0.0);
                        // 最高 33 todayMax
                        stockInfoObj.setTodayMax(rl.get(33) != null ? Double.parseDouble(rl.get(33)) : 0.0);
                        // 最低 34 todayMin
                        stockInfoObj.setTodayMin(rl.get(34) != null ? Double.parseDouble(rl.get(34)) : 0.0);
                        // 成交额（万） 37 tradeAmount
                        stockInfoObj.setTradeAmount(rl.get(37) != null ? Double.parseDouble(rl.get(37)) : 0.0);
                        // 换手率 38 turnover
                        stockInfoObj.setTurnover(rl.get(38) != null ? rl.get(38).trim() + "%" : Strings.EMPTY);
                        // ttm市盈率 39 pe
                        stockInfoObj.setPe(rl.get(39) != null && Strings.isNotBlank(rl.get(39))
                            ? Double.parseDouble(rl.get(39)) : 0.0);
                        // 振幅 43 swing
                        stockInfoObj.setSwing(rl.get(43) != null ? Double.parseDouble(rl.get(43)) : 0.0);
                        // 流通市值(亿元) 44 circulation_value
                        stockInfoObj.setCirculation_value(rl.get(44) != null && Strings.isNotBlank(rl.get(44))
                            ? Double.parseDouble(rl.get(44)) : 0.0);
                        // 总市值 45 all_value
                        stockInfoObj.setAll_value(rl.get(45) != null && Strings.isNotBlank(rl.get(45))
                            ? Double.parseDouble(rl.get(45)) : 0.0);
                        // 市净率 46 pb
                        stockInfoObj.setPb(rl.get(46) != null ? Double.parseDouble(rl.get(46)) : 0.0);
                        // 涨停价 47 highLimit
                        stockInfoObj.setHighLimit(rl.get(47) != null ? Double.parseDouble(rl.get(47)) : 0.0);
                        // 跌停价 48 downLimit
                        stockInfoObj.setDownLimit(rl.get(48) != null ? Double.parseDouble(rl.get(48)) : 0.0);
                        // 流通股本（万股） 72 currcapital
                        stockInfoObj.setCurrcapital(
                            rl.get(72) != null && Strings.isNotBlank(rl.get(72)) && Strings.isNotEmpty(rl.get(72))
                                ? Double.parseDouble(rl.get(72).trim()) / 10000 : 0.0);
                        // 总股本（万股） 73 totalcapital
                        stockInfoObj.setTotalcapital(
                            rl.get(73) != null && Strings.isNotBlank(rl.get(73)) && Strings.isNotEmpty(rl.get(73))
                                ? Double.parseDouble(rl.get(73).trim()) / 10000 : 0.0);
                        // 52周最大价 max52 67
                        stockInfoObj.setMax52(rl.get(67) != null && Strings.isNotBlank(rl.get(67))
                            ? Double.parseDouble(rl.get(67)) : 0.0);
                        // 52周最低价 min52 68
                        stockInfoObj.setMin52(rl.get(68) != null && Strings.isNotBlank(rl.get(68))
                            ? Double.parseDouble(rl.get(68)) : 0.0);
                        // appointRate 委比% 74
                        stockInfoObj.setAppointRate(rl.get(74) != null && Strings.isNotBlank(rl.get(74))
                            ? Double.parseDouble(rl.get(74)) : 0.0);
                        // appointDiff 委差（手） 50
                        stockInfoObj.setAppointDiff(rl.get(50) != null && Strings.isNotBlank(rl.get(50))
                            ? Double.parseDouble(rl.get(50)) : 0.0);

                        // 年初至今 涨幅% 62 zjzhangfu
                        stockInfoObj.setZjzhangfu(rl.get(62) != null && Strings.isNotBlank(rl.get(62))
                            ? Double.parseDouble(rl.get(62)) : 0.0);
                        // 外盘7
                        stockInfoObj.setWaipan(rl.get(7) != null ? Double.parseDouble(rl.get(7)) : 0.0);
                        // 内盘8
                        stockInfoObj.setNeipan(rl.get(8) != null ? Double.parseDouble(rl.get(8)) : 0.0);
                        // 量比 49
                        stockInfoObj.setLiangbi(rl.get(49) != null ? Double.parseDouble(rl.get(49)) : 0.0);
                        // 5日内 涨幅% 63
                        stockInfoObj.setZhangfu5(rl.get(63) != null && Strings.isNotBlank(rl.get(63))
                            ? Double.parseDouble(rl.get(63)) : 0.0);
                        // 10日内 涨幅% 69
                        stockInfoObj.setZhangfu10(rl.get(69) != null && Strings.isNotBlank(rl.get(69))
                            ? Double.parseDouble(rl.get(69)) : 0.0);
                        // 每股净资产 66 yestodayClosePrice
                        stockInfoObj.setYestodayClosePrice(rl.get(66) != null && Strings.isNotBlank(rl.get(66))
                            ? Double.parseDouble(rl.get(66)) : 0.0);

                        // 均价 51
                        stockInfoObj.setJunjia(rl.get(51) != null ? Double.parseDouble(rl.get(51)) : 0.0);
                        // 所以市盈率越低越好
                        // 动态市盈率小于静态市盈率说明这个股票的成长性好。
                        // 动态市盈率 52
                        stockInfoObj.setDtpe(rl.get(52) != null && Strings.isNotBlank(rl.get(52))
                            ? Double.parseDouble(rl.get(52)) : 0.0);
                        // 静态市盈率 53
                        stockInfoObj.setJtpe(rl.get(53) != null && Strings.isNotBlank(rl.get(53))
                            ? Double.parseDouble(rl.get(53)) : 0.0);

                        // logger.warn("freeGetStockInfoHistoryBatch0001max52 : " + rl.get(2) + " -- "
                        // + JSON.toJSONString(stringBuilder1));
                        //
                        // logger.warn("freeGetStockInfoHistoryBatch0001min52 : " + rl.get(2) + " -- "
                        // + JSON.toJSONString(stringBuilder2));

                        StockInfoProducts.add(stockInfoObj);
                    }
                }
            }

            if (CollectionUtils.isEmpty(StockInfoProducts)) {
                logger.warn("freeTencentGetStockInfoHistoryBatch0006 : " + JSON.toJSONString("OVER"));
            } else {
                stockService.addStockInfoData(StockInfoProducts);
            }
        } catch (Exception e) {
            logger.warn("freeGetStockInfoHistory9999err : " + JSON.toJSONString(" error"));
            e.printStackTrace();
        }
    }

    /**
     * 股票列表 免费
     *
     * @param stocksParam
     */
    public void freeGetAllDate(String stocksParam) {
        // http://api.k780.com/?app=finance.stock_list&category=hs&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json
        // 5971
        String host = "http://api.k780.com/";
        String path = "";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("app", "finance.stock_list");
        querys.put("category", "hs");
        querys.put("appkey", "10003");
        querys.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        querys.put("format", "json");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String s = EntityUtils.toString(response.getEntity());// 获取返回的数据 转换成字符串
            JSONObject datas = JSONObject.parseObject(s);// 转换成JSON格式

            JSONObject data = JSONObject.parseObject(datas.get("result").toString());
            JSONObject jo = JSONObject.parseObject(data.toJSONString());
            JSONArray records = JSONObject.parseArray(jo.get("lists").toString());
            logger.warn("getStockListAA123 : " + JSON.toJSONString(records.size()));
            String itemCode = Strings.EMPTY;
            String itemMarket = Strings.EMPTY;
            String itemCodePer = Strings.EMPTY;
            String itemName = Strings.EMPTY;
            if (!CollectionUtils.isEmpty(records)) {
                List<String> codePre = Arrays.asList("60", "00", "90", "20");
                for (Object item : records) {
                    // JSONObject.parseObject(item.toString()).getString("sname"); sh501001
                    itemName = JSONObject.parseObject(item.toString()).getString("sname");
                    itemCode = JSONObject.parseObject(item.toString()).getString("symbol").substring(2);
                    itemCodePer = itemCode.substring(0, 2);
                    itemMarket = JSONObject.parseObject(item.toString()).getString("symbol").substring(0, 2);
                    if (codePre.contains(itemCodePer)) {
                        stockService.addStockByCodeAndMarket(itemName, itemCode, itemMarket);
                    }
                }
            }
            logger.warn("freeGetAllDate0006 : " + JSON.toJSONString("OVER"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
