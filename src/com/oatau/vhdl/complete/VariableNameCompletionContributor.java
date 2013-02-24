package com.oatau.vhdl.complete;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.psi.scope.util.PsiScopesUtil;
import com.intellij.util.ProcessingContext;
import com.oatau.vhdl.psi.VhdlNamedElement;
import com.oatau.vhdl.psi.VhdlScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 22/02/13
 * Time: 4:16 PM
 */
public class VariableNameCompletionContributor extends CompletionContributor {
    public VariableNameCompletionContributor() {
        super();

        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull final CompletionResultSet completionResultSet) {


                PsiScopesUtil.treeWalkUp(new BaseScopeProcessor() {
                    @Override
                    public boolean execute(@NotNull PsiElement psiElement, ResolveState resolveState) {
                        if (psiElement instanceof VhdlScope) {
                            Iterable<VhdlNamedElement> variables = ((VhdlScope) psiElement).getVariables();
                            for (VhdlNamedElement variable : variables) {
                                completionResultSet.addElement(variable.getLookupElement());
                            }
                        }

                        return true;
                    }
                }, completionParameters.getOriginalPosition(), completionParameters.getOriginalFile());
            }
        });
    }
}
