package com.oatau.vhdl.parser.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import com.intellij.psi.PsiElement
import reflect.ClassTag

/**
 * User: Michael
 * Date: 17/03/13
 * Time: 11:55
 */
class VBaseScalaPsiElement(node:ASTNode) extends ASTWrapperPsiElement(node){
  def childByType(elementType:IElementType) : Option[PsiElement]= findChildByType(elementType) match {
    case null => None
    case e => Some(e)
  }

  def childByClass[T](implicit clazz: ClassTag[T]) : Option[T]= findChildByClass(clazz.erasure) match {
    case null => None
    case e => Some(e.asInstanceOf[T])
  }
}
