
package cn.com.wysha.debate_tournament.tools;

/**
 * @author wysha
 */
public class Tools {
    public static double gcd(double num1, double num2) {
        //辗转相除法(欧几里得算法)
        while (num2 != 0) {
            double temp = num1 % num2;
            num1 = num2;
            num2 = temp;
        }
        return num1;
    }
}
