package com.nativenote.movielist_retrofit.request_urls;

import com.nativenote.movielist_retrofit.model.RSS;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by IMTIAZ on 3/5/17.
 */

public interface ApiRequestUrls {
    @GET("news/rss.xml")
    Call<RSS> getRSSFeed();
}
