package kekolab.okhttptransport;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;

import okhttp3.OkHttpClient;

public class OkHttpTransport extends HttpTransport {
    private final OkHttpClient client;

    private static final String[] SUPPORTED_METHODS = new String[] { 
        HttpMethods.DELETE, HttpMethods.GET, HttpMethods.HEAD, HttpMethods.PATCH, HttpMethods.POST, HttpMethods.PUT
    };

    static {
        Arrays.sort(SUPPORTED_METHODS);
    }

    public OkHttpTransport(OkHttpClient client) {
        this.client = Objects.requireNonNull(client);
    }

    @Override
    protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new OkHttpLowLevelHttpRequest(client, method, url);
    }

    @Override
    public boolean supportsMethod(String method) throws IOException {
        return Arrays.binarySearch(SUPPORTED_METHODS, method) >= 0;
    }
}
