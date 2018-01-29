package visitor;
import java.util.*;
public class ClassType
{
	public String className ;
	public HashMap<String,String> declarations ;
	public HashMap<String,MType> methods ;
	public String parent ;
	public HashMap<String,Map.Entry<String,Integer>> vtable_methods ;
	public HashMap<String,Map.Entry<String,Integer>> vtable_fields ;
	public int vtable_fields_size;

	public void Generate_VTable_Code(Integer curr_cnt)	
	{
		int function_table_index = curr_cnt ,variable_table_index = curr_cnt + 1,reqd ;
		// System.out.println(vtable_fields_size) ;
		System.out.println("BEGIN");
		System.out.println("MOVE TEMP "+curr_cnt+" HALLOCATE "+4*vtable_methods.size()) ; curr_cnt++ ;		
		reqd = curr_cnt ;
		System.out.println("MOVE TEMP "+curr_cnt+" HALLOCATE "+4*(vtable_fields_size+1)) ; curr_cnt++ ;
		for ( Map.Entry<String, Map.Entry<String,Integer>> entry : vtable_methods.entrySet()) {
			String label = entry.getValue().getKey() + "_" + entry.getKey();
			Integer value = entry.getValue().getValue();
			System.out.println("HSTORE TEMP "+function_table_index+" "+value+" "+ label);
		}				
		System.out.println("HSTORE TEMP "+variable_table_index+" 0 TEMP "+function_table_index);
		for (int i=4;i<=4*vtable_fields_size;i=i+4) {			
			System.out.println("HSTORE TEMP "+variable_table_index+" "+i+" 0") ;
		}
		System.out.println("RETURN TEMP "+reqd+"\nEND");
	}


}
	