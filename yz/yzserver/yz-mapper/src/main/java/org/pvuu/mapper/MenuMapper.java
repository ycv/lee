package org.pvuu.mapper;

import org.pvuu.model.Menu;

import java.io.Serializable;
import java.util.List;

public interface MenuMapper extends Serializable {
    static final long serialVersionUID = 8841433872811285791L;

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getMenusByHrId(Integer hrid);

    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenus();

    List<Integer> getMidsByRid(Integer rid);
}