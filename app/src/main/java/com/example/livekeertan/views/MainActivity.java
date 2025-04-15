package com.example.livekeertan.views;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.livekeertan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private MediaPlayer mediaPlayer;
    private ExoPlayer exoPlayer;
    private Boolean audioPrepared = false;
    private Boolean audioPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        exoPlayer = new ExoPlayer.Builder(this).build();

        Log.i("Volume", String.valueOf(exoPlayer.getVolume()));

        MediaItem mediaItem = MediaItem.fromUri("https://live.sgpc.net:8442/;nocache=889869");
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        audioPrepared = true;

//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes
//                        .Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .build());

//        try {
////            Trial source
////            mediaPlayer.setDataSource("https://audio-edge-vqwx4.yyz.g.radiomast.io/ref-128k-mp3-stereo");
//            mediaPlayer.setDataSource("https://live.sgpc.net:8442/;nocache=889869");
//            mediaPlayer.prepareAsync();
//
////            mediaPlayer.setOnPreparedListener(mp -> {
////                audioPrepared = true;
////                binding.btnPlay.setEnabled(true);
////            });
//
//            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
//                Log.e("MediaPlayer", "Error occurred: what=" + what + ", extra=" + extra);
//                return false;
//            });
//        } catch (Exception e) {
//            Log.i("MediaPlayerError", e.getMessage() == null ? "" : e.getMessage() );
//        }

        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioPlaying){
                    exoPlayer.pause();
                    binding.btnPlay.setText("Play");
                }
                else{
                    exoPlayer.play();
                    binding.btnPlay.setText("Pause");
                }
                audioPlaying = !audioPlaying;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioPlaying) {
            exoPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }
}