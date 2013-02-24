package com.oatau.vhdl;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 3:23 PM
 */
public class VhdlFileType extends LanguageFileType {
    public static final VhdlFileType INSTANCE = new VhdlFileType();

    protected VhdlFileType() {
        super(VhdlLanguage.Language);
    }

    @NotNull
    @Override
    public String getName() {
        return "Vhdl File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Vhdl language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "vhdl";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return VhdlIcons.FILE;
    }
}
