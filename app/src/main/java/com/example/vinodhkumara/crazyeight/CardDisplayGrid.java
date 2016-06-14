package com.example.vinodhkumara.crazyeight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vinodh.kumara on 8/12/2015.
 */
public class CardDisplayGrid extends BaseAdapter {

    Context mContext = null;
    CardDisplayGrid mAdapter = this;

    public ArrayList<Integer> mPlayerCardIds = new ArrayList<Integer>();
    // Constructor
    public CardDisplayGrid(Context c,ArrayList<Integer> mImageIds) {
        mContext = c;
        mPlayerCardIds = mImageIds;
    }

    @Override
    public int getCount() {
        return mPlayerCardIds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mPlayerCardIds.get(position);
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

        imageView.setImageResource(mPlayerCardIds.get(position));

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
