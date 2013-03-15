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
//C_STYLE_COMMENT=("/*" [^"*"] {COMMENTAIL} ) | "/*"
//DOC_COMMENT = ("/**" {COMMENTAIL} ) | "/**"
//COMMENTAIL=( [^"*"]* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?
IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_]*
NUM_LIT = [0-9_]+
SIGNAL_LIT = "\'" [01] "\'"
//HEX_LIT = 0x[0-9a-fA-F]+
//REAL_LIT = ("-"\s*)? [0-9]+[.][0-9]+
STR_LIT = "\"" ([^\"]|"^\"")* "\""
//FORMACODE = :#+(\.#+)?

%state IN_VALUE
%state IN_KEY_VALUE_SEPARATOR

%%

<YYINITIAL> {
"entity" { yybegin(YYINITIAL); return VhdlTypes.ENTITY;}
"architecture" { yybegin(YYINITIAL); return VhdlTypes.ARCHITECTURE;}
"library" { yybegin(YYINITIAL); return VhdlTypes.LIBRARY;}
"use" { yybegin(YYINITIAL); return VhdlTypes.USE;}


"of" { yybegin(YYINITIAL); return VhdlTypes.OF;}
"begin" { yybegin(YYINITIAL); return VhdlTypes.BEGIN;}
"is" { yybegin(YYINITIAL); return VhdlTypes.IS;}
"." { yybegin(YYINITIAL); return VhdlTypes.DOT;}

";" { yybegin(YYINITIAL); return VhdlTypes.SEMI;}
":" { yybegin(YYINITIAL); return VhdlTypes.COLON;}
"(" { yybegin(YYINITIAL); return VhdlTypes.LEFPAREN;}
")" { yybegin(YYINITIAL); return VhdlTypes.RIGHPAREN;}
"=" { yybegin(YYINITIAL); return VhdlTypes.EQU;}

"+" { yybegin(YYINITIAL); return VhdlTypes.ADD;}
"-" { yybegin(YYINITIAL); return VhdlTypes.SUB;}
"/" { yybegin(YYINITIAL); return VhdlTypes.DIV;}
"*" { yybegin(YYINITIAL); return VhdlTypes.MUL;}
"and" { yybegin(YYINITIAL); return VhdlTypes.AND;}
"or" { yybegin(YYINITIAL); return VhdlTypes.OR;}
"nand" { yybegin(YYINITIAL); return VhdlTypes.NAND;}
"nor" { yybegin(YYINITIAL); return VhdlTypes.NOR;}
"xor" { yybegin(YYINITIAL); return VhdlTypes.XOR;}
"xnor" { yybegin(YYINITIAL); return VhdlTypes.XNOR;}
"sll" { yybegin(YYINITIAL); return VhdlTypes.SLL;}
"srl" { yybegin(YYINITIAL); return VhdlTypes.SRL;}
"sla" { yybegin(YYINITIAL); return VhdlTypes.SLA;}
"sra" { yybegin(YYINITIAL); return VhdlTypes.SRA;}
"rol" { yybegin(YYINITIAL); return VhdlTypes.ROL;}
"ror" { yybegin(YYINITIAL); return VhdlTypes.ROR;}

"**" { yybegin(YYINITIAL); return VhdlTypes.EXP;}
"not" { yybegin(YYINITIAL); return VhdlTypes.NOT;}
"abs" { yybegin(YYINITIAL); return VhdlTypes.ABS;}
"mod" { yybegin(YYINITIAL); return VhdlTypes.MOD;}
"rem" { yybegin(YYINITIAL); return VhdlTypes.REM;}
"&" { yybegin(YYINITIAL); return VhdlTypes.CONCAT;}
"/=" { yybegin(YYINITIAL); return VhdlTypes.NE;}
"<" { yybegin(YYINITIAL); return VhdlTypes.LT;}
">" { yybegin(YYINITIAL); return VhdlTypes.GT;}
">=" { yybegin(YYINITIAL); return VhdlTypes.GTE;}

"process" { yybegin(YYINITIAL); return VhdlTypes.PROCESS;}

"port" { yybegin(YYINITIAL); return VhdlTypes.PORT;}
"end" { yybegin(YYINITIAL); return VhdlTypes.END;}
"when" { yybegin(YYINITIAL); return VhdlTypes.WHEN;}
"else" { yybegin(YYINITIAL); return VhdlTypes.ELSE;}
"others" { yybegin(YYINITIAL); return VhdlTypes.OTHERS;}
"case" { yybegin(YYINITIAL); return VhdlTypes.CASE;}
"next" { yybegin(YYINITIAL); return VhdlTypes.NEXT;}
"exit" { yybegin(YYINITIAL); return VhdlTypes.EXIT;}
"return" { yybegin(YYINITIAL); return VhdlTypes.RETURN;}
"variable" { yybegin(YYINITIAL); return VhdlTypes.VARIABLE;}
"block" { yybegin(YYINITIAL); return VhdlTypes.BLOCK;}
"pure" { yybegin(YYINITIAL); return VhdlTypes.PURE;}
"impure" { yybegin(YYINITIAL); return VhdlTypes.IMPURE;}
"function" { yybegin(YYINITIAL); return VhdlTypes.FUNCTION;}
"procedure" { yybegin(YYINITIAL); return VhdlTypes.PROCEDURE;}
"postponed" { yybegin(YYINITIAL); return VhdlTypes.POSTPONED;}
"component" { yybegin(YYINITIAL); return VhdlTypes.COMPONENT;}
"assert" { yybegin(YYINITIAL); return VhdlTypes.ASSERT;}
"report" { yybegin(YYINITIAL); return VhdlTypes.REPORT;}
"severity" { yybegin(YYINITIAL); return VhdlTypes.SEVERITY;}
"configuration" { yybegin(YYINITIAL); return VhdlTypes.CONFIGURATION;}
"constraint" { yybegin(YYINITIAL); return VhdlTypes.CONSTRAINT;}
"array" { yybegin(YYINITIAL); return VhdlTypes.ARRAY;}
"package" { yybegin(YYINITIAL); return VhdlTypes.PACKAGE;}
"type" { yybegin(YYINITIAL); return VhdlTypes.TYPE;}
"subtype" { yybegin(YYINITIAL); return VhdlTypes.SUBTYPE;}
"until" { yybegin(YYINITIAL); return VhdlTypes.UNTIL;}
"constant" { yybegin(YYINITIAL); return VhdlTypes.CONSTANT;}
"transport" { yybegin(YYINITIAL); return VhdlTypes.TRANSPORT;}
"reject" { yybegin(YYINITIAL); return VhdlTypes.REJECT;}
"group" { yybegin(YYINITIAL); return VhdlTypes.GROUP;}
"generic" { yybegin(YYINITIAL); return VhdlTypes.GENERIC;}

"loop" { yybegin(YYINITIAL); return VhdlTypes.LOOP;}
"for" { yybegin(YYINITIAL); return VhdlTypes.FOR;}
"while" { yybegin(YYINITIAL); return VhdlTypes.WHILE;}
"then" { yybegin(YYINITIAL); return VhdlTypes.THEN;}
"elsif" { yybegin(YYINITIAL); return VhdlTypes.ELSIF;}
"if" { yybegin(YYINITIAL); return VhdlTypes.IF;}
"open" { yybegin(YYINITIAL); return VhdlTypes.OPEN;}
"access" { yybegin(YYINITIAL); return VhdlTypes.ACCESS;}
"alias" { yybegin(YYINITIAL); return VhdlTypes.ALIAS;}
"new" { yybegin(YYINITIAL); return VhdlTypes.NEW;}
"attribute" { yybegin(YYINITIAL); return VhdlTypes.ATTRIBUTE;}
"inertial" { yybegin(YYINITIAL); return VhdlTypes.INERTIAL;}
"buffer" { yybegin(YYINITIAL); return VhdlTypes.BUFFER;}
"linkage" { yybegin(YYINITIAL); return VhdlTypes.LINKAGE;}
"with" { yybegin(YYINITIAL); return VhdlTypes.WITH;}
"select" { yybegin(YYINITIAL); return VhdlTypes.SELECT;}
"generate" { yybegin(YYINITIAL); return VhdlTypes.GENERATE;}
"map" { yybegin(YYINITIAL); return VhdlTypes.MAP;}
"signal" { yybegin(YYINITIAL); return VhdlTypes.SIGNAL;}
"literal" { yybegin(YYINITIAL); return VhdlTypes.LITERAL;}
"label" { yybegin(YYINITIAL); return VhdlTypes.LABEL;}
"file" { yybegin(YYINITIAL); return VhdlTypes.FILE;}
"to" { yybegin(YYINITIAL); return VhdlTypes.TO;}
"downto" { yybegin(YYINITIAL); return VhdlTypes.DOWNTO;}
"disconnect" { yybegin(YYINITIAL); return VhdlTypes.DISCONNECT;}
"after" { yybegin(YYINITIAL); return VhdlTypes.AFTER;}
"guarded" { yybegin(YYINITIAL); return VhdlTypes.GUARDED;}
"body" { yybegin(YYINITIAL); return VhdlTypes.BODY;}
"register" { yybegin(YYINITIAL); return VhdlTypes.REGISTER;}
"bus" { yybegin(YYINITIAL); return VhdlTypes.BUS;}
"unaffected" { yybegin(YYINITIAL); return VhdlTypes.UNAFFECTED;}
"shared" { yybegin(YYINITIAL); return VhdlTypes.SHARED;}
"all" { yybegin(YYINITIAL); return VhdlTypes.ALL;}
"<>" { yybegin(YYINITIAL); return VhdlTypes.NE;}
"," { yybegin(YYINITIAL); return VhdlTypes.CMA;}
"on" { yybegin(YYINITIAL); return VhdlTypes.ON;}

"null" { yybegin(YYINITIAL); return VhdlTypes.NULL;}
"in" { yybegin(YYINITIAL); return VhdlTypes.IN;}
"out" { yybegin(YYINITIAL); return VhdlTypes.OUT;}
"inout" { yybegin(YYINITIAL); return VhdlTypes.INOUT;}
"units" { yybegin(YYINITIAL); return VhdlTypes.UNITS;}

"<=" { yybegin(YYINITIAL); return VhdlTypes.NOBLOCK_ASSIGN;}
":=" { yybegin(YYINITIAL); return VhdlTypes.BLOCK_ASSIGN;}
"=>" { yybegin(YYINITIAL); return VhdlTypes.RARR;}

    {STR_LIT}                     { yybegin(YYINITIAL); return VhdlTypes.STRLIT; }
    {IDENTIFIER}                 { yybegin(YYINITIAL); return VhdlTypes.ID; }
    {END_OF_LINE_COMMENT}                 { yybegin(YYINITIAL); return VhdlTypes.COMMENT; }
    {NUM_LIT}                        {yybegin(YYINITIAL); return VhdlTypes.INTLIT;}
    {SIGNAL_LIT}                        {yybegin(YYINITIAL); return VhdlTypes.SIGLIT;}

}
{WHITE_SPACE_CHAR}+                      { return TokenType.WHITE_SPACE; }
.                                        { return TokenType.BAD_CHARACTER; }