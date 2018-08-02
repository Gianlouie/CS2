// Gianlouie Molinary gi713278
// COP 3503, Spring 2017

import java.io.*;
import java.util.*;

class Node<T>
{
	private T data;
	private int height;
	ArrayList<Node<T>> levels;
	
	Node(int height) // head node constructor
	{
		this.height = height;
		levels = new ArrayList<Node<T>>();
		
		for (int i = 0; i < height; i++) // initialize all to null references
			levels.add(null);
	}
	
	Node(T data, int height) // normal node constructor
	{
		this.height = height;
		this.data = data;
		levels = new ArrayList<Node<T>>();
		
		for (int i = 0; i < height; i++) // initialize all to null references
			levels.add(null);
	}
	
	public T value()
	{
		return this.data;
	}
	
	public int height()
	{
		return this.height;
	}
	
	// reference to the next node at this level. 
	public Node<T> next(int level) 
	{
		if (level < 0 || level > this.height - 1)
			return null;
		
		return levels.get(level);
	}
	
	// set the next reference at this level within this node to the node passed in
	public void setNext(int level, Node<T> node)
	{
		levels.set(level, node);
	}
	
	public void grow()
	{
		levels.add(null);
		this.height++;
	}
	
	public void maybeGrow()
	{
		Random rand = new Random();
		int flip = rand.nextInt(2); // 50% probabality to grow by one
		
		if (flip == 0) 
		{
			levels.add(null);
			this.height++;
		}
		
		else
			; // 50% probabality to do nothing 
	}
	
	public void trim(int height)
	{
		while (levels.size() != height)
		{
			levels.remove(this.height - 1);
			this.height--;
		}
	}
}

public class SkipList<T extends Comparable<T>>
{
	private Node<T> head;
	private int size = 0;
	
	SkipList()
	{
		head = new Node<T>(1);
	}
	
	SkipList(int height)
	{
		if (height < 1)
			head = new Node<T>(1);
		
		else
			head = new Node<T>(height);
	}
	
	public int size()
	{
		return this.size;
	}
	
	public int height()
	{
		return head.height();
	}
	
	public Node<T> head()
	{
		return head;
	}
	
	public void insert(T data)
	{
		// new node that will be generated a random height.
		Node<T> node = new Node<T>(data, generateRandomHeight(getMaxHeight(this.size, head.height())));
		
		this.size++;
		
		Node<T> current = head;
		T nextVal;
		
		int level = head.height() - 1;
		
		while  (level >= 0)
		{
			// null check to avoid null pointer exceptions
			if (current.next(level) == null) // red dots
			{	
				if (level <= node.height() - 1)
				{	
					// The next links in the new node will take on the values previously held by 
					// the red dot nodes that are linking up to the new node.
					node.setNext(level, current.next(level)); 
					
					// All the links denoted by a red dot need to be updated to point to the new node, 
					// as long as they occur at the node's height or lower.
					current.setNext(level, node);
				}
				
				level--; 
			}
			
			// guaranteed to not have null pointer exceptions 
			else if (current.next(level) != null) // red dots
			{
				nextVal = current.next(level).value();
				
				if (nextVal.compareTo(node.value()) >= 0)
				{
					if (level <= node.height() - 1)
					{	
						// The next links in the new node will take on the values previously held by 
						// the red dot nodes that are linking up to the new node.
						node.setNext(level, current.next(level)); 
					
						// All the links denoted by a red dot need to be updated to point to the new node, 
						// as long as they occur at the node's height or lower.
						current.setNext(level, node);
					}
					
					level--;
				}
				
				else if (nextVal.compareTo(node.value()) < 0)
					current = current.next(level);
			}
		}
		
		// If inserting the node causes the ceiling of log_2 n to exceed the skip list's current height
		// we must increase the overall height of the skip list. 
		if (SkipList.getMaxHeight(this.size(), head.height()) > head.height())
		{
			// any node that is reachable at this level should be the only ones that have the abililty to grow.
			level = head.height() - 1;
			
			head.grow(); // head node increases by one.
			
			current = head.next(level); // next node that which is one level below that of the new head height.

			while(current != null)
			{
				current.maybeGrow(); // this node has a 50% chance to grow by one. 
				current = current.next(level);
			}
		}
	}
	
