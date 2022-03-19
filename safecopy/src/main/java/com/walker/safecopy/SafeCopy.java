package com.walker.safecopy;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class SafeCopy implements ICopyAction {

    static {
        System.loadLibrary("SafeCopy");
    }

    @Override
    public void putContent(Context context, String content) {
        String path = getFilePath(context);
        putString2memory(path, content);
    }

    private String getFilePath(Context context) {
        String path = context.getExternalFilesDir("SafeCopy").getPath() + "/safecopy.txt";
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Log.e("SafeCopy", e.toString());
            }
        }
        return path;
    }

    @Override
    public String getContent(Context context) {
        String path = getFilePath(context);
        return getString4memory(path);
    }

    private native void putString2memory(String path, String content);

    private native String getString4memory(String path);
}
