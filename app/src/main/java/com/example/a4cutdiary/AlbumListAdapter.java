
package com.example.a4cutdiary;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Album> albumList;

    public AlbumListAdapter(Context context, int layout, ArrayList<Album> AlbumList) {
        this.context = context;
        this.layout = layout;
        this.albumList = AlbumList;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtDiary;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtDiary = (TextView) row.findViewById(R.id.textView);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Album album = albumList.get(position);

        holder.txtDiary.setText(album.getDiary());

        byte[] AlbumImage = album.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(AlbumImage, 0, AlbumImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}


