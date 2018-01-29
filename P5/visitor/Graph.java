package visitor;
import java.util.*;

public class Graph
{
	public HashMap<Integer,GraphNode> line_node ;	
	public HashMap<Integer,Vector<Integer>> live_range ;
	public HashMap<Integer,Vector<Integer>> reg_alloc ;  // temp_num  --> ( (0/1) , number of reg/stack slot     0-> register  1--> stack slot)
	public int first_line,last_line ;
	public int spilled_args;
	public int spilled_temps ;
	public int callee_saved ;
	public int used_registers ;
	public int third_num ;
	Graph()
	{
		line_node = new HashMap<Integer,GraphNode>() ;
		live_range = new HashMap<Integer,Vector<Integer>>() ;
		reg_alloc = new HashMap<Integer,Vector<Integer>>() ;
		spilled_args = 0 ;
		spilled_temps = 0 ;
		callee_saved = 8 ;
		used_registers = 0 ;
		third_num = 0;
	}

	public void Liveness()
	{
		boolean flag = false ;
		int iter = 0 ;
		while(!flag)
		{
			iter++ ;
			flag = true ;
			for (int i = last_line ; i >= first_line ; i--) 
			{
				GraphNode curr = line_node.get(i) ;
				int in_size = curr.in.size() ;
				int out_size = curr.out.size() ;
				if(curr.nbhs != null)
				{
					for (Integer nbh_line : curr.nbhs ) 
					{
						// System.out.println(nbh_line);
						// System.out.println(line_node.get(nbh_line));
						curr.out.addAll(line_node.get(nbh_line).in) ;		
					}
				}
				
				if (out_size != curr.out.size() ) 			
					flag = false ;	

				Set<Integer> temp = new HashSet<Integer>(curr.out);
				temp.removeAll(curr.def) ;
				curr.in.addAll(temp);
				curr.in.addAll(curr.use);			
				if (in_size != curr.in.size())
					flag = false ;
			}	
		}
		// System.out.println("Iterations: "+iter);
		

		GraphNode curr ;
		for (int i = first_line ; i <= last_line ; i++) 
		{
			curr = line_node.get(i) ;
			for (Integer temp : curr.in ) 
			{
				if (live_range.get(temp) != null) 			
					live_range.get(temp).setElementAt(i,1) ;				
				else
				{
					Vector<Integer> v =  new Vector<Integer>();
					v.add(i); v.add(i);
					live_range.put(temp,v) ;
				}
			}
		}
	}


	public void LinearScan()
	{
		int num_reg = 18 ; // TODO 
		int spill_loc = 0 ;
		Vector<Integer> active = new Vector<Integer>() ;
		Vector<Integer> ordered_ranges = new Vector<Integer>();
		for (Map.Entry<Integer,Vector<Integer>> entry : live_range.entrySet() ) 		
			ordered_ranges.add(entry.getKey()) ;		
		Collections.sort(ordered_ranges, new Comparator<Integer>() {
	        @Override
	        public int compare(Integer a, Integer b) 
	        {	   
	        	if(live_range.get(a).get(0) <  live_range.get(b).get(0))return -1 ;
	      		return 1;
	        }           
	    });

		Vector<Integer> registers = new Vector<Integer>();
		for (int i=0;i<num_reg ;i++)
			registers.add(i) ;
		

	    for (Integer x : ordered_ranges) 
	    {
	    	while(!active.isEmpty() && live_range.get(active.firstElement()).get(1) < live_range.get(x).get(0))
	    	{
	    		// System.out.println("Expire: "+active.firstElement()) ;
	    		//Adding to front of active
	    		registers.add(reg_alloc.get(active.firstElement()).get(1)) ;
	    		active.removeElementAt(0) ;	    		
	    	}

	    	if (active.size() == num_reg) 
	    	{
	    		if (live_range.get(active.lastElement()).get(1) > live_range.get(x).get(1) ) 
	    		{
	    			Vector<Integer> v1 = new Vector<Integer>() ; v1.add(0); v1.add(reg_alloc.get(active.lastElement()).get(1)) ;
	    			Vector<Integer> v2 = new Vector<Integer>() ; v2.add(1); v2.add(spill_loc); spill_loc++ ;
	    			reg_alloc.put(x,v1);
	    			reg_alloc.put(active.lastElement(),v2) ;
	    			active.removeElementAt(active.size()-1) ;
	    			active.add(x) ; 
	    			Collections.sort(active, new Comparator<Integer>() {
				        @Override
				        public int compare(Integer a, Integer b) 
				        {	   
				        	if(live_range.get(a).get(1) <  live_range.get(b).get(1))return -1 ;
				      		return 1;
				        }           
				    });
				    // System.out.println("Replaced: "+active.lastElement()+" with :"+x) ;
	    		}
	    		else
	    		{
	    			// System.out.println("Spilled: "+x) ;
	    			Vector<Integer> v = new Vector<Integer>() ; v.add(1); v.add(spill_loc); spill_loc++ ; 
	    			reg_alloc.put(x,v) ;
	    		}
	    	}
	    	else
	    	{
	    		// System.out.println("Added: "+x) ; 
	    		Vector<Integer> v = new Vector<Integer>() ; v.add(0); v.add(registers.firstElement()) ; registers.removeElementAt(0) ;
	    		reg_alloc.put(x,v) ;
	    		
	    		active.add(x) ; 
	    		Collections.sort(active, new Comparator<Integer>() {
			        @Override
			        public int compare(Integer a, Integer b) 
			        {	   
			        	if(live_range.get(a).get(1) <  live_range.get(b).get(1))return -1 ;
			      		return 1;
			        }           
			    });
	    	}
	    }
	    spilled_temps = spill_loc ;

	    for (Map.Entry<Integer,Vector<Integer>> entry : reg_alloc.entrySet() ) 
	    {
	    	if(entry.getValue().get(0) == 0 && used_registers < entry.getValue().get(1))	    	
	    		used_registers = entry.getValue().get(1) ;	    	
	    }


	}



}