package com.example.harsh.mytube;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 10/16/2015.
 */
public class YoutubeConnector {

    private static YouTube youtube;
    private YouTube.Search.List searchQuery;
    private YouTube.Videos.List videoItemQuery;
    private YouTube.PlaylistItems.List playlist_list_query;

    // Your developer key goes here
    public static final String KEY = "AIzaSyBcR9h7h9IqYFesGuJgn793uyimLgVTciA";//"AIzaSyALrfDVeG_jqrx_G-r6pLClyphFjttTRXE";

    public YoutubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            searchQuery = youtube.search().list("id,snippet");
            searchQuery.setKey(KEY);
            searchQuery.setType("video");
            searchQuery.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url,snippet/publishedAt)");
            searchQuery.setMaxResults((long) 20);

            videoItemQuery =  youtube.videos().list("statistics");
            videoItemQuery.setKey(KEY);



        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }


    }

    public List<VideoItem> search(String keywords) {
        searchQuery.setQ(keywords);
        try {
            SearchListResponse response = searchQuery.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());

                videoItemQuery.setId(result.getId().getVideoId().toString());
                System.out.println(videoItemQuery);
                VideoListResponse videoResponse = videoItemQuery.execute();
                System.out.println("****************" + videoResponse);
                item.setTotalViews(videoResponse.getItems().get(0).getStatistics().getViewCount().toString());

                try {
                    item.setCreationTime((result.getSnippet().getPublishedAt().toString().split("T"))[0]);

                }
                catch(Exception e) {
                    e.printStackTrace();
                    item.setCreationTime(result.getSnippet().getPublishedAt().toString());
                    Log.d("YC", "From the date convertion exception");
                }

                //item.setViews(result.getSnippet().getViews());
                items.add(item);
            }
            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

    public YouTube getYouTubeConnectorObject()
    {
        return this.youtube;
    }
}
