/* Function.java */

/* The Function class has one method calculateF, which define f(x) and f(x,y).
 * User can try different equations by changing the calculateF below.
 *
 * Four examples are defined and listed in the final report, which are:
 * f(x) = e^x - 5x 
 * f(x) = 5 + (x-2)^4
 * f(x,y) = (1-x)^2 + 100*(y-x^2)^2
 * f(x,y) = (x-2)^4 + (x-2y)^2
 * 
 *@author Yibo Wang, yibow6@uci.edu
 * 10-06-2015
 */

public class Function {
    double xCoordinate;
    double yCoordinate;
    double zCoordinate;
    
    public Function(double x0) {
        xCoordinate = x0;
        yCoordinate = 0;
        zCoordinate = 0;
    }
    
    public Function(double x0, double y0) {
        xCoordinate = x0;
        yCoordinate = y0;
        zCoordinate = 0;
    }
    
    public Function(double x0, double y0, double z0) {
        xCoordinate = x0;
        yCoordinate = y0;
        zCoordinate = z0;
    }
    
    /*
     * change the test function here:
     */
    public double calculateF() {
        double x = xCoordinate;
        double y = yCoordinate;
        
        //double f = Math.pow((x-2), 4) + Math.pow((x-2*y), 2);
        //double f = Math.pow((1-x), 2) + 100 * Math.pow((y - Math.pow(x,2)),2);
        double f = Math.pow(Math.E, x) - 5*x;
        //double f = 5 + Math.pow((x-2), 4);
        return f;
    }
}