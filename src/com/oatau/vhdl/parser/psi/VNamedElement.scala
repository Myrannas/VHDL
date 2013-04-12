package com.oatau.vhdl.parser.psi

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiNamedElement

/**
 * User: Michael
 * Date: 18/03/13
 * Time: 14:28
 */
trait VNamedElement extends PsiNamedElement {
  def getLookupElement: LookupElement
}
