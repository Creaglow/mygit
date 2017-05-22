package com.example.cpydr.cpys.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.cpydr.cpys.R;


public class ForumFragment extends Fragment {
    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forum,container,false);
        webView = (WebView)v.findViewById(R.id.webView);
        webView.loadUrl("http://bbs.3dmgame.com/forum.php");
        return v;
    }


}
