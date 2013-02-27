package com.oatau.vhdl.complete

import com.intellij.psi.PsiElement

/**
 * User: Michael
 * Date: 24/02/13
 * Time: 12:47
 */
object ScopeUtil {
  def treeWalkUp(start:PsiElement, end:PsiElement, method: PsiElement => Any) {
    if (start != end) {
      method(start)
      val nextElement = start.getParent
      treeWalkUp(nextElement,end,method)
    }
  }

}
