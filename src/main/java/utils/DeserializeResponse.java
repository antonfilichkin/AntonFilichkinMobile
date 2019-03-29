package utils;

import com.google.gson.Gson;
import io.restassured.response.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.List;

public class DeserializeResponse {
    public static <T> T deserializeResponse(String response, Class<T> beanClass) {
        return new Gson().fromJson(response, beanClass);
    }

    public static <T> List<T> deserializeResponseList(String response, Class<T> beanClass) {
        return new Gson().fromJson(response, new ListOfJson<>(beanClass));
    }

    // REST ASSURED
    // Deserialize json response to object
    public static <T> T deserializeResponse(Response response, Class<T> beanClass) {
        return new Gson().fromJson(response.asString().trim(), beanClass);
    }

    // Deserialize json response to list of objects
    public static <T> List<T> deserializeResponseList(Response response, Class<T> beanClass) {
        return new Gson().fromJson(response.asString().trim(), new ListOfJson<>(beanClass));
    }

    // Wrapper class for list so the wrapper can store the exactly type of list.
    private static class ListOfJson<T> implements ParameterizedType {
        private Class<?> wrapped;

        private ListOfJson(Class<T> wrapper) {
            this.wrapped = wrapper;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{wrapped};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}