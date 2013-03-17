package com.oatau.vhdl.parser

import com.intellij.lang.{ASTNode, PsiParser, PsiBuilder}
import psi.ParserTypes
import templating.{After, Before, ParserTemplate, ValidParserTemplate}
import com.intellij.psi.tree.{IElementType, TokenSet}
import com.oatau.vhdl.lexer.VhdlTypes

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
    case (ARCHITECTURE,t) => t.parse(parseArchitecture)
    case (token,t) => t.error("Expected use or entity or library or architecture")
  }

  def parse(builder: PsiBuilder) {
    while (!builder.eof()) {
      if (parseFileElement(new ValidParserTemplate(builder)).inError) {
        builder.advanceLexer()
      }
    }
  }


  def parseUse(template: ParserTemplate) = template
    .mark(ParserTypes.PACKAGE)(_.expect(USE).doWhile(_==DOT)(_.expectIn(ID,ALL)).recoverWith(SEMI->Before).expect(SEMI))

  def parseLibrary(template: ParserTemplate) = template
    .mark(ParserTypes.PACKAGE)(_.expect(LIBRARY).expect(ID).recoverWith(SEMI->Before).expect(SEMI))

  def parseEntity(template: ParserTemplate) = template
    .mark(ParserTypes.ENTITY)(_.expect(ENTITY).expect(ID).expect(IS).when(_==GENERIC)(_.parse(parseGenericEntity)).when(_==PORT)(_.parse(parsePortStatement)).recoverWith((END->Before)).expect(END).optional(ENTITY).optional(ID).recoverWith(SEMI->Before).expect(SEMI))

  def parseArchitecture(template: ParserTemplate) = template
    .mark(ParserTypes.ARCHITECTURE)(
      _.expect(ARCHITECTURE).expect(ID).expect(OF).expectAndMarkSingle(ID,ParserTypes.ENTITY_REFERENCE).expect(IS)
        .expect(BEGIN)
        .parse(parseConcurrentStatementList)
        .recoverWith((END->Before))
        .expect(END).optional(ARCHITECTURE).optional(ID)
        .recoverWith(SEMI->Before).expect(SEMI))

  def parseGenericEntity(template: ParserTemplate) = template
  def parsePortStatement(template: ParserTemplate) = template
    .expect(PORT).expect(LEFPAREN).when(_!=RIGHPAREN)(_.doWhile(_==SEMI)(_.parse(parsePort))).recoverWith((RIGHPAREN->Before)).expect(RIGHPAREN).expect(SEMI)

  def parsePort(template: ParserTemplate) = template
    .mark(ParserTypes.PORT)(_.expect(ID).expect(COLON).expectIn(TokenSet.create(IN,OUT,INOUT,BUFFER)).parse(parseType))

  def parseType(template: ParserTemplate) = template.expect(ID)

  def parseConcurrentStatementList(template:ParserTemplate) = template.`while`(_!=END)(_.parse(parseConcurrentStatement))

  def parseConcurrentStatement(template: ParserTemplate) = template.switch {
    case (PROCESS,p) => template.parse(parseProcess)
    case (_,p) => template.error("Unrecognised token")
  }

  def parseProcess(template: ParserTemplate) = template
    .mark(ParserTypes.PROCESS)(
      _.expect(PROCESS)
        .parse(parseSensitivityList)
        .parse(parseSignalList)
        .expect(BEGIN)
          .parse(parseSequentialStatementList)
        .expect(END)
        .recoverWith(SEMI->Before)
        .expect(SEMI))

  def parseSensitivityList(template: ParserTemplate) = template
    .when(_==LEFPAREN)(
      _.mark(ParserTypes.SENSITIVITY_LIST)(
        _.advance().doWhile(_==CMA)(_.expect(ID)).expect(RIGHPAREN).recoverWith(RIGHPAREN->After)
      )
  )

  def parseSignalList(template:ParserTemplate) = template.`while`(_!=BEGIN)(_.parse(parseSignal))
  def parseSignal(template:ParserTemplate) = template.expect(SIGNAL).endMark(ID).expect(SEMI).parse(parseType).when(_==BLOCK_ASSIGN)(_.advance().parse(parseLiteral))
  def parseSequentialStatementList(template:ParserTemplate) = template
  def parseLiteral(template:ParserTemplate) = template.expectIn(STRLIT,INTLIT,SIGLIT)

  def parse(elementType: IElementType, builder: PsiBuilder): ASTNode = {
    val rootMarker = builder.mark()
    parse(builder)
    rootMarker.done(elementType)
    builder.getTreeBuilt
  }
}
