import syntaxtree.*;
import visitor.*;

public class P5 {
   public static void main(String [] args) {
      try {
         Node root = new microIRParser(System.in).Goal();
         DepthFirstVisitor v1 = new DepthFirstVisitor();
         root.accept(v1); // Your assignment part is invoked here.
         GJNoArguDepthFirst<String> v2 = new GJNoArguDepthFirst<String>();
         v2.graph_set = v1.graph_set ;
         v2.label_line = v1.label_line ;
         root.accept(v2);
         GJDepthFirst v3 = new GJDepthFirst() ;
         v3.graph_set = v2.graph_set ;
         v3.label_line = v2.label_line ;
         root.accept(v3,null);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



