package weatherapi.http;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class QueryParser {  

    public Map<String, String> parse(String query) {
        Map<String, String> queryParams = new HashMap<>();

        if (query == null || query.isBlank()) {
            return queryParams;
        }

        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);

            String key = decode(keyValue[0]);
            String value = keyValue.length == 2
                    ? decode(keyValue[1])
                    : "";

            queryParams.put(key, value);
        }
        return queryParams;
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
