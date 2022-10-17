package com.nikhil.vjitece;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class studymaterial extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studymaterial);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();


        listAdapter = new ExpandableListAdapt(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {



                if(groupPosition==0){
                    if(childPosition == 0){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1tXZS_zBnZaC9-HihaRL1tTyO__FKa4_b?usp=sharing"));
                        startActivity(intent);
                       }
                    if(childPosition == 1){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1zy--m9RIVw9nvqi3PRVET6Rh82hjwmoZ?usp=sharing"));
                        startActivity(intent);
                       }

                }
                if(groupPosition==1){
                    if(childPosition == 0){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1qq8kUBUkTDzEJ0UnFwp_c7cdC3TFX1XZ?usp=sharing"));
                        startActivity(intent);
                      }
                    if(childPosition == 1){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1hxC4ZjSoMUhj3WdksjxOMkuDrsl5mYVL?usp=sharing"));
                        startActivity(intent);
                       }

                }

                if(groupPosition==2){
                    if(childPosition == 0){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1CR_lJuk8KSuCDCrlyQqg7o2RZ5z_bJrj?usp=sharing"));
                        startActivity(intent);
                       }
                    if(childPosition == 1){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/19_I4tRYY7bme0nQJo6WbtogSXFmU2zMv?usp=sharing"));
                        startActivity(intent);
                       }

                }

                if(groupPosition==3){
                    if(childPosition == 0){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1FOMYjstFfO2yaQ-QgeDqWBbGRx8D9Au5?usp=sharing"));
                        startActivity(intent);
                       }
                    if(childPosition == 1){
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1x66-eOcI4BzEyWgaqByLoh3ySa-zDPNK?usp=sharing"));
                        startActivity(intent);
                      }

                }
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("I YEAR");
        listDataHeader.add("II YEAR");
        listDataHeader.add("III YEAR");
        listDataHeader.add("IV YEAR");

        // Adding child data
        List<String> sem1= new ArrayList<String>();
        sem1 .add("I SEMESTER");
        sem1 .add("II SEMESTER");

        List<String> sem2 = new ArrayList<String>();
        sem2 .add("I SEMESTER");
        sem2 .add("II SEMESTER");
        List<String> sem3 = new ArrayList<String>();
        sem3 .add("I SEMESTER");
        sem3 .add("II SEMESTER");
        List<String> sem4 = new ArrayList<String>();
        sem4 .add("I SEMESTER");
        sem4 .add("II SEMESTER");
        listDataChild.put(listDataHeader.get(0), sem1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), sem2);
        listDataChild.put(listDataHeader.get(2), sem3);
        listDataChild.put(listDataHeader.get(3), sem4);
    }
}
