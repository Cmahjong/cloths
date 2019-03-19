package com.yj.service.baseResponseConverter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 这里参考了 GsonConverterFactory
 * 额外加了一个BaseResponse的检测功能
 *
 * see {@link retrofit2.converter.gson.GsonRequestBodyConverter}
 *
 * @param <T>
 */
final class BaseResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    BaseResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            return result;
        } finally {
            value.close();
        }
    }

}
