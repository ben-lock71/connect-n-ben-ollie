package com.thg.accelerator21.connectn.ai.name;

import com.thg.accelerator23.connectn.ai.benandollie.AIrJordan;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AIrJordanTest {

  @Test
  public void VerticalWinTest() {
    String XMoves = "";
    String OMoves = "00001111000000001100000000000000110000001100000000000000110000001100000011100000";
    HashMap<String, String> boardState= new HashMap<>(2);
    boardState.put("X", XMoves);
    boardState.put("O", OMoves);
    assertTrue(AIrJordan.winCheck(boardState));
  }

  @Test
  public void NoVerticalWinAcrossColumns() {
    String XMoves = "00000111100000001100000000000000110000001100000000000000110000001100000011100000";
    String OMoves = "";
    HashMap<String, String> boardState= new HashMap<>(2);
    boardState.put("X", XMoves);
    boardState.put("O", OMoves);
    assertFalse(AIrJordan.winCheck(boardState));
  }

  @Test
  public void HorizontalWinTest() {
    String XMoves = "00001101000000011100000100000000110000001100000000000001110000011100000111100001";
    String OMoves = "";
    HashMap<String, String> boardState= new HashMap<>(2);
    boardState.put("X", XMoves);
    boardState.put("O", OMoves);
    assertTrue(AIrJordan.winCheck(boardState));
  }

  @Test
  public void DiagonalLTRWinTest() {
    String XMoves = "10001101010000011110000100010000110000001100000000000001110000011100000111100001";
    String OMoves = "";
    HashMap<String, String> boardState= new HashMap<>(2);
    boardState.put("X", XMoves);
    boardState.put("O", OMoves);
    assertTrue(AIrJordan.winCheck(boardState));
  }    @Test
  public void DiagonalRTLWinTest() {
    String XMoves = "10001101000000111110010100011000110000001100000000000001110000011100000111100001";
    String OMoves = "";
    HashMap<String, String> boardState= new HashMap<>(2);
    boardState.put("X", XMoves);
    boardState.put("O", OMoves);
    assertTrue(AIrJordan.winCheck(boardState));
  }
}
