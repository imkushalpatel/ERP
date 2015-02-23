package com.sample.erp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Main extends ActionBarActivity {

    // String url = "http://192.168.1.32/ierp/index.php/mobile/getprojects/";
    Spinner spn, spn1, spn2, spn3;
    DatePicker dp;
    JSONParser jsonParser = new JSONParser();
    List<String> list, list1, list2, list3 = new ArrayList<String>();
    ArrayAdapter<String> ar, ar1, ar2, ar3;
    JSONObject obj;

    //Context ct=this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn = (Spinner) findViewById(R.id.spinner);
        spn1 = (Spinner) findViewById(R.id.spinner2);
        spn2 = (Spinner) findViewById(R.id.spinner3);
        spn3 = (Spinner) findViewById(R.id.spinner4);
        dp = (DatePicker) findViewById(R.id.datePicker);

        obj = jsonParser.getJSONFromUrl(this.getString(R.string.getdate));
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        Calendar date = Calendar.getInstance();
        try {
            date.setTime(df.parse(obj.getString("Date")));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dp.setMaxDate(date.getTime().getTime());
        date.add(Calendar.DATE, -15);
        dp.setMinDate(date.getTime().getTime());
        list = jsonParser.getJSONListFromUrl(this.getString(R.string.getprojects), "projects");
        ar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spn.setAdapter(ar);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                list1 = jsonParser.getJSONListFromUrl(getApplicationContext().getString(R.string.getheads) + spn.getSelectedItem().toString(), spn.getSelectedItem().toString());
                list2 = jsonParser.getJSONListFromUrl(getApplicationContext().getString(R.string.getcat) + spn.getSelectedItem().toString(), spn.getSelectedItem().toString());
                list3 = jsonParser.getJSONListFromUrl(getApplicationContext().getString(R.string.getloc) + spn.getSelectedItem().toString(), spn.getSelectedItem().toString());
                ar1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list1);
                ar2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list2);
                ar3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list3);
                spn1.setAdapter(ar1);
                spn2.setAdapter(ar2);
                spn3.setAdapter(ar3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
