package com.ostan.prtest.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ostan.prtest.utils.CommonUtils;
import com.ostan.prtest.R;
import com.ostan.prtest.RequestTasks.AbstractGetRequest;
import com.ostan.prtest.RequestTasks.ImagesSearchGetRequest;
import com.ostan.prtest.data.DataProccessor;
import com.ostan.prtest.fragments.ImagesSearchResultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int mFragmentContainer = -1;
    public FragmentManager mFragmentManager = null;
    private Button searchBtn = null;
    private EditText querryEt = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        searchBtn = (Button)findViewById(R.id.btn_search);
        querryEt = (EditText)findViewById(R.id.et_search_querry);

        searchBtn.setOnClickListener(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentContainer = R.id.fragment_container;
        mFragmentManager
                .beginTransaction()
                .replace(mFragmentContainer,
                        new ImagesSearchResultFragment(),
                        ImagesSearchResultFragment.TAG).commit();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                String querryString = querryEt.getText().toString();
                if(querryString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_querry_is_invalid), Toast.LENGTH_LONG).show();
                    break;

                }
                ((ImagesSearchResultFragment) mFragmentManager.findFragmentByTag(ImagesSearchResultFragment.TAG)).newSearchStarted();
                fireSearchQuerry(querryString);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void fireSearchQuerry(String querry){

        new ImagesSearchGetRequest("images/search", true, querry, new AbstractGetRequest.RequestListener() {
            @Override
            public void onRequestFailure() {
                Toast.makeText(getApplicationContext(), getString(R.string.error_fetching_images), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(JSONObject result) {
                try {

                    Map<String, Object> queryResult = CommonUtils.jsonToMap(result);
                    DataProccessor.importReceivedData((List<Map>) queryResult.get("data"), (ImagesSearchResultFragment) mFragmentManager.findFragmentByTag(ImagesSearchResultFragment.TAG));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }).execute();
    }
}
