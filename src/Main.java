import java.util.Random;
import java.util.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        LinkedHashMap<Integer, Integer> linearsomme = new LinkedHashMap<Integer, Integer>();
        LinkedHashMap<Integer, Integer> dichosomme = new LinkedHashMap<Integer, Integer>();
        int slinear = 0;
        int sdicho = 0;
        Random rd = new Random();
        Linear linear = new Linear();
        Dicho dicho = new Dicho();
        int N = 100000;
        int nbrExecution = 100;
        int target = -10;
        int[] donnes = new int[N];
        for (int i = 0; i < donnes.length; i++) {
            donnes[i] = rd.nextInt(1000); // storing random integers in an array
            System.out.println(donnes[i]); // printing each array element
        }
        Arrays.sort(donnes);
        System.out.println("le temps pour la recherche sequencielle");
        for (int i = 0; i < nbrExecution; i++) {
            long debut = System.nanoTime();
            linear.linearSearch(donnes, target);
            long fin = System.nanoTime();
            slinear += fin - debut;
        }
        System.out.println("la moyenne de sequencielle:  " + slinear / nbrExecution);


        for (int i = 0; i <= nbrExecution; i++) {
            long debut = System.nanoTime();
            dicho.binarySearch(donnes, 0, donnes.length - 1, target);
            long fin = System.nanoTime();
            sdicho += fin - debut;
        }
        System.out.println("");
        System.out.println("la moyenne de dicho:  " + sdicho / nbrExecution);

        // table des valeur pour le graphe
        //dicho
        for (int i = 10; i <= 100000000; i*=100) {
            int[] internaldonnes = new int[i];
            for (int j = 0; j <= nbrExecution; j++) {
                long debut = System.nanoTime();
                dicho.binarySearch(internaldonnes, 0, internaldonnes.length - 1, target);
                long fin = System.nanoTime();
                sdicho += fin - debut;
            }
                dichosomme.put(i,sdicho/nbrExecution);
        }
        System.out.println(dichosomme);

        //Linear
        for (int i = 10; i <= 100000000; i*=100) {
            int[] internaldonnes = new int[i];
            for (int j = 0; j <= nbrExecution; j++) {
                long debut = System.nanoTime();
                linear.linearSearch(internaldonnes, target);
                long fin = System.nanoTime();
                slinear += fin - debut;
            }
            linearsomme.put(i,slinear/nbrExecution);
        }
        System.out.println(linearsomme);
    }
}