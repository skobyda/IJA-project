/**
 * Class: GameFactory, tovaren na tvorbu hry
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

import game.Board;
import common.Game;
import common.Chess;

public abstract class GameFactory {
   public GameFactory(){}

    /**
     * Vytvorenie sachu.
     * @param board - Doska na ktorej bude hra prebiehat.
     * @return game
     */
   public static Game createChessGame(Board board){
       Game game = new Chess(board);
       return game;
   }
}
