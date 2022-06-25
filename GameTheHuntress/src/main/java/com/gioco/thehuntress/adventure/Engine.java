package com.gioco.thehuntress.adventure;

import com.gioco.thehuntress.database.DbClass;
import com.gioco.thehuntress.eventi.Eventi;
import com.gioco.thehuntress.graphic.Grafica;
import com.gioco.thehuntress.games.TheHuntressGame;
import com.gioco.thehuntress.parser.Parser;
import com.gioco.thehuntress.parser.ParserOutput;
import com.gioco.thehuntress.type.CommandType;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;


/**
 *
 * @author Margari Chiara
 * @author Ricciardi Raffaella
 * @author Sasanelli Ilenia
 */

/**
 * Classe main del programma
 */
public class Engine {

    public DbClass db = new DbClass(); //ricordare di chiudere la connessione col db con il metodo close() di Connection;
    public static final String PATHSTOPWORDS="file//stopwords";

    private final GameDescription game;
    private Parser parser;

    private static Engine engine;

    /**
     *engine builder
     */
    public Engine(GameDescription game) {
        this.game = game;
        try {
            this.game.init();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        try {
            Set<String> stopwords = Utils.loadFileListInSet(new File(PATHSTOPWORDS));
            parser = new Parser(stopwords);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * method that manages the game's initial menu
     * @throws IOException
     */
    public  void start() throws IOException {

        Scanner io = new Scanner(System.in);
        Grafica.writeMenu();
        String input ;
        

        do {
             System.out.print("\nScrivi un opzione:  ");
                input = io.nextLine();
                input=input.toLowerCase();

                switch (input) {
                    case "nuova partita":
                      execute();
                        break;
                    case "regole del gioco":
                        Eventi.readRules();
                        break;
                     case "comandi":
                        Eventi.readCommands();
                        break;
                    case "esci partita":
                        break;
                    default: System.out.println("Scelta non valida\n"+" Riprova!!");
                        break;
                }//end of game
        } while (!input.equals("esci partita"));

        Grafica.end();
        System.exit(0);
        }

    /**
     *method that manages game run and input
     */
    public void execute(){
            Grafica.writeIntro();
            System.out.println("======================================================================");
            System.out.println("                          "+ game.getCurrentRoom().getName(db));
            System.out.println("======================================================================");
            System.out.println();
            System.out.println(game.getCurrentRoom().getDescription(db));
            System.out.println();
            System.out.print("Cosa vuoi fare? \n");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.inventario.getList(), db);

                if (p.getCommand() == null ) {
                    System.out.println("Non capisco quello che mi vuoi dire.\n"+"Riprova!");

                }else if (p.getCommand() != null && p.getCommand().getType() == CommandType.ESCI) {
                    System.out.println("Fine partita");
                    
                    try {
                        try {
                            this.game.init();
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                        engine.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                 } else {
                    game.nextMove(db,p, System.out);
                    System.out.println();
                    System.out.print("Cosa vuoi fare?\n");
                }
            }
        }

    /**
     * main method of the application
     * @param args  the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
       engine= new Engine(new TheHuntressGame());
        engine.start();
    }
    
}
