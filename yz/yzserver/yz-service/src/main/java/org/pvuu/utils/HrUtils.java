package org.pvuu.utils;

import org.pvuu.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;


public class HrUtils {
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext()
                                          .getAuthentication()
                                          .getPrincipal());
    }
}
