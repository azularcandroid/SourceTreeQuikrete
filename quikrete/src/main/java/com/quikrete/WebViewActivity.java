package com.quikrete;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quikrete.utils.ScreenUtils;

public class WebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScreenUtils.fullScreen(this);
		setContentView(R.layout.activity_web_view);

		WebView webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(PluginState.ON);
		//webView.getSettings().setLoadWithOverviewMode(true);
		//webView.getSettings().setUseWideViewPort(true);
		webView.setWebViewClient(new Callback());
		String pdf = "http://quikrete.com/PDFs/Projects/AnchoringProjects.pdf";
		webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
		//webView.loadUrl("http://docs.google.com/viewer?url=http%3A%2F%2Fquikrete.com%2FPDFs%2FProjects%2FAnchoringProjects.pdf");
	}

	private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }
}
