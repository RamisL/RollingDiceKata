package src;


//import java.util.ArrayList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.lang.Float.NaN;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }
    public String emptyText[] = {""};
    final String SEPARATEURD = "d";
    final String SEPARATEURMP = "\\+";
    final String WRONGSEPARATEUR = "/";
    final  String NOSEPARATEUR[] ={"\\+", "-"};
    final String SEPARATEURMN = "-";
    public int dice;
    public String form;
    public int nbR;
    public int modif;
    public int result;

    ArrayList<Integer> list = new ArrayList<>();


    public Roll(String formula) {
        this.form = formula;
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.dice = diceValue;
        this.modif = modifier;
        this.nbR = nbRoll;


    }

    public int makeRoll(RollType rollType) {

        if (form != null){

            String finform[] = form.split(SEPARATEURD);
            String finformP[] = finform[1].split(SEPARATEURMP);
            String finformM[] = finform[1].split(SEPARATEURMN);
            String finformW[] = finform[1].split(WRONGSEPARATEUR);
            System.out.println("Trace");

            if (!Pattern.matches("[a-zA-Z]+", finform[0]) && !Pattern.matches("[a-zA-Z]+", finform[1]) && finform[0].length() < 2){

                if (!finform[0].equals(emptyText[0]) ) {
                    nbR = Integer.parseInt(finform[0]);
                }else{
                    nbR =1;
                }

                if (!finformP[0].equals(emptyText[0]) && finformP.length == 2  ){
                    dice = Integer.parseInt(finformP[0]);
                    modif = Integer.parseInt(finformP[1]);
                }

                if (!finformM[0].equals(emptyText[0]) && finformM.length == 2 ){
                    dice = Integer.parseInt(finformM[0]);
                    modif = Integer.parseInt(finformM[1]);
                    modif *= -1;
                }

                if (finformP.length != 2  &&  finformM.length != 2 && finformW.length != 2 ){
                    dice = Integer.parseInt(finform[1]);
                }
            }

        }


        if (nbR > 0 && dice > 0) {
            if (rollType == RollType.NORMAL) {
                for (int index = 0; index < nbR; index++) {
                    result += ((dice / 2) + 1);
                }result += modif;
                if (result < 0) result = 0;
            }

            if (rollType == RollType.ADVANTAGE) {
                for (int index = 0; index < nbR; index++) {
                    result += ((dice / 2) + 1);
                    list.add(result);
                    if (index > 0 && list.get(index - 1) > list.get(index - 1)) {
                        result= list.get(index -1);
                        list.set(index, result);
                    }
                }
            }

            if (rollType == RollType.DISADVANTAGE) {
                for (int index = 0; index < nbR; index++) {
                    result += ((dice / 2) + 1);
                    list.add(result);
                    if (index > 0 && list.get(index - 1) < list.get(index - 1)) {
                        result= list.get(index -1);
                        list.set(index, result);
                    }
                }
            }

        }else{
            result = -1;
        }

        return result;
    }
}
