package kekolab.okhttptransport;

import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.http.LowLevelHttpResponse;

import okhttp3.Response;

public class OkHttpLowLevelHttpResponse extends LowLevelHttpResponse {
    private final Response response;

    public OkHttpLowLevelHttpResponse(Response response) {
        this.response = response;
    }

    @Override
    public InputStream getContent() throws IOException {
        return response.body().byteStream();
    }

    @Override
    public String getContentEncoding() throws IOException {
        return response.header("Content-Encoding", null);
    }

    @Override
    public long getContentLength() throws IOException {
        return Long.parseLong(response.header("Content-Length", "0"));
    }

    @Override
    public String getContentType() throws IOException {
        return response.header("Content-Type", null);
    }

    @Override
    public String getStatusLine() throws IOException {
        final int code = response.code();
        final String message = response.message();
        StringBuffer statusLine = new StringBuffer();
        if (code > 0)
            statusLine.append(code);
        
        if (message != null) {
            if (statusLine.length() > 0)
                statusLine.append(" ");
            statusLine.append(message);
        }
        return statusLine.toString();
    }

    @Override
    public int getStatusCode() throws IOException {
        return response.code();
    }

    @Override
    public String getReasonPhrase() throws IOException {
        return response.message();
    }

    @Override
    public int getHeaderCount() throws IOException {
        return response.headers().size();
    }

    @Override
    public String getHeaderName(int index) throws IOException {
        return response.headers().name(index);
    }

    @Override
    public String getHeaderValue(int index) throws IOException {
        return response.headers().value(index);
    }
}