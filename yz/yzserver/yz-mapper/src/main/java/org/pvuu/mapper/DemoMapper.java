package org.pvuu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pvuu.model.Stock;

public interface DemoMapper {

    List<Stock> getDemoListByCode(@Param("code") String code);

    List<Stock> getDemoListByCodeList(@Param("codeList") List<String> codeList);
}
