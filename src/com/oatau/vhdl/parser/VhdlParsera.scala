package com.oatau.vhdl.parser

import com.intellij.lang.PsiBuilder
import psi.ParserTypes
import templating.{Before, ParserTemplate, ValidParserTemplate}
import com.intellij.psi.tree.TokenSet

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 1/03/13
 * Time: 8:14 AM
 */
class VhdlParsera(val builder: PsiBuilder) {
  import com.oatau.vhdl.lexer.VhdlTypes._

  def parse() : Boolean = buildFile(builder)


  def parseUse(template: ParserTemplate) = template
    .expect(USE).mark(ParserTypes.PACKAGE)(_.doWhile(_==DOT)(_.expect(ID))).recoverWith((SEMI->Before)).expect(SEMI)

  def parseEntity(template: ParserTemplate) = template
    .expect(ENTITY).mark(ParserTypes.ENTITY)(_.expect(ID).expect(IS).when(_==GENERIC)(_.parse(parseGenericEntity)).when(_==PORT)(_.parse(parsePortStatement)).recoverWith((END->Before)).expect(END).optional(ENTITY).optional(ID)).recoverWith((SEMI->Before)).expect(SEMI)

  def parseGenericEntity(template: ParserTemplate) = template
  def parsePortStatement(template: ParserTemplate) = template
    .expect(PORT).expect(LEFPAREN).when(_!=RIGHPAREN)(_.doWhile(_==';')(_.parse(parsePort))).recoverWith((RIGHPAREN->Before)).expect(LEFPAREN).expect(SEMI)

  def parsePort(template: ParserTemplate) = template
    .mark(ParserTypes.PORT)(_.expect(ID).expectIn(TokenSet.create(IN,OUT,INOUT,BUFFER)).parse(parseType))

  def parseType(template: ParserTemplate) = template.expect(ID)

  def buildFile(b:PsiBuilder): Boolean = {
    true
  }
}
