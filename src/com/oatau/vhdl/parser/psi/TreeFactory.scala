package com.oatau.vhdl.parser.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement

/**
 * User: Michael
 * Date: 13/03/13
 * Time: 20:25
 */
object TreeFactory {
  def createElement(astNode:ASTNode) = astNode.getElementType match {
    case _ => new ASTWrapperPsiElement(astNode)
  }
}
