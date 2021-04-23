
// Omar R. Gebril 	|| 	SID: 23323978

package view_controller;

// A Game of 20 questions game played at the console with standard IO
//
// @author Rick Mercer
//
import java.util.Scanner;

import model.Choice;
import model.GameTree;

public class GameOf20Console {

  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    GameTree game = new GameTree("animals.txt");
    String input;

    do {
      System.out.print(game.getCurrent() + " ");
      input = keyboard.next().trim();
      input = input.toUpperCase();
      if (input.charAt(0) == 'Y')
        game.playerSelected(Choice.YES);
      else
        game.playerSelected(Choice.NO);
    } while (!game.foundAnswer());

    System.out.print("\nWhere you thinking of a " + game.getCurrent() + "? ");
    input = keyboard.next().trim();
    input = input.toUpperCase();
    System.out.println();
    if (input.charAt(0) == 'N') {
      System.out.print("What were you thinking of? ");
      String newAnswer = keyboard.nextLine().trim();
      System.out.print("Give me a yes answer for " + newAnswer + " and a no answer for a " + game.getCurrent() + ": ");
      String newQuestion = keyboard.next().trim();
      if (!newQuestion.endsWith("?"))
        newQuestion += "?";
      game.add(newQuestion, newAnswer);
    } else {
      System.out.println("I win");
    }

    System.out.println("\n" + game);
    keyboard.close();
  }
}
