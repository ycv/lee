package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.Stock;

public interface StockMapper {

    int insert(Stock record);

    int insertBatch(List<Stock> list);

    int updateBatch(List<Stock> list);

    int updateBatchByCode(List<Stock> list);

    List<Stock> getStockByCode(String code);

    List<Stock> getAllStocks();

    Integer deleteStockByIds(@Param("ids") int[] ids);

    int deleteStockByPrimaryKey(Integer id);

    List<Stock> getStockCodeByStateAndMarket(@Param("markets") String[] markets, @Param("state") String state);

    List<Stock> getStockListByPage(@Param("page") Integer page, @Param("size") Integer size,
        @Param("markets") String[] markets, @Param("state") String state, @Param("name") String name);

    Long getStockTotal(@Param("markets") String[] markets, @Param("state") String state, @Param("name") String name);

    List<Stock> getAllStocksByStateAndCodePre(@Param("state") String state, @Param("codePre") String[] codePre);
}
