class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac(1));
    }
}

class Fac {
    public int ComputeFac(){
        int num_aux ;
        if ((num <= 1)&&(num != 1))
            num_aux = (1+0) ;
        else
            num_aux = num * (this.ComputeFac(num-1)) ;
        return num_aux ;
    }
}
