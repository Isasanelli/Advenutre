
package com.gioco.thehuntress.adventure;

import com.gioco.thehuntress.eventi.DbClass;
import com.gioco.thehuntress.eventi.Eventi;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Margari Chiara
 * @author Ricciardi Raffaella
 * @author Sasanelli Ilenia
 */
public class Engine {
    public static final String PATROOM1FIRSTPART="file//stanza1.1.txt";
    public DbClass db = new DbClass(); //ricordare di chiudere la connessione col db con il metodo close() di Connection;

    public  void start() throws IOException {

        Scanner io = new Scanner(System.in);
        Grafica menu = new Grafica();
        menu.writeMenu();
        String input ;

        do {
                input = io.nextLine();
                input=input.toLowerCase();

                switch (input) {
                    case "nuova partita":
                        Eventi.readFileDialog(PATROOM1FIRSTPART);
                        /*lasciare le prossime righe di codice commentate per promemoria
                        AdvObject oggetto= new AdvObject(1);
                        System.out.println(oggetto.getName(db));*/
                        break;
                    case "regole del gioco":
                        Eventi.readRules();
                        break;
                     case "comandi":
                        Eventi.readCommands();
                        break;
                    case "esci":
                        break;
                    default: System.out.println("Scelta non valida. Riprova");
                        break;
                }//end of game
        } while (!input.equals("esci"));

        System.out.println("Il gioco e' bello quando dura poco."
                + " PACE E AMORE "
                + " Un saluto da :"
                + " Chiara Margari, "
                + " Ricciardi Raffaella e"
                + " Sasanelli Ilenia");
        }

    public static void main(String[] args) throws IOException {
        new Engine().start();
        //new TicTacGame().computer_play();

    }
}
