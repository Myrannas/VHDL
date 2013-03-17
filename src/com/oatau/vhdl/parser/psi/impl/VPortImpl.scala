package com.oatau.vhdl.parser.psi.impl

import com.oatau.vhdl.parser.psi.VPort
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

/**
 * User: Michael
 * Date: 13/03/13
 * Time: 20:43
 */
class VPortImpl(node:ASTNode) extends ASTWrapperPsiElement(node) with VPort {

}
