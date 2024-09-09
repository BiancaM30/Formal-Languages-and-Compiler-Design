%{

 #include <stdio.h>
 #include <stdlib.h>
 #include <string.h>

 extern int liniaCurenta;
 extern int yylex(void);
 int yyparse();
 void show();
 void yyerror(const char* s);
 FILE* yyin;
 
%}

// cuvinte cheie
%token MAIN
%token INT
%token DOUBLE
%token ID
%token INT_CONST
%token FLOAT_CONST
%token READ_INT
%token READ_DOUBLE
%token PRINT
%token IF
%token ELSE
%token EQUAL
%token NOT_EQUAL
%token LESS_THAN
%token GREATER_THAN
%token AND
%token OR
%token WHILE

%%

program : MAIN lista_declaratii lista_instr '}' '}'
        ;
////////////////////////////////////////////////////
lista_declaratii : declaratie ';'   // or /* empty */
                 | declaratie  ';' lista_declaratii
                 ;    

declaratie : tip lista_id
           ;
  
lista_id : ID 
         | ID ',' lista_id
         ;

tip : INT 
    | DOUBLE
    ;
////////////////////////////////////////////////////
lista_instr : instr 
            | instr lista_instr
            ;

instr : instr_IO ';'
      | instr_atribuire ';'
      | instr_conditionala
      | instr_ciclare
      ;
////////////////////////////////////////////////////
instr_IO : citire | scriere
         ;

citire : ID '=' READ_INT 
       | ID '=' READ_DOUBLE 
       ;

scriere : PRINT '(' ID ')'
        ;
//////////////////////////////////////////////////
instr_atribuire : ID '=' ID 
                | ID '=' CONST 
                | ID '=' expresie 
                ;

expresie : ID op_aritmetic ID 
         | ID op_aritmetic CONST 
         | CONST op_aritmetic ID
         ;
         
CONST : INT_CONST
      | FLOAT_CONST
      ;

op_aritmetic : '+'
             | '-'
             | '*'
             | '/'
             | '%' 
             ;
//////////////////////////////////////////////////
instr_conditionala : IF '(' lista_conditii ')' '{' lista_instr '}' 
                   | IF '(' lista_conditii ')' '{' lista_instr '}' ELSE '{' lista_instr '}' 
                   ;

lista_conditii : conditie 
               | conditie op_logic conditie

conditie : ID op_relational ID 
         | ID op_relational CONST 
         | CONST op_relational ID
         ;

op_relational : EQUAL 
              | NOT_EQUAL
              | '<' 
              | '>' 
              | LESS_THAN
              | GREATER_THAN
              ;

op_logic : AND
         | OR
         ;

//////////////////////////////////////////////////
instr_ciclare : WHILE '(' lista_conditii ')' '{' lista_instr '}'
              ;

%%

int main(int argc, char* argv[]) {
  FILE* f;
  char filename[100];
  printf("Fisier: ");
  scanf("%s", filename);
  f = fopen(filename, "r");
  if (!f) {
   printf("Cannot open file\n");
   return -1;
  }
  yyin = f;
  //if (yylex() == -1) return -1;
  do {
    yyparse();
  } while (!feof(yyin));
  printf("\nAnaliza sintactica a avut loc cu succes!\n");
  show();
}

void yyerror(const char* s) {
  printf("Eroare sintactica la linia %d! Mesaj : %s\n", liniaCurenta, s);
  exit(1);
}