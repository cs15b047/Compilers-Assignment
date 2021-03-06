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
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	Vector<String> expr_list = new Vector<String>() ;
    String curr_class,curr_method ;
    public int pass = 1 ;
    public SymbolTable st = new SymbolTable() ;
    
    //check
    public R check_id(String str)
    {
    	  ClassType a = st.table.get(curr_class);
		  MType c = a.methods.get(curr_method);
		  String typ = c.var_decl.get(str);
		  if(typ == null) 
		  {
			  if(c.formal_args_name.contains(str))
			  {
				  int index = c.formal_args_name.indexOf(str) ;
				  return (R)c.formal_args_type.get(index) ;
			  }
			  else
			  {
				  if(a.declarations.get(str) == null)
				  {
					  //TODO:searching in base classes remaining
					  ClassType curr = a ;
					  while(curr.parent != null)
					  {
						  curr = st.table.get(curr.parent) ;
						  if(curr.declarations.get(str) != null)
						  {
							  return (R)curr.declarations.get(str) ;
						  }
					  }
					  System.out.println("Symbol not found");
//      			  System.out.println("yerror_1");
					  System.exit(0);
				  }
				  else
					  return (R)a.declarations.get(str) ;
  		  }    			  
		  }    		      		  
		  return (R)typ ;
    }
        
   public R visit(NodeList n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n) {
      if ( n.present() )
         return n.node.accept(this);
      else
         return null;
   }

   public R visit(NodeSequence n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      pass = pass + 1 ;
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      n.f8.accept(this);
      n.f9.accept(this);
      n.f10.accept(this);
      n.f11.accept(this);
      n.f12.accept(this);
      n.f13.accept(this);
      n.f14.accept(this);
      n.f15.accept(this);
      n.f16.accept(this);
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      curr_class = n.f1.f0.toString() ;        
      if(pass == 1) {
//    	  System.out.println("Class:"+curr_class);
    	  st.table.get(curr_class).methods = new HashMap<String,MType>() ;
          st.table.get(curr_class).declarations = new HashMap<String,String>();
          st.table.get(curr_class).parent = null ;
      }
      
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      curr_class = null ;
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n) {
      R _ret=null;
      curr_class = n.f1.f0.toString() ;      
      if(pass == 1)
      {
//    	  System.out.println("Extend:"+curr_class+n.f3.f0.toString());
    	  if(st.table.get(n.f3.f0.toString()) == null)
    	  {
    		  System.out.println("Symbol not found");
    		  System.exit(0);
    	  }
    	  st.table.get(n.f1.f0.toString()).parent = n.f3.f0.toString() ;
    	  st.table.get(curr_class).methods = new HashMap<String,MType>() ;
    	  st.table.get(curr_class).declarations = new HashMap<String,String>();
      }
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      curr_class= null ;
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n) {
      R _ret=null;
      if(pass == 1)
      {
	      if(curr_class != null && curr_method == null)
	      {
	    	 if(st.table.get(curr_class).declarations.get(n.f1.f0.toString()) == null)	    	 
	    		 st.table.get(curr_class).declarations.put(n.f1.f0.toString(),(String)n.f0.accept(this)) ;
	    	 else
	    	 {
	    		 System.out.println("Type error");
	    		 System.exit(0) ;
	    	 } 
	      }
	      else if(curr_class != null && curr_method != null)  
	      {
	        String str = (String)n.f0.accept(this) ;
	        ClassType x = st.table.get(curr_class) ;        
	        MType y = (MType)x.methods.get(curr_method);
	        if(y.var_decl.get(n.f1.f0.toString()) == null && !y.formal_args_name.contains(n.f1.f0.toString()))
	        	y.var_decl.put(n.f1.f0.toString(),str) ;
	        else
	        {
	        	 System.out.println("Type error");
	    		 System.exit(0) ;
	        }
	      }
      }
      else if(pass == 2)
    	  n.f0.accept(this) ;
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n) {
      R _ret=null;      
      curr_method = n.f2.f0.toString() ;      
      if(pass == 1)
      {  	  
    	  if(st.table.get(curr_class).methods.get(curr_method) != null)
    	  {
    		  System.out.println("Type error");
    		  System.exit(0);
    	  }
    	  st.table.get(curr_class).methods.put(n.f2.f0.toString(),new MType()) ;
	      st.table.get(curr_class).methods.get(n.f2.f0.toString()).methodName = n.f2.f0.toString() ;
	      ClassType x = ((ClassType)st.table.get(curr_class)) ;
	      MType y = (MType)x.methods.get(n.f2.f0.toString()) ;  
	      y.var_decl = new HashMap<String,String>() ;
	      y.formal_args_type = new Vector<String>() ;
	      y.formal_args_name = new Vector<String>() ;
	      y.parentClass = curr_class ;      
	      n.f0.accept(this);
	      y.returnType = (String)n.f1.accept(this);
      }
      else if(pass == 2)
      {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
      }
//      System.out.println(y.returnType) ;
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      n.f8.accept(this);
      n.f9.accept(this);
      n.f10.accept(this);
      n.f11.accept(this);
      n.f12.accept(this);
      if(pass == 2)
      {
    	  if( !st.Match((String)n.f1.accept(this),(String)n.f10.accept(this) )  )
    	  {    		  
    		  System.out.println("Type error");
    		  System.exit(0);
    	  }
      }
      curr_method = null ;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n) {
      R _ret=null;
      if(pass == 1)
      {    	  
	      ClassType x = ((ClassType)st.table.get(curr_class)) ;
	      MType y = (MType)x.methods.get(curr_method) ;  
	      
	      //TODO:check for duplicate formal arguments
	      if(y.formal_args_name.contains(n.f1.f0.toString()))
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }	      
	      y.formal_args_type.add((String)n.f0.accept(this)) ;
	      y.formal_args_name.add(n.f1.f0.toString()) ;
//	      System.out.println("Formal_arg_name:"+n.f1.f0.toString());
//	      System.out.println("Formal_arg_type:"+(String)n.f0.accept(this));
	//      System.out.println(y.formal_args.get(0)) ;
      }
      else if(pass == 2)
    	  n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n) {
      R _ret=null;
      String x = n.f0.accept(this).toString() ;  
      if(n.f0.which == 3)
      {
    	  if(st.table.get(x) == null)
    	  {
    		  System.out.println("Symbol not found");
    		  System.exit(0) ;
    	  }
      }
      return (R)x;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n) {
      R _ret=null;
      n.f0.accept(this);
      String x = n.f0.tokenImage ;
//      System.out.println("Array:"+x);
      n.f1.accept(this);
      n.f2.accept(this);
      return (R)"Array";
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n) {
      R _ret=null;
      n.f0.accept(this);
      return (R)n.f0.tokenImage;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n) {
      R _ret=null;
      n.f0.accept(this);
      String x = n.f0.tokenImage ;
      return (R)x;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n) {
      R _ret=null;
      String lhs = (String)n.f0.accept(this);
      n.f1.accept(this);
      String typ_rhs = (String)n.f2.accept(this);
      n.f3.accept(this);      
      if(pass == 2)
      {
//    	  System.out.println("assignment");
    	  String typ_lhs =(String)check_id(lhs) ;    	  
    	  if(!st.Match(typ_lhs,typ_rhs))
    	  {
    		  System.out.println("Type error");
			  System.exit(0) ;
    	  }
      }           
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public R visit(ArrayAssignmentStatement n) {
      R _ret=null;
      String id = (String)n.f0.accept(this);
      n.f1.accept(this);
      R y = n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      R x = n.f5.accept(this);
      n.f6.accept(this);
      
      if(pass == 2 )
      {
//    	  System.out.println("arr_assignment");
    	  if(((String)y).equals("int") && ((String)x).equals("int") && ((String)check_id(n.f0.f0.toString())).equals("Array") )
    	  {}
    	  else
    	  {
    		  System.out.println("Type error");
			  System.exit(0) ;
    	  }
      }
      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(pass == 2 && !((String)n.f2.accept(this)).equals("boolean"))
      {       	  
		  System.out.println("Type error");
		  System.exit(0) ;    	  
      }
//      else if(pass == 2)System.out.println("if stmt");
      n.f3.accept(this);
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(pass == 2 && !((String)n.f2.accept(this)).equals("boolean"))
      {    	  
		  System.out.println("Type error");
		  System.exit(0) ;    	  
      }
//      else if(pass==2)System.out.println("if_else");
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(pass == 2 && !((String)n.f2.accept(this)).equals("boolean"))
      {    	  
		  System.out.println("Type error");
		  System.exit(0) ;    	  
      }
//      else if(pass==2)System.out.println("while");
      n.f3.accept(this);
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(pass == 2 && !((String)n.f2.accept(this)).equals("int"))
      {    	  
		  System.out.println("Type error");
		  System.exit(0) ;    	  
      }
//      else if(pass==2)System.out.println("print");
      n.f3.accept(this);
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public R visit(Expression n) {
      R _ret=null;
      String x = (String)n.f0.accept(this);
      return (R)x;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n) {
      R _ret=null;
      if(pass == 2)
      {
	      boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ((String)n.f0.accept(this)).equals("boolean"))	      
	    	  return (R)"boolean" ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ((String)n.f0.accept(this)).equals("boolean") )	      
	    	  return n.f0.accept(this) ;
	      else
	      {	    	  
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ( (((String)n.f0.accept(this)).equals("int")) )  )	      
	    	  return (R)"boolean" ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ( (((String)n.f0.accept(this)).equals("int"))) )  	      
	    	  return (R)"boolean" ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ((String)n.f0.accept(this)).equals("int"))	      
	    	  return n.f0.accept(this) ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ((String)n.f0.accept(this)).equals("int"))	      
	    	  return n.f0.accept(this) ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ((String)n.f0.accept(this)).equals("int"))	      
	    	  return n.f0.accept(this) ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n) {
      R _ret=null;
      if(pass == 2)
      {
    	  boolean match = st.Match((String)n.f0.accept(this),(String)n.f2.accept(this)) ;
	      if(match == true && ((String)n.f0.accept(this)).equals("int"))	      
	    	  return n.f0.accept(this) ;
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      else {
    	  n.f0.accept(this);
    	  n.f1.accept(this);
    	  n.f2.accept(this);
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n) {
      R _ret=null;
      n.f0.accept(this);      
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      if(pass == 2)
      {
	      if(((String)n.f2.accept(this)).equals("int") && ((String)n.f0.accept(this)).equals("Array"))
	      {
	    	  return (R)"int" ;
	      }
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n) {
      R _ret=null;      
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(pass == 2)
      {
	      if(((String)n.f0.accept(this)).equals("Array"))
	      {
	    	  return (R)"int" ;
	      }
	      else
	      {
	    	  System.out.println("Type error");
	    	  System.exit(0);
	      }
      }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n) {
      R _ret=null;
      n.f0.accept(this);      
      n.f1.accept(this);
      n.f2.accept(this);
      MType m = null;
      ClassType c = null ;
      Vector<String> temp_expr_list = new Vector<String>() ;  ;
      if(pass == 2)
      {
    	  if(st.table.get((String)n.f0.accept(this)) != null)
          {
    		  c = st.table.get((String)n.f0.accept(this)) ;
    		  while(c != null && c.methods.get((String)n.f2.accept(this)) == null )
    		  {
    			  if(c.parent == null)c = null ;
    			  else c = st.table.get(c.parent) ;
    		  }
    		  if(c == null)
    		  {
    			  System.out.println("Symbol not found");//TODO: Actually : Method not found
    			  System.exit(0);
    		  }
    		  else 
    		  {
	    		  m = c.methods.get((String)n.f2.accept(this)) ;   
	//			  System.out.println("Function Call for class:"+c.className+" method:"+m.methodName);
			      temp_expr_list.addAll(expr_list) ;
			      expr_list.clear();
    		  }
          }
    	  else
    	  {
    		  System.out.println("Type error");//TODO:Ambiguous
    		  System.exit(0);
    	  }
      }
      n.f3.accept(this);            
      n.f4.accept(this);
      
      if(pass == 2)
      {
    	  if(expr_list.size() != m.formal_args_type.size())
    	  {    		  
        	  System.out.println("Type error");//TODO:Ambiguous
        	  System.exit(0);
    	  }
    	  Iterator<String> itr1 = expr_list.iterator();
    	  Iterator<String> itr2 = m.formal_args_type.iterator() ;
    	  while(itr1.hasNext() && itr2.hasNext()){
    		  String s1 = itr1.next(),s2 = itr2.next() ; 
    		  if(!st.Match(s2,s1))
              {
//            	  System.out.println(s1+" "+s2);
            	  System.out.println("Type error");
            	  System.exit(0);
              }
          }
    	  expr_list.clear();
    	  expr_list.addAll(temp_expr_list) ;
    	  return (R)m.returnType ;
      }                            
      n.f5.accept(this);                  
      return _ret;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n) {
      R _ret=null;
      if(pass == 2)
      {
    	  expr_list.add((String)n.f0.accept(this)) ;
      }
      else
    	  n.f0.accept(this) ;
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      expr_list.add((String)n.f1.accept(this)) ;
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n) {
      R _ret=null;      
      if(pass == 2)
      {   	  
    	  if(n.f0.which == 3)
    	  {
    		  //check scope
    		  String str = (String)n.f0.accept(this) ;
//    		  System.out.println(curr_class+":"+curr_method);
    		  return check_id(str);    		      		  
    	  }
    	  String x = (String)n.f0.accept(this) ;          
          return (R)x;
      }
      else
    	  return null;
      
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n) {
      R _ret=null;
      n.f0.accept(this);     
      String x = "int" ;
      return (R)x;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      return (R)"boolean";
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      return (R)"boolean";
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n) {
      R _ret=null;
      n.f0.accept(this);
      return (R)n.f0.tokenImage;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n) {
      R _ret=null;
      n.f0.accept(this);      
      return (R)curr_class;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      if(pass == 2 && !((String)n.f3.accept(this)).equals("int") )
      {
    	  System.out.println("Type error");
		  System.exit(0);
      }
      n.f4.accept(this);      
      return (R)"Array";
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n) {
      R _ret=null;      
      n.f0.accept(this);
      if(pass == 2)
      {
    	  String id = (String)n.f1.accept(this);
    	  ClassType c = st.table.get(id) ;
    	  if(c == null)
    	  {
    		  System.out.println("Symbol not found");//TODO:
    		  System.exit(0);
    	  }
    	  return (R)id ;
      }
      n.f2.accept(this);
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n) {
      R _ret=null;
      n.f0.accept(this);
      if(pass == 2)
      {
    	  String b = "boolean" ;
    	  if( ((String)n.f1.accept(this)).equals(b))
    	  {
    		  return (R)"boolean";
    	  }
    	  else
    	  {
    		  System.out.println("Type error");
    		  System.exit(0);
    		  return null ;
    	  }
      }
      else
    	  return null ;      
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      if(pass == 2)
    	  return (R)n.f1.accept(this);
      else
    	  return null ;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }
}


