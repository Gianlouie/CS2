// Gianlouie Molinary gi713278
// COP 3503, Spring 2017

import java.io.*;
import java.util.*;

public class ConstrainedTopoSort
{
	ArrayList<LinkedList<Integer>> list;
	ArrayList<Integer> top;
	
	public ConstrainedTopoSort(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));
		
		int n = in.nextInt(); // the number of vertices in the graph
		
		int k; // the number of vertices in the adjacency list
		
		list = new ArrayList<LinkedList<Integer>>();
		
		for (int i = 0; i < n; i++) // initialize the arraylist with the adjacency lists
		{
			list.add(new LinkedList<Integer>());
			
			// k dictates the length of the adjacency list so the loop below 
			// should capture the vertices in which vertex i is adjacent to
			// and place them in the appropriate list.
			k = in.nextInt();
			
			for (int j = 0; j < k; j++)
			{
				list.get(i).add(in.nextInt());
			}
		}
	}
	
	// Dr. Szumlanski's topological sort method from his notes with a different return type
	// and instead of a adjacency matrix, it works with an adjacency list. 
	public ArrayList<Integer> topoSort()
	{
		int [] incoming = new int[list.size()];
		int cnt = 0;
		
		top = new ArrayList<Integer>();
		
		// Count the number of incoming edges incident to each vertex.
		for (int i = 0; i < list.size(); i++)
		{
			incoming[i] = list.get(i).size();
		}
		
		Queue<Integer> q = new ArrayDeque<Integer>();
		
		for (int i = 0; i < list.size(); i++)
		{
			if (incoming[i] == 0)
				q.add(i);
		}
		
		while(!q.isEmpty())
		{
			// Pull a vertex out of the queue and add it to the topological sort.
			int node = q.remove();
			
			top.add(node);
			
			// Count the number of unique vertices we see
			++cnt;
			
			for (int i = 0; i < list.size(); i++)
			{
				if (--incoming[i] == 0)
					q.add(i);
			}
		}
		
		// If we pass out of the loop without including each vertex in our
		// topological sort, we must have a cycle in the graph.
		if (cnt != list.size())
			return null;
		
		return top;
	}
	
	public boolean hasConstrainedTopoSort(int x, int y)
	{
		if (x == y)
			return false;
		
		// vertex x should not be in the adjacency list of vertex y
		if (list.get(y - 1).contains(x))
			return false;
		
		top = topoSort();
		
		if (top == null) // cycle is present in the graph
			return false;
			
		if (top.indexOf(x) > top.indexOf(y))
			return false;
		
		return true;
	}

	public static double difficultyRating()
	{
		return 5.0;
	}

	public static double hoursSpent()
	{
		return 20;
	}
}