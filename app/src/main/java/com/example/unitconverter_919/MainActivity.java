package com.example.unitconverter_919;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private Context mContext;
    private Converter mConverter = new Converter();
    private EditText scrn_input;
    private EditText scrn_output;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_0;
    private Button btn_clr;
    private Button btn_back;
    private Button btn_dot;
    private Button btn_scientific;
    private Button btn_negative;
    private Button btn_in;
    private Button btn_out;

    String input_txt;
    String output_txt;

    String numerator_txt = "1";
    String denominator_txt = "1";

    ArrayList<Unit> inputUnitList = null;
    ArrayList<Unit> outputUnitList = null;

    String[] unitName_stringArray = null;
    String[] unitValue_stringArray = null;

    MyAdapter<Unit> myAdapter_in;
    MyAdapter<Unit> myAdapter_out;

    private ListView lv_in;
    private ListView lv_out;

    private AlertDialog altDialog_in = null;
    private AlertDialog.Builder builder_in = null;
    private AlertDialog altDialog_out = null;
    private AlertDialog.Builder builder_out = null;

    private View listview_in;
    private View listview_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContext = MainActivity.this;
        bindView();
    }

    private void bindView() {

        inputUnitList = new ArrayList<Unit>();
        outputUnitList = new ArrayList<Unit>();



        scrn_input = (EditText) findViewById(R.id.scrn_input);
        scrn_output = (EditText) findViewById(R.id.scrn_output);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_clr = (Button) findViewById(R.id.btn_clr);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_dot = (Button) findViewById(R.id.btn_dot);
        btn_scientific = (Button) findViewById(R.id.btn_scientific);
        btn_negative = (Button) findViewById(R.id.btn_negative);


        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_dot.setOnClickListener(this);
        btn_scientific.setOnClickListener(this);
        btn_negative.setOnClickListener(this);

        input_txt = "0";
        output_txt = "=0";

        Resources res = getResources();
        unitName_stringArray = res.getStringArray(R.array.length_unitName);
        unitValue_stringArray = res.getStringArray(R.array.length_unitValue);

        addUnitList(unitName_stringArray, unitValue_stringArray, inputUnitList);
        addUnitList(unitName_stringArray, unitValue_stringArray, outputUnitList);


        myAdapter_in = new MyAdapter<Unit>(inputUnitList, R.layout.dialog_unit_in) {
            @Override
            public void bindView(ViewHolder holder, Unit obj) {
                holder.setText(R.id.spin_txt, obj.getUnitName());
            }
        };

        myAdapter_out = new MyAdapter<Unit>(outputUnitList, R.layout.dialog_unit_out) {
            @Override
            public void bindView(ViewHolder holder, Unit obj) {
                holder.setText(R.id.spout_txt, obj.getUnitName());
            }
        };



        //-------------------------------------------------------------------test

        btn_in = (Button) findViewById(R.id.btn_in_test);
        btn_out = (Button) findViewById(R.id.btn_out_test);
        btn_in.setOnClickListener(this);
        btn_out.setOnClickListener(this);



        builder_in = new AlertDialog.Builder(mContext);
        builder_out = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater_in = MainActivity.this.getLayoutInflater();
        final LayoutInflater inflater_out = MainActivity.this.getLayoutInflater();
        listview_in = inflater_in.inflate(R.layout.unit_in_listview, null, false);
        listview_out = inflater_out.inflate(R.layout.unit_out_listview, null, false);
        builder_in.setView(listview_in);
        builder_out.setView(listview_out);



        altDialog_in = builder_in.create();
        altDialog_out = builder_out.create();

        lv_in = (ListView) listview_in.findViewById(R.id.lv_in);
        lv_out = (ListView) listview_out.findViewById(R.id.lv_out);
        lv_in.setAdapter(myAdapter_in);
        lv_out.setAdapter(myAdapter_out);

        lv_in.setOnItemClickListener(this);
        lv_out.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Resources res_new = getResources();
        switch (id){
            case R.id.length:{
                unitName_stringArray = res_new.getStringArray(R.array.length_unitName);
                unitValue_stringArray = res_new.getStringArray(R.array.length_unitValue);
                mConverter.setIndexCategory("length");
                break;
            }
            case R.id.mass:{
                unitName_stringArray = res_new.getStringArray(R.array.mass_unitName);
                unitValue_stringArray = res_new.getStringArray(R.array.mass_unitValue);
                mConverter.setIndexCategory("mass");
                break;
            }
            case R.id.time:{
                unitName_stringArray = res_new.getStringArray(R.array.time_unitName);
                unitValue_stringArray = res_new.getStringArray(R.array.time_unitValue);
                mConverter.setIndexCategory("time");
                break;
            }
            case R.id.current:{
                unitName_stringArray = res_new.getStringArray(R.array.current_unitName);
                unitValue_stringArray = res_new.getStringArray(R.array.current_unitValue);
                mConverter.setIndexCategory("current");
                break;
            }
            case R.id.temperature:{
                unitName_stringArray = res_new.getStringArray(R.array.temperature_unitName);
                unitValue_stringArray = res_new.getStringArray(R.array.temperature_unitValue);
                mConverter.setIndexCategory("temperature");
                break;
            }
            default:{
                unitName_stringArray = res_new.getStringArray(R.array.length_unitName);
                unitValue_stringArray = res_new.getStringArray(R.array.length_unitValue);
                mConverter.setIndexCategory("length");
                break;
            }

        }

        addUnitList(unitName_stringArray, unitValue_stringArray, inputUnitList);
        addUnitList(unitName_stringArray, unitValue_stringArray, outputUnitList);
        btn_in.setText(inputUnitList.get(0).getUnitName());
        btn_out.setText(outputUnitList.get(0).getUnitName());
        numerator_txt = inputUnitList.get(0).getUnitValue();
        denominator_txt =outputUnitList.get(0).getUnitValue();
        mConverter.update_test_num(numerator_txt,denominator_txt);
        lv_in.setAdapter(myAdapter_in);
        lv_out.setAdapter(myAdapter_out);
        mConverter.clear_num();
        display_result();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addUnitList(String[] str_name,String[] str_value, ArrayList<Unit> unit_list ){
        unit_list.clear();
        for(int i = 0; i < str_name.length; i++){
            unit_list.add(new Unit(str_name[i], str_value[i]));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:{
                String new_txt = "";
                new_txt +=((Button)view).getText();
                mConverter.addNewNum(new_txt);
                break;
            }
            case R.id.btn_clr:{
                mConverter.clear_num();
                break;
            }
            case R.id.btn_back:{
                mConverter.refreshBackNum();
                break;
            }
            case R.id.btn_negative: {
                mConverter.negate();
                break;
            }
            case R.id.btn_dot: {
                mConverter.setOnDecimalMode();
                break;
            }
            case R.id.btn_scientific: {
                mConverter.setOnScientificMode();
                break;
            }
            case R.id.btn_in_test: {
                altDialog_in.show();
                break;
            }
            case R.id.btn_out_test: {
                altDialog_out.show();
                break;
            }
        }
        display_result();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        switch (adapterView.getId()) {
            case R.id.lv_in: {
                numerator_txt = ""+inputUnitList.get(position).getUnitValue();
                altDialog_in.dismiss();
                btn_in.setText(inputUnitList.get(position).getUnitName());
                break;
            }
            case R.id.lv_out: {
                denominator_txt = ""+outputUnitList.get(position).getUnitValue();
                altDialog_out.dismiss();
                btn_out.setText(outputUnitList.get(position).getUnitName());
                break;
            }
        }
        mConverter.update_test_num(numerator_txt,denominator_txt);
        display_result();
    }

    public void display_result(){
        input_txt = mConverter.input2txt();
        output_txt = mConverter.output2txt();
        scrn_input.setText(input_txt);
        scrn_input.setSelection(input_txt.length());
        scrn_output.setText(output_txt);
    }
}
