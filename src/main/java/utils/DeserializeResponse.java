package utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class DeserializeResponse {
    public static <T> T deserializeResponse(String response, Class<T> beanClass) {
        return new Gson().fromJson(response, beanClass);
    }

    public static <T> List<T> deserializeResponseList(String response, Class<T> beanClass) {
        return new Gson().fromJson(response, new ListOfJson<>(beanClass));
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