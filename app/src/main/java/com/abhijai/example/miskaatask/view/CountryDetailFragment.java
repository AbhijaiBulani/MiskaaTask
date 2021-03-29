package com.abhijai.example.miskaatask.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhijai.example.miskaatask.R;
import com.abhijai.example.miskaatask.models.CountryResponse;
import com.abhijai.example.miskaatask.models.Language;
import com.abhijai.example.miskaatask.view_models.CountriesViewModel;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class CountryDetailFragment extends Fragment {

    private static final String TAG = "CountryDetailFragmenO";

    private ImageView imageViewFlag;
    private TextView textViewName;
    private TextView textViewCapital;
    private TextView textViewRegion;
    private TextView textViewSubRegion;
    private TextView textViewPopulation;
    private TextView textViewBorders;
    private TextView textViewLanguages;
    private CountriesViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_country_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String countryName = CountryDetailFragmentArgs.fromBundle(getArguments()).getCountryName();
        initViews(view);
        mViewModel = new ViewModelProvider(getActivity()).get(CountriesViewModel.class);
        setData(countryName);
        Log.d(TAG, "onViewCreated: "+countryName);
    }


    private void initViews(View view){
        imageViewFlag = view.findViewById(R.id.iv_countryFlagDetail);
        textViewName = view.findViewById(R.id.tv_countryNameDetail);
        textViewCapital = view.findViewById(R.id.tv_capitalDetail);
        textViewRegion = view.findViewById(R.id.tv_regionDetail);
        textViewSubRegion = view.findViewById(R.id.tv_subRegionDetail);
        textViewPopulation = view.findViewById(R.id.tv_populationDetail);
        textViewBorders = view.findViewById(R.id.tv_borderDetail);
        textViewLanguages = view.findViewById(R.id.tv_languageDetail);
    }


    private void setData(String countryName)
    {
        mViewModel.getSingleCountry(countryName).observe(getViewLifecycleOwner(), new Observer<CountryResponse>() {
            @Override
            public void onChanged(CountryResponse res) {
                if (res!=null){
                    setImage(res.getFlag());
                    textViewName.setText("Country: "+res.getName());
                    textViewCapital.setText("Capital: "+res.getCapital());
                    textViewRegion.setText("Region: "+res.getRegion());
                    textViewSubRegion.setText("Sub-Region: "+res.getSubregion());
                    textViewPopulation.setText("Population: "+res.getPopulation());
                    textViewBorders.setText("Borders: "+fromListToString(res.getBorders()));
                    textViewLanguages.setText("Languages: "+fromLanguageListToString(res.getLanguages()));
                }
            }
        });
    }


    private void setImage(String url){
        GlideToVectorYou.init().with(getActivity()).withListener(new GlideToVectorYouListener() {
            @Override
            public void onLoadFailed() {
                Log.e(TAG, "onLoadFailed: .....");
            }

            @Override
            public void onResourceReady() {
                Log.d(TAG, "onResourceReady: ");
            }
        })
                .load(Uri.parse(url),imageViewFlag);
    }

    private String fromListToString(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list){
            stringBuilder.append(str).append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
    }


    private String fromLanguageListToString(List<Language> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (Language la : list){
            stringBuilder.append(la.getName()).append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
    }



}