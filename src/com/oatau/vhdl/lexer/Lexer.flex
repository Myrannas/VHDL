package com.oatau.vhdl.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.oatau.vhdl.psi.VhdlTypes;

%%

%class _VhdlLexer
%implements FlexLexer
%unicode
%ignorecase
%function advance
%type IElementType
%eof{  return;
%eof}

//CRLF= \n | \r | \r\n
WHITE_SPACE_CHAR=[\ \n\r\t\f]
END_OF_LINE_COMMENT=("--")[^\r\n]*
//BANG_COMMENT = ("!")[^\r\n]*
//C_STYLE_COMMENT=("/*" [^"*"] {COMMENT_TAIL} ) | "/*"
//DOC_COMMENT = ("/**" {COMMENT_TAIL} ) | "/**"
//COMMENT_TAIL=( [^"*"]* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?
IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_]*
NUM_LIT = [0-9_]+
SIGNAL_LIT = "\'" [01] "\'"
//HEX_LIT = 0x[0-9a-fA-F]+
//REAL_LIT = ("-"\s*)? [0-9]+[.][0-9]+
STR_LIT = "\"" ([^\"]|"^\"")* "\""
//FORMAT_CODE = :#+(\.#+)?

%state IN_VALUE
%state IN_KEY_VALUE_SEPARATOR

%%

<YYINITIAL> {
"entity" { yybegin(YYINITIAL); return VhdlTypes.T_ENTITY;}
"architecture" { yybegin(YYINITIAL); return VhdlTypes.T_ARCHITECTURE;}
"library" { yybegin(YYINITIAL); return VhdlTypes.T_LIBRARY;}
"use" { yybegin(YYINITIAL); return VhdlTypes.T_USE;}


"of" { yybegin(YYINITIAL); return VhdlTypes.T_OF;}
"begin" { yybegin(YYINITIAL); return VhdlTypes.T_BEGIN;}
"is" { yybegin(YYINITIAL); return VhdlTypes.T_IS;}
"." { yybegin(YYINITIAL); return VhdlTypes.T_DOT;}

";" { yybegin(YYINITIAL); return VhdlTypes.T_SEMI;}
":" { yybegin(YYINITIAL); return VhdlTypes.T_COLON;}
"(" { yybegin(YYINITIAL); return VhdlTypes.T_LEFT_PAREN;}
")" { yybegin(YYINITIAL); return VhdlTypes.T_RIGHT_PAREN;}
"=" { yybegin(YYINITIAL); return VhdlTypes.T_EQU;}

"+" { yybegin(YYINITIAL); return VhdlTypes.T_ADD;}
"-" { yybegin(YYINITIAL); return VhdlTypes.T_SUB;}
"/" { yybegin(YYINITIAL); return VhdlTypes.T_DIV;}
"*" { yybegin(YYINITIAL); return VhdlTypes.T_MUL;}
"and" { yybegin(YYINITIAL); return VhdlTypes.T_AND;}
"or" { yybegin(YYINITIAL); return VhdlTypes.T_OR;}
"nand" { yybegin(YYINITIAL); return VhdlTypes.T_NAND;}
"nor" { yybegin(YYINITIAL); return VhdlTypes.T_NOR;}
"xor" { yybegin(YYINITIAL); return VhdlTypes.T_XOR;}
"xnor" { yybegin(YYINITIAL); return VhdlTypes.T_XNOR;}
"sll" { yybegin(YYINITIAL); return VhdlTypes.T_SLL;}
"srl" { yybegin(YYINITIAL); return VhdlTypes.T_SRL;}
"sla" { yybegin(YYINITIAL); return VhdlTypes.T_SLA;}
"sra" { yybegin(YYINITIAL); return VhdlTypes.T_SRA;}
"rol" { yybegin(YYINITIAL); return VhdlTypes.T_ROL;}
"ror" { yybegin(YYINITIAL); return VhdlTypes.T_ROR;}

"**" { yybegin(YYINITIAL); return VhdlTypes.T_EXP;}
"not" { yybegin(YYINITIAL); return VhdlTypes.T_NOT;}
"abs" { yybegin(YYINITIAL); return VhdlTypes.T_ABS;}
"mod" { yybegin(YYINITIAL); return VhdlTypes.T_MOD;}
"rem" { yybegin(YYINITIAL); return VhdlTypes.T_REM;}
"&" { yybegin(YYINITIAL); return VhdlTypes.T_CONCAT;}
"/=" { yybegin(YYINITIAL); return VhdlTypes.T_NE;}
"<" { yybegin(YYINITIAL); return VhdlTypes.T_LT;}
">" { yybegin(YYINITIAL); return VhdlTypes.T_GT;}
">=" { yybegin(YYINITIAL); return VhdlTypes.T_GTE;}

"process" { yybegin(YYINITIAL); return VhdlTypes.T_PROCESS;}

"port" { yybegin(YYINITIAL); return VhdlTypes.T_PORT;}
"end" { yybegin(YYINITIAL); return VhdlTypes.T_END;}
"when" { yybegin(YYINITIAL); return VhdlTypes.T_WHEN;}
"else" { yybegin(YYINITIAL); return VhdlTypes.T_ELSE;}
"others" { yybegin(YYINITIAL); return VhdlTypes.T_OTHERS;}
"case" { yybegin(YYINITIAL); return VhdlTypes.T_CASE;}
"next" { yybegin(YYINITIAL); return VhdlTypes.T_NEXT;}
"exit" { yybegin(YYINITIAL); return VhdlTypes.T_EXIT;}
"return" { yybegin(YYINITIAL); return VhdlTypes.T_RETURN;}
"variable" { yybegin(YYINITIAL); return VhdlTypes.T_VARIABLE;}
"block" { yybegin(YYINITIAL); return VhdlTypes.T_BLOCK;}
"pure" { yybegin(YYINITIAL); return VhdlTypes.T_PURE;}
"impure" { yybegin(YYINITIAL); return VhdlTypes.T_IMPURE;}
"function" { yybegin(YYINITIAL); return VhdlTypes.T_FUNCTION;}
"procedure" { yybegin(YYINITIAL); return VhdlTypes.T_PROCEDURE;}
"postponed" { yybegin(YYINITIAL); return VhdlTypes.T_POSTPONED;}
"component" { yybegin(YYINITIAL); return VhdlTypes.T_COMPONENT;}
"assert" { yybegin(YYINITIAL); return VhdlTypes.T_ASSERT;}
"report" { yybegin(YYINITIAL); return VhdlTypes.T_REPORT;}
"severity" { yybegin(YYINITIAL); return VhdlTypes.T_SEVERITY;}
"configuration" { yybegin(YYINITIAL); return VhdlTypes.T_CONFIGURATION;}
"constraint" { yybegin(YYINITIAL); return VhdlTypes.T_CONSTRAINT;}
"array" { yybegin(YYINITIAL); return VhdlTypes.T_ARRAY;}
"package" { yybegin(YYINITIAL); return VhdlTypes.T_PACKAGE;}
"type" { yybegin(YYINITIAL); return VhdlTypes.T_TYPE;}
"subtype" { yybegin(YYINITIAL); return VhdlTypes.T_SUBTYPE;}
"until" { yybegin(YYINITIAL); return VhdlTypes.T_UNTIL;}
"constant" { yybegin(YYINITIAL); return VhdlTypes.T_CONSTANT;}
"transport" { yybegin(YYINITIAL); return VhdlTypes.T_TRANSPORT;}
"reject" { yybegin(YYINITIAL); return VhdlTypes.T_REJECT;}
"group" { yybegin(YYINITIAL); return VhdlTypes.T_GROUP;}
"generic" { yybegin(YYINITIAL); return VhdlTypes.T_GENERIC;}

"loop" { yybegin(YYINITIAL); return VhdlTypes.T_LOOP;}
"for" { yybegin(YYINITIAL); return VhdlTypes.T_FOR;}
"while" { yybegin(YYINITIAL); return VhdlTypes.T_WHILE;}
"then" { yybegin(YYINITIAL); return VhdlTypes.T_THEN;}
"elsif" { yybegin(YYINITIAL); return VhdlTypes.T_ELSIF;}
"if" { yybegin(YYINITIAL); return VhdlTypes.T_IF;}
"open" { yybegin(YYINITIAL); return VhdlTypes.T_OPEN;}
"access" { yybegin(YYINITIAL); return VhdlTypes.T_ACCESS;}
"alias" { yybegin(YYINITIAL); return VhdlTypes.T_ALIAS;}
"new" { yybegin(YYINITIAL); return VhdlTypes.T_NEW;}
"attribute" { yybegin(YYINITIAL); return VhdlTypes.T_ATTRIBUTE;}
"inertial" { yybegin(YYINITIAL); return VhdlTypes.T_INERTIAL;}
"buffer" { yybegin(YYINITIAL); return VhdlTypes.T_BUFFER;}
"linkage" { yybegin(YYINITIAL); return VhdlTypes.T_LINKAGE;}
"with" { yybegin(YYINITIAL); return VhdlTypes.T_WITH;}
"select" { yybegin(YYINITIAL); return VhdlTypes.T_SELECT;}
"generate" { yybegin(YYINITIAL); return VhdlTypes.T_GENERATE;}
"map" { yybegin(YYINITIAL); return VhdlTypes.T_MAP;}
"signal" { yybegin(YYINITIAL); return VhdlTypes.T_SIGNAL;}
"literal" { yybegin(YYINITIAL); return VhdlTypes.T_LITERAL;}
"label" { yybegin(YYINITIAL); return VhdlTypes.T_LABEL;}
"file" { yybegin(YYINITIAL); return VhdlTypes.T_FILE;}
"to" { yybegin(YYINITIAL); return VhdlTypes.T_TO;}
"downto" { yybegin(YYINITIAL); return VhdlTypes.T_DOWNTO;}
"disconnect" { yybegin(YYINITIAL); return VhdlTypes.T_DISCONNECT;}
"after" { yybegin(YYINITIAL); return VhdlTypes.T_AFTER;}
"guarded" { yybegin(YYINITIAL); return VhdlTypes.T_GUARDED;}
"body" { yybegin(YYINITIAL); return VhdlTypes.T_BODY;}
"register" { yybegin(YYINITIAL); return VhdlTypes.T_REGISTER;}
"bus" { yybegin(YYINITIAL); return VhdlTypes.T_BUS;}
"unaffected" { yybegin(YYINITIAL); return VhdlTypes.T_UNAFFECTED;}
"shared" { yybegin(YYINITIAL); return VhdlTypes.T_SHARED;}
"all" { yybegin(YYINITIAL); return VhdlTypes.T_ALL;}
"<>" { yybegin(YYINITIAL); return VhdlTypes.T_NE;}
"," { yybegin(YYINITIAL); return VhdlTypes.T_CMA;}
"on" { yybegin(YYINITIAL); return VhdlTypes.T_ON;}

"null" { yybegin(YYINITIAL); return VhdlTypes.T_NULL;}
"in" { yybegin(YYINITIAL); return VhdlTypes.T_IN;}
"out" { yybegin(YYINITIAL); return VhdlTypes.T_OUT;}
"inout" { yybegin(YYINITIAL); return VhdlTypes.T_INOUT;}
"units" { yybegin(YYINITIAL); return VhdlTypes.T_UNITS;}

"<=" { yybegin(YYINITIAL); return VhdlTypes.T_NOBLOCK_ASSIGN;}
":=" { yybegin(YYINITIAL); return VhdlTypes.T_BLOCK_ASSIGN;}
"=>" { yybegin(YYINITIAL); return VhdlTypes.T_RARR;}

    {STR_LIT}                     { yybegin(YYINITIAL); return VhdlTypes.STRLIT; }
    {IDENTIFIER}                 { yybegin(YYINITIAL); return VhdlTypes.ID; }
    {END_OF_LINE_COMMENT}                 { yybegin(YYINITIAL); return VhdlTypes.COMMENT; }
    {NUM_LIT}                        {yybegin(YYINITIAL); return VhdlTypes.INTLIT;}
    {SIGNAL_LIT}                        {yybegin(YYINITIAL); return VhdlTypes.SIGLIT;}

}
{WHITE_SPACE_CHAR}+                      { return TokenType.WHITE_SPACE; }
.                                        { return TokenType.BAD_CHARACTER; }