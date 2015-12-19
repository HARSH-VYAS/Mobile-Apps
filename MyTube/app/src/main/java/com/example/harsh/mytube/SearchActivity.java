package com.example.harsh.mytube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;


public class SearchActivity extends android.support.v4.app.Fragment {

    private EditText searchInput;
    private ListView videosFound;
    ImageButton favoriteButton;

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_search, container, false);
        //setContentView(R.layout.activity_search);
        EditText searchInput = (EditText) view.findViewById(R.id.search_input);
        videosFound = (ListView) view.findViewById(R.id.videos_found);


        handler = new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                else if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }

        });

        return view;

    }
    private List<VideoItem> searchResults;

    private void searchOnYoutube(final String keywords){
        new Thread(){

            public void run(){
                YoutubeConnector yc = new YoutubeConnector(getActivity());
                searchResults = yc.search(keywords);
                handler.post(new Runnable() {
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getContext(), R.layout.video_item, searchResults){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                //TextView description = (TextView)convertView.findViewById(R.id.video_description);


                TextView creationTime = (TextView)convertView.findViewById(R.id.video_creationTime);
                TextView totalViews = (TextView)convertView.findViewById(R.id.video_totalViews);

                favoriteButton= (ImageButton) convertView.findViewById(R.id.favorite);
                final VideoItem searchResult = searchResults.get(position);
                favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View button) {
                        button.setSelected(!button.isSelected());
                        if (button.isSelected()) {
                            System.out.println("***//**** Added to favorite!!!");

                            new Thread(){
                                public void run() {
                                    UpdatePlaylist pud = new UpdatePlaylist();
                                    pud.InsertIntoPlaylist(getContext(), searchResult.getId());
                                }
                            }.start();

                        } else {
                            System.out.println("***//**** Removed from favorite!!!");
                        }
                    }
                });

                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getContext(), PlayerActivity.class);
                        intent.putExtra("VIDEO_ID", searchResult.getId());
                        startActivity(intent);
                    }
                });
                Picasso.with(getContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());

                creationTime.setText(searchResult.getCreationTime());
                totalViews.setText(searchResult.getTotalViews() + " views");
                return convertView;
            }

        };

        videosFound.setAdapter(adapter);
    }


}
