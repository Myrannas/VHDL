package com.oatau.vhdl.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 3:58 PM
 */
public class VhdlLexer extends FlexAdapter {
    public VhdlLexer() {
        super(new _VhdlLexer((Reader)null));
    }
}
