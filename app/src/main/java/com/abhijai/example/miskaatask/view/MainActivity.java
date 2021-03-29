package com.abhijai.example.miskaatask.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.abhijai.example.miskaatask.R;
import com.abhijai.example.miskaatask.models.CountryResponse;
import com.abhijai.example.miskaatask.util.Resource;
import com.abhijai.example.miskaatask.view_models.CountriesViewModel;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UiCommunication {
    private static final String TAG = "NetworkBoundResourceO";
    private CountriesViewModel countriesViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countriesViewModel = new ViewModelProvider(this).get(CountriesViewModel.class);
        //observeResult();
        /*textView.setOnClickListener((view)->{
            countriesViewModel.deleteAllData();
        });*/
    }

    private void observeResult(){
        countriesViewModel.observeResult().observe(this, new Observer<Resource<List<CountryResponse>>>() {
            @Override
            public void onChanged(Resource<List<CountryResponse>> listResource)
            {
                Log.d(TAG, "ACTIVITY STATUS: "+listResource.status);
                switch (listResource.status){
                    case SUCCESS:{
                        Log.e(TAG, "onChanged: Data: "+listResource.data.size());
                        break;
                    }
                    case ERROR:{
                        Log.e(TAG, "onChanged: Error "+listResource.message);
                        if (listResource.data!=null)
                            Log.d(TAG, "onChanged: Data in Error State -> "+listResource.data.size());
                        break;
                    }
                    case LOADING:{
                        Log.e(TAG, "onChanged: Loading.........");
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void displayDeleteAllDataDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("DELETE")
                .setMessage("Are you sure, you want to delete all data")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countriesViewModel.deleteAllData();
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
}