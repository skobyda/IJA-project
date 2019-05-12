/**
 * GameFactory: Trieda reprezentujuca tovaren hry
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package ija.ija2018.homework2;

import ija.ija2018.homework2.game.Board;
import ija.ija2018.homework2.common.Game;
import ija.ija2018.homework2.common.Chess;

public abstract class GameFactory {
    /**
     * konstruktor gameFactory
     */
   public GameFactory(){}

    /**
     * vytvorenie sachu v hre
     * @param board doska na ktorej sa bude hra odohravat
     * @return game - hra
     */
   public static Game createChessGame(Board board){
       Game game = new Chess(board);
       return game;
   }
}


