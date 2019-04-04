package com.poc.loginpoc.dagger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public class PulseBaseFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        inject(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    protected void inject(Activity activity) {
        ((PulseApplication) activity.getApplication()).inject(this);
    }
}

