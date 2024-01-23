package com.thg.accelerator23.connectn.ai.benandollie;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AIrJordan extends Player {
  public AIrJordan(Counter counter) {
    super(counter, "AIrJordan");
  }


  private String makeBitBoard(Board board) {
    StringBuilder boardState = new StringBuilder();
    StringBuilder XState = new StringBuilder();
    try {
      Method getCounterBoard = board.getClass().getDeclaredMethod("getCounterPlacements", Board.class);
      getCounterBoard.setAccessible(true);
      Counter[][] counters = (Counter[][]) getCounterBoard.invoke(null, board);
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
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
    }
      String s1 = String.valueOf(boardState);
    String s2 = String.valueOf(XState);
    StringBuilder OMoves = new StringBuilder();
    for (int i = 0; i < s1.length(); i++) {
      OMoves.append(s1.charAt(i)^s2.charAt(i));
    }
    int length = OMoves.length();
    for (int i = 0; i < length; i += 8) {
      System.out.println(OMoves.substring(i, i+8));
    }
    return String.valueOf(boardState);
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
    return makeRandomMove(board);
  }}
