package visitor;
import java.util.* ;

public class SymbolTable
{
	public HashMap<String,ClassType> table ;
	
	public boolean Match(String a, String b)
	{
		if(a.equals("int") || a.equals("boolean") || a.equals("Array") )
		{
			return a.equals(b) ;
		}
		else
		{
			if(a.equals(b))return true ;
			ClassType curr = table.get(b);
			while(curr.parent != null )
			{
				if(!curr.parent.equals(a))
					curr = table.get(curr.parent) ;
				else
				{
					return true ;
				}
			}
			return false ;				
		}		
	}
}