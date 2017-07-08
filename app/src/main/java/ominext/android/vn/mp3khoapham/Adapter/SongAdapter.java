package ominext.android.vn.mp3khoapham.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.mp3khoapham.MainActivity;
import ominext.android.vn.mp3khoapham.Model.Song;
import ominext.android.vn.mp3khoapham.R;

/**
 * Created by MyPC on 08/07/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHoder> {
    ArrayList<Song> songs = new ArrayList<>();
    Context context;


    public SongAdapter(Context baseContext, int row_layout, ArrayList<Song> songs) {
        this.songs = songs;
    }


    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_song, parent, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoder holder, int position) {
        final Song song = songs.get(position);
        holder.tvTitile.setText(song.getTitle().toString());


    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView tvTitile;

        public MyViewHoder(final View itemView) {
            super(itemView);
            tvTitile = (TextView) itemView.findViewById(R.id.tv_song_title_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    String aaa=getPosition()+"";
                    Intent intent=new Intent(context, MainActivity.class);
                    intent.putExtra("position",aaa);
                    context.startActivity(intent);
                }
            });

        }
    }
}