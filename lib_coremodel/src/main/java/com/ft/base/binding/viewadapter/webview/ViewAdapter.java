package com.ft.base.binding.viewadapter.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.BindingAdapter;



public class ViewAdapter {
    @BindingAdapter({"render"})
    public static void loadHtml(WebView webView, final String html) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @BindingAdapter({"loadH5Page"})
    public static void loadH5Page(WebView mWebView, final String url) {
        if (!TextUtils.isEmpty(url)) {
            mWebView.setHorizontalScrollBarEnabled(false);
            mWebView.setVerticalScrollBarEnabled(false);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("GBK");
            webSettings.setDomStorageEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

           /* mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }


                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed(); // 接受所有证书
                }


                @Override
                public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                    return super.shouldOverrideKeyEvent(view, event);
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    return super.shouldInterceptRequest(view, url);
                }


            });*/

            /*mWebView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                   *//* if(newProgress==100){
                        imageLoad.setVisibility(View.GONE);
                        imageLoad.clearAnimation();
                        mWebView.setVisibility(View.VISIBLE);
                    }
                    else{
                        mWebView.setVisibility(View.GONE);
                        imageLoad.setVisibility(View.VISIBLE);
                        imageLoad.startAnimation(animation);

                    }*//*
                    super.onProgressChanged(view, newProgress);
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                }
            });*/


            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                       // finish();
                    }
                    return false;
                }
            });
            mWebView.loadUrl(url);
        }
    }
}
