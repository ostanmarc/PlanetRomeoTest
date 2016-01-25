package com.ostan.prtest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ostan.prtest.R;
import com.ostan.prtest.adapters.GruppedImagesListAdapter;
import com.ostan.prtest.data.DataProccessor;


public class ImagesSearchResultFragment extends Fragment implements OnClickListener, DataProccessor.Notifyable {

    public static final String TAG = ImagesSearchResultFragment.class.getSimpleName();
    public static final String mTitle = "Main Fragment";

    private boolean fragmentActive = false;
    // Adapter for ListView Contents
    private GruppedImagesListAdapter adapter;

    // ListView Contents
    private ListView journalListView;

    private void prepareUI(View view) {
        adapter = new GruppedImagesListAdapter(getContext());

        // Get a reference to the ListView holder
        journalListView = (ListView) view.findViewById(R.id.list_journal);

        // Set the adapter on the ListView holder
        journalListView.setAdapter(adapter);

        // Listen for Click events
        journalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
                String item = (String) adapter.getItem(position);
                Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    static ImagesSearchResultFragment mInstance = null;

    public static ImagesSearchResultFragment getInstance() {
        if (mInstance == null)
            mInstance = new ImagesSearchResultFragment();
        return mInstance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.images_fragment_layout, container, false);
        prepareUI(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onResume() {
        fragmentActive = true;
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            default:
                break;
        }

    }

    @Override
    public void newPresentableArrived(DataProccessor.Presentable newbie) {
        adapter.addNewImage(newbie);
    }

    @Override
    public void newSearchStarted() {
        adapter.clearData();
    }


}


