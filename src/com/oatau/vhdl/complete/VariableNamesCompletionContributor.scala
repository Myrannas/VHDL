package com.oatau.vhdl.complete

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.oatau.vhdl.util.ScopeUtil
import com.oatau.vhdl.parser.psi.{VScope, ParserTypes}
import com.oatau.vhdl.lexer.VhdlTypes

/**
 * User: Michael
 * Date: 24/02/13
 * Time: 12:32
 */
class VariableNamesCompletionContributor extends CompletionContributor{
  extend(CompletionType.BASIC, PlatformPatterns.psiElement, new CompletionProvider[CompletionParameters] {
    protected def addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {

      ScopeUtil.treeWalkUp(completionParameters.getOriginalPosition,completionParameters.getOriginalFile, _ match {
          case v : VScope =>
            v.signals.foreach(v => completionResultSet.addElement(v.getLookupElement))
            true
          case _ => true
        })

    }
  })

  extend(CompletionType.BASIC,
    PlatformPatterns.psiElement
      .inside(PlatformPatterns.psiElement(ParserTypes.SENSITIVITY_LIST))
      .andOr(
        PlatformPatterns.psiElement().afterLeaf(PlatformPatterns.psiElement(VhdlTypes.LEFPAREN)),
        PlatformPatterns.psiElement().afterLeaf(PlatformPatterns.psiElement(VhdlTypes.CMA))
    ),new CompletionProvider[CompletionParameters]() {
    def addCompletions(completionParameters: CompletionParameters, p2: ProcessingContext, completionResultSet: CompletionResultSet) {

    }
  })
}
