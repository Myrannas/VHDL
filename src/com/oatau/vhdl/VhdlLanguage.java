package com.oatau.vhdl;

import com.intellij.lang.Language;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 3:18 PM
 */
public class VhdlLanguage extends Language {
    protected VhdlLanguage() {
        super("Vhdl");
    }

    public static VhdlLanguage Language = new VhdlLanguage();
}
