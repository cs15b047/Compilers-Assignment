package visitor;
import java.util.*;
public class GraphNode
{
	public int line ;
	public Set<Integer> in;
	public Set<Integer> out;
	public Set<Integer> use;
	public Set<Integer> def;
	public Set<Integer> nbhs ;

	GraphNode(int num)
	{
		line = num ;
		in = new HashSet<Integer>() ;
		out = new HashSet<Integer>() ;
		use = new HashSet<Integer>() ;
		def = new HashSet<Integer>() ;
		nbhs = new HashSet<Integer>() ;
	}

}