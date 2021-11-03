@file:Suppress("UnstableApiUsage")

package com.jetbrains.rider.plugins.dotnetwatch.run

import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.openapi.project.Project
import com.intellij.workspaceModel.ide.WorkspaceModel
import com.jetbrains.rider.projectView.workspace.getProjectModelEntities
import com.jetbrains.rider.projectView.workspace.isUnloadedProject
import java.nio.file.Path

class DotNetWatchRunConfigurationOptions : RunConfigurationOptions() {

    private var projectFilePathOption = string("").provideDelegate(this, "projectFilePath")
    private var projectTfmOption = string("").provideDelegate(this, "projectTfm")
    private var exePathOption = string("").provideDelegate(this, "exePath")
    private var programParametersOption = string("").provideDelegate(this, "programParameters")
    private var workingDirectoryOption = string("").provideDelegate(this, "workingDirectory")
    private var envsOption = map<String, String>().provideDelegate(this, "envs")
    private var isPassParentEnvsOption = property(true).provideDelegate(this, "isPassParentEnvs")
    private var useExternalConsoleOption = property(false).provideDelegate(this, "useExternalConsole")
    private var isVerboseOption = property(false).provideDelegate(this, "isVerbose")

    var projectFilePath: String
        get() = projectFilePathOption.getValue(this) ?: ""
        set(value) = projectFilePathOption.setValue(this, value)

    var projectTfm: String
        get() = projectTfmOption.getValue(this) ?: ""
        set(value) = projectTfmOption.setValue(this, value)

    var exePath: String
        get() = exePathOption.getValue(this) ?: ""
        set(value) = exePathOption.setValue(this, value)

    var programParameters: String
        get() = programParametersOption.getValue(this) ?: ""
        set(value) = programParametersOption.setValue(this, value)

    var workingDirectory: String
        get() = workingDirectoryOption.getValue(this) ?: ""
        set(value) = workingDirectoryOption.setValue(this, value)

    var envs: Map<String, String>
        get() = envsOption.getValue(this)
        set(value) = envsOption.setValue(this, value.toMutableMap())

    var isPassParentEnvs: Boolean
        get() = isPassParentEnvsOption.getValue(this)
        set(value) = isPassParentEnvsOption.setValue(this, value)

    var useExternalConsole: Boolean
        get() = useExternalConsoleOption.getValue(this)
        set(value) = useExternalConsoleOption.setValue(this, value)

    var isVerbose: Boolean
        get() = isVerboseOption.getValue(this)
        set(value) = isVerboseOption.setValue(this, value)

    fun isUnloadedProject(project: Project) = WorkspaceModel.getInstance(project)
        .getProjectModelEntities(Path.of(projectFilePath), project)
        .any { it.isUnloadedProject() }
}