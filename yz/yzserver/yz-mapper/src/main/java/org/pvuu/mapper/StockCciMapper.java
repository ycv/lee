package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.StockCci;

public interface StockCciMapper {
    int insert(StockCci stockCci);

    int updateStockCciData(@Param("stockCci") StockCci stockCci);

    List<StockCci> getStockCciChartDataByParam(@Param("code") String code,
        @Param("workDateList") List<String> workDateList);
}
