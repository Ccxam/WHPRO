package com.wh.whpro.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.wh.whpro.MainActivity;
import com.wh.whpro.R;
import com.wh.whpro.ui.MyComponents.titanic.Titanic;
import com.wh.whpro.ui.MyComponents.titanic.TitanicTextView;
import com.wh.whpro.ui.home.utils.TypeFaces;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TitanicTextView titTv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button home_btn1 = root.findViewById(R.id.home_btn1);
        titTv=root.findViewById(R.id.home_tv1);
        titTv.setTypeface(TypeFaces.get(getActivity(), "Satisfy-Regular.ttf"));
        new Titanic().start(titTv);
        home_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().deleteDatabase("WH1.db");

            }
        });


        return root;
    }
}