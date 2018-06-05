import edu.princeton.cs.algs4.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

public final class Solver
{
    private SearchNode goalNode;
    int count = 0;
    private static class SearchNode implements Comparable<SearchNode>
    {
        Board board;
        SearchNode prevNode;
        int moves;
        int priority;
        SearchNode(Board board)
        {

            this.board = board;
            prevNode = null;
            moves = 0;
            priority = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that)
        {
            if (that == null) throw new NullPointerException();
            /*int priorityA = board.manhattan() + moves;
            int priorityB = that.board.manhattan() + moves;*/
            if (priority < that.priority) return -1;
            if (priority > that.priority) return 1;
            return 0;
        }
    }

    public Solver(Board initial) // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new IllegalArgumentException("Null board.");

        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial));

        MinPQ<SearchNode> minPQTwin = new MinPQ<>();
        minPQTwin.insert(new SearchNode(initial.twin()));

        SearchNode minNode = minPQ.delMin();
        SearchNode minTwnNode = minPQTwin.delMin();

        long start = System.currentTimeMillis();

        while (!minNode.board.isGoal() && !minTwnNode.board.isGoal()) {
            for (Board main : minNode.board.neighbors()) {

                if (minNode.prevNode == null || !main.equals(minNode.prevNode.board)) {
                    SearchNode current = new SearchNode(main);
                    current.prevNode = minNode;
                    current.moves = minNode.moves + 1;
                    current.priority += current.moves;
                    minPQ.insert(current);
                    ++count;
                }

            }//for

            for (Board twn : minTwnNode.board.neighbors()) {
                if (minNode.prevNode == null || !twn.equals(minNode.prevNode.board)) {
                    SearchNode current = new SearchNode(twn);
                    current.prevNode = minNode;
                    current.moves = minNode.moves + 1;
                    current.priority += current.moves;
                    minPQTwin.insert(current);
                }

            }//for

            minNode = minPQ.delMin();
            minTwnNode = minPQTwin.delMin();

        }//while

        if (minNode.board.isGoal()) goalNode = minNode;
        else goalNode = null;
    }


    public boolean isSolvable()            // is the initial board solvable?
    {
        return (goalNode != null);
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return (isSolvable()) ? goalNode.moves : -1;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable()) return null;

        Stack<Board> solution = new Stack<>();
        solution.push(goalNode.board);

        while (goalNode.prevNode != null) {
            goalNode = goalNode.prevNode;
            solution.push(goalNode.board);
        }
        return solution;
    }

    public static void main(String[] args) throws IOException// solve a slider puzzle (given below)
    {
        File file = new File(args[0]);
        Scanner fileInp = new Scanner(file);
        //In in = new In(args[0]);
        int n = fileInp.nextInt();
        int[][] blocks = new int[n][n];
        int i, j;
        i = 0;
        while (fileInp.hasNext() && i < n) {
            j = 0;
            while (fileInp.hasNext() && j < n)
                blocks[i][j++] = fileInp.nextInt();
            i++;
        }
        /*for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = fileInp.nextInt();*/
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
           /* PrintWriter fileOut = new PrintWriter("4x4Goal");
            fileOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                fileOut.print(board + " ");*/
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
           // fileOut.close();
        }


    }
}
