package com.oatau.vhdl.psi;

import com.intellij.psi.tree.IElementType;
import com.oatau.vhdl.VhdlLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 3:20 PM
 */
public class SimpleTokenType extends IElementType {
    public SimpleTokenType(@NotNull @NonNls String debugName) {
        super(debugName, VhdlLanguage.Language);
    }
}
