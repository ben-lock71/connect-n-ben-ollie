package com.thg.accelerator23.connectn.ai.benandollie;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class AIrJordan extends Player {
  public AIrJordan(Counter counter) {
    super(counter, "AIrJordan");
  }

  private HashMap<String, String> makeBitBoard(Board board) {
    StringBuilder boardState = new StringBuilder();
    StringBuilder XState = new StringBuilder();
    try {
      Method getCounterBoard = board.getClass().getDeclaredMethod("getCounterPlacements");
      getCounterBoard.setAccessible(true);
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
    String s1 = String.valueOf(boardState);
    String s2 = String.valueOf(XState);
    StringBuilder OMoves = new StringBuilder();
    for (int i = 0; i < s1.length(); i++) {
      OMoves.append(s1.charAt(i) ^ s2.charAt(i));
    }
    int length = OMoves.length();
    for (int i = 0; i < length; i += 8) {
      System.out.println(boardState.substring(i, i + 8));
    }
    HashMap<String, String> boardMap = new HashMap<>(2);
    boardMap.put("X", String.valueOf(XState));
    boardMap.put("O", String.valueOf(OMoves));
    return boardMap;
  }

  public static boolean winCheck(HashMap<String, String> bitBoard) {
    for (String counterBoard : bitBoard.values()) {
      //Vertical win
      for (int i = 0; i < counterBoard.length(); i += 8) {
        char[] row = counterBoard.substring(i, i + 8).toCharArray();
        for (int j = 0; j < row.length - 3; j++) {
          if (row[j] == '1' && row[j + 1] == '1' && row[j + 2] == '1' && row[j + 3] == '1') {
            return true;
          }
        }
      }
      //Horizontal win
      char[] board = counterBoard.toCharArray();
      for (int i = 0; i < counterBoard.length() - 24; i++) {
        if (board[i] == '1' && board[i + 8] == '1' && board[i + 16] == '1' && board[i + 24] == '1') {
          return true;
        }
      }
      //Diagonal win left-to-right
      for (int i = 0; i < counterBoard.length() - 30; i++) {
        if (board[i] == '1' && board[i + 9] == '1' && board[i + 19] == '1' && board[i + 30] == '1') {
          return true;
        }
      }
      //Diagonal win right-to-left
      for (int i = 0; i < counterBoard.length() - 18; i++) {
        if (board[i] == '1' && board[i + 7] == '1' && board[i + 13] == '1' && board[i + 18] == '1') {
          return true;
        }
      }
    }
    return false;
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
    makeBitBoard(board);
    return makeRandomMove(board);
  }
}
