import syntaxtree.*;
import visitor.*;
import java.util.*;

public class P2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();         
         DepthFirstVisitor v1 = new DepthFirstVisitor();
         GJNoArguDepthFirst<Type> v2 = new GJNoArguDepthFirst<Type>();         
         root.accept(v1);
         v2.st = v1.st ;
         root.accept(v2); 
         
         Set entrySet = v2.st.table.entrySet();         
         Iterator it = entrySet.iterator();
         
         while(it.hasNext())
         {
        	 Map.Entry<String, ClassType> m = (Map.Entry<String, ClassType>)it.next();
             // System.out.println(m.getKey()) ;
             // System.out.println(m.getValue().parent) ;
        	 if(m.getValue().parent != null)
        	 {                 
        		 Set es = m.getValue().methods.entrySet() ;         
                 Iterator it2 = es.iterator();                 
                 while(it2.hasNext())
                 {                     
                     // System.out.println("yo1") ;
                	 Map.Entry<String, MType> m2 = (Map.Entry<String, MType>)it2.next();
                	 ClassType c = m.getValue() ;
                     // System.out.println(m2.getValue().methodName) ;
                	 while(c.parent != null)
                	 {
                         // System.out.println(c.parent) ;
                         ClassType c_req = v2.st.table.get(c.parent) ;
                		 if(c_req.methods.containsKey(m2.getKey()))
                		 {                             
                             // System.out.println(c_req.methods.get(m2.getKey()).methodName);
                             // System.out.println(m2.getValue().formal_args_name.get(0)+" and "+c_req.methods.get(m2.getKey()).formal_args_name.get(0));
                             if(! m2.getValue().formal_args_type.equals(c_req.methods.get(m2.getKey()).formal_args_type))
                             {
                                System.out.println("Type error");
                                System.exit(0);    
                             }
                	// 		 Iterator<String> itr1 = m2.getValue().formal_args_type.iterator();
                	// 		 Iterator<String> itr2 = c.methods.get(m2.getKey()).formal_args_type.iterator() ;
                			 
                	// 		 if(m2.getValue().formal_args_type.size() != c.methods.get(m2.getKey()).formal_args_type.size())
                	// 		 {
                	// 			 System.out.println("Type error");
         			 			 // System.exit(0);
                	// 		 }                			 
            			 	//  while(itr1.hasNext() && itr2.hasNext())
            			 	//  {
            			 	// 	String s1 = itr1.next(),s2 = itr2.next() ;
            			 	// 	if(!s1.equals(s2))
            			 	// 	{
            			 	// 		System.out.println("Type error");
            			 	// 		System.exit(0);
            			 	// 	}
            			 	//  }
            			 	 if(!v2.st.Match(c_req.methods.get(m2.getKey()).returnType,m2.getValue().returnType))
            			 	 {
            			 		System.out.println("Type error");
        			 			System.exit(0);
            			 	 }                			                 			
                		 }
                		 c = v2.st.table.get(c.parent) ;
                	 }
                 }
        	 }
         }
         
         root.accept(v2);         
                                           
         System.out.println("Program type checked successfully");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}

