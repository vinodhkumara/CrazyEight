package com.example.vinodhkumara.crazyeight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by vinodh.kumara on 6/16/2016.
 */
public class ComputerCardList extends BaseAdapter {
    Context mContext = null;

    public ComputerCardList(Context c) {
        mContext = c;
    }
    @Override
    public int getCount() {

        return 5;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(R.drawable.computer_card);

        /*imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg1) {
                // list.remove(position);
                Toast.makeText(mContext,"Image selected",Toast.LENGTH_LONG);
                mPlayerCardIds.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });*/

        return imageView;
    }
}
