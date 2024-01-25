package com.thg.accelerator21.connectn.ai.name;

import com.thg.accelerator23.connectn.ai.benandollie.AIrJordan;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AIrJordanTest {

  @Test
  public void VerticalWinTest() {
    StringBuilder OMoves = new StringBuilder("00001111000000000011000000000000000011000000011000000000000000011000000011000000011100000");
    assertTrue(AIrJordan.winCheck(OMoves));
  }

  @Test
  public void NoVerticalWinAcrossColumns() {
    StringBuilder XMoves = new StringBuilder("00000111010000000011000000000000000011000000011000000000000000011000000011000000011100000");
    StringBuilder OMoves = new StringBuilder("1");
    assertFalse(AIrJordan.winCheck(XMoves));
    assertFalse(AIrJordan.winCheck(OMoves));
  }

  @Test
  public void HorizontalWinTest() {
    StringBuilder XMoves = new StringBuilder("00001101000000001011000001000000000011000000011000000000000001011000001011000001011100001");
    assertTrue(AIrJordan.winCheck(XMoves));
  }

  @Test
  public void DiagonalLTRWinTest() {
    StringBuilder XMoves = new StringBuilder("10001101001000001011100001000010000011000000011000000000000001011000001011000001011100001");
    assertTrue(AIrJordan.winCheck(XMoves));
  }

  @Test
  public void DiagonalRTLWinTest() {
    StringBuilder XMoves = new StringBuilder("10001101000000011011100101000011000011000000011000000000000001011000001011000001011100001");
    assertTrue(AIrJordan.winCheck(XMoves));
  }


}
