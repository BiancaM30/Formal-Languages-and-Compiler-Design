/* analizor pentru un MLP din C */
%{
   /* definitii accesibile in yylex si main */

  #include <string.h>

  struct Token {
    char simbol[20];
  };

  struct TS {
    struct Token tokens[50];
    int pozCurenta;
  };

  struct FIP {
    int coduri[1000];
    int pozTS[1000];
    int pozCurenta;
  };

  struct TS tsId = {.pozCurenta = 0};
  struct TS tsConst = {.pozCurenta = 0};
  struct FIP fip = {.pozCurenta = 0 };
  int liniaCurenta = 1;

  void adaugaFIP(int cod, int codTS);

  void adaugaTS_ID(int codFIP, char simbol[]);

  void adaugaTS_CONST(int codFIP, char simbol[]);

%}

  /* reguli */
id       [a-zA-Z_]+[a-zA-Z_0-9]*
int_const ("-")?([1-9][0-9]*|0)
float_const ("-")?((([1-9][0-9]*|0)[.][0-9]*)|(([1-9][0-9]*|0)?[.][0-9]+))

%%
"public class Main { public static void main(String[] args) {Scanner in = new Scanner(System.in);"  { adaugaFIP(2, -1); }
"int"  { adaugaFIP(3, -1); }
"double"  { adaugaFIP(4, -1); }
"enum"  { adaugaFIP(5, -1); }
"in.next"  { adaugaFIP(6, -1); }
"in.nextInt()"  { adaugaFIP(7, -1); }
"in.nextDouble()"  { adaugaFIP(8, -1); }
"System.out.println"  { adaugaFIP(9, -1); }
"if"  { adaugaFIP(10, -1); }
"while"  { adaugaFIP(11, -1); }
"="  { adaugaFIP(12, -1); }
"+"  { adaugaFIP(13, -1); }
"-"  { adaugaFIP(14, -1); }
"*"  { adaugaFIP(15, -1); }
"/"  { adaugaFIP(16, -1); }
"%"  { adaugaFIP(17, -1); }
"=="  { adaugaFIP(18, -1); }
"!="  { adaugaFIP(19, -1); }
"<"  { adaugaFIP(20, -1); }
">"  { adaugaFIP(21, -1); }
"<="  { adaugaFIP(22, -1); }
">="  { adaugaFIP(23, -1); }
"&&"  { adaugaFIP(24, -1); }
"||"  { adaugaFIP(25, -1); }
"{"  { adaugaFIP(26, -1); }
"}"  { adaugaFIP(27, -1); }
"("  { adaugaFIP(28, -1); }
")"  { adaugaFIP(29, -1); }
"["  { adaugaFIP(30, -1); }
"]"  { adaugaFIP(31, -1); }
","  { adaugaFIP(32, -1); }
"."  { adaugaFIP(33, -1); }
";"  { adaugaFIP(34, -1); }
"cattimp" { adaugaFIP(35, -1); }
"executa" { adaugaFIP(36, -1); }
"sfcattimp" { adaugaFIP(37, -1); }

{id} { adaugaTS_ID(0, yytext); }
{int_const}  { adaugaTS_CONST(1, yytext); }
{float_const}  { adaugaTS_CONST(1, yytext); }

[ \t]+   /* whitespace */
[\n] {liniaCurenta++;}
.  { printf("Eroare la linia %d, tokenul %s nu exista\n", liniaCurenta, yytext); 
     return -1; }

%%

void adaugaFIP(int cod, int pozTS) {
  fip.coduri[fip.pozCurenta] = cod;
  fip.pozTS[fip.pozCurenta] = pozTS;
  fip.pozCurenta++;
 }

void adaugaTS_ID(int codFIP, char simbol[]) {
  // verify if id is already in the table
  for(int i = 0; i < tsId.pozCurenta; i++) 
    if (strcmp(tsId.tokens[i].simbol, simbol) == 0) {
        adaugaFIP(codFIP, i+1);
        return;
  }
  strcpy(tsId.tokens[tsId.pozCurenta].simbol, simbol);
  tsId.pozCurenta++;
  adaugaFIP(codFIP, tsId.pozCurenta);
 }

void adaugaTS_CONST(int codFIP, char simbol[]) {
    // verify if const is already in the table
  for(int i = 0; i < tsConst.pozCurenta; i++) 
    if (strcmp(tsConst.tokens[i].simbol, simbol) == 0) {
      adaugaFIP(codFIP, i+1);
      return;
   }
  strcpy(tsConst.tokens[tsConst.pozCurenta].simbol, simbol);
  tsConst.pozCurenta++;
  adaugaFIP(codFIP, tsConst.pozCurenta);
 }

void print_FIP(){
  printf("\n Forma interna a programului\n");
  printf("\n Cod atom    Cod TS\n");
  for (int i = 0; i < fip.pozCurenta; i++) {
    if(fip.pozTS[i] != -1)  printf("%d           %d\n", fip.coduri[i], fip.pozTS[i]);
    else printf("%d\n", fip.coduri[i]);
  }
  printf("\n");
}

void print_TS_CONST(){
  printf("\n Tabel constante");
  for (int i = 0; i < tsConst.pozCurenta; i++)
    printf("\nkey=%s  poz=%d", tsConst.tokens[i].simbol, i);
}

void print_TS_ID(){
  printf("\n Tabel identificatori");
  for (int i = 0; i < tsId.pozCurenta; i++)
    printf("\nkey=%s  poz=%d", tsId.tokens[i].simbol, i);
}

int yywrap() {}
int main()
{
  FILE* f;
  char* filename="C://Users//munte//LFTC//Lab3//program2.txt";
  f = fopen(filename, "r");
  yyin = f;
  if (yylex() == -1) return -1;
  else {
    print_TS_CONST();
    printf("\n");
    print_TS_ID();
    printf("\n");
    print_FIP();
  }
  return 0;
}