package com.oatau.vhdl.parser.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import impl.{VArchitectureEntityRefImpl, VEntityImpl, VPortImpl}
import com.intellij.psi.PsiElement

/**
 * User: Michael
 * Date: 13/03/13
 * Time: 20:25
 */
object TreeFactory {
  def createElement(astNode:ASTNode) : PsiElement = astNode.getElementType match {
    case ParserTypes.PORT => new VPortImpl(astNode)
    case ParserTypes.ENTITY => new VEntityImpl(astNode)
    case ParserTypes.ENTITY_REFERENCE => new VArchitectureEntityRefImpl(astNode)
    case _ => new ASTWrapperPsiElement(astNode)
  }
}
