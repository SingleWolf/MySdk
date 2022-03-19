package com.walker.safecopy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class SafeCopyHelper {
    private static volatile SafeCopyHelper sInstance;
    private ICopyAction mCopyAction;
    private static final String TAG = "SafeCopyHelper";

    public static SafeCopyHelper get() {
        if (sInstance == null) {
            synchronized (SafeCopyHelper.class) {
                if (sInstance == null) {
                    sInstance = new SafeCopyHelper();
                }
            }
        }
        return sInstance;
    }

    private SafeCopyHelper() {
        mCopyAction = new SafeCopy();
    }

    public void doInBackground(Context context) {
        Log.d(TAG, "----doInBackground----");
        String content = get4Clipboard(context);
        mCopyAction.putContent(context, content);
        clearClipboard(context);
    }

    public void doInForeground(Context context) {
        Log.d(TAG, "----doInForeground----");
        String content = get4Clipboard(context);
        if (TextUtils.isEmpty(content)) {
            String temp = mCopyAction.getContent(context);
            put2Clipboard(context, temp);
        }
    }

    private void clearClipboard(Context context) {
        Log.d(TAG, "----clearClipboard----");
        put2Clipboard(context, "");
    }

    private void put2Clipboard(Context context, String content) {
        try {
            // 获取系统剪贴板
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            ClipData clipData = ClipData.newPlainText(null, content);
            // 把数据集设置（复制）到剪贴板
            clipboard.setPrimaryClip(clipData);

            Log.d(TAG, "put2Clipboard: " + content);
        } catch (Throwable e) {
            Log.e(TAG, "put2Clipboard: error = " + e.toString());
        }
    }

    private String get4Clipboard(Context context) {
        String result = "";
        try {
            // 获取系统剪贴板
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 获取剪贴板的剪贴数据集
            ClipData clipData = clipboard.getPrimaryClip();

            if (clipData != null && clipData.getItemCount() > 0) {
                // 从数据集中获取（粘贴）第一条文本数据
                CharSequence text = clipData.getItemAt(0).getText();
                result = text.toString();
            }
            Log.d(TAG, "get4Clipboard: " + result);
        } catch (Throwable e) {
            Log.e(TAG, "get4Clipboard: error = " + e.toString());
        }
        return result;
    }
}
