package com.example.harsh.mytube;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.Plus;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.VideoListResponse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 10/18/2015.
 */
public class PlayListConnector {


    private static YouTube youtube;
    //private YouTube.Search.List query;
    private YouTube.PlaylistItems.List playlist_list_query;
    private YouTube.PlaylistItems.List playlist_list_query2;
    public YouTube.Channels.List channel_list_query;
    private YouTube.Videos.List query2;
    // Your developer key goes here
    public static final String KEY = "AIzaSyBcR9h7h9IqYFesGuJgn793uyimLgVTciA";////"AIzaSyALrfDVeG_jqrx_G-r6pLClyphFjttTRXE";
    public static String token = "";

    PlayListConnector() {
        String scope_string = "oauth2:" + Scopes.PLUS_LOGIN + " " + Scopes.PLUS_ME + Scopes.EMAIL+" "+Scopes.PROFILE +" "+ YouTubeScopes.YOUTUBE;
        GetGoogleToken getGoogleToken = new GetGoogleToken(MainActivity.mActivity, MainActivity.account_name, scope_string);
        try {
            Object[] temp = null;
            token = getGoogleToken.doInBackground(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void channelConnector(Context context) throws IOException {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        channel_list_query = youtube.channels().list("contentDetails");

        channel_list_query.setOauthToken(token).setMine(true).setFields("item/contentDetails,nextPageToken,pageInfo").setKey(KEY);

    }

    public List<VideoItem> fetchChannels() {

        try {
            ChannelListResponse response = channel_list_query.execute();

            List<Channel> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (Channel result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                //item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());

                items.add(item);
            }
            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }


    public void playListConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            playlist_list_query = youtube.playlistItems().list("id,snippet");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
    }

    public List<VideoItem> fetchPlaylist() {

        try {
            PlaylistItemListResponse response = playlist_list_query.setPlaylistId("PLCDQlFY1pWHEnRgsUHufHyHbHggBvU_MP").setKey(KEY).execute();
            query2 = youtube.videos().list("statistics");
            query2.setKey(KEY);
            List<PlaylistItem> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for (PlaylistItem result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                //item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getSnippet().getResourceId().getVideoId().toString());

                query2.setId(result.getSnippet().getResourceId().getVideoId().toString());
                VideoListResponse videoResponse = query2.execute();

                item.setTotalViews(videoResponse.getItems().get(0).getStatistics().getViewCount().toString());

                try {

                    item.setCreationTime((result.getSnippet().getPublishedAt().toString().split("T"))[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                    item.setCreationTime(result.getSnippet().getPublishedAt().toString());
                    Log.d("YC", "From the date convertion exception");
                }

                //item.setId(result.getId().getVideoId());
                //item.setViews(result.getSnippet().getViews());
                items.add(item);
            }
            return items;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}






