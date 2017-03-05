package com.nativenote.movielist_retrofit.client;

import android.content.Context;

import com.nativenote.movielist_retrofit.R;
import com.nativenote.movielist_retrofit.error.LoggingInterceptors;
import com.nativenote.movielist_retrofit.request_urls.ApiRequestUrls;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by IMTIAZ on 3/5/17.
 */
public class ApiClient {
    public static final int CONNECTION_TIME_OUT = 10;
    public static final int CONNECTION_READ_TIME_OUT = 30;

    public static ApiRequestUrls getApiInterface(String api_url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // RxJava adapter
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getClient())
//                .client(trustCert(GlobalApplication.getContext()))
                .build();

        return retrofit.create(ApiRequestUrls.class);
    }

    public static class LogJsonInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Response response = chain.proceed(request);
            String rawJson = response.body().string();
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        }
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_READ_TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(new LogJsonInterceptor())
//                .addInterceptor(getLogging())
//                .addInterceptor(new LoggingInterceptor())
                .build();

//        OkHttpClient client = new OkHttpClient();
//        client.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES);
//        client.setReadTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES);
//        client.interceptors().add(getLogging());
//        client.interceptors().add(new LoggingInterceptor());
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder()
//                        .addHeader("X-LYOMATON-KEY", StaticData.getToken())
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        });
//        return client;
    }

    public static OkHttpClient trustCert(Context context){
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            KeyStore ksTrust = KeyStore.getInstance("BKS");
            InputStream instream = context.getResources().openRawResource(R.raw.mykeystore);
            ksTrust.load(instream, "sample".toCharArray());
            // TrustManager decides which certificate authorities to use.
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ksTrust);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            okHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory());
//            okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
        }
        return okHttpClient;
    }

    private static HttpLoggingInterceptor getLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private static final Logger logger = Logger.getLogger(LoggingInterceptors.class.getName());

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long t1 = System.nanoTime();
            Request request = chain.request();
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            logger.info(String.format("Received response for %s in %.1fms%n%s",
                    request.url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }


}
