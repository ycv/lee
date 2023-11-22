package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.StockInfo;

public interface StockInfoMapper {
    int insertStockInfoMapperBatch(List<StockInfo> list);

    int updateStockInfoByCodeAndDate(@Param("stockInfo") StockInfo stockInfo);

    List<StockInfo> getStockInfoListByPage(@Param("page") Integer page, @Param("size") Integer size,
        @Param("stockInfo") StockInfo stockInfo);

    Long getStockInfoListTotal(@Param("stockInfo") StockInfo stockInfo);

    StockInfo getStockInfoByCodeAndDate(@Param("code") String code, @Param("date") String date);
}
