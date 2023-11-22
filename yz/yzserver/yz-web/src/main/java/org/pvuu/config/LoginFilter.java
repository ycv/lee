package org.pvuu.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pvuu.model.Hr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        logger.warn("getEmployeeByPage11 ： " + JSON.toJSONString("333"));
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String verify_code = (String)request.getSession().getAttribute("verify_code");
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)
            || request.getContentType().contains(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            Map<String, String> loginData = new HashMap<>();
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
            } finally {
                String code = loginData.get("code");
                checkCode(response, code, verify_code);
            }
            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());

            logger.warn("getEmployeeByPage22 ： " + JSON.toJSONString("123"));

            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            Hr principal = new Hr();
            principal.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession(true).getId(), principal);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            checkCode(response, request.getParameter("code"), verify_code);
            return super.attemptAuthentication(request, response);
        }
    }

    public void checkCode(HttpServletResponse resp, String code, String verify_code) {
        if (code == null || verify_code == null || "".equals(code)
            || !verify_code.toLowerCase().equals(code.toLowerCase())) {
            // 验证码不正确
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
