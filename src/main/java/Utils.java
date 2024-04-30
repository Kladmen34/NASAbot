import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class Utils {
    public static String getURL(String nasaURL) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        ObjectMapper mapper = new ObjectMapper();

        HttpGet httpGet = new HttpGet(nasaURL);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class);
            return answer.url;
        } catch (Exception e){
            System.out.println("Сервер НАСА недоступен");
        }

        return "";
    }
}
