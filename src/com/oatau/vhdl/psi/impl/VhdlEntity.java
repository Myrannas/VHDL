package com.oatau.vhdl.psi.impl;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import com.oatau.vhdl.psi.*;
import com.oatau.vhdl.util.ElementUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import scala.Option;
import scala.Some;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 21/02/13
 * Time: 6:07 PM
 */
public class VhdlEntity {
    public static String getName(PsiElement e) {
        if (e instanceof VEntity) {
            return ((VEntity) e).getEntityName().getText();
        } else if (e instanceof VPort) {
            return ((VPort) e).getIdentifier().getText();
        } else if (e instanceof VArchitectureSignal) {
            return ((VArchitectureSignal) e).getIdentifier().getText();
        }

        return null;
    }

    public static PsiElement setName(PsiElement e, String name) throws IncorrectOperationException {
        return null;
    }

    public static LookupElement getLookupElement(PsiElement e) {
        if (e instanceof VEntity) {
            return LookupElementBuilder.create((VEntity)e).withIcon(PlatformIcons.INTERFACE_ICON);
        } else if (e instanceof VPort) {
            VPort port = (VPort) e;
            return LookupElementBuilder.create(port).withIcon(PlatformIcons.VARIABLE_ICON).withTypeText(port.getPortType().getText());
        } else if (e instanceof VArchitectureSignal) {
            VArchitectureSignal signal = (VArchitectureSignal) e;
            return LookupElementBuilder.create(signal).withIcon(PlatformIcons.VARIABLE_ICON).withTypeText(signal.getPortType().getText());
        }

        return null;
    }

    public static List<VhdlNamedElement> getVariables(VArchitecture e) {
        List<VhdlNamedElement> variables = new ArrayList<VhdlNamedElement>();
        String entityName = e.getEntityRef().getText();
        Module module = ModuleUtilCore.findModuleForPsiElement(e);

        Option<VEntity> entity = ElementUtil.entityByName(entityName, module);
        if (entity.isDefined()) {
            List<VPort> ports = entity.get().getPortList();
            variables.addAll(ports);
        }

        List<VArchitectureDeclarativeItem> itemList = e.getArchitectureDeclarativeItemList();
        for (VArchitectureDeclarativeItem item : itemList) {
            VArchitectureSignal signal = item.getArchitectureSignal();

            if (signal != null) {
                variables.add(signal);
            }
        }

        return variables;
    }
}
