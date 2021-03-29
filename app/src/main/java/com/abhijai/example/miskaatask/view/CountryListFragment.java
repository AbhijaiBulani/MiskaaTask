package com.abhijai.example.miskaatask.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abhijai.example.miskaatask.R;
import com.abhijai.example.miskaatask.util.VerticalSpacingItemDecorator;
import com.abhijai.example.miskaatask.view_models.CountriesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.security.auth.login.LoginException;


//https://developer.android.com/guide/navigation/navigation-navigate
public class CountryListFragment extends Fragment {

    private static final String TAG = "CountryListFragmentO";
    private RecyclerView recyclerView;
    private CountryListRecyclerAdapter mAdapter;
    private CountriesViewModel mViewModel;
    private NavController mNavController;
    private UiCommunication uiListener;
    private FloatingActionButton floatingActionButton;
    private TextView textView;
    private ProgressBar progressBar;
    public CountryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            uiListener = (UiCommunication) context;
        }
        catch (ClassCastException exp){
            Log.e(TAG, "Must implement interface in "+getActivity().getLocalClassName()+" Exception: "+exp.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_country_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_country);
        floatingActionButton = view.findViewById(R.id.fbtn);
        textView = view.findViewById(R.id.tv_noData);
        progressBar = view.findViewById(R.id.pb_progress);
        mAdapter = new CountryListRecyclerAdapter(countryName -> navigateToCountryDetailFragment(countryName));
        mViewModel = new ViewModelProvider(getActivity()).get(CountriesViewModel.class);
        initRecycler();
        observeListFromActivity();
        mNavController = NavHostFragment.findNavController(this);
        floatingActionButton.setOnClickListener((v)->{
            uiListener.displayDeleteAllDataDialog();
        });
        /*NavController navController = NavHostFragment.findNavController(this);
        textView.setOnClickListener((v)->{
            navController.navigate(R.id.action_countryListFragment_to_countryDetailFragment);
        });*/
    }

    private void initRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new VerticalSpacingItemDecorator(80));
        recyclerView.setAdapter(mAdapter);
    }

    private void observeListFromActivity(){
        mViewModel.observeResult().observe(getViewLifecycleOwner(),listResource -> {
            switch (listResource.status){
                case LOADING:{
                    Log.e(TAG, "observeListFromActivity: LOADING......");
                    showView(progressBar);
                    hideView(recyclerView);
                    hideView(textView);
                    hideView(floatingActionButton);
                    break;
                }
                case ERROR:{
                    Log.e(TAG, "observeListFromActivity: ERROR....."+listResource.message);
                    hideView(progressBar);
                    hideView(recyclerView);
                    showView(textView);
                    textView.setText(listResource.message);
                    hideView(floatingActionButton);
                    break;
                }
                case SUCCESS:{
                    hideView(progressBar);
                    if (listResource.data!=null){
                        if (listResource.data.size()>0){
                            showView(recyclerView);
                            hideView(textView);
                            showView(floatingActionButton);
                            mAdapter.submitList(listResource.data);
                        }else {
                            hideView(recyclerView);
                            hideView(floatingActionButton);
                            showView(textView);
                        }
                    }
                }
            }
        });
    }

    private void navigateToCountryDetailFragment(String countryName){
        CountryListFragmentDirections.ActionCountryListFragmentToCountryDetailFragment action = CountryListFragmentDirections.actionCountryListFragmentToCountryDetailFragment();
        action.setCountryName(countryName);
        mNavController.navigate(action);
    }


    private void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view){
        view.setVisibility(View.GONE);
    }

}





















