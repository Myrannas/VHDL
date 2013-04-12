package com.oatau.vhdl.parser.psi

import com.intellij.psi.tree.IElementType

/**
 * User: Michael
 * Date: 18/03/13
 * Time: 14:29
 */
trait VSignal extends VNamedElement {
  def direction : String
  def tpe : Option[VType]
}
