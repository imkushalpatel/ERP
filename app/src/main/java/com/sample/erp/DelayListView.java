package com.sample.erp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapter.DelayListAdapter;
import Data.DelayList;


public class DelayListView extends ActionBarActivity {
    final Calendar c = Calendar.getInstance();
    ArrayList<DelayList> delayLists;
    DelayListAdapter delayListAdapter;
    Boolean proj;
    TextView textView;
    AutoCompleteTextView completeTextView;
    JSONParser jsonParser = new JSONParser();
    JSONObject object;
    ListView listView;
    EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_list);
        textView = (TextView) findViewById(R.id.textView13);
        completeTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
        listView = (ListView) findViewById(R.id.listView);
        et1 = (EditText) findViewById(R.id.editText3);
        et2 = (EditText) findViewById(R.id.editText4);

        Intent i = this.getIntent();
        proj = i.getExtras().getBoolean("Proj");
        if (proj) {
            textView.setText("Project:");
            List<String> list = new ArrayList<String>();
            list = jsonParser.getJSONListFromUrl(this.getString(R.string.getprojects), "projects");
            list.add("ALL");
            ArrayAdapter<String> ar = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            completeTextView.setAdapter(ar);
        } else {
            textView.setText("Category:");
            List<String> list = new ArrayList<String>();
            list = jsonParser.getJSONListFromUrl(this.getString(R.string.getissues), "issues");
            list.add("ALL");
            ArrayAdapter<String> ar = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            completeTextView.setAdapter(ar);
        }


        et1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });


        et2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });


        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(DelayListView.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                et1.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });


        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(DelayListView.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                et2.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
        completeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createdata(completeTextView.getText().toString(), '/' + et1.getText().toString() + '/' + et2.getText().toString());
                delayListAdapter = new DelayListAdapter(DelayListView.this, delayLists);
                listView.setAdapter(delayListAdapter);
            }
        });

        //Log.i("bool",proj.toString());
    }

    public boolean createdata(String param, String date) {
        delayLists = new ArrayList<DelayList>();

        if (proj) {
            try {
                object = jsonParser.getJSONFromUrl("http://192.168.2.33/ierp/index.php/mobile/getdelaylistproj/" + param + date);
                Log.i("object", object.toString());
                JSONArray jsonArray = object.getJSONArray(param);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    delayLists.add(new DelayList(object1.getString("cat"), object1.getString("values")));
                }
                delayLists.add(new DelayList("Null", object.getString("nocat")));
                delayLists.add(new DelayList("Total", object.getString("total")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                object = jsonParser.getJSONFromUrl("http://192.168.2.33/ierp/index.php/mobile/getdelaylistcat/" + param);
                Log.i("object", object.toString());
                JSONArray jsonArray = object.getJSONArray(param);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    delayLists.add(new DelayList(object1.getString("proj"), object1.getString("values")));

                }
                delayLists.add(new DelayList("Total", object.getString("total")));

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return false;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delay_list, menu);
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
}
