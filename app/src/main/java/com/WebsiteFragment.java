package com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.project.R;

public class WebsiteFragment extends Fragment {
    WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website, container, false);
        webview = (WebView)view.findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();


        webview.loadUrl("https://www.gcuh.edu.pk/");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webSettings.setJavaScriptEnabled(true);

        return view;
    }

}