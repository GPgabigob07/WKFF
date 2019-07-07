package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;

public class FieldSliderFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.skr_fragment_slider, container, false);

        TextView tv = v.findViewById(R.id.title);
        tv.setText(getArguments().getString("text"));

        ImageView imageView = v.findViewById(R.id.field_bg);
        imageView.setBackgroundResource(getArguments().getInt("img"));

        return v;
    }

    public static FieldSliderFragment newInstance(String text, int image) {

        FieldSliderFragment f = new FieldSliderFragment();
        Bundle b = new Bundle();
        b.putString("text", text);
        b.putInt("img", image);

        f.setArguments(b);

        return f;
    }
}
