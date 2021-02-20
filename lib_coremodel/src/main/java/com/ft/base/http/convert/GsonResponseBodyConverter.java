package com.ft.base.http.convert;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.ft.base.http.BaseResponse;
import com.ft.base.http.ServerResponseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    public GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
//            Object object = adapter.fromJson(value.charStream());
//            if (object instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) adapter.fromJson(value.charStream());
                if (response.getCode() == 0) {
                    /*Object object = response.getData();
                    if (object instanceof ArrayList && ((ArrayList) object).size() == 0) {
                        return response.getMessage();
                    } else if (object instanceof LinkedTreeMap && (((LinkedTreeMap) object).isEmpty())) {
                        return response.getMessage();
                    }*/
                 //   return response.getData();
                    // 兼容应援币领取列表接口数据
                    if (response.getItems() != null) {
                        return response;
                    } else {
                        return response.getData();
                    }
                } else {
                    // 特定 API 的错误，在相应的 DefaultObserver 的 onError 的方法中进行处理
                    throw new ServerResponseException(response.getCode(), response.getMessage());
                }
//            }
//            return object;
        }finally {
            value.close();
        }
    }
}
