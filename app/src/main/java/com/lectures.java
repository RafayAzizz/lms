package com;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.project.R;


public class lectures extends Fragment {
    private WebView webView;
    TextView topic ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lectures, container, false);
        String link = getArguments().getString("link");
//        String topicname = getArguments().getString("topicname");
        webView = view.findViewById(R.id.webview);
        topic = view.findViewById(R.id.topicname);
        topic.setText("Lecture");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<div id=\"player\"></div>\n" +
                "<script>\n" +
                "var tag = document.createElement('script');\n" +
                "tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                "var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                "var player;\n" +
                "function onYouTubeIframeAPIReady() {\n" +
                "  player = new YT.Player('player', {\n" +
                "    height: '300px',\n" +
                "    width: '100%',\n" +
                "    videoId: '" + link + "',\n" +
                "    playerVars: {\n" +
                "      'playsinline': 1\n" +
                "    },\n" +
                "    events: {\n" +
                "      'onReady': onPlayerReady,\n" +
                "      'onStateChange': onPlayerStateChange\n" +
                "    }\n" +
                "  });\n" +
                "}\n" +
                "function onPlayerReady(event) {\n" +
                "  event.target.playVideo();\n" +
                "}\n" +
                "var done = false;\n" +
                "function onPlayerStateChange(event) {\n" +
                "  if (event.data == YT.PlayerState.PLAYING && !done) {\n" +
                "    setTimeout(stopVideo, 6000);\n" +
                "    done = true;\n" +
                "  }\n" +
                "}\n" +
                "function stopVideo() {\n" +
                "  player.stopVideo();\n" +
                "}\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
        setUponBackPressed();
        return view;
    }
    public void setUponBackPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }
        });
    }
}
