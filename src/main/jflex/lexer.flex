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
 int RANGO_IDENTIFICADOR = 256;

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
Symbol = "." | ";" | "+" | "-" | "*" | "/" | "-" | "_" | "¿" | "?" | "&" | "," | " " | \t | "@" | "%"
Mayor = ">"

WhiteSpace = {LineTerminator} | {Identation}
Id = {Letter} ({Letter}|{Digit})*
Const_Int = {Digit}+
Const_String = \"({Letter}|{Digit}|{Symbol})*\"
Const_Float = ({Digit}+"."{Digit}*) | ({Digit}*"."{Digit}+)

Ciclo = "ciclo"
Write = "write"
If = "if"
Else = "else"
Int = "Int"
Float = "Float"
String = "String"
Comma = ","
Colon = ":"
Init = "init"

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
  {Comma}                                   { return symbol(ParserSym.COMMA, yytext()); }
  {Colon}                                   { return symbol(ParserSym.COLON, yytext()); }
  {Init}                                    { return symbol(ParserSym.INIT, yytext()); }

  /* identifiers */
  {Id}                                      { return symbol(ParserSym.IDENTIFIER, yytext()); }
  /* Constants */
  {Const_Int}                               {
                                                try
                                                {
                                                    Integer value = Integer.parseInt(yytext());
                                                    if(Math.abs(value) <= RANGO_ENTERO)
                                                        return symbol(ParserSym.INTEGER_CONSTANT, value);
                                                    else
                                                        throw new InvalidIntegerException( "La constante [" + value + "] excede el tamaño permitido para un Int. Max permitido: " + RANGO_ENTERO + " Min permitido: -" + RANGO_ENTERO + ")");
                                                }
                                                catch (Exception e)
                                                {
                                                    throw new InvalidIntegerException( "La constante [" + yytext() + "]excede el tamaño permitido para un Int. Max permitido: " + RANGO_ENTERO + " Min permitido: -" + RANGO_ENTERO + ")");
                                                }
                                            }

  {Const_String}                            {
                                                final String value = new String(yytext());
                                                if (value.length() - 2 <= RANGO_STRING)
                                                    return symbol(ParserSym.STRING_CONSTANT, value);
                                                else
                                                    throw new InvalidLengthException( "La constante [" + value + "] excede el largo permitido para un string. (Se obtuvo una cadena de tamaño " + value.length() + ", maximo permitido: " + RANGO_STRING + ")");
                                            }
  {Const_Float}                             {
                                                Float value = Float.parseFloat(yytext());
                                                if(Math.abs(value) <= RANGO_FLOAT)
                                                    return symbol(ParserSym.FLOAT_CONSTANT, yytext());
                                                else
                                                    throw new NumberFormatException("La constante [" + value + "] excede el tamaño permitido para un Float. Max permitido: " + RANGO_FLOAT + " Min permitido: -" + RANGO_FLOAT + ")");
                                            }

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
  {Mayor}                                   { return symbol(ParserSym.OP_MAYOR); }

  /* whitespace */
  {WhiteSpace}                             { /* ignore */ }
}


/* error fallback */
[^]                              { throw new UnknownCharacterException(yytext()); }
