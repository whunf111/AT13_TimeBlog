package com.example.whunf.at13_timeblog.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.model.Artical;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by whunf on 2015/6/12 in PC
 */
public class MyListAdapter extends BaseAdapter {
    List<Artical> articalList;
    Context context;
    SimpleDateFormat simpleDataFormat;

    public MyListAdapter(List<Artical> articalList, Context context) {
        this.articalList = articalList;
        this.context = context;
        simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public int getCount() {
        return articalList.size();
    }

    @Override
    public Object getItem(int position) {
        return articalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.articalmain_list_item, null);

        MyViewHold mvh = new MyViewHold();

        mvh.devider = (TextView) view.findViewById(R.id.item_line);
        mvh.time = (TextView) view.findViewById(R.id.item_time);
        mvh.title = (TextView) view.findViewById(R.id.item_title);
        mvh.userIcon = (ImageView) view.findViewById(R.id.item_icon);
        mvh.replyCount = (TextView) view.findViewById(R.id.item_count);
        mvh.userName = (TextView) view.findViewById(R.id.item_user_name);

        Artical artical = articalList.get(position);
        long currentTime = System.currentTimeMillis();
        long publishTime = Long.parseLong(artical.getTime());
        if ((currentTime - publishTime) < 60 * 60 * 1000) {
            Drawable dividreDrawable = context.getResources().getDrawable(R.drawable
                    .article_divider_new);
            mvh.devider.setBackground(dividreDrawable);
        } else {
            Drawable dividreDrawable = context.getResources().getDrawable(R.drawable
                    .article_divider_1);
            mvh.devider.setBackground(dividreDrawable);
        }

        String str_Time = simpleDataFormat.format(publishTime);
        mvh.time.setText(str_Time);
        mvh.replyCount.setText(artical.getReply_count());
        mvh.title.setText(artical.getTitle());
        mvh.userName.setText(artical.getU_name());


        return view;
    }


    class MyViewHold {
        TextView replyCount;
        TextView time;
        TextView devider;
        TextView title;
        TextView userName;
        ImageView userIcon;
    }

}
