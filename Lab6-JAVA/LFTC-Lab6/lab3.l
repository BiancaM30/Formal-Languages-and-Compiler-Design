/* analizor pentru un MLP din C */

%{
   /* definitii accesibile in yylex si main */
  #include "lab4.tab.h"
  #include <string.h>
  int liniaCurenta = 1;
%}

%option noyywrap
  /* reguli */
id    [a-zA-Z_]+[a-zA-Z_0-9]*
const ("-")?([1-9][0-9]*|0)
%%
"import java.util.Scanner;public class Main { public static void main(String[] args) {Scanner in = new Scanner(System.in);"  { return MAIN; }
"int"  { return INT; }
"in.nextInt()"  { return READ_INT; }
"System.out.println"  { return PRINT; }
"="  { return '='; }
"+"  { return '+'; }
"-"  { return '-'; }
"*"  { return '*'; }
"/"  { return '/'; }
"%"  { return '%'; }
"{"  { return '{'; }
"}"  { return '}';}
"("  { return '('; }
")"  { return ')'; }
","  { return ','; }
";"  { return ';'; }

{id} { memset(yylval.sval, 0, sizeof(yylval.sval)); strncpy(yylval.sval, yytext, strlen(yytext)); return ID; }
{const}  { memset(yylval.sval, 0, sizeof(yylval.sval)); strncpy(yylval.sval, yytext, strlen(yytext)); return CONST; }
[ \t]  /* whitespace */
[\n]  { liniaCurenta++; }
.  {printf("%s  token nerecunoscut la linia %d!!\n", yytext, liniaCurenta); return -1; }

%%
