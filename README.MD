# OkHttpTransport - an implementation of Google Client Http Library's HttpTransport based on OkHttpClient

## Integration

You can use jitpack to integrate the library in your project. For instructions on how to integrate the library, click on the following badge

[![](https://jitpack.io/v/kekolab/okhttptransport.svg)](https://jitpack.io/#kekolab/okhttptransport)

## Usage

Build an OkHttpClient as you are accustomed to and create the HttpTransport like this:

```java
OkHttpClient client;
HttpTransport transport = new OkHttpTransport(client);
```

That's it!

## Caveats

Any implementation of `HttpTransport` is supposed to handle per-request timeouts by overriding two methods of `LowLevelHttpRequest` these two methods ([reference](https://www.javadoc.io/doc/com.google.http-client/google-http-client/latest/com/google/api/client/http/LowLevelHttpRequest.html)):

```java
public void setTimeout(int connectTimeout, int readTimeout) throws IOException;

public void setWriteTimeout(int writeTimeout) throws IOException;
```

OkHttpClient *does not* support per-request, but only per-client, timeout settings. So calling these two methods has no effect.
