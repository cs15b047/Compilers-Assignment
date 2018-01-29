import syntaxtree.*;
import visitor.*;

public class P3 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         GJNoArguDepthFirst<String> v2 = new GJNoArguDepthFirst<String>() ;         
         root.accept(v2);                  
         root.accept(new GJDepthFirst(v2.st),null);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



