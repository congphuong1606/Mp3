package ominext.android.vn.mp3khoapham;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.mp3khoapham.Model.Song;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_titile)
    TextView tvTitile;
    @BindView(R.id.tv_time_song)
    TextView tvTimeSong;
    @BindView(R.id.tv_time_total)
    TextView tvTimeTotal;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.imb_back)
    ImageButton imbBack;
    @BindView(R.id.imb_play)
    ImageButton imbPlay;
    @BindView(R.id.imb_pause)
    ImageButton imbPause;
    @BindView(R.id.imb_next)
    ImageButton imbNext;
    @BindView(R.id.imb_list)
    ImageButton imbList;
    int i = 0;
    int cout = 0;
    MediaPlayer mediaPlayer;
    @BindView(R.id.imv_album)
    ImageView imvAlbum;
    Animation animation;
    private ArrayList<Song> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        animation = AnimationUtils.loadAnimation(this, R.anim.dick_rotate);
        loadAudio();
        playSong();
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


    }



    private void setTimeAndSeekbar() {
        SimpleDateFormat housFomart = new SimpleDateFormat("mm:ss");
        tvTimeTotal.setText(housFomart.format(mediaPlayer.getDuration()));
        seekbar.setMax(mediaPlayer.getDuration());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                seekbar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void playSong() {
        tvTitile.setText(songs.get(i).getTitle().toString());
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songs.get(i).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
        setTimeAndSeekbar();
    }

    public void loadAudio() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);
        cursor.moveToFirst();
        cout = cursor.getCount() - 1;
        for (int i = 0; i <= cout; i++) {
            cursor.moveToPosition(i);
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            songs.add(new Song(data, title, album, artist));
        }
    }

    @OnClick({R.id.imb_back, R.id.imb_list, R.id.imb_play, R.id.imb_pause, R.id.imb_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imb_list:
                Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
                startActivity(intent);
                break;
            case R.id.imb_back:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    if (i > 0) {
                        i--;
                        playSong();
                        mediaPlayer.start();
                    } else if (i == 0) {
                        i = cout;
                        playSong();
                        mediaPlayer.start();
                    }
                    imvAlbum.startAnimation(animation);
                } else {
                    mediaPlayer.stop();
                    imbPause.setVisibility(View.GONE);
                    imbPlay.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        i = cout;
                    } else {
                        i--;
                    }
                    playSong();
                }


                break;
            case R.id.imb_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    imbPause.setVisibility(View.VISIBLE);
                    imbPlay.setVisibility(View.GONE);
                    imvAlbum.startAnimation(animation);
                }

                break;
            case R.id.imb_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imbPause.setVisibility(View.GONE);
                    imbPlay.setVisibility(View.VISIBLE);
                    imvAlbum.getAnimation().cancel();
                    imvAlbum.clearAnimation();
                }
                mediaPlayer.pause();

                break;
            case R.id.imb_next:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    if (i == cout) {
                        i = 0;
                        playSong();
                        mediaPlayer.start();
                    } else if (i < cout) {
                        i++;
                        playSong();
                        mediaPlayer.start();

                    }
                    imvAlbum.startAnimation(animation);
                } else {
                    mediaPlayer.stop();
                    imbPause.setVisibility(View.GONE);
                    imbPlay.setVisibility(View.VISIBLE);
                    if (i == cout) {
                        i = 0;
                    } else {
                        i++;
                    }
                    playSong();
                }

                break;
        }
    }


}
