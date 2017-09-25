package ex.wangll.com.recyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.recyclerloadandrefresh.library.CommonRecyclerAdapter;
import com.recyclerloadandrefresh.library.MultiTypeSupport;
import com.recyclerloadandrefresh.library.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class testAdapter extends CommonRecyclerAdapter<String> {

    private Context mContext;

    public testAdapter(Context mContext,int resoureId, List<String> mdatas) {
        super(mContext,resoureId, mdatas);
        this.mContext = mContext;
    }

    public testAdapter(Context mContext, MultiTypeSupport<String> support, List<String> mdatas){
        super(mContext,support,mdatas);
        this.mContext = mContext;
    }

    @Override
    public void convert(ViewHolder holder, final String s, int position,int type) {
        /*if(position == 6){
            holder.setText(R.id.tv_content,"To be My girl friend?");
            holder.setOnClickListener(R.id.list_msg_btn_accept, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Accept your love!",Toast.LENGTH_SHORT).show();
                }
            });
            holder.setOnClickListener(R.id.list_msg_btn_refuse, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"sorry,Refuse your love!",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            holder.setText(R.id.list_item,s);
        }*/
        holder.setText(R.id.list_item,s);
        holder.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(view.getContext(),"You Click "+ s,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,Main2Activity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
