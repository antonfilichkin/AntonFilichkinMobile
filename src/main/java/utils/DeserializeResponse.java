package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;

import java.util.List;

public class DeserializeResponse {
    //TODO implement deserialization to List using generics
//    public static List<Card> cardsDeserializeResponseToList(Response response) {
//        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<Card>>() {
//        }.getType());
//    }

    public static <B> B deserializeResponse(Response response, Class<B> beanClass) {
        return new Gson().fromJson(response.asString().trim(), beanClass);
    }
}
