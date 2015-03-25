package Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.erp.R;

import java.util.ArrayList;

import Data.DelayList;

/**
 * Created by kushal on 16/3/15.
 */
public class DelayListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DelayList> delayListArrayAdapter;

    public DelayListAdapter(Context context, ArrayList<DelayList> delayListArrayAdapter) {
        this.context = context;
        this.delayListArrayAdapter = delayListArrayAdapter;
    }


    @Override
    public int getCount() {
        return delayListArrayAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return delayListArrayAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return delayListArrayAdapter.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.delay_list, null);
        }

        TextView groupname = (TextView) convertView
                .findViewById(R.id.textView11);
        groupname.setTypeface(null, Typeface.BOLD);
        groupname.setText(delayListArrayAdapter.get(position).getName());
        TextView groupvalue = (TextView) convertView
                .findViewById(R.id.textView12);
        groupvalue.setTypeface(null, Typeface.BOLD);
        groupvalue.setText(delayListArrayAdapter.get(position).getValue());


        return convertView;
    }
}
