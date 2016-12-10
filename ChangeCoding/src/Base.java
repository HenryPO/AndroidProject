
public class Base
{
    private String baseName = "base";
    public Base()
    {
    	System.out.println("base name = "+baseName);
        callName();
    }
 
    public void callName()
    {
        System. out. println("base");
    }
 
    static class Sub extends Base
    {
        private String baseName = "sub";
        public Sub()
        {
        	System.out.println("sub name = "+baseName);
        }
        public void callName()
        {
            System. out. println ("sub") ;
        }
    }
    public static void main(String[] args)
    {
        Base b = new Sub();
    }
}