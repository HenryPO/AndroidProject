package com.mobile.library.http.util;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mobile.library.http.bean.HttpPair;
import com.mobile.library.http.cookie.CookieUtil;
import com.mobile.library.http.enums.HttpType;
import com.mobile.library.utils.MLog;
import com.mobile.library.utils.StringUtil;

/**
 * 
 * @ClassName: HttpUtil
 * @Description: Http请求管理工具
 * @author lhy
 * @date 2014-10-9 上午11:16:46
 * 
 */
public class HttpUtil {

	private Context context;

	private static int CONNECTION_TIMEOUT = 6 * 1000;
	private static int SO_TIMEOUT = 6 * 1000;

	public HttpUtil(Context context) {
		this.context = context;
	}

	/**
	 * 设置连接超时
	 * 
	 * @param time
	 */
	public void setConnectionTimeOut(int time) {
		CONNECTION_TIMEOUT = time;
	}

	/**
	 * 设置读取超时
	 * 
	 * @param time
	 */
	public void setSoTimeOut(int time) {
		SO_TIMEOUT = time;
	}

	/**
	 * 
	 * @Title: get @Description:get请求 @param @param context @param @param
	 *         url @param @param para @param @param cls @param @return
	 *         设定文件 @return T 返回类型 @throws
	 */
	public <T> T get(String url, ArrayList<HttpPair> para, Type cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(get(url, para), cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @Title: get @Description: get请求 @param @param context @param @param
	 *         url @param @param para @param @return 设定文件 @return String
	 *         返回类型 @throws
	 */
	public String get(String url, ArrayList<HttpPair> para) {
		HttpResponse response = getResponse(url, para);
		if (response == null) {
			return null;
		}
		int statusCode = response.getStatusLine().getStatusCode();
		MLog.i("HttpStatus=>" + statusCode);
		// 判断请求是否成功
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String body = null;
			try {
				body = EntityUtils.toString(response.getEntity(), "utf-8");
				MLog.i("请求服务器端成功=>\n" + body);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return body;
		}
		return null;
	}

	/**
	 * 
	 * @Title: getResponse @Description: get请求 @param @param
	 *         context @param @param url @param @param para @param @return
	 *         设定文件 @return HttpResponse 返回类型 @throws
	 */
	public HttpResponse getResponse(String url, ArrayList<HttpPair> para) {
		try {
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(url);
			if (para != null) {
				urlBuilder.append("?");
				for (int i = 0; i < para.size(); i++) {
					HttpPair pair = para.get(i);
					urlBuilder.append(URLEncoder.encode(pair.getName(), "UTF-8")).append('=')
							.append(URLEncoder.encode(pair.getValue().toString(), "UTF-8"));
					urlBuilder.append('&');
				}
				urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
			}
			Log.i("getRequest=>", urlBuilder.toString());

			// 创建HttpClient对象
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// 请求超时
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 读取超时
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			if (url.startsWith("https://")) {
				MLog.v("Get=>Https");
				httpClient = initHttpClient(httpClient.getParams());
			}
			// 发送get请求创建HttpGet对象
			HttpGet httpget = new HttpGet(urlBuilder.toString());
			CookieUtil cookieStore = CookieUtil.getInstance(context);
			httpClient.setCookieStore(cookieStore);
			HttpResponse response = httpClient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			// 判断请求是否成功
			if (statusCode == HttpStatus.SC_OK) {
				cookieStore = CookieUtil.getInstance(context);
				List<Cookie> cookies = httpClient.getCookieStore().getCookies();
				for (Cookie cookie : cookies) {
					cookieStore.addCookie(cookie);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: post @Description: post请求 @param @param context @param @param
	 *         url @param @param para @param @param cls @param @param
	 *         mStream @param @return 设定文件 @return T 返回类型 @throws
	 */
	public <T> T post(String url, ArrayList<HttpPair> para, Type cls, FilterOutputStream mStream) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(post(url, para, mStream), cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @Title: post @Description: post请求 @param @param context @param @param
	 *         url_Str @param @param pairs @param @param mStream @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	public String post(String url_Str, ArrayList<HttpPair> pairs, FilterOutputStream mStream) {
		HttpResponse httpResponse = postResponse(url_Str, pairs, mStream);
		if (httpResponse == null) {
			return null;
		}
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		MLog.i("HttpStatus+>" + statusCode);
		if (statusCode == HttpStatus.SC_OK) {
			String body = null;
			try {
				body = EntityUtils.toString(httpResponse.getEntity());
				MLog.i("请求服务器端成功=>\n" + body);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return body;
		}
		return null;
	}

	/**
	 * 
	 * @Title: postResponse @Description: Post请求 @param @param
	 *         context @param @param url_Str @param @param pairs @param @param
	 *         mStream @param @return 设定文件 @return HttpResponse 返回类型 @throws
	 */
	public HttpResponse postResponse(String url_Str, ArrayList<HttpPair> pairs, final FilterOutputStream mStream) {
		try {
			MLog.i("Post请求URL->" + url_Str);
			// 判断是否包含上传文件
			boolean isUploadFile = false;
			HttpPost httpPost = new HttpPost(url_Str);
			MultipartEntity multipartEntity = new MultipartEntity() {
				@Override
				public void writeTo(OutputStream outstream) throws IOException {
					super.writeTo(new FilterOutputStream(outstream) {
						@Override
						public void write(byte[] buffer, int offset, int length) throws IOException {
							super.write(buffer, offset, length);
							if (mStream != null) {
								mStream.write(buffer, offset, length);
							}
						}
					});
				}
			};
			if (pairs != null) {
				for (int i = 0; i < pairs.size(); i++) {
					HttpPair httpPair = pairs.get(i);
					HttpType type = httpPair.getType();
					if (type != HttpType.NORMAL) {
						isUploadFile = true;
						break;
					}
				}
				for (int i = 0; i < pairs.size(); i++) {
					HttpPair multipleNameValuePair = pairs.get(i);
					HttpType type = multipleNameValuePair.getType();
					String key = multipleNameValuePair.getName().trim();
					String value = multipleNameValuePair.getValue().trim();
					MLog.i("上传参数type->" + type.getValue() + "\n" + "上传参数key->" + key + "\n" + "上传参数value->" + value);
				}
				if (isUploadFile) {
					for (int i = 0; i < pairs.size(); i++) {
						HttpPair multipleNameValuePair = pairs.get(i);
						HttpType type = multipleNameValuePair.getType();
						String key = multipleNameValuePair.getName().trim();
						String value = multipleNameValuePair.getValue().trim();
						// MLog.i("上传参数type->" + type.getValue() + "\n" +
						// "上传参数key->" + key + "\n" + "上传参数value->" + value);
						if (type != HttpType.NORMAL) {
							if (!StringUtil.isNullOrEmpty(value)) {
								multipartEntity.addPart(key, new FileBody(new File(value), type.getValue()));
							}
						} else {
							multipartEntity.addPart(key, new StringBody(value, Charset.forName("utf-8")));
						}
					}
					httpPost.setEntity(multipartEntity);
				} else {
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
					httpPost.setEntity(urlEncodedFormEntity);
				}
			}

			CookieUtil cookieStore = CookieUtil.getInstance(context);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			// 请求超时
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 读取超时
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			if (url_Str.startsWith("https://")) {
				MLog.v("Post=>Https");
				httpClient = initHttpClient(httpClient.getParams());
			}
			httpClient.setCookieStore(cookieStore);
			// return httpClient.execute(httpPost);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				if (!isUploadFile) {
					cookieStore = CookieUtil.getInstance(context);
					List<Cookie> cookies = httpClient.getCookieStore().getCookies();
					for (Cookie cookie : cookies) {
						cookieStore.addCookie(cookie);
					}
				}
			}
			return httpResponse;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: post @Description: post请求 @param @param context @param @param
	 *         url @param @param para @param @param cls @param @param
	 *         mStream @param @return 设定文件 @return T 返回类型 @throws
	 */
	public <T> T post(String url, String xmlData, Type cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(post(url, xmlData), cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @Title: post @Description: post请求 @param @param context @param @param
	 *         url_Str @param @param pairs @param @param mStream @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	public String post(String url_Str, String xmlData) {
		HttpResponse httpResponse = postResponse(url_Str, xmlData);
		if (httpResponse == null) {
			return null;
		}
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		MLog.i("HttpStatus+>" + statusCode);
		if (statusCode == HttpStatus.SC_OK) {
			String body = null;
			try {
				body = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				MLog.i("请求服务器端成功=>\n" + body);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return body;
		}
		return null;
	}

	/**
	 * 
	 * @param url_Str
	 * @param xmlData
	 * @return
	 */
	public HttpResponse postResponse(String url_Str, String xmlData) {
		try {
			MLog.i("Post请求URL->" + url_Str);
			// 判断是否包含上传文件
			boolean isUploadFile = false;
			HttpPost httpPost = new HttpPost(url_Str);

			StringEntity entity = new StringEntity(xmlData);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("text/plain");
			httpPost.setEntity(entity);
			CookieUtil cookieStore = CookieUtil.getInstance(context);
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// 请求超时
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 读取超时
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			if (url_Str.startsWith("https://")) {
				MLog.v("Post=>Https");
				httpClient = initHttpClient(httpClient.getParams());
			}
			httpClient.setCookieStore(cookieStore);
			// return httpClient.execute(httpPost);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				if (!isUploadFile) {
					cookieStore = CookieUtil.getInstance(context);
					List<Cookie> cookies = httpClient.getCookieStore().getCookies();
					for (Cookie cookie : cookies) {
						cookieStore.addCookie(cookie);
					}
				}
			}
			return httpResponse;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DefaultHttpClient initHttpClient(HttpParams params) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient(params);
		}
	}

	public static class SSLSocketFactoryImp extends SSLSocketFactory {
		final SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryImp(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
				throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
