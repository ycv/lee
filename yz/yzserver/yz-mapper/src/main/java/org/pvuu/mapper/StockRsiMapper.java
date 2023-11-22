package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.StockRsi;

public interface StockRsiMapper {

    int insert(StockRsi stockRsi);

    int updateStockRsiDataByCodeAndDate(@Param("stockRsi") StockRsi stockRsi);

    int insertStockRsiBatch(List<StockRsi> list);

    List<StockRsi> getStockChartDataByParam(@Param("stockRsiParam") StockRsi stockRsiParam,
        @Param("workDateList") List<String> workDateList);

    int deleteStockRsiByCode(@Param("code") String code, @Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    List<StockRsi> getStockRsiListByPage(@Param("page") Integer page, @Param("size") Integer size,
        @Param("stockRsiParam") StockRsi stockRsiParam);

    Long getStockRsiTotal(@Param("stockRsiParam") StockRsi stockRsiParam);

}
