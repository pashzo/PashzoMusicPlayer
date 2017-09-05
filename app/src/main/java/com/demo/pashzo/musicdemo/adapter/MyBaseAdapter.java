package com.demo.pashzo.musicdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.pashzo.musicdemo.R;

import java.util.List;

/**
 * Created by pashzo on 2017/3/28.
 */

public class MyBaseAdapter extends BaseAdapter {
    Context context;
    private List<String> list;

    public MyBaseAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.musiclist_layout,null);
            vh.tv = (TextView) convertView.findViewById(R.id.MusicName);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(getMusicName(list.get(position)));
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }

    public String getMusicName(String  path) {

        StringBuffer str = new StringBuffer(path);
        String filename = str.substring(str.lastIndexOf("/") + 1);
        String musicname = filename.substring(0, filename.lastIndexOf("."));
        return musicname;
    }
}
