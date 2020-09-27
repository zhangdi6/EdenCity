package com.edencity.customer.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends BaseFragment2 {

    private String webTitle;
    private String webUrl;

    @BindView(R.id.text_title)
    TextView titleView;
    @BindView(R.id.webview)
    WebView webView;

    public WebFragment() {
        // Required empty public constructor
    }

    public static WebFragment newInstance(String title,String url){
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString("web_title", title);
        args.putString("web_url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            webTitle = getArguments().getString("web_title");
            webUrl = getArguments().getString("web_url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this,v);
        titleView.setText(webTitle);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        if (webUrl.startsWith("http")){
            webView.loadUrl(webUrl);
        }else {
            webView.loadUrl("file:///android_asset/"+webUrl);
        }
        return v;
    }

    @Override
    public void onViewItemClicked(View view) {
        if (view.getId()==R.id.btn_back){
            pop();
        }
    }
}
