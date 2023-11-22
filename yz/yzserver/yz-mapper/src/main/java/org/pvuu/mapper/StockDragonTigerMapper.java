package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.StockDragonTiger;

public interface StockDragonTigerMapper {
    int insertBatch(List<StockDragonTiger> list);

    List<StockDragonTiger> getStockDragonTigerListByParam(@Param("page") Integer page, @Param("size") Integer size,
        @Param("stockDragonTiger") StockDragonTiger stockDragonTiger);

    Long getStockDragonTigerTotal(@Param("stockDragonTiger") StockDragonTiger stockDragonTiger);
}
