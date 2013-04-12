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
class VArchitectureEntityRefImpl(node:ASTNode) extends ScalaPsiElement(node) {

  override def getReference: EntityReference = new EntityReference(this)


  def entityName = childByType(VhdlTypes.ID).map(_.getText).get
}

class EntityReference(node:VArchitectureEntityRefImpl) extends PsiReferenceBase[VArchitectureEntityRefImpl](node) {
  def resolve(): PsiElement = {
    entity.getOrElse(null)
  }

  def entity = ElementUtil.allEntities(ModuleUtilCore.findModuleForPsiElement(getElement)).find(_.getName == getElement.getText)

  def getVariants: Array[AnyRef] = Array()

  override def getRangeInElement: TextRange = TextRange.allOf(node.entityName)
}
