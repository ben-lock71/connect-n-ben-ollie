package com.thg.accelerator23.connectn.ai.benandollie;


import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AIrJordan extends Player {
    int counterIndex;


    public AIrJordan(Counter counter) {
        super(counter, "AIrJordan");
        if (counter.getStringRepresentation() == "X") {
            counterIndex = 0;
        } else {
            counterIndex = 1;
        }
    }

    public static boolean winCheck(StringBuilder playerMoves) {
        BigInteger realBoard = new BigInteger(String.valueOf(playerMoves), 2);

        BigInteger vertStep1 = realBoard.shiftRight(1);
        BigInteger vertStep2 = realBoard.shiftRight(2);
        BigInteger vertStep3 = realBoard.shiftRight(3);

        String vertResult = realBoard.and(vertStep1).and(vertStep2).and(vertStep3).toString(2);
        if (vertResult.contains("1")) {
            return true;
        }

        BigInteger horizStep1 = realBoard.shiftRight(9);
        BigInteger horizStep2 = realBoard.shiftRight(18);
        BigInteger horizStep3 = realBoard.shiftRight(27);
        String horizResult = realBoard.and(horizStep1).and(horizStep2).and(horizStep3).toString(2);
        if (horizResult.contains("1")) {
            return true;
        }

        BigInteger diagLTRStep1 = realBoard.shiftRight(10);
        BigInteger diagLTRStep2 = diagLTRStep1.shiftRight(10);
        BigInteger diagLTRStep3 = diagLTRStep2.shiftRight(10);
        String diagLTRResult = realBoard.and(diagLTRStep1).and(diagLTRStep2).and(diagLTRStep3).toString(2);
        if (diagLTRResult.contains("1")) {
            return true;
        }

        BigInteger diagRTLStep1 = realBoard.shiftRight(8);
        BigInteger diagRTLStep2 = diagLTRStep1.shiftRight(8);
        BigInteger diagRTLStep3 = diagLTRStep2.shiftRight(8);
        String diagRTLResult = realBoard.and(diagRTLStep1).and(diagRTLStep2).and(diagRTLStep3).toString(2);
        return diagRTLResult.contains("1");
    }

    private List<StringBuilder> makeBitBoard(Board board) {
        StringBuilder boardState = new StringBuilder();
        StringBuilder XState = new StringBuilder();
        List<List<Counter>> columns = Arrays.stream(board.getCounterPlacements()).map(Arrays::asList).toList();
        for (List<Counter> row : columns) {
            List<Character> emptyList = row.stream().map(c -> c == null ? '0' : '1').toList();
            List<Character> XList = row.stream().map(c -> c != null && Objects.equals(c.getStringRepresentation(), "X") ? '1' : '0').toList();
            StringBuilder emptyString = new StringBuilder();
            StringBuilder XString = new StringBuilder();
            for (Character c : emptyList) {
                emptyString.append(c);
            }
            for (Character c : XList) {
                XString.append(c);
            }
            XState.append(XString).append("0");
            boardState.append(emptyString).append("0");

        }

        StringBuilder OState = new StringBuilder();
        for (int i = 0; i < boardState.length(); i++) {
            OState.append(boardState.charAt(i) ^ XState.charAt(i));
        }
        List<StringBuilder> boardMap = new ArrayList<>(3);
        boardMap.add(XState);
        boardMap.add(OState);
        boardMap.add(boardState);
        return boardMap;
    }


    private int makeRandomMove(Board board) {
        int column = (int) (Math.random() * 9) + 1;
        while (true) {
            if (!board.hasCounterAtPosition(new Position(column, 7))) {
                return column;
            }
        }
    }


    @Override
    public int makeMove(Board board) {
        List<StringBuilder> bitBoard = makeBitBoard(board);
        int bestMove;
        int columnOutcome = tryColumns(bitBoard, counterIndex, 2);
        if (columnOutcome == -1) {
            bestMove = makeRandomMove(board);
        } else bestMove = columnOutcome;
        return bestMove;
    }

    int tryColumns(List<StringBuilder> bitBoard, int playerIndex, int depth) {
        StringBuilder playerMoves = bitBoard.get(playerIndex);
        StringBuilder emptySquares = bitBoard.get(2);
        for (int i = 0; i <= depth; i++)
            for (int col = 0; col < 10; col++) {
                int firstEmpty = emptySquares.substring(9 * (col), 8 + (9 * (col))).indexOf("0");
                if (firstEmpty < 9) {
                    playerMoves.setCharAt(((col * 9) + firstEmpty), '1');
                }
                if (winCheck(playerMoves)) {
                    return col;
                }
                playerMoves.setCharAt(((col * 9) + firstEmpty), '0');
            }
        return -1;
    }
}




