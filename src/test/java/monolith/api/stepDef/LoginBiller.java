package monolith.api.stepDef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;

public class LoginBiller {
    private static final String APPLICATION_JSON = "application/json";
    private static final String ENVIRONMENT_LINK = "http://localhost:8080";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpResponse response;
    @When("Biller login api is called")
    public void biller_login_api_is_called() throws Throwable{
        HttpPost request = new HttpPost(ENVIRONMENT_LINK + "/oauth/token");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "dip@a.com"));
        params.add(new BasicNameValuePair("password", "secret"));
        request.setEntity(new UrlEncodedFormEntity(params));


        request.addHeader("accept", APPLICATION_JSON);
        HttpResponse httpResponse = httpClient.execute(request);
        response = httpResponse;

        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(200));
    }
    @Then("Api must return a token")
    public void api_must_return_a_token() throws Throwable{

        String responseString = convertResponseToString(response);

        System.out.println(responseString);
        JsonReader jsonReader = Json.createReader(new StringReader(responseString));
        JsonObject jsonResponse = jsonReader.readObject();

        System.out.println(jsonResponse.getString("access_token").length());
        assertThat(jsonResponse,hasKey("access_token"));
        assertThat(jsonResponse,hasKey("token_type"));
        assertThat(jsonResponse,hasKey("expires_in"));

        assertThat(jsonResponse.getString("access_token").length(),equalTo(283));
        assertThat(jsonResponse.getString("token_type"),equalTo("Bearer"));
        assertThat(jsonResponse.getInt("expires_in"),equalTo(3600));

    }

    String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream;
        responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
