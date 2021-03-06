package com.appbonus.android.ui.fragments.navigation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbonus.android.R;
import com.nostra13.universalimageloader.core.ImageLoader;


public class NavigationDrawerAdapter extends BaseAdapter {

    private Context context;
    private int mCount;

    public NavigationDrawerAdapter(Context context, int count) {
        super();
        this.context = context;
        this.mCount = count;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        switch (position) {
            case 0 :
                viewHolder.title.setText(R.string.offers);
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.apps_icon, viewHolder.icon);
                break;
            case 1 :
                viewHolder.title.setText(R.string.balance);
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.money_icon, viewHolder.icon);
                break;
            case 2 :
                viewHolder.title.setText(R.string.friends);
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.friends_icon, viewHolder.icon);
                break;
            case 3 :
                viewHolder.title.setText(R.string.profile);
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.profile_icon, viewHolder.icon);
                break;
            case 4 :
                viewHolder.title.setText(R.string.faq);
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_faq, viewHolder.icon);
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
        ImageView icon;
    }
}
