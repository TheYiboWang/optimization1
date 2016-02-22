/* SampleAnalysis1D.java */

/**
 *  The SampleAnalysis1D class is a program that generate a mount(100) of random starting point by a random seed,
 *  and then by applying the quadFitX() method in the Optimization class to get the minimum point of x.
 *  
 *  The function to be optimized should be edited in the Function.java file.
 *
 *  Data is stored and written into file "1dSampleAnalysis.txt".
 *
 *
 *  @author Yibo Wang     any questions please contact yibow6@uci.edu
 */


import java.io.*;
import java.util.*;

public class SampleAnalysis1D {
    
    public static void main(String[] args) {
        
        // Open a new file out.txt, write the title onto the first line of file.
        try {
            FileWriter fw = new FileWriter("1dSampleAnalysis.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("x0\t\t\t final x \t\t final f(x) \t timesOfEvalution \t timer(ns)");
            bw.newLine();
            bw.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("error" + e.getMessage());
        }
        
        /* random generate a hundred x0 using seed 10062015(due date),
         and run a hundred times optimization. */
        Random random = new Random (10062015);
        int sampleNumber = 100;
        double[] x0 = new double [sampleNumber];
        double[] xf = new double [sampleNumber];
        double[] ffx = new double [sampleNumber];
        double[] toe = new double [sampleNumber];
        double[] timer = new double [sampleNumber];
        
        for (int i = 0; i <= sampleNumber-1; i++){
            
            // timer starts
            long tStart = System.nanoTime();
            
            // starting point x0 is randomly chosen from 0 ~ +50 (user choose)
            double xStart = random.nextDouble() * 50;
            
            Optimization opt1 = new Optimization(xStart);
            double [] result = opt1.quadFitX(); // apply quadFit
            
            // store the x0, final x, final f(x), and times of evaluation.
            x0[i] = xStart;
            xf[i] = result[0];
            ffx[i] = result[1];
            toe[i] = result[2];
            
            // timer ends
            double tEnd = System.nanoTime();
            double tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta;
            timer[i] = elapsedSeconds;
            
            // write the results into file.
            try {
                FileWriter fw = new FileWriter("1dSampleAnalysis.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(xStart+"\t"+result[0]+"\t"+result[1]+"\t"+result[2]+"\t\t"+elapsedSeconds);
                bw.newLine();
                bw.close();
            } catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("error"+e.getMessage());
            }
        }
        System.out.printf("Open 1dSampleAnalysis.txt in current directory to see all data.\n");
        
        /*
         * Mathematical analysis including average, variance and error bar for x, f(x), times of evolution,
         * running time, and relative distance.
         */
        // average, variance, and error bar of x
        double sumXf = 0;
        for (int j = 0; j<=xf.length - 1; j++) {
    	   sumXf = sumXf + xf[j];
        }
        double avrXf = sumXf/xf.length;
        System.out.printf("the average x of 100 samples is %.15g\n", avrXf);
        
        double varXf = 0;
        for (int j = 0; j<xf.length; j++) {
        	varXf = varXf+ Math.pow((xf[j] - avrXf), 2);
        }
        varXf = varXf/xf.length;
        System.out.printf("the sample variance of x is %.15g\n", varXf);
        
        double errorXf = Math.pow(varXf/(sampleNumber-1), 0.5);
        System.out.printf("the sample error bar of x is %.15g\n\n", errorXf);
        
        
        // average, variance, and error bar of f(x) 
        double sumF = 0;
        for (int j = 0; j<ffx.length; j++) {
        	sumF = sumF + ffx[j];
        }
       double avrF = sumF/ffx.length;
       System.out.printf("the average of f of 100 samples is %.15g\n", avrF);
       
       double varF = 0;
       for (int j = 0; j<ffx.length; j++) {
       	varF = varF+ Math.pow((ffx[j] - avrF), 2);
       }
       varF = varF/ffx.length;
       System.out.printf("the sample variance of f(x) is %.15g\n", varF);  
       
       double errorF = Math.pow(varF/(sampleNumber-1), 0.5);
       System.out.printf("the sample error bar of f(x) is %.15g\n\n", errorF);
       
       
       // average, variance, and error bar of times of evaluation
       double sumToe = 0;
       for (int j = 0; j<toe.length; j++) {
    	   sumToe = sumToe + toe[j];
       }
       double avrToe = sumToe/toe.length;
       System.out.printf("the average of times of evaluation is %.0f\n ", avrToe);
       
       double varToe = 0;
       for (int j = 0; j<toe.length; j++) {
       	varToe = varToe+ Math.pow((toe[j] - avrToe), 2);
       }
       varToe = varToe/toe.length;
       System.out.printf("the sample variance of times of evaluation is %.15g\n", varToe);  
       
       double errorToe = Math.pow(varToe/(sampleNumber-1), 0.5);
       System.out.printf("the sample error bar of times of evaluation is %.15g\n\n", errorToe);
       
       
       // average of wall clock running time
       double sumTimer = 0;
       for (int j= 0; j<timer.length; j++) {
    	   sumTimer = sumTimer + timer[j];
       }
       double avrTimer = sumTimer/timer.length;
       System.out.printf("the average of running time is %f nano seconds\n", avrTimer);

       double varTimer = 0;
       for (int j = 0; j<timer.length; j++) {
       	varTimer = varTimer+ Math.pow((timer[j] - avrTimer), 2);
       }
       varTimer = varTimer/timer.length;
       System.out.printf("the sample variance of running time is %.15g\n", varTimer);  
       
       double errorTimer = Math.pow(varTimer/(sampleNumber-1), 0.5);
       System.out.printf("the sample error bar of running time is %.15g\n\n", errorTimer);
       
       
       
       // average of relative distance between output optimum and true mathematical optimum
       /*
        *  this part is particular used for function f(x) = e^x - 5x, 
        *  the mathematical optimum = 5-5*ln5 where x = ln5;
        *  
        *  for f(x) = 5 + (x-2)^4; the mathematical optimum is 5 where x = 2;
        */
       double optimum = 5-5*Math.log(5);
       //double optimum = 5;
       double sumDis = 0;
       for (int j= 0; j<ffx.length; j++) {
    	   sumDis = sumDis + Math.abs(ffx[j] - optimum);
       }
       double avrDis = sumDis/ffx.length;
       System.out.printf("average of relative distance is %.15g\n", avrDis);

       double varDis = 0;
       for (int j = 0; j<ffx.length; j++) {
       	varDis = varDis+ Math.pow((Math.abs(ffx[j] - optimum) - avrDis), 2);
       }
       varDis = varDis/ffx.length;
       System.out.printf("the sample variance of relative distance is %.15g\n", varDis);  
       
       double errorDis = Math.pow(varDis/(sampleNumber-1), 0.5);
       System.out.printf("the sample error bar of relative distance is %.15g\n\n", errorDis);

       // write the results into file.
       try {
           FileWriter fw = new FileWriter("1dSampleAnalysis.txt",true);
           BufferedWriter bw = new BufferedWriter(fw);
           bw.write("\n"+avrXf+"\t"+varXf+"\t"+errorXf+"\n"+avrF+"\t"+varF+"\t"+errorF+"\n"
                    +avrToe+"\t"+varToe+"\t"+errorToe+"\n"
                    +avrTimer+"\t"+varTimer+"\t"+errorTimer+"\n"+avrDis+"\t"+varDis+"\t"+errorDis+"\t");
           bw.newLine();
           bw.close();
       } catch(Exception e)
       {
           e.printStackTrace();
           System.out.println("error"+e.getMessage());
       }
    }
}