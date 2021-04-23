
// Omar R. Gebril 	|| 	SID: 23323978

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// A model for the game of 20 questions. This type can be used to build a 
// console based game of 20 questions or a GUI based game.
//
// @author Rick Mercer and Omar R. Gebril
//
public class GameTree {

	// BinaryTreeNode inner class used to create new nodes in the GameTree.
	private class TreeNode {

		// Instance variables
		private String data;
		private TreeNode left;
		private TreeNode right;

		// You might find this constructor useful, maybe not. If you are losing code
		// coverage with a working object and never used this, feel free to delete this
		// to improve code coverage percentage.
		TreeNode(String theData) {
			data = theData;
			left = null;
			right = null;
		}

		// You might find this constructor useful, maybe not. If you are losing code
		// coverage with a working object and never used this, feel free to delete this
		// to improve code coverage percentage.
		TreeNode(String theData, TreeNode leftLink, TreeNode rightLink) {
			data = theData;
			left = leftLink;
			right = rightLink;
		}
	}

	// Instance variables
	private TreeNode root;
	private String fileName;
	private TreeNode currentNodeReferenceInTree;
	private Scanner scanner;
	private PrintWriter outFile;
	// Hint: You will also need a Scanner and a reference to the current.

	// Constructor needed to create the game. It should open the input file and call
	// the recursive method build(). The String parameter name is the name of the
	// file from which we need to read the game questions and answers from.
	//
	public GameTree(String name) {
		// Complete this constructor. Remember, this needs a try/catch
		// to open the file and a call to the build method code demoed in class
		fileName = name;
		try {
			scanner = new Scanner(new File(fileName));
			root = build();
			currentNodeReferenceInTree = root;
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Method Build() creates the binary tree needed
	private TreeNode build() {
		if (!scanner.hasNextLine()) {
			return null;
		}
		String token = scanner.nextLine().trim();
		if (isAnswer(token)) {
			return new TreeNode(token);
		} else {
			TreeNode left = build();
			TreeNode right = build();
			return new TreeNode(token, left, right);
		}

	}

	private boolean isAnswer(String token) {
		return !token.endsWith("?");
	}

	// Add a new question and answer to the currentNode. If the current node is
	// Feathers?, theGame.add("Does it swim?", "goose"); should change a node
	// like this:
	//
	// ----- Feathers? ----------------- Feathers? ----
	// ----- / ---- \ ------------------ / ------ \ ---
	// - duck --- horse ------ Does it swim? ---- horse
	// --------------------------- / ---- \ -----------
	// ----------------------- goose --- duck ---------
	//
	// @param newQuestion: The question to add where the old answer was.
	// @param newAnswer: The new Yes answer for the new question.
	//
	public void add(String newQuestion, String newAnswer) {
		TreeNode temp = new TreeNode(currentNodeReferenceInTree.data);
		currentNodeReferenceInTree.left = new TreeNode(newAnswer);
		currentNodeReferenceInTree.data = newQuestion;
		currentNodeReferenceInTree.right = temp;

	}

	// Return true if getCurrent() returns an answer rather than a question. Return
	// False
	// if the current node is an internal node rather than an answer at a leaf.
	public boolean foundAnswer() {
		String temp = getCurrent();
		if (temp.endsWith("?")) {
			return false;
		}
		return true;
	}

	// Return the data for the current node, which could be a question or an answer.
	public String getCurrent() {
		// System.out.print(currentNodeReferenceInTree.data);
		return currentNodeReferenceInTree.data;
	}

	// Ask the game to update the current node in the tree by going left for
	// Choice.yes or right for Choice.no Example code:
	// theGame.playerSelected(Choice.Yes);
	//
	public void playerSelected(Choice yesOrNo) {
		if (yesOrNo == Choice.YES) {
			currentNodeReferenceInTree = currentNodeReferenceInTree.left;
			// System.out.print(currentNodeReferenceInTree.data);
		} else if (yesOrNo == Choice.NO) {
			currentNodeReferenceInTree = currentNodeReferenceInTree.right;
			// System.out.print(currentNodeReferenceInTree.data);
		}
	}

	// Begin a game at the root of the tree. getCurrent should return the question
	// at the root of this GameTree.
	public void reStart() {
		GameTree newGame = new GameTree(fileName);
		root = newGame.root;
		currentNodeReferenceInTree = newGame.currentNodeReferenceInTree;

	}

	// Overwrite the old file for this gameTree with the current state that may have
	// new questions added since the game started.
	public void saveGame() {
		// Hint: Call a private helper method with a root argument to do
		// a preorder traversal over the current state of this GameTree
		try {
			if (root == null) {
				return;
			} else {
				outFile = new PrintWriter(new FileOutputStream(fileName));
				transfer(root, outFile);
				outFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void transfer(TreeNode inRoot, PrintWriter outfile) {
		outfile.write(inRoot.data + "\n");
		if (inRoot.left != null) {
			transfer(inRoot.left, outfile);
		}
		if (inRoot.right != null) {
			transfer(inRoot.right, outfile);
		}
	}

	// Method used to print out a text version of the game file.
	String result = "";

	@Override
	public String toString() {
		result = "";
		infix(root, 0);
		return result;
	}

	private void infix(TreeNode t, int level) {
		if (t == null) {
			return;
		} else {
			infix(t.right, level + 1);
			String prefix = "";
			for (int count = 1; count <= level; count++) {
				prefix += "- ";
			}
			result += prefix + t.data + "\n";
			infix(t.left, level + 1);
		}
	}
}