package com.oatau.vhdl.parser.psi.impl

import com.oatau.vhdl.parser.psi.{VSignal, VNamedElement, VArchitecture}
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.codeInsight.lookup.{LookupElementBuilder, LookupElement}
import com.intellij.util.PlatformIcons

/**
 * User: Michael
 * Date: 18/03/13
 * Time: 14:54
 */
class VArchitectureImpl(node:ASTNode) extends ScalaPsiElement(node) with VArchitecture{
  def getLookupElement: LookupElement = LookupElementBuilder.create(this).withTypeText(entityReference.map(_.getName).getOrElse("")).withIcon(PlatformIcons.CLASS_ICON)

  def signals: Seq[VNamedElement] = childrenByClass[VSignal].union(entityReference.map(_.signals).getOrElse(Seq())).union(childrenByClass[VConstantImpl])

  def setName(p1: String): PsiElement = null

  def entityReference = childByClass[VArchitectureEntityRefImpl].map(_.getReference.entity).getOrElse(None)
}
