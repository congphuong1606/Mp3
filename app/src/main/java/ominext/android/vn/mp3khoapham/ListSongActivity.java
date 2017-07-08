package ominext.android.vn.mp3khoapham;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ominext.android.vn.mp3khoapham.Adapter.SongAdapter;
import ominext.android.vn.mp3khoapham.Controler.SongManager;
import ominext.android.vn.mp3khoapham.Model.Song;

public class ListSongActivity extends AppCompatActivity {
    ArrayList<Song> songs=new ArrayList<>();
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private RecyclerView.LayoutManager layoutManager;
    private SongAdapter songAdapter;
    SongManager songManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(ListSongActivity.this, 1);
        recycleview.setLayoutManager(layoutManager);
        recycleview.setHasFixedSize(true);
        songAdapter = new SongAdapter(ListSongActivity.this, R.layout.row_song, songs);
        recycleview.setAdapter(songAdapter);
       loadAudio();
        Toast.makeText(this, songs.get(1).getTitle(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, songs.size() + "", Toast.LENGTH_SHORT).show();
    }
    public void loadAudio() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            songs.add(new Song(data, title, album, artist));
        }
        songAdapter.notifyDataSetChanged();
    }
}
