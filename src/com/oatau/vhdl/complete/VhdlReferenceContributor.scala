package com.oatau.vhdl.complete

import com.intellij.psi._
import com.intellij.util.ProcessingContext

/**
 * User: Michael
 * Date: 16/03/13
 * Time: 14:46
 */
class VhdlReferenceContributor extends PsiReferenceProvider{
  def getReferencesByElement(p1: PsiElement, p2: ProcessingContext): Array[PsiReference] = Array()
}
