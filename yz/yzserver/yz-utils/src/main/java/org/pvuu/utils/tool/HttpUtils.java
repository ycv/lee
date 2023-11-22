package org.pvuu.utils.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * 
 * 
 * @ClassName:HTTP请求工具类 基于HTTCLIENT对GET，POST请求进行封装
 * 
 * 
 * @Version:1.0
 **/

public class HttpUtils {

    private static Log log = LogFactory.getLog(HttpUtils.class);
    // 定义编码格式 UTF-8
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";
    // 定义编码格式 GBK
    public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";
    private static final String URL_PARAM_CONNECT_FLAG = "&";
    private static final String EMPTY = "";

    private static int connectionTimeOut = 10000;
    private static int socketTimeOut = 800000;
    private static int maxConnectionPerHost = 20;
    private static int maxTotalConnections = 20;
    private static HttpClient client;
    private static MultiThreadedHttpConnectionManager connectionManager = null;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * GET 方式调用 默认UTF8编码
     *
     * @param url
     * @return
     */
    public static String URLGet(String url) {
        return URLGet(url, null, URL_PARAM_DECODECHARSET_UTF8);
    }

    /**
     * 返回流
     */
    public static InputStream UrlGetImage(String url) {
        return URLStream(url, null, URL_PARAM_DECODECHARSET_UTF8);
    }

    /**
     * GET 方式调用 默认UTF8编码
     *
     * @param url
     * @return
     */
    public static String URLGet(String url, Map params) {
        return URLGet(url, params, URL_PARAM_DECODECHARSET_UTF8);
    }

    /**
     * POST 方式调用 默认UTF8编码
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String URLPost(String url, Map params) {
        return URLPost(url, params, URL_PARAM_DECODECHARSET_UTF8);
    }

    /**
     * POST 方式调用接口，参数已JSON字符串格式传入
     *
     * @param url
     * @param jsonData 参数json字符串
     * @param enc 编码
     * @return
     */
    public static String URLPostJSON(String url, String jsonData, String enc) {
        if (null == enc || enc.equals("")) {
            enc = URL_PARAM_DECODECHARSET_UTF8;
        }
        log.debug(" -- Http call url : " + url);
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/json;charset=" + enc);
            // 将表单的值放入postMethod中
            RequestEntity entity = new StringRequestEntity(jsonData, "application/json", "UTF-8");
            postMethod.setRequestEntity(entity);
            postMethod.releaseConnection();
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("响应状态码 = " + postMethod.getStatusCode());
                log.error("响应信息 = " + postMethod.getResponseBodyAsString());
            }
            response = postMethod.getResponseBodyAsString();
            log.debug(" -- Http call result : " + response);
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;

    }

    /**
     * POST方式提交数据
     *
     * @param url 待请求的URL
     * @param params 要提交的数据
     * @param enc 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLPost(String url, Map params, String enc) {
        log.debug(" -- Http call url : " + url);
        log.debug(" -- Http call parmas : " + params);
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 将表单的值放入postMethod中
            if (params != null && params.size() > 0) {
                Set keySet = params.keySet();
                for (Iterator it = keySet.iterator(); it.hasNext();) {
                    String key = (String)it.next();
                    String value = (String)params.get(key);
                    postMethod.addParameter(key, value);
                }
            }
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("响应状态码 = " + postMethod.getStatusCode());
                log.error("响应信息 = " + postMethod.getResponseBodyAsString());
            }
            response = postMethod.getResponseBodyAsString();

            log.debug(" -- Http call result : " + response);
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }

    /**
     * POST方式提交数据 contentType:application/json
     *
     * @param url 待请求的URL
     * @param params 要提交的数据
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLPost2(String url, String params) {
        log.debug(" -- Http call url : " + url);
        log.debug(" -- Http call parmas : " + params);
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            RequestEntity requestEntity = new StringRequestEntity(params, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("响应状态码 = " + postMethod.getStatusCode());
                log.error("响应信息 = " + postMethod.getResponseBodyAsString());
            }
            response = postMethod.getResponseBodyAsString();

            log.debug(" -- Http call result : " + response);
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return response;
    }

    public static String URLPostCheckAndLoginout(String url, Map params, String enc) {
        log.debug(" -- Http call url : " + url);
        log.debug(" -- Http call parmas : " + params);
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 将表单的值放入postMethod中
            Set keySet = params.keySet();
            for (Iterator it = keySet.iterator(); it.hasNext();) {
                String key = (String)it.next();
                String value = (String)params.get(key);
                postMethod.addParameter(key, value);
            }
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = "{\"status\":true}";
            } else {
                response = "{\"status\":false}";
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
            log.debug(" -- Http call result : " + response);
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }

    /**
     * GET方式提交数据
     *
     * @param url 待请求的URL
     * @param params 要提交的数据
     * @param enc 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLGet(String url, Map params, String enc) {
        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if (url.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            } else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }

    /**
     * @param url
     * @param params
     * @param enc
     * @return
     */
    public static InputStream URLStream(String url, Map params, String enc) {
        InputStream response = null;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if (url.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsStream();
            } else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }

    /**
     * 据Map生成URL字符串
     *
     * @param map Map
     * @param valueEnc URL编码
     * @return URL
     */
    private static String getUrl(Map map, String valueEnc) {
        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        valueEnc = StringUtils.defaultIfEmpty(valueEnc, URL_PARAM_DECODECHARSET_UTF8);
        StringBuffer url = new StringBuffer();
        Set keys = map.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = (String)it.next();
            if (map.containsKey(key)) {
                String val = (String)map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(), e);
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return (strURL);
    }

    /**
     * POST方式提交数据
     *
     * @param url 待请求的URL
     * @param params 要提交的数据
     * @param enc 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static Map Post(String url, Map params, String enc) {
        if (null == enc || enc.equals("")) {
            enc = URL_PARAM_DECODECHARSET_UTF8;
        }
        Map result = new HashMap<>();
        log.info(" -- Http call url : " + url);
        log.info(" -- Http call parmas : " + params);
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 将表单的值放入postMethod中
            Set keySet = params.keySet();
            for (Iterator it = keySet.iterator(); it.hasNext();) {
                String key = (String)it.next();
                String value = params.get(key).toString();
                postMethod.addParameter(key, value);
            }
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            log.info("响应状态码 = " + postMethod.getStatusCode());
            result.put("resCode", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                log.info("响应信息 = " + postMethod.getResponseBodyAsString());
                response = postMethod.getResponseBodyAsString();
            } else {
                response = "请求接口失败！响应状态码" + statusCode;
            }
            log.info(" -- Http call result : " + response);
        } catch (Exception e) {
            if (e instanceof HttpException) {
                log.info("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            } else if (e instanceof IOException) {
                log.info("发生网络异常", e);
            }
            response = "请求接口发生异常!" + e.getMessage();
            e.printStackTrace();
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        result.put("resMessage", response);
        return result;
    }

}
