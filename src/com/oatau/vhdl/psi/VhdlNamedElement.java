package com.oatau.vhdl.psi;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiNamedElement;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 21/02/13
 * Time: 6:10 PM
 */
public interface VhdlNamedElement extends PsiNamedElement {
    LookupElement getLookupElement();
}
