package com.thg.accelerator23.connectn.ai.benandollie;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


public class AIrJordan extends Player {
    private static final int MAX_DEPTH = 10;

    public AIrJordan(Counter counter) {
        super(counter, "AIrJordan");
    }

    public static boolean winCheck(HashSet<StringBuilder> bitBoard) {
        for (StringBuilder counterBoard : bitBoard) {
            char[] board = String.valueOf(counterBoard).toCharArray();
            int boardLength = board.length;
            //Vertical win
            for (int i = 0; i < boardLength; i += 8) {
                char[] row = Arrays.copyOfRange(board, i, i + 8);
                for (int j = 0; j < row.length - 3; j++) {
                    if (row[j] == '1' && row[j + 1] == '1' && row[j + 2] == '1' && row[j + 3] == '1') {
                        return true;
                    }
                }
            }
            //Horizontal win
            for (int i = 0; i < boardLength - 24; i++) {
                if (board[i] == '1' && board[i + 8] == '1' && board[i + 16] == '1' && board[i + 24] == '1') {
                    return true;
                }
            }
            //Diagonal win left-to-right
            for (int i = 0; i < boardLength - 30; i++) {
                if (board[i] == '1' && board[i + 9] == '1' && board[i + 19] == '1' && board[i + 30] == '1') {
                    return true;
                }
            }
            //Diagonal win right-to-left
            for (int i = 0; i < boardLength - 18; i++) {
                if (board[i] == '1' && board[i + 7] == '1' && board[i + 13] == '1' && board[i + 18] == '1') {
                    return true;
                }
            }
        }
        return false;
    }

    private HashSet<StringBuilder> makeBitBoard(Board board) {
        StringBuilder boardState = new StringBuilder();
        StringBuilder XState = new StringBuilder();
        try {
            Method getCounterBoard = board.getClass().getDeclaredMethod("getCounterPlacements");
            Counter[][] counters = (Counter[][]) getCounterBoard.invoke(board);
            List<List<Counter>> columns = Arrays.stream(counters)
                    .map(Arrays::asList).toList();
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
                XState.append(XString);
                boardState.append(emptyString);

            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        StringBuilder OMoves = new StringBuilder();
        for (int i = 0; i < boardState.length(); i++) {
            OMoves.append(boardState.charAt(i) ^ XState.charAt(i));
        }
        HashSet<StringBuilder> boardMap = new HashSet<>(2);
        boardMap.add(XState);
        boardMap.add(OMoves);
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
        if (depth == 0 || winCheck(makeBitBoard(board))) {
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