	public void insert(T data, int height)
	{
		// new node that will be given the height passed in.
		Node<T> node = new Node<T>(data, height);
		
		this.size++;
		
		Node<T> current = head;
		T nextVal;
		
		int level = head.height() - 1;
		
		while  (level >= 0)
		{
			// null check to avoid null pointer exceptions
			if (current.next(level) == null)
			{	
				if (level <= node.height() - 1)
				{	
					// The next links in the new node will take on the values previously held by 
					// the red dot nodes that are linking up to the new node.
					node.setNext(level, current.next(level)); 
					
					// All the links denoted by a red dot need to be updated to point to the new node, 
					// as long as they occur at the node's height or lower.
					current.setNext(level, node);
				}
				
				level--; 
			}
			
			// guaranteed to not have null pointer exceptions 
			else if (current.next(level) != null)
			{
				nextVal = current.next(level).value();
				
				if (nextVal.compareTo(node.value()) >= 0)
				{
					if (level <= node.height() - 1)
					{	
						// The next links in the new node will take on the values previously held by 
						// the red dot nodes that are linking up to the new node.
						node.setNext(level, current.next(level)); 
					
						// All the links denoted by a red dot need to be updated to point to the new node, 
						// as long as they occur at the node's height or lower.
						current.setNext(level, node);
					}
					
					level--;
				}
				
				else if (nextVal.compareTo(node.value()) < 0)
					current = current.next(level);
			}
		}
		
		// If inserting the node causes the ceiling of log_2 n to exceed the skip list's current height
		// we must increase the overall height of the skip list. 
		if (SkipList.getMaxHeight(this.size(), head.height()) > head.height())
		{
			// any node that is reachable at this level should be the only ones that have the abililty to grow.
			level = head.height() - 1;
			
			head.grow(); // head node increases by one.
			
			current = head.next(level); // next node that which is one level below that of the new head height.

			while(current != null)
			{
				current.maybeGrow(); // this node has a 50% chance to grow by one. 
				current = current.next(level);
			}
		}
	}
	
	public void delete(T data)
	{
		Node<T> current = head;
		
		int level = head.height() - 1;
		T nextVal;
		
		while (level >= 0)
		{
			// avoids null pointer exceptions 
			if (current.next(level) == null)
				level--;
			
			else if (current.next(level) != null)
			{
				nextVal = current.next(level).value();
				
				if (nextVal.compareTo(data) > 0) 
					level--;
				
				else if (nextVal.compareTo(data) < 0)
					current = current.next(level);
				
				else // found it
				{ 
					current.setNext(level, current.next(level).next(level));
					level--;
					
					if (level < 0)
					{
						this.size--;
						
						if (head.height() > (int)(Math.ceil(Math.log(this.size) / Math.log(2))))
						{
							trimSkipList();
						}
					}
				}
			}
		}
	}
	
	public boolean contains(T data)
	{
		Node<T> current = head;
		
		int level = head.height() - 1;
		T nextVal;
		
		while (level >= 0)
		{
			// avoid null pointer exceptions 
			if (current.next(level) == null)
				level--;
			
			else if (current.next(level) != null)
			{
				nextVal = current.next(level).value();
				
				if (nextVal.compareTo(data) > 0)
					level--;
				
				else if (nextVal.compareTo(data) < 0)
					current = current.next(level);
				
				else // found it 
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Node<T> get(T data)
	{
		Node<T> current = head;
		
		int level = head.height() - 1;
		T nextVal;
		
		while (level >= 0)
		{
			if (current.next(level) == null)
				level--;
			
			else if (current.next(level) != null)
			{
				nextVal = current.next(level).value();
				
				if (nextVal.compareTo(data) > 0)
					level--;
				
				else if (nextVal.compareTo(data) < 0)
					current = current.next(level);
				
				else // found it 
				{
					return current.next(level);
				}
			}
		}
		
		return null;
	}
	
	private static int getMaxHeight(int n, int height)
	{
		// the max of the height of the head node and the ceiling of log_2 n, where n is the number of nodes. 
		return Math.max(height, (int)(Math.ceil(Math.log(n) / Math.log(2))));
	}
	
	private static int generateRandomHeight(int maxHeight)
	{
		// continues with decreasing probability of what the node's height is to be
		// 50%, 25%, 12.5%...
		for (int i = 1; i <= maxHeight; i++)
		{
			if (Math.random() < 0.5)
				return i;
		}
		
		// if all coin flips fail, node will just be max height
		return maxHeight;
	}
	
	private void trimSkipList()
	{
		// all nodes that reach this height exceed the new maximum height
		int level = head.height() - 1;
		
		int newMaxHeight = (int)(Math.ceil(Math.log(this.size) / Math.log(2)));
		
		head.trim(newMaxHeight);
		
		Node<T> current = head.next(level);
		
		while (current != null)
		{
			current.trim(newMaxHeight);
			current = current.next(level);
		}
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
