package com.oatau.vhdl.parser.psi.impl

import com.oatau.vhdl.parser.psi.{VType, VSignal}
import com.intellij.codeInsight.lookup.{LookupElementBuilder, LookupElement}
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.oatau.vhdl.lexer.VhdlTypes
import com.intellij.util.PlatformIcons

/**
 * User: Michael
 * Date: 18/03/13
 * Time: 14:30
 */
class VSignalImpl(node:ASTNode) extends ScalaPsiElement(node) with VSignal {
  def getLookupElement: LookupElement = LookupElementBuilder.create(this).withIcon(PlatformIcons.VARIABLE_ICON).withTypeText(tpe.map(_.label).getOrElse(""))

  def setName(p1: String): PsiElement = null

  override def getName: String = childByType(VhdlTypes.ID).map(_.getText).getOrElse(null)

  def direction: String = "inout"

  def tpe: Option[VType] = childByClass[VType]
}
