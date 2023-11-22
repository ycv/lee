package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.StockKdj;

public interface StockKdjMapper {
    int insert(StockKdj stockKdj);

    List<StockKdj> getStockKdjChartDataByParam(@Param("code") String code,
        @Param("workDateList") List<String> workDateList);
}
