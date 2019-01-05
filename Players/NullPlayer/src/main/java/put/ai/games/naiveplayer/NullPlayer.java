/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.naiveplayer;

import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;

import java.util.ArrayList;
import java.util.List;

public class NullPlayer extends Player {
    @Override
    public String getName() {
        return "Artur Mostowski 132289 Łukasz Grygier 132234";
    }


    @Override
    public Move nextMove(Board b) {
        List<Move> moves = b.getMovesFor(getColor());
        List<Integer> goodness = new ArrayList<>();
        for (Move move: moves) {
            b.doMove(move);
            goodness.add(b.getMovesFor(getOpponent(getColor())).size());
            b.undoMove(move);
        }
        int min=goodness.get(0);
        int iMin=0;
        for(int i=0;i<goodness.size();i++){
            if(goodness.get(i)<min){
                min=goodness.get(i);
                iMin=i;
            }
        }
        return moves.get(iMin);
    }
}
