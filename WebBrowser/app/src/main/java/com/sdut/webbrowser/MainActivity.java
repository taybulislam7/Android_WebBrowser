package com.sdut.webbrowser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView WebView;
    EditText editTextLink;
    Button btn_view,btn_back,btn_refresh,btn_foreword;
    ProgressBar progressBar;
//    long t1,t2;

    //user exit dialoag
    AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.webViewProgress);

        WebView = findViewById(R.id.WebView);
        editTextLink = findViewById(R.id.editTextLink);
        btn_view = findViewById(R.id.btn_view);

        btn_back = findViewById(R.id.btn_back);
        btn_refresh = findViewById(R.id.btn_refresh);
        btn_foreword = findViewById(R.id.btn_foreword);

        //exit dialog
//        t1 = System.currentTimeMillis();
        builder=new AlertDialog.Builder(this);
        builder.setTitle("confirm Dialog");
        builder.setMessage("Exit?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("cancel",null);
        dialog=builder.create();


        WebView.setWebViewClient(new myWebViewClient());

        WebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress<100){
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);

                }else{
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextLink.getText().toString();

                WebView.getSettings().setLoadsImagesAutomatically(true);
                WebView.getSettings().setJavaScriptEnabled(true);

                WebView.loadUrl(url);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WebView.canGoBack()){
                    WebView.goBack();
                }
            }
        });
        btn_foreword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WebView.canGoForward()){
                    WebView.goForward();
                }
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebView.reload();
            }
        });


    }



    private class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode==KeyEvent.KEYCODE_BACK){
//            t2=System.currentTimeMillis();
//            if (t2-t1<2000){
//                MainActivity.this.finish();
//            }else{
//                t1=System.currentTimeMillis();
//                Toast.makeText(this, "Exit?", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return true;
//    }

    //exit dialog
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            dialog.show();
        }
        return true;

    }
}