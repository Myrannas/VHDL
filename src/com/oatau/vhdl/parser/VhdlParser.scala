package com.oatau.vhdl.parser

import com.intellij.lang.{ASTNode, PsiParser, PsiBuilder}
import psi.ParserTypes
import templating.{Before, ParserTemplate, ValidParserTemplate}
import com.intellij.psi.tree.{IElementType, TokenSet}

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 1/03/13
 * Time: 8:14 AM
 */
class VhdlParser() extends PsiParser{
  import com.oatau.vhdl.lexer.VhdlTypes._

  def parseFileElement(template: ValidParserTemplate) = template.switch{
    case (USE,t) => t.parse(parseUse)
    case (ENTITY,t) => t.parse(parseEntity)
    case (LIBRARY,t) => t.parse(parseLibrary)
    case (token,t) => t.error("Expected use or entity or library")
  }.recoverWith(SEMI->Before).expect(SEMI)

  def parse(builder: PsiBuilder) {
    while (!builder.eof()) {
      if (parseFileElement(new ValidParserTemplate(builder)).inError) {
        builder.advanceLexer()
      }
    }
  }


  def parseUse(template: ParserTemplate) = template
    .expect(USE).mark(ParserTypes.PACKAGE)(_.doWhile(_==DOT)(_.expectIn(ID,ALL)))

  def parseLibrary(template: ParserTemplate) = template
    .expect(LIBRARY).mark(ParserTypes.PACKAGE)(_.expect(ID))

  def parseEntity(template: ParserTemplate) = template
    .expect(ENTITY).mark(ParserTypes.ENTITY)(_.expect(ID).expect(IS).when(_==GENERIC)(_.parse(parseGenericEntity)).when(_==PORT)(_.parse(parsePortStatement)).recoverWith((END->Before)).expect(END).optional(ENTITY).optional(ID))

  def parseGenericEntity(template: ParserTemplate) = template
  def parsePortStatement(template: ParserTemplate) = template
    .expect(PORT).expect(LEFPAREN).when(_!=RIGHPAREN)(_.doWhile(_==SEMI)(_.parse(parsePort))).recoverWith((RIGHPAREN->Before)).expect(RIGHPAREN).expect(SEMI)

  def parsePort(template: ParserTemplate) = template
    .mark(ParserTypes.PORT)(_.expect(ID).expect(COLON).expectIn(TokenSet.create(IN,OUT,INOUT,BUFFER)).parse(parseType))

  def parseType(template: ParserTemplate) = template.expect(ID)

  def parse(elementType: IElementType, builder: PsiBuilder): ASTNode = {
    val rootMarker = builder.mark()
    parse(builder)
    rootMarker.done(elementType)
    builder.getTreeBuilt
  }
}
