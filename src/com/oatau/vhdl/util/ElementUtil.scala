package com.oatau.vhdl.util

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.oatau.vhdl.VhdlFileType
import com.oatau.vhdl.psi.{VhdlFile, VEntity}
import com.intellij.psi.PsiManager

/**
 * User: Michael
 * Date: 24/02/13
 * Time: 11:53
 */
object ElementUtil {
  def entityByName(name:String, module:Module) = {
    allEntities(module).find(_.getName == name)
  }

  def allEntities(module: Module) : Seq[VEntity] = {
    val manager = PsiManager.getInstance(module.getProject)
    val files = allFiles(module)
    files.map(v => getEntities(manager,v)).flatMap(_.toSeq)
  }

  def getEntities(manager:PsiManager, file:VirtualFile) = manager.findFile(file).asInstanceOf[VhdlFile].entities

  def allFiles(module: Module) : Seq[VirtualFile] = {
    val instance = ModuleRootManager.getInstance(module)

    instance.getSourceRoots.map(_.getChildren).flatMap(_.toSeq).filter(_.getFileType == VhdlFileType.INSTANCE)
  }
}
