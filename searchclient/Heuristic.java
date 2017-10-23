package searchclient;

import java.util.Comparator;

import searchclient.NotImplementedException;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Pair other){
    	return other.first == first && other.second == second;
    }
}

public abstract class Heuristic implements Comparator<Node> {
	public Heuristic(Node initialState) {
		// Here's a chance to pre-process the static parts of the level.
	}

	public int h(Node n) {
		// throw new NotImplementedException();
		// compare columns away
		// create stack of goal locations and arraylist of box locations in map
		Stack<Pair<Character,Integer>> goals = new Stack<Pair<Character,Integer>>();
		ArrayList<Pair<Character,Integer>> boxes = new ArrayList<Pair<Character,Integer>>();
		for (int r : new Range(Node.MAX_ROW)){
			for (int c: new Range(Node.MAX_COL)){
				if (n.boxes[r][c] != 0){
					boxes.add(new Pair<Character,Integer>(n.boxes[r][c],c));
				}
				if (n.goals[r][c] != 0){
					goals.push(new Pair<Character,Integer>(n.goals[r][c],c));
				}
			}
		}

		int total = 0;
		// matches goals with boxes, summing up the column differences between each pair
		while (!goals.isEmpty()){
			Pair<Character,Integer> g = goals.pop();
			char c = g.first;
			int col = g.second;

			for (Pair<Character,Integer> box: boxes){
				if (Character.toLowerCase(box.first) == c){
					total+= Math.abs(box.second-col);
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
}
