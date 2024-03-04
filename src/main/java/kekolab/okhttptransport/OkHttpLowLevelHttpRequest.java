package kekolab.okhttptransport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpLowLevelHttpRequest extends LowLevelHttpRequest {
    private final Request.Builder builder;
    private final OkHttpClient client;
    private final String method;

    protected OkHttpLowLevelHttpRequest(OkHttpClient client, String method, String url) {
        this.client = Objects.requireNonNull(client);
        this.method = Objects.requireNonNull(method);
        this.builder = new Request.Builder()
                .url(url);
    }

    @Override
    public void addHeader(String name, String value) throws IOException {
        builder.addHeader(name, value);
    }

    @Override
    public LowLevelHttpResponse execute() throws IOException {
        if (getStreamingContent() == null) {
            builder.method(method, null);
        } else {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                getStreamingContent().writeTo(baos);
                baos.flush();
                builder.method(method, RequestBody.create(baos.toByteArray()));
            }
        }
        final Request request = builder.build();
        return new OkHttpLowLevelHttpResponse(getClient().newCall(request).execute());
    }

    private OkHttpClient getClient() {
        return client;
    }

    /**
     * The implementation does nothing, but only calls the super method. <br />
     * <br />
     * OkHttp client does not allow to set a connect or read timeout per request,
     * but
     * only per client.
     */
    @Override
    public void setTimeout(int connectTimeout, int readTimeout) throws IOException {
        super.setTimeout(connectTimeout, readTimeout);
    }

    /**
     * The implementation does nothing, but only calls the super method. <br />
     * <br />
     * OkHttp client does not allow to set a write timeout per request, but
     * only per client.
     */
    @Override
    public void setWriteTimeout(int writeTimeout) throws IOException {
        super.setWriteTimeout(writeTimeout);
    }
}