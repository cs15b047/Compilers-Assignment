package visitor;
import java.util.* ;

public class SymbolTable
{
	public HashMap<String,ClassType> table ;

	public void Generate_VTable()
	{
		for (Map.Entry<String,ClassType> table_entry : table.entrySet())
		{
			ClassType curr_class = table_entry.getValue() ;
			ClassType temp = curr_class ;
			curr_class.vtable_methods = new HashMap<String,Map.Entry<String,Integer>>() ;
			curr_class.vtable_fields = new HashMap<String,Map.Entry<String,Integer>>() ;
			
			Vector<ClassType> parents = new Vector<ClassType>() ;
			// get parents
			{
				while(temp.parent != null)
				{
					parents.add(table.get(temp.parent)) ;
					temp = table.get(temp.parent) ;
				}			
				Collections.reverse(parents) ;
				parents.add(curr_class) ;
			}				
			
			Integer offset1 = 0,offset2=4;	
			//vtables for single class							
			curr_class.vtable_fields_size = 0 ;
			for (ClassType curr_parent : parents)
			{
				//vtable for methods
				for ( Map.Entry<String, MType> entry : curr_parent.methods.entrySet()) 
				{
					MType curr_method = entry.getValue() ;
					Map.Entry<String,Integer> class_and_offset = new AbstractMap.SimpleEntry<String, Integer>(curr_parent.className,offset1);
					//Override
					if(curr_class.vtable_methods.get(curr_method.methodName) != null){
						Map.Entry<String,Integer> temp_entry = curr_class.vtable_methods.get(curr_method.methodName) ;												
						Map.Entry<String,Integer> temp2 = new AbstractMap.SimpleEntry<String, Integer>(curr_parent.className,temp_entry.getValue());
						curr_class.vtable_methods.put(curr_method.methodName,temp2) ;
					}
					//Put new
					else{
						curr_class.vtable_methods.put(curr_method.methodName,class_and_offset) ;
						offset1 = offset1 + 4 ;
					}
					// System.out.println(curr_method.methodName) ;
					// System.out.println(curr_class.vtable_methods.get(curr_class.className+"_"+curr_method.methodName)) ;					
				}				
								
				//vtable for variables
				//remaining for multiple copies of same variable name in diff classes				
				for ( Map.Entry<String, String> decl : curr_parent.declarations.entrySet()) 					
				{
					String variable = decl.getKey() ;
					Map.Entry<String,Integer> class_and_offset = new AbstractMap.SimpleEntry<String, Integer>(curr_class.className,offset2) ;
					curr_class.vtable_fields.put(variable,class_and_offset) ;
					offset2 = offset2 + 4 ;
					curr_class.vtable_fields_size++ ;
					// System.out.println(variable+" "+curr_parent.className) ;
					// System.out.println(curr_class.vtable_fields.get(variable));
				}			
				
			}
		}			
	}
}