package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.StockWr;

public interface StockWrMapper {
    int insert(StockWr stockWr);

    List<StockWr> getStockWrChartDataByParam(@Param("code") String code,
        @Param("workDateList") List<String> workDateList);
}
