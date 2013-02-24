package com.oatau.vhdl.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.oatau.vhdl.VhdlFileType;
import com.oatau.vhdl.VhdlLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 3:54 PM
 */
public class VhdlFile extends PsiFileBase {
    public VhdlFile(@NotNull FileViewProvider fileViewProvider) {
        super(fileViewProvider, VhdlLanguage.Language);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return VhdlFileType.INSTANCE;
    }
}
