package com.example.harsh.mytube;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by harsh on 10/17/2015.
 */
public class SavedPlaylistActivity  extends android.support.v4.app.Fragment{

    private ListView videosFound;
    //ImageButton imgButton;

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        videosFound = (ListView) view.findViewById(R.id.playlist_videos);

        handler = new Handler();

        searchPlaylist();
        //addClickListener();

        return view;

    }
    private List<VideoItem> searchResults;

    private void searchPlaylist(){
        new Thread(){

            public void run(){
                PlayListConnector yc = new PlayListConnector();
                yc.playListConnector(getActivity());
                searchResults = yc.fetchPlaylist();
                handler.post(new Runnable() {
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
        //   addClickListener();
    }

    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getContext(), R.layout.playlist_video_item, searchResults){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.playlist_video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail1);
                TextView title = (TextView)convertView.findViewById(R.id.video_title1);
                //TextView description = (TextView)convertView.findViewById(R.id.video_description);
                //TextView views=(TextView) convertView.findViewById(R.id.video_views);
//                ImageButton btn = (ImageButton)convertView.findViewById(R.id.favorite);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });

                final VideoItem searchResult = searchResults.get(position);


                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getContext(), PlayerActivity.class);
                        intent.putExtra("VIDEO_ID", searchResult.getId());
                        startActivity(intent);
                    }
                });

                TextView creationTime = (TextView)convertView.findViewById(R.id.video_creationTime1);
                TextView totalViews = (TextView)convertView.findViewById(R.id.video_totalViews1);
                creationTime.setText(searchResult.getCreationTime());
                totalViews.setText(searchResult.getTotalViews() + " views");


                Picasso.with(getContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                // description.setText(searchResult.getDescription());
                //views.setText(searchResult.getViews());
                return convertView;
            }

        };

        videosFound.setAdapter(adapter);
    }

    private void addClickListener(){
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Intent intent = new Intent(getContext(), PlayerActivity.class);
                intent.putExtra("VIDEO_ID", searchResults.get(pos).getId());
                startActivity(intent);
            }

        });
    }





}
