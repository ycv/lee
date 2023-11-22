package org.pvuu.config;

import java.util.Collection;
import java.util.List;

import org.pvuu.model.Menu;
import org.pvuu.model.Role;
import org.pvuu.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * 这个类的作用，主要是根据用户传来的请求地址，分析出请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation)object).getRequestUrl();
        // logger.warn("requestUrlrequestUrl23 ： " + JSON.toJSONString(requestUrl));
        List<Menu> menus = menuService.getAllMenusWithRole();

        // logger.warn("menusmenus43 ： " + JSON.toJSONString(menus));
        for (Menu menu : menus) {
            // logger.warn("menu.getUrl99913 ： " + JSON.toJSONString(menu.getName()));
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    str[i] = roles.get(i).getName();

                    // logger.warn("getAttributes222 : " + JSON.toJSONString(str[i]));
                }
                return SecurityConfig.createList(str);
            } else {

            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
