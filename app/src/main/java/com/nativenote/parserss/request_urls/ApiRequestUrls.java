package com.nativenote.parserss.request_urls;

import com.nativenote.parserss.model.RSS;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by IMTIAZ on 3/5/17.
 */

public interface ApiRequestUrls {
    @GET("news/rss.xml")
    Call<RSS> getRSSFeed();
}
