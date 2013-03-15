package com.oatau.vhdl.parser.templating

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.{TokenSet, IElementType}

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 28/11/12
 * Time: 4:54 PM
 */
class InvalidParserTemplate(val builder: PsiBuilder) extends ParserTemplate {
  def expect(element: IElementType, error: String = "") = this

  def expectAndMarkSingle(element: IElementType, markAs: IElementType, error: String = "") = this

  def mark(element: IElementType)(block: (ParserTemplate) => Unit) = this


  def advance() = this

  def apply() = false

  def recoverWith(elements: (IElementType, Position)*): ParserTemplate = {
    val escapes = TokenSet.create(elements.map(_._1): _*)
    while (!builder.eof()) {
      if (escapes.contains(builder.getTokenType)) {
        if (elements.find(_._1 == builder.getTokenType).get._2 == After) {
          builder.advanceLexer()
        }
        return new ValidParserTemplate(builder)
      }

      builder.advanceLexer()
    }

    this
  }

  def expectIn(elements: TokenSet, error: String) = this

  def expectIn(elements: IElementType*) = this

  def expectInAndMarkSingle(element: TokenSet, markAs: IElementType, error: String) = this

  def when(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate) = this

  def parse(block: (ParserTemplate) => ParserTemplate) = this

  def doWhile(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate) = this

  def switch(block: (IElementType, ParserTemplate) => ParserTemplate) = this

  def error(errorMsg: String) = this

  def `while`(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate): ParserTemplate = this

  def current() = builder.getTokenType

  def currentText() = builder.getTokenText

  override def info(message: String) = super.info("invalid: %s".format(message))

  override def warn(message: String) = super.warn("invalid: %s".format(message))

  override def debug(message: String) = super.debug("invalid: %s".format(message))

  val inError = true

  def beginMark() = this

  def endMark(eType: IElementType) = this

  def optional(element: IElementType) = this
}