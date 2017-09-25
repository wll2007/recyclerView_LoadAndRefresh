package ex.wangll.com.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends Activity {
    private GridView view ;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        view = (GridView) findViewById(R.id.picture_gridview);
        int i = 10;
        while (i > 0) {
            data.add(String.valueOf(i--));
        }
        testGridAdapter adapter = new testGridAdapter(data,this,R.layout.gridview_item);
        /*BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int i) {
                return data.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = LayoutInflater.from(Main2Activity.this).inflate(R.layout.gridview_item,viewGroup,false);
                TextView  textView = (TextView)view.findViewById(R.id.gridview_item_txt);
                textView.setText(data.get(i));
                return view;
            }
        };*/
        view.setAdapter(adapter);
    }
}
