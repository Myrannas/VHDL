package com.oatau.vhdl.parser.psi

import com.intellij.psi.tree.IElementType
import com.oatau.vhdl.psi.SimpleTokenType

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 1/03/13
 * Time: 8:31 AM
 */
object ParserTypes {
  final val PACKAGE: IElementType = new SimpleTokenType("package")
  final val ENTITY: IElementType = new SimpleTokenType("entity")
  final val PORT: IElementType = new SimpleTokenType("port")

}
