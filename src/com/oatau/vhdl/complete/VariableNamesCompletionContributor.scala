package com.oatau.vhdl.complete

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.scope.util.PsiScopesUtil
import com.oatau.vhdl.psi.{VhdlNamedElement, VhdlScope}
import com.intellij.util.ProcessingContext
import com.intellij.psi.scope.BaseScopeProcessor
import com.intellij.psi.{ResolveState, PsiElement}
import com.oatau.vhdl.util.{ElementUtil, ScopeUtil}
import com.oatau.vhdl.parser.psi.ParserTypes
import com.oatau.vhdl.lexer.VhdlTypes
import com.intellij.openapi.module.ModuleUtilCore

/**
 * User: Michael
 * Date: 24/02/13
 * Time: 12:32
 */
class VariableNamesCompletionContributor extends CompletionContributor{
  extend(CompletionType.BASIC, PlatformPatterns.psiElement, new CompletionProvider[CompletionParameters] {
    protected def addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {

      ScopeUtil.treeWalkUp(completionParameters.getOriginalPosition,completionParameters.getOriginalFile, _ match {
          case v : VhdlScope =>
            import scala.collection.JavaConversions._
            v.getVariables.foreach(v => completionResultSet.addElement(v.getLookupElement))
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
