package org.pvuu.service;

import java.util.List;

import org.pvuu.mapper.MenuMapper;
import org.pvuu.mapper.MenuRoleMapper;
import org.pvuu.model.Hr;
import org.pvuu.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@CacheConfig(cacheNames = "menus_cache2")
public class MenuService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        return menuMapper
            .getMenusByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());

    }

    @Cacheable
    public List<Menu> getAllMenusWithRole() {
        logger.warn("menusmenus ： " + JSON.toJSONString(111223));
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        logger.warn("menusmenus ： " + JSON.toJSONString(9991));
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if (mids == null || mids.length == 0) {
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result == mids.length;
    }
}
