package com.oatau.vhdl.complete

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.scope.util.PsiScopesUtil
import com.oatau.vhdl.psi.{VhdlNamedElement, VhdlScope}
import com.intellij.util.ProcessingContext
import com.intellij.psi.scope.BaseScopeProcessor
import com.intellij.psi.{ResolveState, PsiElement}

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
}
