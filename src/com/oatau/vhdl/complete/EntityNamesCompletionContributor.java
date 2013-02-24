package com.oatau.vhdl.complete;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.oatau.vhdl.VhdlLanguage;
import com.oatau.vhdl.psi.VElement;
import com.oatau.vhdl.psi.VEntity;
import com.oatau.vhdl.psi.VhdlFile;
import com.oatau.vhdl.psi.VhdlTypes;
import com.oatau.vhdl.psi.impl.VhdlEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 21/02/13
 * Time: 9:10 AM
 */
public class EntityNamesCompletionContributor extends CompletionContributor {

    public EntityNamesCompletionContributor() {
        super();    //To change body of overridden methods use File | Settings | File Templates.

        extend(CompletionType.BASIC, PlatformPatterns.psiElement().afterLeaf(PlatformPatterns.psiElement(VhdlTypes.T_OF)).withLanguage(VhdlLanguage.Language), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
                VhdlFile file = (VhdlFile) completionParameters.getOriginalFile();
                VElement[] entities = file.findChildrenByClass(VElement.class);

                for (int i = 0; i < entities.length; i++) {
                    VEntity entity = entities[i].getEntity();

                    if (entity != null && entity instanceof VhdlEntity) {
                        completionResultSet.addElement(((VhdlEntity) entity).getLookupElement());
                    }
                }


            }
        });
    }
}
