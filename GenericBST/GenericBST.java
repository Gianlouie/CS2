// Gianlouie Molinary gi713278
// COP 3503, Spring 2017

import java.io.*;
import java.util.*;

class Node<T>
{
	T data;
	Node<T> left, right;

	Node(T data)
	{
		this.data = data;
	}
}

public class GenericBST<T extends Comparable<T>> // This guarantees that the object being passed implements Comparable and the compareTo method
{
	private Node<T> root;

	public void insert(T data)
	{
		root = insert(root, data);
	}

	private Node<T> insert(Node<T> root, T data)
	{
		if (root == null)
		{
			return new Node<T>(data); // just create a new root, since it was empty
		}
		
		else if (data.compareTo(root.data) < 0) // the object is guaranteed to have this method
		{
			root.left = insert(root.left, data);
		}
		
		else if (data.compareTo(root.data) > 0) 
		{
			root.right = insert(root.right, data);
		}
		
		else
			; // no need for a case for duplicate values since they are not allowed. 

		return root;
	}

	public void delete(T data)
	{
		root = delete(root, data);
	}

	private Node<T> delete(Node<T> root, T data)
	{
		if (root == null) // empty tree, or data is not present to be deleted. 
		{
			return null;
		}
		
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		
		else // found the node to be deleted 
		{
			if (root.left == null && root.right == null) // no children so just delete it. No one will miss it. 
			{
				return null;
			}
			
			else if (root.right == null) 
			{
				return root.left; // one child, left child takes its place
			}
			
			else if (root.left == null)
			{
				return root.right; // one child, right child takes its place
			}
			
			else // two children so take the max of the left subtree and have that take its place. 
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private T findMax(Node<T> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Returns true if the value is contained in the BST, false otherwise.
	public boolean contains(T data)
	{
		return contains(root, data);
	}

	private boolean contains(Node<T> root, T data)
	{
		if (root == null) // empty tree, or didn't find it
		{
			return false;
		}
		
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		
		else // found it
		{
			return true;
		}
	}

	public void inorder() // Left Middle Right
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	private void inorder(Node<T> root) 
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	public void preorder() // Middle Left Right
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	private void preorder(Node<T> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder() // Left Right Middle
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	private void postorder(Node<T> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
	
	public static double difficultyRating()
	{
		return 1.0;
	}
	
	public static double hoursSpent()
	{
		return 2;
	}

}
