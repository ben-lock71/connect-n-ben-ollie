package com.thg.accelerator23.connectn.ai.benandollie;

public class MoveResult {
    int move;
    int evaluation;

    public MoveResult(int move, int evaluation) {
        this.move = move;
        this.evaluation = evaluation;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getEvaluation() {
        return evaluation;
    }
}
