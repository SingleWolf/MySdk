package com.walker.privacy.bit64;

import com.walker.privacy.extension.PrivacyExt;

import org.apache.tools.ant.taskdefs.condition.Os;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;
import java.util.function.Consumer;

public class Bit64Feature {

    public void apply(Project project, PrivacyExt privacyExt) {
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                // 1. Find the task merge[BuildVariants]NativeLibs
                Task mergeNativeTask = getMergeNativeTask(project, privacyExt.getTaskName());
                if (null == mergeNativeTask) {
                    System.out.println("###### EasyPrivacy => mergeNativeTask is null ######");
                    return;
                } else {
                    System.out.println(String.format("###### EasyPrivacy => mergeNativeTask is %s ######", mergeNativeTask));
                }

                // 2. Create detect task.
                Task task = project.getTasks().create("support64BitAbi");
                task.setGroup("privacy");
                task.dependsOn(mergeNativeTask);
                Task finalMergeNativeTask = mergeNativeTask;
                task.doFirst(new Action<Task>() {
                    @Override
                    public void execute(Task task) {
                        System.out.println("###### EasyPrivacy => Support 64-bit abi start ######");
                        SoFileList soList = new SoFileList();
                        // 2.1 Find so file recursively.
                        finalMergeNativeTask.getInputs().getFiles().forEach(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                findSoFile(file, soList);
                            }
                        });
                        // 2.2 Print 64-bit abi supported status.
                        soList.printlnResult();
                    }
                });
            }
        });
    }

    private Task getMergeNativeTask(Project project, String taskName) {
        Task mergeNativeTask = null;
        mergeNativeTask = project.getTasks().findByName("mergeDebugNativeLibs");
        if (mergeNativeTask == null && !taskName.isEmpty()) {
            mergeNativeTask = project.getTasks().findByName(taskName);
        }
        return mergeNativeTask;
    }

    /**
     * Find so file recursively.
     */
    void findSoFile(File file, SoFileList soList) {
        if (null == file) {
            return;
        }
        if (file.isDirectory()) {
            // recursively
            for (File f : file.listFiles()) {
                findSoFile(f, soList);
            }
        } else if (file.getAbsolutePath().endsWith(".so")) {
            System.out.println(String.format("EasyPrivacy => so: %s", file.getAbsolutePath()));

            SoFile so = generateSoInfo(file);

            if (so.soPath.contains("armeabi-v7a")) {
                soList.armeabiv_v7a.add(so);
            } else if (so.soPath.contains("armeabi")) {
                soList.armeabi.add(so);
            } else if (so.soPath.contains("arm64-v8a")) {
                soList.arm64_v8a.add(so);
            } else if (so.soPath.contains("x86_64")) {
                soList.x86_64.add(so);
            } else if (so.soPath.contains("x86")) {
                soList.x86.add(so);
            } else if (so.soPath.contains("mips64")) {
                soList.mips_64.add(so);
            } else if (so.soPath.contains("mips")) {
                soList.mips.add(so);
            }
        }
    }

    /**
     * Generate information for each so file.
     */
    private SoFile generateSoInfo(File file) {
        String filePath = file.getAbsolutePath();

        SoFile so = new SoFile();
        so.soPath = filePath;
        so.soName = file.getName();

        if (filePath.contains("merged_jni_libs") || filePath.contains("library_jni")) {
            so.pomName = "";
        } else {
            String separator = File.separator;
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                separator = "\\\\";
            }
            String[] dirPath = filePath.split(separator);
            so.pomName = dirPath[dirPath.length - 4];
        }
        return so;
    }
}
