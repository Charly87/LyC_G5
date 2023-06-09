package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.model.*;
import static lyc.compiler.constants.Constants.*;

%%

%public
%class Lexer
%unicode
%cup
%line
%column
%throws CompilerException
%eofval{
  return symbol(ParserSym.EOF);
%eofval}

%{
 int RANGO_ENTERO = (int) (Math.pow(2, 16)-1);
 float RANGO_FLOAT = (float) (Math.pow(2, 32)-1);
 int RANGO_STRING = 40;

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Identation =  [ \t\f]

Plus = "+"
Mult = "*"
Sub = "-"
Div = "/"
Assig = "="
OpenBracket = "("
CloseBracket = ")"
OpenCurlyBracket = "{"
CloseCurlyBracket = "}"
Letter = [a-zA-Z]
Digit = [0-9]
Symbol = "." | ";" | ":" | "+" | "-" | "*" | "/" | "-" | "_" | "¿" | "?" | "&" | "," | " " | \t | "@" | "%" | "(" | ")" | \" | "{" | "}"

Ciclo = "ciclo"
Write = "write"
Read = "read"
If = "if"
Else = "else"
Int = "Int"
Float = "Float"
String = "String"
Comma = ","
Colon = ":"
Init = "init"

AND = "&"
OR = "||"
NOT = "not"
Mayor = ">"
Minor = "<"
MayorEqu = ">="
MinorEqu = "<="
Equ = "=="
Distinct  = "!="

ElementInTheMiddle = "ElementInTheMiddle"

WhiteSpace = {LineTerminator} | {Identation}
Id = {Letter} ({Letter}|{Digit})*
CTE_Int = {Digit}+
CTE_String = \"({Letter}|{Digit}|{Symbol})*\"
CTE_Float = (({Digit}+"."{Digit}*) | ({Digit}*"."{Digit}+))
ContenidoComentario =  {Letter}|{Digit}|{WhiteSpace}
Comment = "*-" {ContenidoComentario}* "-*" | "*-" {ContenidoComentario}* "*-" {ContenidoComentario}* "-*" {ContenidoComentario}* "-*"

%%


/* keywords */

<YYINITIAL> {
    /* Keywords */
    {Ciclo}                                   { return symbol(ParserSym.CICLO, yytext()); }
    {Write}                                   { return symbol(ParserSym.WRITE, yytext()); }
    {If}                                      { return symbol(ParserSym.IF, yytext()); }
    {Else}                                    { return symbol(ParserSym.ELSE, yytext()); }
    {Int}                                     { return symbol(ParserSym.INT, yytext()); }
    {Float}                                   { return symbol(ParserSym.FLOAT, yytext()); }
    {String}                                  { return symbol(ParserSym.STRING, yytext()); }
    {Init}                                    { return symbol(ParserSym.INIT, yytext()); }
    {Read}                                    { return symbol(ParserSym.READ, yytext()); }

    /* operators */
    {Plus}                                    { return symbol(ParserSym.PLUS); }
    {Sub}                                     { return symbol(ParserSym.SUB); }
    {Mult}                                    { return symbol(ParserSym.MULT); }
    {Div}                                     { return symbol(ParserSym.DIV); }
    {Assig}                                   { return symbol(ParserSym.ASSIG); }
    {OpenBracket}                             { return symbol(ParserSym.OPEN_BRACKET); }
    {CloseBracket}                            { return symbol(ParserSym.CLOSE_BRACKET); }
    {OpenCurlyBracket}                        { return symbol(ParserSym.OPEN_CURLY_BRACKET); }
    {CloseCurlyBracket}                       { return symbol(ParserSym.CLOSE_CURLY_BRACKET); }
    {Comma}                                   { return symbol(ParserSym.COMMA, yytext()); }
    {Colon}                                   { return symbol(ParserSym.COLON, yytext()); }

    {AND}                                     { return symbol(ParserSym.OP_AND); }
    {OR}                                      { return symbol(ParserSym.OP_OR); }
    {NOT}                                     { return symbol(ParserSym.OP_NOT); }
    {Mayor}                                   { return symbol(ParserSym.OP_MAYOR); }
    {Minor}                                   { return symbol(ParserSym.OP_MINOR); }
    {MayorEqu}                                { return symbol(ParserSym.OP_MAYOREQU); }
    {MinorEqu}                                { return symbol(ParserSym.OP_MINOREQU); }
    {Equ}                                     { return symbol(ParserSym.OP_EQU); }
    {Distinct}                                { return symbol(ParserSym.OP_DISTINCT); }


    {ElementInTheMiddle}                      { return symbol(ParserSym.ElementInTheMiddle, yytext()); }

    /* identifiers */
    {Id}                                      {
                                                  final String value = new String(yytext());
                                                  if (value.length() - 2 <= RANGO_STRING)
                                                      return symbol(ParserSym.IDENTIFIER, value);
                                                  else
                                                      throw new InvalidLengthException( "El identificador [" + value + "] excede el largo permitido. (Se obtuvo una cadena de tamaño " + value.length() + ", maximo permitido: " + RANGO_STRING + ")"); }
    /* Constants */
    {CTE_Int}                               {
                                                try
                                                {
                                                    Integer value = Integer.parseInt(yytext());
                                                    if(Math.abs(value) <= RANGO_ENTERO)
                                                        return symbol(ParserSym.CTE_INT, value);
                                                    else
                                                        throw new InvalidIntegerException( "La constante [" + value + "] excede el tamaño permitido para un Int. Max permitido: " + RANGO_ENTERO + " Min permitido: -" + RANGO_ENTERO + ")");
                                                }
                                                catch (Exception e)
                                                {
                                                    throw new InvalidIntegerException( "La constante [" + yytext() + "]excede el tamaño permitido para un Int. Max permitido: " + RANGO_ENTERO + " Min permitido: -" + RANGO_ENTERO + ")");
                                                }
                                            }

    {CTE_String}                            {
                                                final String value = new String(yytext());
                                                if (value.length() - 2 <= RANGO_STRING)
                                                    return symbol(ParserSym.CTE_STR, value);
                                                else
                                                    throw new InvalidLengthException( "La constante [" + value + "] excede el largo permitido para un string. (Se obtuvo una cadena de tamaño " + value.length() + ", maximo permitido: " + RANGO_STRING + ")");
                                            }
    {CTE_Float}                             {
                                                Float value = Float.parseFloat(yytext());
                                                if(Math.abs(value) <= RANGO_FLOAT)
                                                    return symbol(ParserSym.CTE_FLOAT, yytext());
                                                else
                                                    throw new InvalidFloatException("La constante [" + value + "] excede el tamaño permitido para un Float. Max permitido: " + RANGO_FLOAT + " Min permitido: -" + RANGO_FLOAT + ")");
                                            }

    /* whitespace */
    {WhiteSpace}                              { /* ignore */ }

    /* comment */
    {Comment}                                 { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
