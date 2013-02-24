package com.oatau.vhdl.psi.impl;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import com.oatau.vhdl.psi.VhdlNamedElement;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 21/02/13
 * Time: 6:07 PM
 */
public class VhdlEntity extends VEntityImpl implements VhdlNamedElement {
    public VhdlEntity(@NotNull ASTNode astNode) {
        super(astNode);
    }


    @Override
    public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
        return null;
    }

    @Override
    public String getName() {
        return getEntityName().getText();
    }

    @Override
    public LookupElement getLookupElement() {
        return LookupElementBuilder.create(this).withIcon(PlatformIcons.INTERFACE_ICON);
    }
}
