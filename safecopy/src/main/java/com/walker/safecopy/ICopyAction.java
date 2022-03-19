package com.walker.safecopy;

import android.content.Context;

public interface ICopyAction {
    void putContent(Context context, String content);
    String getContent(Context context);
}
