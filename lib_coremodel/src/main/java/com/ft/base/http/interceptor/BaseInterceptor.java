package com.ft.base.http.interceptor;

import android.util.Log;


import com.ft.base.base.BaseApplication;
import com.ft.base.net.BuildConfig;
import com.ft.base.utils.LogUtil;
import com.ft.base.utils.MD5Utils;
import com.ft.base.utils.SPUtils;
import com.ft.base.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class BaseInterceptor implements Interceptor {
    private static final String TAG = BaseInterceptor.class.getSimpleName();

    private Map<String, String> headers;
    private Map<String, Object> params; // 公共参数

    public BaseInterceptor(Map<String, String> headers, Map<String, Object> params) {
        this.headers = headers;
        this.params = params;
    }

    public BaseInterceptor(Map<String, Object> params) {
        this(null, params);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();

        RequestBody originRequestBody = request.body();

        String mediaType = null;
        try {
            mediaType = originRequestBody.contentType().toString();
        } catch (NullPointerException ignore) {
        }

        String url =  request.url().toString();
        String api = "";
        if (!StringUtils.isEmpty(url)) {
            String[] split = url.split("/");
            if (split != null && split.length > 1){
                api = split[split.length - 1];
            }
        }

        if (params != null && !params.isEmpty()) {
            params.put("_token", SPUtils.getSharedStringData(BaseApplication.getInstance(),"USER_TOKEN"));
            params.put("_uid", SPUtils.getSharedStringData(BaseApplication.getInstance(),"USER_UID"));

            if ("GET".equals(request.method())) { // GET方法

                HttpUrl httpUrl = urlBuilder.build();
                String _token = "";
                String _uid = "";
                // 打印get请求参数
                Set<String> paramKeys = httpUrl.queryParameterNames();
                for (String key : paramKeys) {
                    String value = httpUrl.queryParameter(key);
                    if (key.equals("_token") && !StringUtils.isEmpty(value)) {
                        _token = value;
                        params.put("_token", _token);
                        urlBuilder.removeAllQueryParameters("_token");
                    } else if (key.equals("_uid") && !StringUtils.isEmpty(value)) {
                        _uid = value;
                        params.put("_uid", _uid);
                        urlBuilder.removeAllQueryParameters("_uid");
                    }
                    Log.d("get请求参数==", key + " " + value);
                }

                // 添加一些公共get参数
                for(Map.Entry<String, Object> entry : params.entrySet()){
                    String mapKey = entry.getKey();
                    String mapValue = (String)entry.getValue();
                    // 请求参数有传token、uid，公共参数不需要再添加
                    if (mapKey.equals("_token") && !StringUtils.isEmpty(_token)) {
                        urlBuilder.addEncodedQueryParameter(mapKey, _token);
                        continue;
                    } else if (mapKey.equals("_uid") && !StringUtils.isEmpty(_uid)) {
                        urlBuilder.addEncodedQueryParameter(mapKey, _uid);
                        continue;
                    }
                    urlBuilder.addEncodedQueryParameter(mapKey, mapValue);
                }
                httpUrl = urlBuilder.build();

                // 将最终的url填充到request中
                requestBuilder.url(httpUrl);
            } else if ("POST".equals(request.method()) || "DELETE".equals(request.method())) { // POST方法
                // 添加公共参数到 url
                StringBuffer paramstr = new StringBuffer();

                if (params != null && params.size() > 0) {
                    Set<String> keys = params.keySet();
                    for (String headerKey : keys) {
                        paramstr.append(headerKey + "=" + params.get(headerKey) + "&");
                    }
                    if (paramstr.length() > 1) {
                        String params = paramstr.substring(0, paramstr.length() - 1);
                        url =  request.url() + "?" + params;
                        requestBuilder.url(url);
                    }
                }

                FormBody.Builder bodyBuilder = new FormBody.Builder();
                // 请求参数
                Map<String,Object> dataMap = new TreeMap<>();

                if (null != mediaType && mediaType.startsWith("application/x-www-form-urlencoded")) {

                    if (request.body() instanceof FormBody) {
                        FormBody formBody = (FormBody) request.body();

                        for (int i = 0; i < formBody.size(); i++) {
                            //值为空的参数不添加、请求参数有token(一键登录需要添加)/uid值不添加
                            /*if (StringUtils.isEmpty(formBody.encodedValue(i))
                                    ||formBody.encodedName(i).equals("uin")
                                    ||(formBody.encodedName(i).equals("token") && !StringUtils.isEmpty(api) && !api.equals("users"))) {
                                continue;
                            }*/
                            bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        }

                        FormBody newBody = bodyBuilder.build();
                        for (int i = 0; i < newBody.size(); i++) {
                            dataMap.put(newBody.name(i), newBody.value(i));
                        }
                        String sign = "";
                        if (!dataMap.isEmpty()) {
                            String paramSrc = MD5Utils.getParamSrc(dataMap);
                            sign = MD5Utils.sign(paramSrc);
                        } else {
                            sign = MD5Utils.md5("key=" + BuildConfig.MD5_KEY);
                        }
                     //   bodyBuilder.addEncoded("sign", sign);
                    }

                    FormBody newBody = bodyBuilder.build();

                    // 打印所有post参数
                    for (int i = 0; i < newBody.size(); i++) {
                        Log.d("post参数==", newBody.name(i) + " " + newBody.value(i));
                    }
                    // 将最终的表单body填充到request中
                    if ("DELETE".equals(request.method())) {
                        requestBuilder.delete(newBody);
                    } else {
                        requestBuilder.post(newBody);
                    }
                } else if (null != mediaType && mediaType.startsWith("multipart/")) {
                    String appendBodyString = "";

                    if (request.body() instanceof MultipartBody) {

                        MultipartBody multipartBody = (MultipartBody) request.body();

                        Buffer buffer1 = new Buffer();
                        multipartBody.writeTo(buffer1);
                        String postParams = buffer1.readUtf8();
                        String[] split = postParams.split("\n");
                        List<String> names = new ArrayList<>();
                        for (String s : split) {
                            if (s.contains("Content-Disposition")) {
                                names.add(s.replace("Content-Disposition: form-data; name=", "").replace("\"", ""));
                            }
                        }

                        List<MultipartBody.Part> parts = multipartBody.parts();
                        for (int i = 0; i < parts.size(); i++) {
                            MultipartBody.Part part = parts.get(i);
                            RequestBody body1 = part.body();
                            String type = null;
                            try {
                                type = body1.contentType().toString();
                            } catch (Exception ignore) {
                            }
                            if (body1.contentLength() != 0 && StringUtils.isEmpty(type)) {
                                Buffer buffer = new Buffer();
                                body1.writeTo(buffer);
                                String value = buffer.readUtf8();
                                if (names.size() > i) {
                                    if (!StringUtils.isEmpty(value)) {
                                        dataMap.put(names.get(i).replace("\r",""), value);
                                    }
                                }
                            }
                        }

                        String sign = "";
                        if (!dataMap.isEmpty()) {
                            String paramSrc = MD5Utils.getParamSrc(dataMap);
                            sign = MD5Utils.sign(paramSrc);
                        } else {
                            sign = MD5Utils.md5("key=" + BuildConfig.MD5_KEY);
                        }

                        String boundary = multipartBody.boundary();
                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        builder.addFormDataPart("sign", sign);


                        MultipartBody appendRequestBody = builder.build();
                        appendBodyString = bodyToString(appendRequestBody);
                        appendBodyString = appendBodyString.replace(appendRequestBody.boundary(), boundary);
                        // 删除最后一行
                        appendBodyString = appendBodyString.substring(0, appendBodyString.length() - 1);
                        appendBodyString = appendBodyString.substring(0, appendBodyString.lastIndexOf('\n') + 1);

                     }


                    byte[] newBodyByteArray = mergeRequestBody(originRequestBody, appendBodyString);

                    requestBuilder.post(RequestBody.create(MediaType.parse(mediaType), newBodyByteArray));
                }
            }
        }

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                requestBuilder.addHeader(headerKey, headers.get(headerKey));
            }
        }

        return chain.proceed(requestBuilder.build());
    }

    //应该注意网络传输的是二进制流,这里必然使用byte[]
    public byte[] mergeRequestBody(RequestBody originRequestBody, String appendBodyString) {
        final Buffer buffer = new Buffer();
        try {
            buffer.writeUtf8(appendBodyString);
            originRequestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.readByteArray();
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}