package com.oatau.vhdl.psi;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 22/02/13
 * Time: 4:22 PM
 */
public interface VhdlScope {
    Iterable<VhdlNamedElement> getVariables();
}
