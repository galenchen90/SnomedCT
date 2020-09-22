package com.nyit.snomedct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.nyit.snomedct.ConceptResults.AtomObject;
import com.nyit.snomedct.Tools.ViewDialog;

import java.util.HashMap;
import java.util.List;

import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTID;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTNAMEKEY;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTSTUTES;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTURLKEY;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<AtomObject>> expandableListDetail;
    ViewDialog viewDialog;


    public ExpandableListAdapter(Context context, List<String> expandableListTitle) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
    }

    public ExpandableListAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<AtomObject>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;

    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {


        if(listPosition==2){
            return 0;

        }

        int size = expandableListDetail.get(expandableListTitle.get(listPosition)).size() ;




        return size;

    }

    @Override
    public Object getGroup(int listPosition) {
        return expandableListTitle.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {

        return expandableListDetail.get(expandableListTitle.get(listPosition)).get(expandedListPosition);
    }


    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }


    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }


    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_title_for_expandable, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.list_title_expendview);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
//
//
//        if (listPosition==2) {
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "map map", Toast.LENGTH_SHORT).show();
//                }
//            });
//

//        }


        return convertView;
    }




    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        AtomObject atomObject= expandableListDetail.get(expandableListTitle.get(listPosition)).get(expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.synonym_listview, null);
        }

        if(listPosition==0) {


            TextView synonymNameView = convertView.findViewById(R.id.synonym_name_view);
            synonymNameView.setText(atomObject.getName());
            TextView synonymTypeView = convertView.findViewById(R.id.synonym_type_view);
            synonymTypeView.setText(atomObject.getType());

        }

        if (listPosition==1) {
            TextView synonymTypeView = convertView.findViewById(R.id.synonym_type_view);
            synonymTypeView.setText(atomObject.getContext());

            TextView synonymNameView = convertView.findViewById(R.id.synonym_name_view);
            synonymNameView.setText(atomObject.getName());

//            if (atomObject.getContext().equals("is a")){
//                synonymNameView.setTextColor(context.getColor(R.color.ISAcolor));
//                synonymTypeView.setTextColor(context.getColor(R.color.ISAcolor));
//            }
//
//            if (atomObject.getContext().equals("Causative agent")){
//                synonymNameView.setTextColor(context.getColor(R.color.CAcolor));
//                synonymTypeView.setTextColor(context.getColor(R.color.CAcolor));            }
//            if (atomObject.getContext().equals("Pathological process")){
//                synonymNameView.setTextColor(context.getColor(R.color.PAcolor));
//                synonymTypeView.setTextColor(context.getColor(R.color.PAcolor));            }
//            if (atomObject.getContext().equals("Finding site")){
//                synonymNameView.setTextColor(context.getColor(R.color.FScolor));
//                synonymTypeView.setTextColor(context.getColor(R.color.FScolor));
//            }


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    viewDialog = new ViewDialog((Activity)context);
//                    viewDialog.showDialog();

                    Intent intent = new Intent(context, ConceptActivity.class);
                    intent.putExtra(CONCEPTNAMEKEY, atomObject.getName());

                    intent.putExtra(CONCEPTURLKEY, atomObject.getUrl());
                    intent.putExtra(CONCEPTID, atomObject.getId());
                    intent.putExtra(CONCEPTSTUTES,"Active" );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //   | Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    context.startActivity(intent);
          //          viewDialog.hideDialog();
        //          ((Activity)context).finish();

         //           Toast.makeText(context, "open new ACTIVITY!", Toast.LENGTH_SHORT).show();
                }
            });


        }


//        if(listPosition==2){
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "map map", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }





        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return false;
    }
}
