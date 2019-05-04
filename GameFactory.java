package ija.ija2018.homework2;

import ija.ija2018.homework2.game.Board;
import ija.ija2018.homework2.common.Game;
import ija.ija2018.homework2.common.Chess;
import ija.ija2018.homework2.common.Checkers;

public abstract class GameFactory {
   public GameFactory(){}

   public static Game createChessGame(Board board){
       Game game = new Chess(board);
       return game;
   }

   public static Game createCheckersGame(Board board){
       Game game = new Checkers(board);
       return game;
   }
}


