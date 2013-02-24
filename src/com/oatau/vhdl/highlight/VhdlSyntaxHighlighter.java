package com.oatau.vhdl.highlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.oatau.vhdl.lexer.VhdlLexer;
import com.oatau.vhdl.psi.VhdlTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 4:34 PM
 */
public class VhdlSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("VHDL_KEYWORD", SyntaxHighlighterColors.KEYWORD);

    public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey("VHDL_COMMENT", SyntaxHighlighterColors.LINE_COMMENT);

    private static final TextAttributesKey[] KEYWORDS_KEYS = new TextAttributesKey[]{KEYWORD};

    private static final TextAttributesKey[] COMMENTS_KEYS = new TextAttributesKey[]{COMMENT};

    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    private static TokenSet keywords = TokenSet.create(VhdlTypes.T_ENTITY, VhdlTypes.T_IS, VhdlTypes.T_END, VhdlTypes.T_IN, VhdlTypes.T_INOUT,
            VhdlTypes.T_OUT, VhdlTypes.T_LIBRARY, VhdlTypes.T_USE, VhdlTypes.T_ARCHITECTURE, VhdlTypes.T_BEGIN, VhdlTypes.T_OF, VhdlTypes.T_PORT,
            VhdlTypes.T_PROCESS, VhdlTypes.T_IF, VhdlTypes.T_CASE, VhdlTypes.T_ELSIF, VhdlTypes.T_ELSE, VhdlTypes.T_SIGNAL, VhdlTypes.T_THEN);

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new VhdlLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        if (keywords.contains(iElementType)) {
            return KEYWORDS_KEYS;
        }

        if (iElementType.equals(VhdlTypes.COMMENT)) {
            return COMMENTS_KEYS;
        }

        return EMPTY_KEYS;
    }
}
