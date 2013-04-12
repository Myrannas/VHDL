package com.oatau.vhdl.parser.templating

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.{TokenSet, IElementType}
import collection.immutable.Stack


class ValidParserTemplate(val builder: PsiBuilder) extends ParserTemplate {
  def current() = builder.getTokenType

  def currentText() = builder.getTokenText

  def fail = {
    markStack.foreach(m => m.drop())
    markStack = Stack()
    new InvalidParserTemplate(builder)
  }

  def expect(element: IElementType, error: String = "") = {
    if (builder.getTokenType != element) {
      builder.error(if (error == "") "Expected %s".format(element.toString) else error)
      fail
    } else {
      builder.advanceLexer()
      this
    }
  }

  def expectAndMarkSingle(element: IElementType, markAs: IElementType, error: String) = {
    if (builder.getTokenType != element) {
      builder.error(if (error == "") "Expected %s".format(element.toString) else error)
      fail
    } else {
      val mark = builder.mark()
      builder.advanceLexer()
      mark.done(markAs)
      this
    }
  }

  def mark(element: IElementType)(block: (ParserTemplate) => Unit) = {
    val mark = builder.mark()
    block(this)
    mark.done(element)
    this
  }

  def apply(): Boolean = true

  def rollbackToMark() = {
    markStack.head.rollbackTo()
    markStack = markStack.pop
    this
  }

  def dropMark() = {
    markStack.head.drop()
    markStack = markStack.pop
    this
  }

  def advance() = {
    builder.advanceLexer()
    this
  }

  def recoverWith(elements: (IElementType, Position)*) = this

  def expectIn(elements: TokenSet, error: String = "") = {
    if (!elements.contains(builder.getTokenType)) {
      builder.error(if (error == "") "Expected %s".format(elements.toString) else error)
      fail
    } else {
      builder.advanceLexer()
      this
    }
  }

  def expectIn(elements: IElementType*) = {
    if (!elements.contains(builder.getTokenType)) {
      builder.error("Expected %s".format(elements.foldLeft("")(_+","+_)))
      fail
    } else {
      builder.advanceLexer()
      this
    }
  }

  def expectInAndMarkSingle(element: TokenSet, markAs: IElementType, error: String) = {
    if (!element.contains(builder.getTokenType)) {
      builder.error(if (error == "") "Expected %s".format(element.toString) else error)
      fail
    } else {
      val mark = builder.mark()
      builder.advanceLexer()
      mark.done(markAs)
      this
    }
  }

  def when(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate) = {
    if (condition(builder.getTokenType)) {
      block(this)
    } else {
      this
    }
  }

  def parse(block: (ParserTemplate) => ParserTemplate) = block(this)

  def doWhile(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate): ParserTemplate = {
    var parser: ParserTemplate = this
    var first = true
    do {
      if (first) {
        first = false
      } else {
        builder.advanceLexer()
      }

      parser = block(parser)

      if (parser.inError) {
        return fail
      }
    } while (condition(builder.getTokenType) && !builder.eof())

    if (builder.eof()) return fail

    parser
  }

  def `while`(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate): ParserTemplate = {
    var parser: ParserTemplate = this
    var first = true
    while (condition(builder.getTokenType) && !builder.eof()) {
      parser = block(parser)

      if (parser.inError) {
        return fail
      }
    }

    if (builder.eof()) return fail

    parser
  }

  def map(block: (IElementType, ParserTemplate) => ParserTemplate) = block(builder.getTokenType, this)

  def switch(block: (IElementType, ParserTemplate) => ParserTemplate) = block(builder.getTokenType, this)

  def error(errorMsg: String) = {
    builder.error(errorMsg)
    builder.advanceLexer()
    fail
  }

  val inError = false

  var markStack: Stack[PsiBuilder.Marker] = new Stack[PsiBuilder.Marker]()

  def beginMark() = {
    markStack = markStack.push(builder.mark());
    this
  }

  def endMark(eType: IElementType) = {
    markStack.head.done(eType);
    markStack = markStack.pop;
    this
  }

  def optional(element: IElementType) = {
    if (element == builder.getTokenType) builder.advanceLexer()

    this
  }
}
