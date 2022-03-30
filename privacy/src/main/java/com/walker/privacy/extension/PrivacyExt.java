package com.walker.privacy.extension;

public class PrivacyExt {
    public static final String TAG = "PrivacyExt";
    private boolean enable64Bit;
    private String taskName = "";

    public boolean isEnable64Bit() {
        return enable64Bit;
    }

    public void setEnable64Bit(boolean enable64Bit) {
        this.enable64Bit = enable64Bit;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
