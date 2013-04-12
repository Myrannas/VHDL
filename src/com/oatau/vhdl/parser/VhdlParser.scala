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
    .mark(ParserTypes.ENTITY)(_.expect(ENTITY).expect(ID).expect(IS).when(_==GENERIC)(_.parse(parseGenericBlock)).when(_==PORT)(_.parse(parsePortStatement)).recoverWith((END->Before)).expect(END).optional(ENTITY).optional(ID).recoverWith(SEMI->Before).expect(SEMI))

  def parseArchitecture(template: ParserTemplate) = template
    .mark(ParserTypes.ARCHITECTURE)(
      _.expect(ARCHITECTURE).expect(ID).expect(OF).expectAndMarkSingle(ID,ParserTypes.ENTITY_REFERENCE).expect(IS)
        .parse(parseArchitectureDeclarations)
        .expect(BEGIN)
        .parse(parseConcurrentStatementList)
        .recoverWith((END->Before))
        .expect(END).optional(ARCHITECTURE).optional(ID)
        .recoverWith(SEMI->Before).expect(SEMI))

  def parseArchitectureDeclarations(template:ParserTemplate) = template
      .`while`(_!=BEGIN)(_.switch {
        case (SIGNAL,p) => p.parse(parseSignal)
        case (CONSTANT,p) => p.parse(parseConstant)
      })

  def parsePortStatement(template: ParserTemplate) = template
    .expect(PORT).expect(LEFPAREN).when(_!=RIGHPAREN)(_.doWhile(_==SEMI)(_.parse(parsePort))).recoverWith((RIGHPAREN->Before)).expect(RIGHPAREN).expect(SEMI)

  def parsePort(template: ParserTemplate) = template
    .mark(ParserTypes.PORT)(_.expect(ID).expect(COLON).expectIn(TokenSet.create(IN,OUT,INOUT,BUFFER)).parse(parseType))

  def parseType(template: ParserTemplate) = template.beginMark().expect(ID).when(_==LEFPAREN)(_.advance().parse(parseExpression).expectIn(TO,DOWNTO).parse(parseExpression).expect(RIGHPAREN)).endMark(ParserTypes.TYPE)

  def parseConcurrentStatementList(template:ParserTemplate) = template.`while`(_!=END)(_.parse(parseConcurrentStatement))

  def parseConcurrentStatement(template: ParserTemplate) = template.mark(ParserTypes.STATEMENT)(_.switch {
    case (ID,p) => p.beginMark().advance().switch {
      case (COLON,pp) => pp.advance().endMark(ParserTypes.LABEL).parse(parseConcurrentStatementTail)
      case (_,pp) => pp.rollbackToMark().parse(parseConcurrentStatementTail)
    }
    case (_,p) => p.parse(parseConcurrentStatementTail)
  })

  def parseConcurrentStatementTail(template: ParserTemplate) = template.switch {
    case (PROCESS,p) => template.parse(parseProcess)
    case (_,p) => p.parse(parseConcurrentAssignment)
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

  def parseSignalList(template:ParserTemplate) = template
    .`while`(_!=BEGIN)(
      _.parse(parseSignal)
    )

  def parseSignal(template:ParserTemplate) = template
    .mark(ParserTypes.SIGNAL)(_
      .expect(SIGNAL)
      .doWhile(_==CMA)(_.expect(ID))
      .expect(COLON)
      .parse(parseType)
      .when(_==BLOCK_ASSIGN)(
        _.advance().parse(parseExpression)
    )).recoverWith(SEMI->Before).expect(SEMI)

  def parseConstant(template:ParserTemplate) = template
    .mark(ParserTypes.CONSTANT)(_
    .expect(CONSTANT)
    .expect(ID)
    .expect(COLON)
    .parse(parseType)
    .when(_==BLOCK_ASSIGN)(
    _.advance().parse(parseExpression)
  )).recoverWith(SEMI->Before).expect(SEMI)

  def parseSequentialStatementList(template:ParserTemplate) = template
  def parseLiteral(template:ParserTemplate) = template.expectIn(STRLIT,INTLIT,SIGLIT)

  def parseConcurrentAssignment(template:ParserTemplate) = template
    .parse(parseSignalReference)
    .expect(NOBLOCK_ASSIGN)
    .parse(parseExpression)
    .when(_==WHEN)(_.advance().parse(parseExpression)
    .`while`(_==ELSE)(
      _.advance().parse(parseExpression).when(_==WHEN)(_.expect(WHEN).parse(parseExpression))
//       .when(_==AFTER)(
//        _.parse(parseTimeExpression)
//      )
  )).recoverWith(SEMI->Before).expect(SEMI)


  def parse(elementType: IElementType, builder: PsiBuilder): ASTNode = {
    builder.setDebugMode(true)
    val rootMarker = builder.mark()
    parse(builder)
    rootMarker.done(elementType)
    builder.getTreeBuilt
  }

  def parseSignalReference(template:ParserTemplate) = template
    .expectAndMarkSingle(ID,ParserTypes.VARIABLE_REFERENCE)


  def parseExpression(template:ParserTemplate) : ParserTemplate = template.parse(parseLogicalExpression)

  def parseLogicalExpression(template:ParserTemplate) = template
    .parse(parseRelationalExpression).switch{
    case (NAND,p) => p.advance().parse(parseRelationalExpression)
    case (NOR,p) => p.advance().parse(parseRelationalExpression)
    case (_,p) => p.`while`(TokenSet.create(AND,OR,XOR,XNOR).contains)(_.advance().parse(parseRelationalExpression))
  }

  def parseRelationalExpression(template: ParserTemplate) = template
    .parse(parseShiftExpression).when(TokenSet.create(EQU,NE,LT,NOBLOCK_ASSIGN,GT,GTE).contains)(_.advance().parse(parseShiftExpression))

  def parseShiftExpression(template: ParserTemplate) = template
    .parse(parseAdditiveExpression).when(TokenSet.create(EQU,NE,LT,NOBLOCK_ASSIGN,GT,GTE).contains)(_.advance().parse(parseAdditiveExpression))

  def parseAdditiveExpression(template: ParserTemplate) :ParserTemplate = template
    .doWhile(TokenSet.create(ADD,SUB,CONCAT).contains)(_.parse(parseMultiplicativeExpression))

  def parseMultiplicativeExpression(template: ParserTemplate) :ParserTemplate = template
    .doWhile(TokenSet.create(MUL,DIV,MOD,REM).contains)(_.parse(parseExponentialExpression))

  def parseExponentialExpression(template: ParserTemplate) = template.switch {
    case (ABS,p) => p.advance().parse(parseExpressionElement)
    case (NOT,p) => p.advance().parse(parseExpressionElement)
    case (LEFPAREN,p) => p.advance().parse(parseExpression).recoverWith(RIGHPAREN->Before).expect(RIGHPAREN)
    case (_,p) => p.parse(parseExpressionElement).`while`(_==EXP)(_.parse(parseExpressionElement))
  }

  def parseExpressionElement(template:ParserTemplate) = template.switch {
    case (ID,p) => p.beginMark().advance().switch{
      case (LEFPAREN,pp) => pp.parse(parseFunctionCall).endMark(ParserTypes.FUNCTION_CALL)
      case (_,pp) => p.endMark(ParserTypes.VARIABLE_REFERENCE)
    }
//    case (LEFPAREN,p) => p.beginMark().
    case (INTLIT,p) => p.expect(INTLIT)
    case (SIGLIT,p) => p.expect(SIGLIT)
    case (STRLIT,p) => p.expect(STRLIT)
    case (a,p) => throw new Exception(a.toString)
  }

  def parseFunctionCall(template:ParserTemplate) = template
      .expect(LEFPAREN).when(_!=RIGHPAREN)(_.doWhile(_==CMA)(_.parse(parseExpression))).expect(RIGHPAREN).recoverWith(RIGHPAREN->After)
  
  def parseGenericBlock(template:ParserTemplate) = template
    .expect(GENERIC)
    .expect(LEFPAREN)
    .when(_!=RIGHPAREN)(_.doWhile(_==SEMI)(_.parse(parseGeneric))).recoverWith((RIGHPAREN->Before)).expect(RIGHPAREN).expect(SEMI)
  
  def parseGeneric(template:ParserTemplate) = template
    .mark(ParserTypes.GENERIC)(_.expect(ID).expect(COLON).expect(ID).when(_==BLOCK_ASSIGN)(_.advance().parse(parseExpression)))

//  def parseExpressionIdentifiedElement(template: ParserTemplate) = template.beginMark().advance().switch {
//    case (DOT,p) =>
//  }

}
