package scrollview.custom.com.viewplay;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);// support zoom
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        //
        webSettings.setDisplayZoomControls(false);
//        String gifPath = "http://pic48.nipic.com/file/20140912/7487939_223919315000_2.jpg";
//        String gifPath = "http://img.zcool.cn/community/018bcf575ed5200000018c1b264854.gif";

        String gifPath = "file:///android_asset/kingfisher.jpg";
        String videoPath = "file:///android_asset/LaunchTour.mp4";

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        String data = "<HTML><div align=\"center\" margin=\"0px\"><IMG  width=\"100%\" height=\"100%\" src=\"" + gifPath + "\" margin=\"0px\"/></Div>";//设置图片位于webview的中间位置

//        webView.loadDataWithBaseURL(gifPath, data, "text/html", "utf-8", null);

        String viedo = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <body style='margin:0'>\n" +
                "        <video   width=\"100%\" height=\"100%\"  autoplay loop>\n" +
                "              <source src=\"" + videoPath + "\" type=\"video/mp4\">\n" +
                "        </video>\n" +
                "    </body>\n" +
                "</html>";

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //loaded finished
                String js = "javascript: var v=document.getElementsByTagName('video')[0]; "
                        + "v.play(); ";

                //start play
                webView.loadUrl(js);
            }
        });
        webView.loadDataWithBaseURL(videoPath, viedo, "text/html", "utf-8", null);
        // 开启支持视频
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setGeolocationEnabled(true);
        String js = "javascript: var v=document.getElementsByTagName('video')[0]; "
                + "v.play(); " + "v.webkitEnterFullscreen(); ";

        //start play
        webView.loadUrl(js);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String js = "javascript: var v=document.getElementsByTagName('video')[0]; "
                + "v.play(); ";
        //start play
        webView.loadUrl(js);
    }
}
