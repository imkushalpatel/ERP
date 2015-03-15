package com.sample.erp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Data.DayWiseGroup;

/**
 * Created by Kushal on 12-03-2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<DayWiseGroup> _listDataGroup;

    public ExpandableListAdapter(Context context, ArrayList<DayWiseGroup> _listDataGroup) {
        this._context = context;
        this._listDataGroup = _listDataGroup;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataGroup.get(groupPosition).getChildren().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String ItemName = _listDataGroup.get(groupPosition).getChildren().get(childPosition).getMonthDay();
        final String ItemValue = _listDataGroup.get(groupPosition).getChildren().get(childPosition).getDayWork();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView itemname = (TextView) convertView
                .findViewById(R.id.txtitemname);
        TextView itemvalue = (TextView) convertView
                .findViewById(R.id.txtitemvalue);
        itemname.setText(ItemName);
        itemvalue.setText(ItemValue);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataGroup.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String GroupName = _listDataGroup.get(groupPosition).getMonthName();
        String GroupValue = _listDataGroup.get(groupPosition).getMonthTotal();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView groupname = (TextView) convertView
                .findViewById(R.id.txtgroupname);
        groupname.setTypeface(null, Typeface.BOLD);
        groupname.setText(GroupName);
        TextView groupvalue = (TextView) convertView
                .findViewById(R.id.txtgroupvalue);
        groupvalue.setTypeface(null, Typeface.BOLD);
        groupvalue.setText(GroupValue);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}