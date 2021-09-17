package com.walker.log.xlog;

import static com.walker.log.xlog.Xlog.AppednerModeAsync;
import static com.walker.log.xlog.Xlog.LEVEL_DEBUG;

public class Options {
    private boolean isLoadLib;
    private int level = LEVEL_DEBUG;
    private int mode = AppednerModeAsync;
    private String cacheDir;
    private String logDir;
    private String namePrefix;
    private String pubkey = "";
    private boolean isConsoleLogOpen;
    private long maxFileSize = 0;
    private long maxAliveTime = 0;
    private int cacheDays = 0;

    public boolean isLoadLib() {
        return isLoadLib;
    }

    public void setLoadLib(boolean loadLib) {
        isLoadLib = loadLib;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public boolean isConsoleLogOpen() {
        return isConsoleLogOpen;
    }

    public void setConsoleLogOpen(boolean consoleLogOpen) {
        isConsoleLogOpen = consoleLogOpen;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public long getMaxAliveTime() {
        return maxAliveTime;
    }

    public void setMaxAliveTime(long maxAliveTime) {
        this.maxAliveTime = maxAliveTime;
    }

    public int getCacheDays() {
        return cacheDays;
    }

    public void setCacheDays(int cacheDays) {
        this.cacheDays = cacheDays;
    }

    @Override
    public String toString() {
        return "Options{" +
                "isLoadLib=" + isLoadLib +
                ", level=" + level +
                ", mode=" + mode +
                ", cacheDir='" + cacheDir + '\'' +
                ", logDir='" + logDir + '\'' +
                ", namePrefix='" + namePrefix + '\'' +
                ", pubkey='" + pubkey + '\'' +
                ", isConsoleLogOpen=" + isConsoleLogOpen +
                ", maxFileSize=" + maxFileSize +
                ", maxAliveTime=" + maxAliveTime +
                ", cacheDays=" + cacheDays +
                '}';
    }
}
