package com.example.harsh.mytube;

import android.content.Context;

import com.google.android.gms.common.Scopes;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;

import java.io.IOException;

/**
 * Created by harsh on 10/18/2015.
 */
public class UpdatePlaylist {

    public static String token = "";
    UpdatePlaylist(){
        token = MainActivity.oauthToken;
    }

    private static YouTube youtube;

    public void InsertIntoPlaylist(Context context, String videoId) {

        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.

        try {
            // Authorize the request.

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(new NetHttpTransport(),
                    new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest hr) throws IOException {
                }
            }).setApplicationName(context.getString(R.string.app_name)).build();

            // Create a new, private playlist in the authorized user's channel.

            // If a valid playlist was created, add a video to that playlist.
            insertPlaylistItem("PLCDQlFY1pWHEnRgsUHufHyHbHggBvU_MP", videoId);

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private static String insertPlaylistItem(String playlistId, String videoId) throws IOException {

        // Define a resourceId that identifies the video being added to the
        // playlist.
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);

        // Set fields included in the playlistItem resource's "snippet" part.
        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setTitle("First video in the test playlist");
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);

        // Create the playlistItem resource and set its snippet to the
        // object created above.
        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(playlistItemSnippet);

        // Call the API to add the playlist item to the specified playlist.
        // In the API call, the first argument identifies the resource parts
        // that the API response should contain, and the second argument is
        // the playlist item being inserted.
        YouTube.PlaylistItems.Insert playlistItemsInsertCommand = youtube.playlistItems().insert("snippet,contentDetails", playlistItem).setOauthToken(token);
        PlaylistItem returnedPlaylistItem = playlistItemsInsertCommand.execute();

        // Print data from the API response and return the new playlist
        // item's unique playlistItem ID.
        return returnedPlaylistItem.getId();
    }

}
