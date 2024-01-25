package com.thg.accelerator23.connectn.ai.benandollie;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;


public class AIrJordan extends Player {
    private static final int MAX_DEPTH = 10;

    public AIrJordan(Counter counter) {
        super(counter, "AIrJordan");
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
        if (diagRTLResult.contains("1")) {
            return true;
        }
        return false;
    }
    private List<StringBuilder> makeBitBoard(Board board) {
        StringBuilder boardState = new StringBuilder();
        StringBuilder XState = new StringBuilder();
        try {
            Method getCounterBoard = board.getClass().getDeclaredMethod("getCounterPlacements");
            Counter[][] counters = (Counter[][]) getCounterBoard.invoke(board);
            List<List<Counter>> columns = Arrays.stream(counters).map(Arrays::asList).toList();
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
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        StringBuilder OState = new StringBuilder();
        for (int i = 0; i < boardState.length(); i++) {
            OState.append(boardState.charAt(i) ^ XState.charAt(i));
        }
        List<StringBuilder> boardMap = new ArrayList<>(2);
        boardMap.add(XState);
        boardMap.add(OState);
        return boardMap;
    }

    private String tryNextMove(String startBoard) {
        StringBuilder endBoard = new StringBuilder(startBoard);
        endBoard.setCharAt(34, 'X');
        return String.valueOf(endBoard);
    }

    private int makeRandomMove(Board board) {
        int column = (int) (Math.random() * 9) + 1;
        while (true) {

            if (!board.hasCounterAtPosition(new Position(column, 7))) {
                System.out.println(column);
                return column;
            }
        }
    }

    @Override
    public int makeMove(Board board) {
        long startTime = System.currentTimeMillis();
        int bestMove = -1;

        for (int depth = 1; depth <= MAX_DEPTH; depth++) {
            int currentBestMove = 0;
            try {
                currentBestMove = minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            long maxSearchTime = 700;
            if (System.currentTimeMillis() - startTime < maxSearchTime) {
                bestMove = currentBestMove;
            } else {
                break;
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<StringBuilder> bitBoard = makeBitBoard(board);
        if (depth == 0 || winCheck(bitBoard.get(0)) || winCheck(bitBoard.get(1))) {
            return evaluateBoard(board);
        }

        int bestEval = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int col = 1; col <= board.getConfig().getWidth(); col++) {
            if (board.isWithinBoard(new Position(col, 7))) {

                Counter counter = null;
                if (maximizingPlayer) {
                    counter = this.getCounter();
                } else {
                    if (Objects.equals(this.getCounter().getStringRepresentation(), "X")) {
                        counter = Counter.valueOf("O");
                    }
                }

                Position position = new Position(col, 7);

                if (isValidMove(board, position)) {
                    Board newBoard = board;
                    Method placeCounter = board.getClass().getDeclaredMethod("placeCounterAtPosition", Counter.class, int.class);
                    placeCounter.setAccessible(true);
                    placeCounter.invoke(newBoard, counter, col);

                    int eval = minimax(newBoard, depth - 1, alpha, beta, !maximizingPlayer);

                    if (maximizingPlayer) {
                        bestEval = Math.max(bestEval, eval);
                        alpha = Math.max(alpha, eval);
                    } else {
                        bestEval = Math.min(bestEval, eval);
                        beta = Math.min(beta, eval);
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return bestEval;
    }

    private int evaluateBoard(Board board) {

        return 0;
    }

    private boolean isValidMove(Board board, Position position) {
        return board.isWithinBoard(position) && !board.hasCounterAtPosition(position);
    }

}

