package API.com.epam.mobilecloud;

import API.com.epam.mobilecloud.exceptions.ServerResponseNotAsExpected;
import API.com.epam.mobilecloud.queryParams.FindParams;
import API.com.epam.mobilecloud.queryParams.Methods;
import API.com.epam.mobilecloud.queryParams.TakeParams;
import beans.DesiredCapabilities;
import beans.Device;
import com.google.gson.Gson;
import enums.Platform;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static API.com.epam.mobilecloud.queryParams.Methods.*;
import static java.lang.String.format;
import static utils.DeserializeResponse.deserializeResponse;
import static utils.DeserializeResponse.deserializeResponseList;

public class API {
    static private final String API_PATH = "automation/api";
    static private final String BOOKING = "booking";
    static private final String DEVICE = "device";
    static private final String APP_INSTALL = "storage/install";

    static private final String AUTH = "Authorization";

    private URIBuilder uriBuilder;
    private HttpRequestBase request;
    private HttpResponse response;

    private final List<Header> headers = new ArrayList<>();
    private final List<NameValuePair> params = new ArrayList<>();
    private HttpEntity body;

    /**
     * Query Builder for Mobile cloud
     */
    public static class QueryBuilder {
        final API resource;

        private QueryBuilder(API resource) {
            this.resource = resource;
        }

        /**
         * Find available device
         * Search for fully operational, available device
         *
         * @param p - Platform (Android / iOS)
         * @return List of {@link beans.Device}
         */
        public List<Device> getAvailableDevices(Platform p) throws IOException, URISyntaxException, ServerResponseNotAsExpected {
            setPath(format("/%s/%s", DEVICE, p.platform));
            buildRequest(GET);
            executeRequest();
            expectStatusCode(HttpStatus.SC_OK);

            String jsonString = null;
            try {
                jsonString = new BasicResponseHandler().handleResponse(this.resource.response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return deserializeResponseList(jsonString, Device.class);
        }

        /**
         * Take available device in use by unique id
         * Assign user (by access token) to device if device is available
         *
         * @param udid - device unique id
         */
        public void takeDevice(String udid) throws URISyntaxException, IOException, ServerResponseNotAsExpected {
            setPath(format("/%s/%s", DEVICE, udid));
            buildRequest(POST);
            executeRequest();
            expectStatusCode(HttpStatus.SC_OK);
        }

        /**
         * Take available device in use by desiredCapabilities
         * Server is going to search device and assign user (by access token) to it
         *
         * @param device Device, with DesiredCapabilities (OS Android | iOS) Mandatory
         * @return udid of assigned Device
         */
        public String takeDevice(Device device) throws URISyntaxException, IOException, ServerResponseNotAsExpected {
            setPath(format("/%s", DEVICE));
            desiredCapabilities(device);
            buildRequest(POST);
            executeRequest();
            expectStatusCode(HttpStatus.SC_OK);

            String jsonString = new BasicResponseHandler().handleResponse(this.resource.response);
            return deserializeResponse(jsonString, DesiredCapabilities.class).getUdid();
        }

        /**
         * Stop using device
         * User (access token) stops using device
         *
         * @param udid of Device to finish usage
         */
        public void stopUsingDevice(String udid) throws IOException, ServerResponseNotAsExpected {
            setPath(format("/%s/%s", DEVICE, udid));
            try {
                buildRequest(DELETE);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            executeRequest();
            expectStatusCode(HttpStatus.SC_OK);
        }

        /**
         * Install application on the device
         * Requestor (access token) installs mobile app on the device.
         * Installation is allowed only if requestor is using device (owner) at the moment of current request.
         *
         * @param udid      of Device to install app
         * @param pathToApp - path to app
         */
        public void installApp(String udid, String pathToApp) throws URISyntaxException, IOException, ServerResponseNotAsExpected {
            File file = new File(pathToApp);
            addBinary(file);
            setPath(format("/%s/%s", APP_INSTALL, udid));
            buildRequest(POST);
            executeRequest();
            expectStatusCode(HttpStatus.SC_CREATED);
        }

        /**
         * API Query Builder for custom requests
         */
        public QueryBuilder buildRequest(Methods method) throws URISyntaxException {
            URI uri = this.resource.uriBuilder.addParameters(this.resource.params).build();
            switch (method) {
                case GET:
                    this.resource.request = new HttpGet(uri);
                    break;
                case POST:
                    this.resource.request = new HttpPost(uri);
                    if (this.resource.body != null) {
                        ((HttpPost) this.resource.request).setEntity(this.resource.body);
                    }
                    break;
                case DELETE:
                    this.resource.request = new HttpPost(uri);
                    break;
            }
            this.resource.request.setHeaders(this.resource.headers.toArray(new Header[0]));
            return this;
        }

        public QueryBuilder executeRequest() throws IOException {
            CloseableHttpClient httpClient = HttpClients.createMinimal();
            this.resource.response = httpClient.execute(this.resource.request);
            httpClient.close();
            return this;
        }

        public QueryBuilder expectStatusCode(int expectedSC) throws ServerResponseNotAsExpected {
            int actualSC = this.resource.response.getStatusLine().getStatusCode();
            if (actualSC != expectedSC) {
                String serverAnswer = this.resource.response.getStatusLine().getReasonPhrase();
                throw new ServerResponseNotAsExpected(expectedSC, actualSC, serverAnswer);
            }
            return this;
        }

        public QueryBuilder addParam(String paramName, String paramValue) {
            this.resource.params.add(new BasicNameValuePair(paramName, paramValue));
            return this;
        }

        public QueryBuilder addParam(FindParams paramName, String paramValue) {
            this.resource.params.add(new BasicNameValuePair(paramName.queryParam, paramValue));
            return this;
        }

        public QueryBuilder addParam(TakeParams paramName, String paramValue) {
            this.resource.params.add(new BasicNameValuePair(paramName.queryParam, paramValue));
            return this;
        }

        public QueryBuilder addParam(BasicNameValuePair param) {
            this.resource.params.add(param);
            return this;
        }

        public QueryBuilder addParams(List<BasicNameValuePair> params) {
            this.resource.params.addAll(params);
            return this;
        }

        public QueryBuilder addParams(Map<String, String> params) {
            List<NameValuePair> list = params.entrySet().stream()
                    .map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
            this.resource.params.addAll(list);
            return this;
        }

        public QueryBuilder setAPIBaseURL(String url) {
            this.resource.uriBuilder = new URIBuilder().setScheme("http").setHost(format("%s/%s", url, API_PATH));
            return this;
        }

        public QueryBuilder setPath(String path) {
            this.resource.uriBuilder.setPath(path);
            return this;
        }

        public QueryBuilder desiredCapabilities(Device device) {
            String json = new Gson().toJson(device);
            this.resource.body = new StringEntity(json, ContentType.APPLICATION_JSON);
            return this;
        }

        public QueryBuilder addBinary(File file) {
            this.resource.body = MultipartEntityBuilder.create().addBinaryBody("file", file).build();
            return this;
        }

        public QueryBuilder addHeader(String name, String value) {
            this.resource.headers.add(new BasicHeader(name, value));
            return this;
        }

        public QueryBuilder auth(String token) {
            this.resource.headers.add(new BasicHeader(AUTH, "Bearer " + token));
            return this;
        }
    }

    public static QueryBuilder queryBuilder() {
        API api = new API();
        return new QueryBuilder(api);
    }
}