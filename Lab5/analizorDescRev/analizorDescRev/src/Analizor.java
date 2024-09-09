import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Analizor {
    Gramatica gramatica;

    Stare stare;
    int i;
    Stack<String> stivaLucru;
    String stivaIntrare;

    String secventa;

    public Analizor(Gramatica gramatica, String secventa) {
        this.gramatica = gramatica;

        this.stare = Stare.q;
        this.i = 0;
        this.stivaLucru = new Stack<String>();
        this.stivaIntrare = "S";

        this.secventa = secventa;
    }


    public String descendentCuReveniri() {
       while(true) {
           if (this.stivaIntrare.length() == 0 && this.i + 1 == secventa.length() + 1) {
               System.out.println("SUCCES");
               this.stare = Stare.t;
               return "succes";
           }

           if (this.i == secventa.length()) {
               System.out.println("INSUCCES DE MOMENT");
               this.stare = Stare.r;
           }

           if (this.stare == Stare.q) {
               if (this.stivaIntrare.length() > 0) {
                   String primElementIntrare = String.valueOf(this.stivaIntrare.charAt(0));
                   if (this.gramatica.getTerminali().contains(primElementIntrare)) {
                       System.out.println(this.i);
                       String elementCurentSecventa = String.valueOf(this.secventa.charAt(this.i));
                       if (primElementIntrare.equals(elementCurentSecventa)) {
                           System.out.println(this);
                           System.out.println("AVANS");
                           this.i++;
                           stivaLucru.add(primElementIntrare);
                           stivaIntrare = stivaIntrare.substring(1);
                       } else {
                           System.out.println(this);
                           System.out.println("INSUCCES DE MOMENT");
                           this.stare = Stare.r;
                       }
                   } else if (this.gramatica.getNeterminali().contains(primElementIntrare)) {
                       System.out.println(this);
                       System.out.println("EXPANDARE");
                       ArrayList<String> primaRegulaPentruNeterminal = new ArrayList<>();
                       for (int j = 0; j < this.gramatica.getReguli().size(); j++) {
                           List<String> regula = this.gramatica.getReguli().get(j);
                           if (regula.get(1).equals(primElementIntrare)) {
                               primaRegulaPentruNeterminal.addAll(regula);
                               // break
                               j = this.gramatica.getReguli().size();
                           }
                       }
                       this.stivaLucru.add(primaRegulaPentruNeterminal.get(0));
                       String stivaIntrareFaraPrimulElement = this.stivaIntrare.substring(1);
                       this.stivaIntrare = primaRegulaPentruNeterminal.get(2) + stivaIntrareFaraPrimulElement;
                   } else {
                       return "ERROR";
                   }
               } else {
                   System.out.println(this);
                   System.out.println("INSUCCES DE MOMENT");
                   this.stare = Stare.r;
               }
           } else {
               if (!this.stivaLucru.isEmpty()) {
//                   System.out.println("LUNGIME " + stivaLucru.peek());
                   if (this.gramatica.getTerminali().contains(stivaLucru.peek())) {
                       System.out.println(this);
                       System.out.println("REVENIRE");
                       this.i--;
                       String ultimElementLucru = this.stivaLucru.pop();
                       this.stivaIntrare = ultimElementLucru.concat(this.stivaIntrare);
                       System.out.println(this);
                   } else {
                       System.out.println(this);
                       System.out.println("ALTA INCERCARE");
                       String ultimElementLucru = this.stivaLucru.peek();
                       int nr_regula = Integer.parseInt(String.valueOf(ultimElementLucru.charAt(1)));
                       System.out.println(nr_regula);
                       ArrayList<String> next_regula = new ArrayList<>();
                       int nr_regula_urm = -1;
                       System.out.println("AICI");
                       for (int j = nr_regula + 1; j < this.gramatica.getReguli().size(); j++) {
                           if (this.gramatica.getReguli().get(j).get(1).equals(this.gramatica.getReguli().get(nr_regula).get(1))) {
                               nr_regula_urm = j;
                               j = this.gramatica.getReguli().size();
                           }
                       }

                       if (nr_regula_urm != -1) {
                           System.out.println("CAZ 1");
                           next_regula.addAll(this.gramatica.getReguli().get(nr_regula_urm));
                           this.stare = Stare.q;
                           stivaLucru.pop();
                           stivaLucru.add(next_regula.get(0));

                           int nrCaractereRegula = this.gramatica.getReguli().get(nr_regula).get(2).length();
                           String stivaIntrareStersElem = this.stivaIntrare.substring(nrCaractereRegula);
                           this.stivaIntrare = next_regula.get(2) + stivaIntrareStersElem;
                       } else if (this.i == 0) {
                           System.out.println("CAZ 2");
                           this.stare = Stare.e;
                           return "ERROR";
                       } else {
                           System.out.println("CAZ 3");
                           this.stivaLucru.pop();
                           int nrCaractereRegula = this.gramatica.getReguli().get(nr_regula).get(2).length();
                           String stivaIntrareStersElem = this.stivaIntrare.substring(nrCaractereRegula);
                           this.stivaIntrare = this.gramatica.getReguli().get(nr_regula).get(1) + stivaIntrareStersElem;
                       }
                   }
               } else {
                   return "ERROR";
               }
           }
       }
    }

    @Override
    public String toString() {
        return stare +
                " " + i +
                " " + stivaLucru +
                " " + stivaIntrare
                ;
    }
}
