package com.oatau.vhdl.parser.psi.impl

import com.oatau.vhdl.parser.psi.{VType, VSignal, VPort}
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import com.intellij.psi.PsiElement
import com.intellij.codeInsight.lookup.{LookupElementBuilder, LookupElement}
import com.oatau.vhdl.lexer.VhdlTypes
import com.intellij.util.PlatformIcons

/**
 * User: Michael
 * Date: 13/03/13
 * Time: 20:43
 */
class VPortImpl(node:ASTNode) extends ScalaPsiElement(node) with VSignal {
  def getLookupElement: LookupElement = LookupElementBuilder.create(this).withIcon(icon).withTypeText(s"$direction ${tpe.map(_.label).getOrElse("")}")

  def setName(p1: String): PsiElement = null

  def icon = direction match {
    case "in" => PlatformIcons.VARIABLE_READ_ACCESS
    case "inout" => PlatformIcons.VARIABLE_RW_ACCESS
    case "out" => PlatformIcons.VARIABLE_WRITE_ACCESS
    case _ => PlatformIcons.VARIABLE_ICON
  }


  override def getName: String = childByType(VhdlTypes.ID).map(_.getText).getOrElse("")

  def direction: String = childByType(VhdlTypes.DIRECTION).map(_.getText.toLowerCase).getOrElse("")

  def tpe : Option[VType] = childByClass[VType]
}
