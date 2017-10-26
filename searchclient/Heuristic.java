package searchclient;

import java.util.Comparator;
import java.util.ArrayList;

import searchclient.NotImplementedException;

public abstract class Heuristic implements Comparator<Node> {
	
	private ArrayList<Pair<Character, Integer>> goals;

	public Heuristic(Node initialState) {
		// Store locations of all goals
		// This information does not change, so running these loops once in our constructor suffices
		this.goals = new ArrayList<Pair<Character, Integer>>();
		for (int r = 0; r < Node.MAX_ROW; r++) {
			for (int c = 0; c < Node.MAX_COL; c++) {
				if (Node.goals[r][c] != 0) {
					this.goals.add(new Pair<Character, Integer>(Node.goals[r][c], c));
				}
			}
		}
	}

	public int h(Node n) {		

		// Store locations of all boxes
		ArrayList<Pair<Character, Integer>> boxes = new ArrayList<Pair<Character, Integer>>();
		for (int r = 0; r < Node.MAX_ROW; r++) {
			for (int c = 0; c < Node.MAX_COL; c++) {
				if (n.boxes[r][c] != 0) {
					boxes.add(new Pair<Character, Integer>(n.boxes[r][c], c));
				}
			}
		}

		// Match each goal with a box, sum up the column distances for each match
		int total = 0;
		for (Pair<Character, Integer> goal : this.goals) {
			char c = goal.first;
			int col = goal.second;
			for (Pair<Character, Integer> box : boxes) {
				if (Character.toLowerCase(box.first) == c) {
					total += Math.abs(box.second - col);
					boxes.remove(box);
					break;
				}
			}
		}
		return total;
	}

	public abstract int f(Node n);

	@Override
	public int compare(Node n1, Node n2) {
		return this.f(n1) - this.f(n2);
	}

	public static class AStar extends Heuristic {
		public AStar(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return n.g() + this.h(n);
		}

		@Override
		public String toString() {
			return "A* evaluation";
		}
	}

	public static class WeightedAStar extends Heuristic {
		private int W;

		public WeightedAStar(Node initialState, int W) {
			super(initialState);
			this.W = W;
		}

		@Override
		public int f(Node n) {
			return n.g() + this.W * this.h(n);
		}

		@Override
		public String toString() {
			return String.format("WA*(%d) evaluation", this.W);
		}
	}

	public static class Greedy extends Heuristic {
		public Greedy(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return this.h(n);
		}

		@Override
		public String toString() {
			return "Greedy evaluation";
		}
	}

	public static class Pair<F, S> {
	    public final F first;
	    public final S second;

	    public Pair(F first, S second) {
	        this.first = first;
	        this.second = second;
	    }

	    // @Override
	    public boolean equals(Pair other) {
	    	return other.first == first && other.second == second;
	    }
	}
}

