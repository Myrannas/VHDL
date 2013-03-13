package com.oatau.vhdl.parser.templating

import com.intellij.openapi.diagnostic.Logger
import org.apache.log4j.Level
import com.intellij.psi.tree.{TokenSet, IElementType}

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 28/11/12
 * Time: 4:54 PM
 */
class LoggingParserTemplate(var template: ParserTemplate) extends ParserTemplate {
  var logger = Logger.getInstance(template.getClass)
  logger.setLevel(Level.ALL)

  def wrap(template: ParserTemplate) = {
    if (this.template != template) {
      this.template = template;
      logger = Logger.getInstance(template.getClass)
    };
    this
  }

  def recoverWith(elements: (IElementType, Position)*) = {
    val tl = template.recoverWith()
    if (tl != template) logger.debug("Successfully recovered %s".format(current()))
    else logger.debug("Unable to recover from errornous state %s".format(current()))
    wrap(tl)
  }

  def expect(element: IElementType, error: String) = {
    logger.debug("[PARSE]: expected '%s' found '%s'. ('%s')".format(element.toString, template.current(), template.current()))
    wrap(template.expect(element, error))
  }

  def expectIn(elements: TokenSet, error: String) = {
    logger.debug("[PARSE]: expected in '%s' found '%s'. ('%s')".format(elements.toString, template.current(), template.current()))
    wrap(template.expectIn(elements, error))
  }

  def expectAndMarkSingle(element: IElementType, markAs: IElementType, error: String) = wrap(template.expectAndMarkSingle(element, markAs, error))

  def expectInAndMarkSingle(element: TokenSet, markAs: IElementType, error: String) = wrap(template.expectInAndMarkSingle(element, markAs, error))

  def mark(element: IElementType)(block: (ParserTemplate) => Unit) = wrap(template.mark(element)(block))

  def advance() = wrap(template.advance())

  def apply() = template.apply()

  def when(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate) = wrap(template.when(condition)(block))

  def parse(block: (ParserTemplate) => ParserTemplate) = wrap(template.parse(block))

  def doWhile(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate) = {
    logger.debug("Iterating through block")
    wrap(template.doWhile(condition)({
      pt => logger.debug("Checking element %s".format(condition(pt.current()))); block(pt)
    }))
  }

  def switch(block: (IElementType, ParserTemplate) => ParserTemplate) = {
    logger.debug("Switching on %s ('%s')".format(current(), currentText()))
    wrap(template.switch(block))
  }

  def error(errorMsg: String) = wrap(template.error(errorMsg))

  def `while`(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate) = wrap(template.`while`(condition)(block))

  def current() = template.current()

  def currentText() = template.currentText()

  val inError = false

  def beginMark() = wrap(template.beginMark())

  def endMark(eType: IElementType) = wrap(template.endMark(eType))

  def optional(element: IElementType) = this
}
