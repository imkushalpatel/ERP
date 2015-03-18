package Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import Data.DelayList;

/**
 * Created by kushal on 16/3/15.
 */
public class DelayListAdapter extends BaseAdapter {
    Context context;
    ArrayAdapter<DelayList> delayListArrayAdapter;

    public DelayListAdapter(Context context, ArrayAdapter<DelayList> delayListArrayAdapter) {
        this.context = context;
        this.delayListArrayAdapter = delayListArrayAdapter;
    }

    @Override
    public int getCount() {
        return delayListArrayAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return delayListArrayAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return delayListArrayAdapter.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
