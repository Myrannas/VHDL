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
  final val ARCHITECTURE: IElementType = new SimpleTokenType("architecture")
  final val ENTITY_REFERENCE: IElementType = new SimpleTokenType("entity_reference")
  final val VARIABLE_REFERENCE: IElementType = new SimpleTokenType("variable_reference")
  final val PORT: IElementType = new SimpleTokenType("port")
  final val SIGNAL: IElementType = new SimpleTokenType("signal")
  final val PROCESS: IElementType = new SimpleTokenType("process")
  final val SENSITIVITY_LIST: IElementType = new SimpleTokenType("sensitivityList")
  final val TYPE : IElementType = new SimpleTokenType("type")
  final var LABEL = new SimpleTokenType("label")
  final var STATEMENT = new SimpleTokenType("statement")
  final val GENERIC = new SimpleTokenType("generic")
  final val CONSTANT = new SimpleTokenType("constant")
  final val FUNCTION_CALL = new SimpleTokenType("function call")
}
