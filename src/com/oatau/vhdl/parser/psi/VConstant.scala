package com.oatau.vhdl.parser.psi


/**
 * User: Michael
 * Date: 18/03/13
 * Time: 14:29
 */
trait VConstant extends VNamedElement {
  def tpe : Option[VType]
}
