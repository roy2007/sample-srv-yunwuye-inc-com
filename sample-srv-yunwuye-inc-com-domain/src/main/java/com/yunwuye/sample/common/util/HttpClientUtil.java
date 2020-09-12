/**
 *
 */
package com.yunwuye.sample.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Roy
 *
 */
public class HttpClientUtil {
    /** 日志输出 */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final int DEFAULT_CONNECTION_TIMEOUT = 6000;
    private static final int DEFAULT_SO_TIMEOUT = 10000;

    /**
     *
     * get请求
     *
     * @param invokeUrl
     * @return String
     */
    public static String createRequestGet(String invokeUrl) {
        if (StringUtils.isBlank(invokeUrl)) {
            return null;
        }
        /**
         * 创建HttpClient实例
         */
        HttpClient httpclient = new HttpClient();
        try {
            GetMethod method = new GetMethod(invokeUrl);
            int statusCode = httpclient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return method.getURI().getEscapedURI();
            } else {
                LOGGER.warn("[createRequestGet]GET请求URL返回状态不为200! invokeUrl={0} statusCode={1}", invokeUrl, statusCode);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("[createRequestGet]GET请求URL异常={0}! invokeUrl={1}", e.getMessage(), invokeUrl);
            return null;
        }
    }

    /**
     *
     * Method callHttpGet.
     *
     * @param url
     * @return String
     */
    public static String callHttpGet(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        HttpClient httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(DEFAULT_SO_TIMEOUT);
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            int statusCode = httpclient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return method.getResponseBodyAsString();
            }
            LOGGER.warn("[createRequestGet]GET请求URL返回状态不为200! invokeUrl={0} statusCode={1}", url, statusCode);
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[createRequestGet]GET请求URL异常! error={0}! invokeUrl={1} ", e.getMessage(), url);
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[createRequestGet]GET请求URL异常! error={0}! invokeUrl={1} ", e.getMessage(), url);
            return null;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
    }

    /**
     *
     * GET方式请求数据，可设置连接超时时间和读取超时时间
     *
     * @param url
     * @param connectionTimeout
     * @param soTimeout
     * @return String
     */
    public static String requestData(String url, int connectionTimeout, int soTimeout) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        HttpClient httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            int statusCode = httpclient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return method.getResponseBodyAsString();
            }
            LOGGER.warn("[requestData]GET请求URL返回状态不为200! invokeUrl={0} statusCode={1}", url, statusCode);
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[requestData]GET请求URL返回状态不为200! error={0}! invokeUrl={1} ", e.getMessage(), url);
            return null;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
    }

    /**
     *
     * Method callHttpGet.
     *
     * @param url
     * @param headers信息
     * @return String
     */
    public static String callHttpGet(String url, Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        HttpClient httpclient = new HttpClient();
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    method.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            int statusCode = httpclient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return method.getResponseBodyAsString();
            }
            LOGGER.warn("[createRequestGet]GET请求URL返回状态不为200! invokeUrl={0} statusCode={1}", url, statusCode);
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[createRequestGet]GET请求URL异常! error={0}! invokeUrl={1} ", e.getMessage(), url);
            return null;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
    }

    /**
     *
     * http post 请求接口
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String callHttpPost(String url, Map<String, Object> params, Map<String, String> headers) {
        LOGGER.warn("[call http post], invokeUrl={0} params={1},headers={2}", url, JSON.toJSONString(params),
                JSON.toJSONString(headers));
        if (StringUtils.isBlank(url)) {
            return null;
        }
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    postMethod.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            if (!CollectionUtils.isEmpty(params)) {
                String toJson = JSONObject.toJSONString(params);
                RequestEntity se = new StringRequestEntity(toJson, "application/json", "UTF-8");
                postMethod.setRequestEntity(se);
                int statusCode = httpclient.executeMethod(postMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    return postMethod.getResponseBodyAsString();
                }
                LOGGER.warn("[callHttpPost]POST请求URL返回状态不为200! invokeUrl={0} statusCode={1}", url, statusCode);
            }
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[createRequestGet]POST请求URL异常! error={0}! invokeUrl={1} ", e.getMessage(), url);
            return null;
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
    }

    /**
     *
     * 在调用SSL之前需要重写验证方法，取消检测SSL 创建ConnectionManager，添加Connection配置信息
     *
     * @return CloseableHttpClient 支持https
     */
    private static CloseableHttpClient getHttpsClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { trustManager }, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx,
                    NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            LOGGER.error(ex.getMessage(), ex);
            LOGGER.error("[get HttpsClient] error! error={0}! invokeUrl={1} ");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage(), ex);
            LOGGER.error("[get HttpsClient] error! error={0}! invokeUrl={1} ");
        }
        return null;
    }

    /**
     *
     * 如果请求成功，获取响应body
     *
     * @param response
     * @return
     */
    private static String getHttpsResponseBodyAsString(CloseableHttpResponse response) {
        // 服务器返回内容,请求到的是一个页面
        String responseBodyStr = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                responseBodyStr = EntityUtils.toString(entity, "UTF-8");
                // 释放资源
                EntityUtils.consume(entity);
                response.close();
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.error("[response parse] error! error={0}", e.getMessage());
                return null;
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.error("[response parse] error! error={0}", e.getMessage());
                return null;
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        LOGGER.warn("[httpclient close stream] error!" + e.getMessage() + ",错误信息" + e);
                    }
                }
            }
        }
        return responseBodyStr;
    }

    /**
     *
     * 调用https 的get方法
     *
     * @param url
     * @return
     */
    public static String callHttpsGet(String url) {
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = getHttpsClient().execute(get);
            // 服务器返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return getHttpsResponseBodyAsString(response);
            }
            LOGGER.warn("[call https get]Request return status is not 200, invokeUrl={0} statusCode={1}", url,
                    statusCode);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[call Https get]error={0}! invokeUrl={1} ", e.getMessage(), url);
        }
        return null;
    }

    /**
     *
     * 调用https 的post方法
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String callHttpsPost(String url, Map<String, Object> params, Map<String, String> headers) {
        LOGGER.warn("[call https post], invokeUrl={0} params={1},headers={2}", url, JSONObject.toJSONString(params),
                JSONObject.toJSONString(headers));
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (!CollectionUtils.isEmpty(params)) {
                String jsonBody = JSONObject.toJSONString(params);
                StringEntity entity = new StringEntity(jsonBody, StandardCharsets.UTF_8);
                post.setEntity(entity);
                CloseableHttpClient httpsClient = getHttpsClient();
                response = httpsClient.execute(post);
                // 服务器返回码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    return getHttpsResponseBodyAsString(response);
                }
                LOGGER.warn("[call https post]Request return status is not 200, invokeUrl={0} statusCode={1}", url,
                        statusCode);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("[call Https post]error! error={0}! invokeUrl={1} ", e.getMessage(), url);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.error("[call Https post]error! error={0}! invokeUrl={1} ", e.getMessage(), url);
            }
        }
        return null;
    }
}
