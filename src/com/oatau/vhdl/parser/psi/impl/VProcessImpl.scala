package com.oatau.vhdl.parser.psi.impl

import com.intellij.lang.ASTNode
import com.oatau.vhdl.parser.psi.{VSignal, VNamedElement, VProcess}


/**
 * User: Michael
 * Date: 18/03/13
 * Time: 15:17
 */
class VProcessImpl(node:ASTNode) extends ScalaPsiElement(node) with VProcess {
  def signals: Seq[VNamedElement] = childrenByClass[VSignal]
}
