package kitchenpos.acceptance.common.httpcommunication;

import java.util.Map;

public class ProductHttpCommunication {

    public static HttpCommunication create(final Map<String, Object> requestBody) {
        return HttpCommunication.request()
                .create("/api/v2/products", requestBody)
                .build();
    }

    public static HttpCommunication getProducts() {
        return HttpCommunication.request()
                .get("/api/products")
                .build();
    }
}
