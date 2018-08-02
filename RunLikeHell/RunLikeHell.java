// Gianlouie Molinary gi713278
// COP 3503, Spring 2017

import java.io.*;
import java.util.*;

public class RunLikeHell
{
	/*public static int maxGain(int [] blocks)
	{
		return maxGain(blocks, blocks.length-1);
	}
	
	// *recursive solution*
	private static int maxGain(int [] blocks, int k) 
	{
		if (k < 0)
			return 0;
		
		return Math.max(blocks[k] + maxGain(blocks, k - 2), maxGain(blocks, k - 1));
	}*/
	
	// dynamic programming solution 
	public static int maxGain(int [] blocks)
	{
		if (blocks.length == 1)
			return blocks[0];
		
		int skip, knowledgeLost = 0;
		int [] knowledgeGain = new int[blocks.length];
		knowledgeGain[0] = blocks[0];
		
		for (int i = 1; i < blocks.length; i++)
		{
			skip = Math.max(knowledgeGain[i-1], knowledgeLost); 
			
			knowledgeGain[i] = knowledgeLost + blocks[i]; 
			knowledgeLost = skip;
		}
		
		return Math.max(knowledgeGain[blocks.length - 1], knowledgeGain[blocks.length - 2]);
	}
	
	public static double difficultyRating()
	{
		return 3.0;
	}
	
	public static double hoursSpent()
	{
		return 20;
	}
}