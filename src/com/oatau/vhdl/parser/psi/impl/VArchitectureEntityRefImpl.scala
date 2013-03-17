package com.oatau.vhdl.parser.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.{ElementManipulator, PsiElement, PsiReferenceBase, PsiReference}
import com.oatau.vhdl.util.ElementUtil
import com.intellij.openapi.module.ModuleUtilCore
import com.oatau.vhdl.lexer.VhdlTypes
import com.intellij.openapi.util.TextRange

/**
 * User: Michael
 * Date: 16/03/13
 * Time: 15:07
 */
class VArchitectureEntityRefImpl(node:ASTNode) extends VBaseScalaPsiElement(node)  {

  override def getReference: PsiReference = new PsiReferenceBase[VArchitectureEntityRefImpl](this) {
    def resolve(): PsiElement = {
      ElementUtil.allEntities(ModuleUtilCore.findModuleForPsiElement(getElement)).find(_.getName == getElement.getText).getOrElse(null)
    }

    def getVariants: Array[AnyRef] = Array()

    override def getRangeInElement: TextRange = TextRange.allOf(entityName)
  }


  def entityName = childByType(VhdlTypes.ID).map(_.getText).get
}
