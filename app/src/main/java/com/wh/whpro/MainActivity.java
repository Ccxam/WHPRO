package com.wh.whpro;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.wh.whpro.database.MyDatabaseHelper;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    //自己的数据库
    private MyDatabaseHelper dbHelper;
    private Integer tempLove;
    private ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] columns={"name","id"};
        //deleteDatabase("WH1.db");
        dbHelper = new MyDatabaseHelper(this,"WH1.db",null,1);
        final SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        values=new ContentValues();
        values.put("name","wys");
        values.put("loveCount",0);
        values.put("Message","let");

        String sql="select * from WH_Record where name=?";

        Cursor cursor=db1.rawQuery(sql, new String[]{"wys"});
        if (cursor == null) {
            Toast.makeText(MainActivity.this, "查询为空", Toast.LENGTH_SHORT).show();;
        }else {
            cursor.moveToPosition(0);
            while (cursor.moveToNext()) {

                tempLove = cursor.getInt(cursor.getColumnIndex("loveCount"));
                Log.d("TAG", "onCreate: "+tempLove);

            }
            //Toast.makeText(MainActivity.this, "id="+tempLove+":loveCount="+tempLove, Toast.LENGTH_SHORT).show();;
        }
        if(tempLove==null){

            Toast.makeText(MainActivity.this, "kong", Toast.LENGTH_SHORT).show();;
            db1.insert("WH_Record",null,values);
            tempLove=0;

        }else {
            Toast.makeText(MainActivity.this, "bukong", Toast.LENGTH_SHORT).show();;

        }
        cursor.close();
        values.clear();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                tempLove=tempLove+1;
                values.put("loveCount",tempLove);
                //db1.execSQL("update WH_Record set loveCount=? where name=? ",new String[]{tempLove,"你爱我"});
                db1.update("WH_Record",values,"name=?",new String[]{"wys"});
                Toast.makeText(MainActivity.this,"你爱我"+tempLove+"次", Toast.LENGTH_SHORT).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
