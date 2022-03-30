package com.walker.privacy.bit64;

public class SoFile {

    /**
     * So File path.
     */
    String soPath = "";

    /**
     * So File name.
     */
    String soName = "";

    /**
     * Pom name.
     */
    String pomName = "";

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(pomName);
        builder.append(":");
        builder.append(soName);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return 31 * pomName.hashCode() + soName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SoFile)) {
            return false;
        }
        return pomName == ((SoFile) other).pomName && soName == ((SoFile) other).soName;
    }
}