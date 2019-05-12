// package project.src;
/* authors: Simon Kobyda, Michal Zelena (xkobyd00, xzelen24)
 */

import game.Board;
import common.Game;
import common.Chess;

public abstract class GameFactory {
   public GameFactory(){}

   public static Game createChessGame(Board board){
       Game game = new Chess(board);
       return game;
   }
}
