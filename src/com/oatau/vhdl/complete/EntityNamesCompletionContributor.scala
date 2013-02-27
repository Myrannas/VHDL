package com.oatau.vhdl.complete

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.oatau.vhdl.psi.{VEntity, VhdlFile, VhdlTypes}
import com.oatau.vhdl.VhdlLanguage
import com.intellij.util.ProcessingContext
import com.oatau.vhdl.util.ElementUtil
import com.intellij.openapi.module.ModuleUtilCore

/**
 * User: Michael
 * Date: 24/02/13
 * Time: 11:54
 */
class EntityNamesCompletionContributor extends CompletionContributor{
  extend(CompletionType.BASIC,
    PlatformPatterns.psiElement().afterLeaf(PlatformPatterns.psiElement(VhdlTypes.T_OF)).withLanguage(VhdlLanguage.Language),
    new CompletionProvider[CompletionParameters]() {

    @Override
    def addCompletions(completionParameters:CompletionParameters , processingContext:ProcessingContext , completionResultSet:CompletionResultSet ) {
      val module = ModuleUtilCore.findModuleForPsiElement(completionParameters.getOriginalPosition)

      ElementUtil.allEntities(module).foreach(e=>completionResultSet.addElement(e.getLookupElement))
    }
  })
}
