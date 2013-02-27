package com.oatau.vhdl.psi;

import com.intellij.psi.PsiElement;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 22/02/13
 * Time: 4:22 PM
 */
public interface VhdlScope extends PsiElement {
    Iterable<VhdlNamedElement> getVariables();
}
