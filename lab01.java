public class lab01 {

    /**
     * @param args the command line arguments
     */
	public static int step (long number) {
	int i;
	int count=0;
	long ostatok;
	for(i = 0;number > 0;i ++) {
            ostatok=number%2;
            if (ostatok==1) count++;
            number=(number-ostatok)/2;
        }
        return count;
   }
    public static void main(String[] args) {
        // TODO code application logic here
       long number;
        if(args.length > 0 ) {
           number=Long.parseLong(args[0]);
            System.out.println(" Входное число - " + number);
	System.out.println("Результат  - " + step(number) + ";");
       } 
        else {
            System.out.println("Вы не задали входное число");
	}  
}      
    }

