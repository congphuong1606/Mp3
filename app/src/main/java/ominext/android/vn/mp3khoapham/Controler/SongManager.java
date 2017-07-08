package ominext.android.vn.mp3khoapham.Controler;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import ominext.android.vn.mp3khoapham.Model.Song;

/**
 * Created by MyPC on 08/07/2017.
 */

public class SongManager {
    private static ArrayList<Song> songs=new ArrayList<>();
    public static ArrayList<Song> getAllSong(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
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
        cursor.close();
        return null;
    }
    public ArrayList<Song> Songs(){
        return songs;
    }
}
