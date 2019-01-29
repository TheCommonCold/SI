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
    public static void main(String[] args) {}

    @Override
    public String getName() {
        return "Artur Mostowski 132289 Łukasz Grygier 132234";
    }


    @Override
    public Move nextMove(Board b) {
        return findBestMove(b,0);
//        if(b.getMovesFor(getOpponent(getColor())).size()==0)
//        {
//            return findBestMove(b,0);
//        }
//        else{
//            int complexity= (b.getMovesFor(getColor()).size()+b.getMovesFor(getOpponent(getColor())).size())/2;
//            int maxdepth=0;
//            while(getTime()>complexity*100){
//                complexity=complexity*((b.getMovesFor(getColor()).size()+b.getMovesFor(getOpponent(getColor())).size())/2);
//                maxdepth++;
//                if (maxdepth>10)break;
//            }
//            return findBestMove(b,maxdepth);
//        }
    }

    private float evaluate(Board b, Color color1, Color color2){
        return b.getMovesFor(color1).size()-b.getMovesFor(color2).size();
    }

    private float minimax(Board board, int depth, boolean isMe, int maxDepth)
    {
        Color color1;
        Color color2;
        color1=getColor();
        color2=getOpponent(getColor());
        if(depth==maxDepth || board.getMovesFor(getColor()).size()==0 || board.getMovesFor(getOpponent(getColor())).size()==0) return evaluate(board,color1,color2);
        float best;
        List<Move> moves;
        if(isMe) {
            best = 1000;
            moves = board.getMovesFor(color2);
        }
        else {
            best = -1000;
            moves = board.getMovesFor(color1);
        }
            for (Move move: moves) {
                board.doMove(move);

                if(isMe) {
                    best = Math.min( best,minimax(board, depth+1, !isMe,maxDepth) );
                }
                else  {
                    best = Math.max( best,minimax(board, depth+1, !isMe,maxDepth) );
                }
                board.undoMove(move);
            }
            return best;
    }

    private Move findBestMove(Board board, int maxDepth) {
        List<Move> moves = board.getMovesFor(getColor());
        float bestVal = -1000;
        Move bestMove= moves.get(0);

        for (Move move : moves) {
            board.doMove(move);

            float moveVal = minimax(board, 0, true,maxDepth);

            board.undoMove(move);

            if (moveVal > bestVal) {
                bestMove=move;
                bestVal = moveVal;
            }

        }
        return bestMove;
    }
}