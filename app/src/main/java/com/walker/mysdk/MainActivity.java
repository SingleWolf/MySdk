package com.walker.mysdk;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.walker.log.BuildConfig;
import com.walker.log.xlog.Log;
import com.walker.log.xlog.Options;
import com.walker.log.xlog.Xlog;
import com.walker.protect.helper.IProtector;
import com.walker.protect.helper.Protector;

public class MainActivity extends Activity implements IProtector{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLog();
    }

    private void onLog() {
        String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
        String logPath = this.getExternalFilesDir("DripStone").getPath() + "/logDir";
        // this is necessary, or may crash for SIGBUS
        String cachePath = this.getFilesDir() + "/xlog";
        String pubkey = "9b40b6c9ad693aba1651f6bd294efca3701272056c8803fc8626ba411fc1d6887f5c1936c72bbe0a29de05da8f363b9535ee7e231ee62d603edeed65e23dd836";
        String logFileName = "drip";
        Options options = new Options();
        options.setNamePrefix(logFileName);
        options.setCacheDir(cachePath);
        options.setLogDir(logPath);
        options.setLoadLib(true);
        options.setPubkey(pubkey);
        options.setMode(Xlog.AppednerModeAsync);
        options.setMaxFileSize(5 * 1024 * 1024);
        if (BuildConfig.DEBUG) {
            options.setConsoleLogOpen(true);
            options.setLevel(Xlog.LEVEL_DEBUG);
        } else {
            options.setConsoleLogOpen(true);
            options.setLevel(Xlog.LEVEL_INFO);
        }
        Xlog xlog = new Xlog();
        Log.setLogImp(xlog);
        Xlog.open(options);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Xlog","onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Xlog","onDestroy()");
        Log.appenderClose();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Xlog","onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Xlog","onPause()");
    }

    @Override
    @Protector(level = 1,resultType = 1)
    public void onProtect(String msg) {
        Log.i("Protect",msg);
    }
}
