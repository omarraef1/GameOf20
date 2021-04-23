
// Omar R. Gebril 	|| 	SID: 23323978

package model;

// Choice is an enum to be used in class GameTree as an argument to a method
//
//   public void playerSelected(Choice yesOrNo) 
// 
// This helps avoid problems when a string or char ar used with issues like case sensitivity
//
// Sample code:
// 
//  Choice userSelection = Choice.Yes;
//
//  if(yesOrNo == Choice.YES
// 
// @author Rick Mercer
//
public enum Choice {
  YES, NO
};
