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
//        List<Move> moves = b.getMovesFor(getColor());
//        List<Integer> goodness = new ArrayList<>();
//        for (Move move: moves) {
//            b.doMove(move);
//            goodness.add(b.getMovesFor(getOpponent(getColor())).size());
//            b.undoMove(move);
//        }
//        int min=goodness.get(0);
//        int iMin=0;
//        for(int i=0;i<goodness.size();i++){
//            if(goodness.get(i)<min){
//                min=goodness.get(i);
//                iMin=i;
//            }
//        }
//        return moves.get(iMin);
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
            // Traverse all cells
            for (Move move: moves) {
                // Make the move
                board.doMove(move);

                // Call minimax recursively and choose
                // the maximum value
                if(isMe) {
                    best = Math.min( best,minimax(board, depth+1, !isMe,maxDepth) );
                }
                else  {
                    best = Math.max( best,minimax(board, depth+1, !isMe,maxDepth) );
                }
                // Undo the move
                board.undoMove(move);
            }
            return best;
    }

    private Move findBestMove(Board board, int maxDepth) {
        List<Move> moves = board.getMovesFor(getColor());
        float bestVal = -1000;
        Move bestMove= moves.get(0);

        // Traverse all cells, evaluate minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (Move move : moves) {
            // Make the move
            board.doMove(move);

            // compute evaluation function for this
            // move.
            float moveVal = minimax(board, 0, true,maxDepth);

            // Undo the move
            board.undoMove(move);

            // If the value of the current move is
            // more than the best value, then update
            // best/
            if (moveVal > bestVal) {
                bestMove=move;
                bestVal = moveVal;
            }

        }
        return bestMove;
    }
}