package cn.lognteng.editspinner.mytesteditspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.lognteng.editspinner.lteditspinner.DataTest;
import cn.lognteng.editspinner.lteditspinner.LTEditSpinner;

public class MainActivity extends AppCompatActivity
{
    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        List<DataTest> list = new ArrayList<DataTest>();
        for (int i=0; i<data.length; i++)
        {
            list.add(new DataTest(i, data[i]));
        }

        LTEditSpinner<DataTest> ltEditSpinner = (LTEditSpinner)findViewById(R.id.edit_spinner);
        ltEditSpinner.initData(list, new LTEditSpinner.OnESItemClickListener<DataTest>() {
            @Override
            public void onItemClick(DataTest item) {
                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
