package com.oatau.vhdl.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.oatau.vhdl.{VhdlFileType, VhdlLanguage}
import com.intellij.openapi.fileTypes.FileType
import com.oatau.vhdl.parser.psi.VEntity

/**
 * User: Michael
 * Date: 24/02/13
 * Time: 12:10
 */
class VhdlFile(fvp: FileViewProvider)
  extends PsiFileBase(fvp,VhdlLanguage.Language) {
  def getFileType: FileType = VhdlFileType.INSTANCE

  def entities = findChildrenByClass(classOf[VEntity])
}
