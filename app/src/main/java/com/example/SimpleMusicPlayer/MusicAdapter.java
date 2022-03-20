package com.example.SimpleMusicPlayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {
    Music[] musicJgh;
    Context ctx;

    public MusicAdapter(Context ctx, Music[] musicArrayJgh) {
        this.ctx = ctx;
        this.musicJgh = musicArrayJgh;

    }

    @Override
    public int getCount() {
        return musicJgh.length;
    }

    @Override
    public Object getItem(int position) {
        return musicJgh[position];
    }

    @Override
    public long getItemId(int position) {
        return musicJgh[position].getId();
    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflaterJgh = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") final View musicItem = inflaterJgh.inflate(R.layout.music_list_item,viewGroup,false);

        // situar el titulo del archivo
        TextView title = (TextView) musicItem.findViewById(R.id.musicTitle);
        title.setText(musicJgh[i].getTitleJgh());
        return musicItem;
    }
}
