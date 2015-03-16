package com.sample.erp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Main extends ActionBarActivity {

    Spinner spn1, spn2, spn3;
    AutoCompleteTextView spn;
    DatePicker dp;
    Button btn, btn1, btn2;
    EditText et, et1;
    JSONParser jsonParser = new JSONParser();
    List<String> list, list1, list2, list3 = new ArrayList<String>();
    ArrayAdapter<String> ar, ar1, ar2, ar3;
    JSONObject obj;

    //Context ct=this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        spn1 = (Spinner) findViewById(R.id.spinner2);
        spn2 = (Spinner) findViewById(R.id.spinner3);
        spn3 = (Spinner) findViewById(R.id.spinner4);
        dp = (DatePicker) findViewById(R.id.datePicker);
        btn = (Button) findViewById(R.id.button);
        btn1 = (Button) findViewById(R.id.button2);
        btn2 = (Button) findViewById(R.id.btndelay);
        et = (EditText) findViewById(R.id.editText);
        et1 = (EditText) findViewById(R.id.editText2);

        list = jsonParser.getJSONListFromUrl(this.getString(R.string.getprojects), "projects");
        ar = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        spn.setAdapter(ar);
        obj = jsonParser.getJSONFromUrl(this.getString(R.string.getdate));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar date = Calendar.getInstance();
        try {
            date.setTime(df.parse(obj.getString("Date")));

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // date.add(Calendar.MONTH,1);
        dp.setMaxDate(date.getTime().getTime());
        date.add(Calendar.DATE, -15);
        dp.setMinDate(date.getTime().getTime());
        //date.add(Calendar.DATE, 15);
        spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list1 = jsonParser.getJSONListFromUrl(getApplicationContext().getString(R.string.getheads) + spn.getText().toString(), spn.getText().toString());
                list2 = jsonParser.getJSONListFromUrl(getApplicationContext().getString(R.string.getcat) + spn.getText().toString(), spn.getText().toString());
                list3 = jsonParser.getJSONListFromUrl(getApplicationContext().getString(R.string.getloc) + spn.getText().toString(), spn.getText().toString());
                ar1 = new ArrayAdapter<String>(Main.this, android.R.layout.simple_spinner_dropdown_item, list1);
                ar2 = new ArrayAdapter<String>(Main.this, android.R.layout.simple_spinner_dropdown_item, list2);
                ar3 = new ArrayAdapter<String>(Main.this, android.R.layout.simple_spinner_dropdown_item, list3);
                spn1.setAdapter(ar1);
                spn2.setAdapter(ar2);
                spn3.setAdapter(ar3);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, DayWise.class);
                startActivity(i);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dt = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
                Log.i("Date", dt);
                JSONObject ob = jsonParser.getJSONFromUrl(getApplicationContext().getString(R.string.insertdata) +
                        spn.getText().toString() + '/' +
                        spn1.getSelectedItem().toString() + '/' +
                        spn2.getSelectedItem().toString() + '/' +
                        spn3.getSelectedItem().toString() + '/' +
                        dt + '/' +
                        et.getText() + '/' +
                        et1.getText());
                String status = null;
                try {
                    status = ob.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals("ok")) {
                    Toast.makeText(getApplicationContext(), "Data Saved Successfully.....", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Operation Failed", Toast.LENGTH_LONG).show();
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
