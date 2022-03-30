package com.walker.privacy.bit64;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

public class SoFileList {
    List<SoFile> armeabi = new ArrayList<>();
    List<SoFile> armeabiv_v7a = new ArrayList<>();
    List<SoFile> arm64_v8a = new ArrayList<>();
    List<SoFile> x86 = new ArrayList<>();
    List<SoFile> x86_64 = new ArrayList<>();
    List<SoFile> mips = new ArrayList<>();
    List<SoFile> mips_64 = new ArrayList<>();

    /**
     * Print 64-bit abi supported status of the current project.
     */
    void printlnResult() {
        System.out.println(String.format("EasyPrivacy => armeabi size: %d", armeabi.size()));
        // printSoFileList(armeabi)

        System.out.println(String.format("EasyPrivacy => armeabiv_v7a size: %d", armeabiv_v7a.size()));
        // printSoFileList(armeabiv_v7a)

        System.out.println(String.format("EasyPrivacy => arm64-v8a size: %d", arm64_v8a.size()));
        // printSoFileList(arm64_v8a)

        System.out.println(String.format("EasyPrivacy => x86 size: %d", x86.size()));
        // printSoFileList(x86)

        System.out.println(String.format("EasyPrivacy => x86_64 size: %d", x86_64.size()));
        // printSoFileList(x86_64)

        System.out.println(String.format("EasyPrivacy => mips size: %d", mips.size()));
        // printSoFileList(mips)

        System.out.println(String.format("EasyPrivacy => mips_64 size: %d", mips_64.size()));
        // printSoFileList(mips_64)

        System.out.println("so in armeabi, but not in arm64-v8a:");
        diffSoFileList(armeabi, arm64_v8a);

        System.out.println("so in armeabiv-v7a, but not in arm64-v8a:");
        diffSoFileList(armeabiv_v7a, arm64_v8a);

        System.out.println("so in x86, but not in x86-64:");
        diffSoFileList(x86, x86_64);

        System.out.println("so in mips, but not in mips-64:");
        diffSoFileList(mips, mips_64);
    }

    /**
     * Print every so file's information.
     */
    private void printSoFileList(List<SoFile> soList) {
        if (!soList.isEmpty()) {
            int count = 0;
            for (SoFile so : soList) {
                if (++count >= 3) {
                    System.out.println("");
                    count = 0;
                }
                System.out.println(String.format("%s\t", so.toString()));
            }
            System.out.println("");
        }
    }

    /**
     * Print every so file's information in so32List but not in so64List.
     *
     * @param so32List 32-bit so file list.
     * @param so64List 64-bit so file list.
     */
    private void diffSoFileList(@Nonnull List<SoFile> so32List, @Nonnull List<SoFile> so64List) {
        if (so32List.isEmpty()) {
            System.out.println("\n");
            return;
        }
        Set<SoFile> so64Set = new HashSet<>();
        for (SoFile soFile : so64List) {
            so64Set.add(soFile);
        }
        int countSum = 0;
        for (SoFile so32 : so32List) {
            if (!so64Set.contains(so32)) {
                if (3 <= ++countSum) {
                    System.out.println("");
                    countSum = 0;
                }
                System.out.println(String.format("%s\t", so32.toString()));
            }
        }
    }
}
