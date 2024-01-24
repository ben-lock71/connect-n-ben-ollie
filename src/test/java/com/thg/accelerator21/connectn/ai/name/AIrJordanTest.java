package com.thg.accelerator21.connectn.ai.name;

import com.thg.accelerator23.connectn.ai.benandollie.AIrJordan;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static com.thg.accelerator23.connectn.ai.benandollie.AIrJordan.winCheck;
import static org.junit.jupiter.api.Assertions.*;

public class AIrJordanTest {

  @Test
  public void VerticalWinTest() {
    StringBuilder XMoves = new StringBuilder();
    StringBuilder OMoves = new StringBuilder("00001111000000001100000000000000110000001100000000000000110000001100000011100000");
    HashSet<StringBuilder> boardState= new HashSet<>(2);
    boardState.add(XMoves);
    boardState.add(OMoves);
    assertTrue(winCheck(boardState));
  }

  @Test
  public void NoVerticalWinAcrossColumns() {
    StringBuilder XMoves = new StringBuilder("00000111100000001100000000000000110000001100000000000000110000001100000011100000");
    StringBuilder OMoves = new StringBuilder();
    HashSet<StringBuilder> boardState= new HashSet<>(2);
    boardState.add(XMoves);
    boardState.add(OMoves);
    assertFalse(winCheck(boardState));
  }

  @Test
  public void HorizontalWinTest() {
    StringBuilder XMoves = new StringBuilder("00001101000000011100000100000000110000001100000000000001110000011100000111100001");
    StringBuilder OMoves = new StringBuilder();
    HashSet<StringBuilder> boardState= new HashSet<>(2);
    boardState.add(XMoves);
    boardState.add(OMoves);
    assertTrue(winCheck(boardState));
  }

  @Test
  public void DiagonalLTRWinTest() {
    StringBuilder XMoves = new StringBuilder("10001101010000011110000100010000110000001100000000000001110000011100000111100001");
    StringBuilder OMoves = new StringBuilder();
    HashSet<StringBuilder> boardState= new HashSet<>(2);
    boardState.add(XMoves);
    boardState.add(OMoves);
    assertTrue(AIrJordan.winCheck(boardState));
  }    @Test
  public void DiagonalRTLWinTest() {
    StringBuilder XMoves = new StringBuilder("10001101000000111110010100011000110000001100000000000001110000011100000111100001");
    StringBuilder OMoves = new StringBuilder();
    HashSet<StringBuilder> boardState= new HashSet<>(2);
    boardState.add(XMoves);
    boardState.add(OMoves);
    assertTrue(AIrJordan.winCheck(boardState));
  }
}
