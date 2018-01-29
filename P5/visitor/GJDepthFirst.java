//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public HashMap<String,Integer> label_line = new HashMap<String,Integer>();
   public HashMap<String,Graph> graph_set = new HashMap<String,Graph>() ;
   public String curr_func ;
   public boolean func_call ;
   public int line_number = 0 ;
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;

         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) 
         {
         		if(func_call)
         		{
         			String x = (String)e.nextElement().accept(this,argu);
         			if(_count <= 3)
         				System.out.println("MOVE a"+_count+" "+x) ;
         			else
         				System.out.println("PASSARG "+(_count-3)+" "+x) ;         				         			
         		}
         		else
            	e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
      {
      	System.out.println(curr_func+((Label)(n.node)).f0.tokenImage); 
        return n.node.accept(this,argu);
      }
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      curr_func = "MAIN" ;

      int second_num = graph_set.get(curr_func).spilled_temps + 18 ;
      int num3 = graph_set.get(curr_func).third_num ;
      System.out.println("MAIN[0]["+second_num+"]["+num3+"]") ;

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      System.out.println("END") ;
      if(graph_set.get(curr_func).spilled_temps == 0)
      	System.out.println("// NOTSPILLED");
      else
      	System.out.println("// SPILLED");
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n, A argu) {
      R _ret=null;      
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n, A argu) {
      R _ret=null;
      curr_func = n.f0.f0.tokenImage ;
      int num_args = (Integer.parseInt(n.f2.f0.tokenImage)) ;
      if(num_args > 4)      
      	graph_set.get(curr_func).spilled_args = num_args - 4 ;      	

      int second_num = graph_set.get(curr_func).spilled_args + 10 + 8 + graph_set.get(curr_func).spilled_temps ;
      int num3 = graph_set.get(curr_func).third_num ;

      System.out.println(curr_func+"["+num_args+"]["+second_num+"]["+num3+"]") ;

      // save # callee saved regs			
      for (int i=0;i <= 7 ; i++)       
      		System.out.println("ASTORE SPILLEDARG "+(graph_set.get(curr_func).spilled_args+i)+" s"+i ) ;

      // set arguments and free a0,a1,...
      for (int i=0;i<num_args ;i++ ) 
      {
      	String reg1 ;
	      Integer x = i;
	      Vector<Integer> v = graph_set.get(curr_func).reg_alloc.get(x) ;
	    	if(v != null)
	    	{
	    		if(v.get(0) == 0)
		    	{
		    		if(v.get(1) >= 8)reg1 = "t"+(v.get(1)-8) ;
		    		else reg1 = "s"+v.get(1) ;	
		    	}
		    	else	    	
		    		reg1 = "v0" ;	    	

	      	if(i < 4)      	
	      		System.out.println("MOVE "+reg1+" a"+i) ;	      	
	      	else      	
	      		System.out.println("ALOAD "+reg1+" SPILLEDARG "+(i-4)) ;	

	    		if(reg1.equals("v0"))
	    			System.out.println("ASTORE SPILLEDARG "+(graph_set.get(curr_func).spilled_args+18+v.get(1))+" v0") ;
	    	}      	      	
      }
    	     
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n, A argu) {
      R _ret=null;
      line_number++ ;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      System.out.println("NOOP") ;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) {
      R _ret=null;
      System.out.println("ERROR") ;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      String reg = (String)n.f1.accept(this, argu);      
      System.out.println("CJUMP "+reg+" "+(curr_func+n.f2.f0.tokenImage)) ;      
      n.f0.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      System.out.println("JUMP "+(curr_func+n.f1.f0.tokenImage)) ;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public R visit(HStoreStmt n, A argu) {
      R _ret=null;
      String reg1,reg2 ;
      reg1 = (String)n.f1.accept(this, argu);
      if(reg1.equals("v0"))
      	reg2 = (String)n.f3.accept(this, (A)"1");
      else
      	reg2 = (String)n.f3.accept(this, argu);
      System.out.println("HSTORE "+reg1+" "+n.f2.f0.tokenImage+" "+reg2) ;
      n.f0.accept(this, argu);    
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;

      Integer y = (Integer)(Integer.parseInt(n.f1.f1.f0.tokenImage)) ;
      Vector<Integer> v_reg = graph_set.get(curr_func).reg_alloc.get(y) ;
      Vector<Integer> v_range = graph_set.get(curr_func).live_range.get(y) ;
      if (v_reg == null || line_number < (v_range.get(0) - 1) || line_number > v_range.get(1) )       
      	return _ret ;

      String reg1,reg2 ;
      reg1 = (String)n.f1.accept(this, argu);
      reg2 = (String)n.f2.accept(this, argu);
      
      System.out.println("HLOAD "+reg1+" "+reg2+" "+n.f3.f0.tokenImage) ;
      if(reg1.equals("v0"))
      {
      	Integer x = (Integer)(Integer.parseInt(n.f1.f0.tokenImage)) ;
      	Vector<Integer> v = graph_set.get(curr_func).reg_alloc.get(x) ;
      	int reg_slot1 = v.get(1) ;
 	     System.out.println("ASTORE SPILLEDARG "+(graph_set.get(curr_func).spilled_args+18+reg_slot1)+" v0") ;
      }

      n.f0.accept(this, argu);        
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);

      Integer y = (Integer)(Integer.parseInt(n.f1.f1.f0.tokenImage)) ;
      Vector<Integer> v_reg = graph_set.get(curr_func).reg_alloc.get(y) ;
      Vector<Integer> v_range = graph_set.get(curr_func).live_range.get(y) ;
      if (v_reg == null || line_number < (v_range.get(0) - 1) || line_number > v_range.get(1) )       
      	return _ret ;      


      String str1 = (String)n.f1.accept(this, (A)"1");
      String str2 = (String)n.f2.accept(this, argu);
      System.out.println("MOVE "+str1+" "+str2);
      if(str1.equals("v1"))
      {
      	Integer x = (Integer)(Integer.parseInt(n.f1.f1.f0.tokenImage)) ;
      	Vector<Integer> v = graph_set.get(curr_func).reg_alloc.get(x) ;
      	if(v != null)
      	{
      		int reg_slot = v.get(1) ;
      		System.out.println("ASTORE SPILLEDARG "+(graph_set.get(curr_func).spilled_args+18+reg_slot)+" v1") ;	
      	}
      	
      }
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String ret = (String)n.f1.accept(this, argu);
      System.out.println("PRINT "+ret) ;
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n, A argu) {
      R _ret=null;
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public R visit(StmtExp n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      line_number++ ;
      n.f2.accept(this, argu);
      String ret = (String)n.f3.accept(this, argu);
      System.out.println("MOVE v0 "+ret) ;

      // restore # callee saved regs			
      for (int i=0;i <= 7 ; i++)       
      		System.out.println("ALOAD s"+i+" SPILLEDARG "+(graph_set.get(curr_func).spilled_args+i)) ;

      n.f4.accept(this, argu);
      System.out.println("END") ;
      if(graph_set.get(curr_func).spilled_temps == 0)
      	System.out.println("// NOTSPILLED");
      else
      	System.out.println("// SPILLED");
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public R visit(Call n, A argu) {
      R _ret=null;      
      n.f0.accept(this, argu);
      String str1 = (String)n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      func_call = true ;
      n.f3.accept(this, argu);
      func_call = false ;
      n.f4.accept(this, argu);
      for (int i=0;i<=9;i++)       
      	System.out.println("ASTORE SPILLEDARG "+(graph_set.get(curr_func).spilled_args+8+i)+" t"+i);		

      System.out.println("CALL "+str1) ;      

      for (int i=0;i<=9;i++)       
      	System.out.println("ALOAD t"+i+" SPILLEDARG "+(graph_set.get(curr_func).spilled_args+8+i));
      
      return (R)"v0";
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String ret = (String)n.f1.accept(this, argu);
      return (R)("HALLOCATE "+ret);
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      String op = (String)n.f0.accept(this, argu);
      String str2 = (String)n.f2.accept(this, argu);
      String reg1 = (String)n.f1.accept(this, (A)"1"); 
      return (R)(op+" "+reg1+" "+str2);
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public R visit(Operator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String x ="";
      if(n.f0.which == 0)	x = "LE" ;
      else if(n.f0.which == 1)	x = "NE" ;
      else if(n.f0.which == 2)	x = "PLUS" ;
      else if(n.f0.which == 3)	x = "MINUS" ;
      else if(n.f0.which == 4)	x = "TIMES" ;
      else if(n.f0.which == 5)	x = "DIV" ;
      return (R)x ;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n, A argu) {
      R _ret=null;
      String reg1 ;
      if (n.f0.which == 0) 
      {
      	reg1 = (String)n.f0.accept(this, argu);
      }
      else if(n.f0.which == 1)      
      	reg1 = ((IntegerLiteral)(n.f0.choice)).f0.tokenImage ;
      else
				reg1 = ((Label)(n.f0.choice)).f0.tokenImage ;      	     
      return (R)reg1;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n, A argu) {
      R _ret=null;
      String reg1 ;
      Integer x = (Integer)(Integer.parseInt(n.f1.f0.tokenImage)) ;
      Vector<Integer> v = graph_set.get(curr_func).reg_alloc.get(x) ;
      if(v == null)
      {
      	return (R)"v1" ;
      }
    	if(v.get(0) == 0)
    	{
    		if(v.get(1) >= 8)reg1 = "t"+(v.get(1)-8) ;
    		else reg1 = "s"+v.get(1) ;	
    	}
    	else
    	{
    		if(argu == null) reg1 = "v0" ;
    		else reg1 = "v1" ;
    		System.out.println("ALOAD "+reg1+" SPILLEDARG "+(graph_set.get(curr_func).spilled_args+18+v.get(1))) ;      	
    	}
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return (R)reg1;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

}
