package com.oatau.vhdl.parser.psi.impl

import com.oatau.vhdl.parser.psi.VType
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

/**
 * User: Michael
 * Date: 18/03/13
 * Time: 14:44
 */
class VTypeImpl(node:ASTNode) extends ASTWrapperPsiElement(node) with VType{
  def label: String = getText
}
