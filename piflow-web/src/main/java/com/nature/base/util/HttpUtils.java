package com.nature.base.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

/**
 * Http工具类
 */
public class HttpUtils {

	static Logger logger = LoggerUtil.getLogger();

	/**
	 * post请求传输json数据
	 * 
	 * @param url
	 * @param json
	 * @param encoding
	 * @return
	 */
	public static String doPost(String url, String json, String encoding) {
		String result = "";
		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = null;
		try {
			// 创建httpclient对象
			CloseableHttpClient httpClient = HttpClients.createDefault();

			// 创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);

			logger.info("传入的json参数：" + json);
			// 设置参数到请求对象中
			StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
			stringEntity.setContentEncoding("utf-8");
			httpPost.setEntity(stringEntity);

			logger.info("调用" + url + "，开始");
			// 执行请求操作，并拿到结果（同步阻塞）
			response = httpClient.execute(httpPost);
			logger.info("调用成功，返回信息：" + response.toString());
			// 获取结果实体
			// 判断网络连接状态码是否正常(0--200都数正常)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (UnsupportedCharsetException e) {
			logger.debug("接口调用出错", e);
			result = "接口调用出错:UnsupportedCharsetException";
		} catch (ClientProtocolException e) {
			logger.debug("接口调用出错", e);
			result = "接口调用出错:ClientProtocolException";
		} catch (ParseException e) {
			logger.debug("接口调用出错", e);
			result = "接口调用出错:ParseException";
		} catch (IOException e) {
			logger.debug("接口调用出错", e);
			result = "接口调用出错:IOException";
		}
		return result;
	}

	/**
	 * get请求传输数据
	 * 
	 * @param url
	 * @param map 请求传入参数(key为参数名称，value为参数值) map可为空
	 * @return
	 */
	public static String doGet(String url, Map<String, String> map) {
		String result = "";
		if (StringUtils.isNotBlank(url)) {
			try {
				// 创建httpclient对象
				CloseableHttpClient httpClient = HttpClients.createDefault();

				// 创建get方式请求对象
				HttpGet httpGet = null;
				// 判断是否添加参数
				if (null != map && !map.isEmpty()) {
					// 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数

					URIBuilder uriBuilder = new URIBuilder(url);

					List<NameValuePair> list = new LinkedList<>();
					for (String key : map.keySet()) {
						BasicNameValuePair param = new BasicNameValuePair(key, map.get(key));
						list.add(param);
					}

					uriBuilder.setParameters(list);
					// 根据带参数的URI对象构建GET请求对象
					httpGet = new HttpGet(uriBuilder.build());
				} else {
					httpGet = new HttpGet(url);
				}

				// 添加请求头信息
				// 浏览器表示
				// httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1;
				// en-US; rv:1.7.6)");
				// 传输的类型
				// httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");

				// 传输的类型
				httpGet.addHeader("Content-type", "application/json");
				logger.info("调用" + url + "，开始");
				// 通过请求对象获取响应对象
				CloseableHttpResponse response = httpClient.execute(httpGet);
				logger.info("调用成功，返回信息：" + response.toString());
				// 获取结果实体
				// 判断网络连接状态码是否正常(0--200都数正常)
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					result = EntityUtils.toString(response.getEntity(), "utf-8");
				}
				// 释放链接
				response.close();
			} catch (ClientProtocolException e) {
				logger.debug("接口调用出错", e);
				result = "接口调用出错:ClientProtocolException";
			} catch (ParseException e) {
				logger.debug("接口调用出错", e);
				result = "接口调用出错:ParseException";
			} catch (IOException e) {
				logger.debug("接口调用出错", e);
				result = "接口调用出错:IOException";
			} catch (URISyntaxException e) {
				logger.debug("接口调用出错", e);
				result = "接口调用出错:URISyntaxException";
			}
		}
		return result;
	}
}
