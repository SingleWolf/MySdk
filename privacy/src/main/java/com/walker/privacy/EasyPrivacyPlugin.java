package com.walker.privacy;

import com.walker.privacy.bit64.Bit64Feature;
import com.walker.privacy.extension.PrivacyExt;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class EasyPrivacyPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // 1. Apply plugin extensions.
        applyExtension(project);
        // 2. Apply privacy features.
        applyFeature(project);
    }

    private void applyExtension(Project project) {
        project.getExtensions().create(PrivacyExt.TAG, PrivacyExt.class);
    }

    private void applyFeature(Project project) {
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                PrivacyExt privacyExt = project.getExtensions().findByType(PrivacyExt.class);
                if (privacyExt == null || !privacyExt.isEnable64Bit()) {
                    return;
                }
                // 2.1 Apply 64-bit architectures
                Bit64Feature feature = new Bit64Feature();
                feature.apply(project, privacyExt);
            }
        });
    }
}