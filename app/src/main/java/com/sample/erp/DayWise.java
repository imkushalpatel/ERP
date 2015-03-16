package com.sample.erp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.ExpandableListAdapter;
import Data.DayWiseGroup;
import Data.DayWiseList;


public class DayWise extends ActionBarActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<DayWiseGroup> listDataHeader;

    JSONObject object;
    JSONParser jsonParser = new JSONParser();
    TextView txtdead, txtrrr, txttotal, txt1, txt2, txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_wise);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        txtdead = (TextView) findViewById(R.id.txtDead);
        txtrrr = (TextView) findViewById(R.id.txtRRR);
        txttotal = (TextView) findViewById(R.id.txtTotal);
        txt1 = (TextView) findViewById(R.id.textView10);
        txt2 = (TextView) findViewById(R.id.textView9);
        txt3 = (TextView) findViewById(R.id.textView8);

        final AutoCompleteTextView atxt = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);

        List<String> list = new ArrayList<String>();
        list = jsonParser.getJSONListFromUrl(this.getString(R.string.getprojects), "projects");
        ArrayAdapter<String> ar = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        atxt.setAdapter(ar);
        atxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prepareListData(atxt.getText().toString())) {
                    listAdapter = new ExpandableListAdapter(DayWise.this, listDataHeader);

                    expListView.setAdapter(listAdapter);
                    expListView.setVisibility(View.VISIBLE);
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);

                } else {
                    expListView.setVisibility(View.INVISIBLE);
                    txt1.setVisibility(View.INVISIBLE);
                    txt2.setVisibility(View.INVISIBLE);
                    txt3.setVisibility(View.INVISIBLE);
                    txttotal.setText("");
                    txtdead.setText("");
                    txtrrr.setText("");

                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_wise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean prepareListData(String param) {
        listDataHeader = new ArrayList<DayWiseGroup>();

        object = jsonParser.getJSONFromUrl("http://192.168.2.33/ierp/index.php/mobile/getdata/" + param);
        try {
            if (object.getBoolean("status")) {
                JSONArray jsonArray = object.getJSONArray("Overheads");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    JSONArray jsonArray1 = object1.getJSONArray("list");
                    ArrayList<DayWiseList> listDataChild = new ArrayList<DayWiseList>();
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject object2 = jsonArray1.getJSONObject(j);
                        listDataChild.add(new DayWiseList(object2.getString("date"), object2.getString("work")));
                    }
                    listDataHeader.add(new DayWiseGroup(object1.getString("month"), object1.getString("sum"), listDataChild));
                }
                ArrayList<DayWiseList> listDataChild = new ArrayList<DayWiseList>();
                listDataChild.add(new DayWiseList("Total", object.getString("totaldone")));
                listDataHeader.add(new DayWiseGroup("Total", object.getString("totaldone"), listDataChild));
                txtdead.setText(object.getString("deadline"));
                txtrrr.setText(object.getString("rrr"));
                txttotal.setText(object.getString("totalreq"));
                return true;
            } else {
                Toast.makeText(DayWise.this, "No Data Available", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* listDataHeader = new ArrayList<DayWiseGroup>();
        listDataChild = new ArrayList<DayWiseList>();

       listDataChild.add(new DayWiseList("1","1"));
        listDataChild.add(new DayWiseList("2","2"));
        listDataChild.add(new DayWiseList("3","3"));
        listDataHeader.add(new DayWiseGroup("1","1",listDataChild));
        listDataHeader.add(new DayWiseGroup("2","2",listDataChild));
        listDataHeader.add(new DayWiseGroup("3","3",listDataChild));*/
        //listDataChild.clear();


        return false;
    }
}
