package com.abhijai.example.miskaatask.view;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abhijai.example.miskaatask.R;
import com.abhijai.example.miskaatask.models.CountryResponse;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

public class CountryListRecyclerAdapter extends ListAdapter<CountryResponse, CountryListRecyclerAdapter.CountryViewHolder>{

    private static final String TAG = "CountryListRecyO";
    private final CountryClickListener mListener;
    protected CountryListRecyclerAdapter(CountryClickListener listener) {
        super(new CountryDiffItemCallback());
        mListener = listener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CountryViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bindItems(getItem(position),mListener);
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView textViewCountryName;
        private TextView textViewCapitalName;
        private final View mView;
        private CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void bindItems(CountryResponse response, CountryClickListener listener){
            init();
            textViewCountryName.setText(response.getName());
            textViewCapitalName.setText(response.getCapital());
            GlideToVectorYou.init().with(mView.getContext()).withListener(new GlideToVectorYouListener() {
                @Override
                public void onLoadFailed() {
                    Log.e(TAG, "onLoadFailed: .....");
                    imageView.setBackgroundResource(R.drawable.broke);
                }

                @Override
                public void onResourceReady() {
                    Log.d(TAG, "onResourceReady: ");
                }
            })
                    .load(Uri.parse(response.getFlag()),imageView);
            mView.setOnClickListener(v -> listener.onCountryClick(response.getName()));
        }

        private void init(){
            imageView = mView.findViewById(R.id.iv_countryFlag);
            textViewCountryName = mView.findViewById(R.id.tv_countryName);
            textViewCapitalName = mView.findViewById(R.id.tv_countryCapital);
        }

        static CountryViewHolder from(ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.country_list_item,parent,false);
            return new CountryViewHolder(view);
        }
    }

    static class CountryDiffItemCallback extends DiffUtil.ItemCallback<CountryResponse>{

        @Override
        public boolean areItemsTheSame(@NonNull CountryResponse oldItem, @NonNull CountryResponse newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CountryResponse oldItem, @NonNull CountryResponse newItem)
        {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getFlag().equals(newItem.getFlag());
        }
    }

    interface CountryClickListener{
        void onCountryClick(String countryName);
    }
}
