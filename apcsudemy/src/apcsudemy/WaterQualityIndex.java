package apcsudemy;

import java.util.Scanner;

public class WaterQualityIndex {

    private static final double pATM = 0.9767, // pressure (atm) expected at 650 ft elevation
                                CtoK = 273.15, // conversion from celsius to kelvin
                                A = 0.00000006436, // quadratic fitting function
                                B = 0.00001426,
                                C = 0.000975;


    private static final double[] DOxset = {0.0, 16.0, 20.0, 32.0, 34.0, 62.0, 67.0, 70.0, 74.0, 80.0, 84.0, 90.0, 94.0, 98.0, 102.0, 106.0, 137.0, 140.0},
                                  DOyset = {2.0, 10.0, 12.0, 20.0, 22.0, 60.0, 70.0, 75.0, 80.0, 87.0, 90.0, 95.0, 98.0, 99.0, 99.0,  98.0,  80.0,  78.0 },
                                  
                                  PHxset = {2.0, 3.0, 3.5, 4.0, 4.1,  4.5,  4.8,  5.1,  6.2,  6.8,  7.0,  7.1,  7.2,  7.4,  7.6,  7.8,  8.0,  8.9,  9.7,  10.0, 10.3, 10.7, 10.8, 11.0, 11.5, 12.0},
                                  PHyset = {2.0, 4.0, 6.0, 9.0, 10.0, 15.0, 20.0, 30.0, 60.0, 83.0, 88.0, 90.0, 92.0, 93.0, 92.0, 90.0, 84.0, 52.0, 26.0, 20.0, 15.0, 11.0, 10.0, 8.0,  5.0,  3.0},
                                  
                                  TPxset = {  0.0,  0.2,  0.5,  0.7,  1.0,  1.3,  1.6,  2.0,  3.2,  4.0,  5.0,  6.0, 7.0, 8.0, 10.0},
                                  TPyset = {100.0, 92.0, 60.0, 50.0, 40.0, 34.0, 30.0, 27.0, 20.0, 17.0, 13.0, 10.0, 8.0, 7.0,  7.0},
                                  
                                  NIxset = { 0.0,  2.0,  3.0,  3.5,  4.0,  6.0, 10.0, 17.0, 27.0, 37.0, 50.0, 54.0, 71.0, 80.0, 90.0, 100.0},
                                  NIyset = {97.0, 95.0, 90.0, 80.0, 70.0, 60.0, 51.0, 40.0, 30.0, 20.0, 10.0,  8.0,  4.5,  4.0,  3.0,   2.5},

                                  TUxset = { 0.0,  3.0,  8.0, 13.0, 15.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0},
                                  TUyset = {99.0, 90.0, 80.0, 70.0, 67.0, 61.0, 53.0, 45.0, 39.0, 33.0, 29.0, 25.0, 22.0,  17.0};
                                  
    private static double[] weights = {0.17, 0.11, 0.11, 0.10, 0.08};
    private static double weightTotal = weights[0] + weights[1] + weights[2] + weights[3] + weights[4];

    private static double tempC;
	
    private static Scanner input;

    /**
     * DO saturation
     * pH
     * total phosphate
     * nitrate
     * turbidity
     */
	
	public static void main(String[] args)
	{
        double[] inputs = new double[6];
        input = new Scanner(System.in);

        System.out.println("Enter the water temperature (C): "); // store the temp
        tempC = input.nextDouble();
        
        System.out.println("");
        System.out.println("Enter a number for DO (ppm): "); // this gets converted into % sat
        inputs[0] = input.nextDouble();
        inputs[0] = DOPercentFromPPM(inputs[0]);
        System.out.println("DO % Saturation: " + inputs[0]);
        inputs[0] = Math.round(CalculateDOWQI(inputs[0]));
        System.out.println("DO WQI: " + inputs[0]);

        System.out.println("");
        System.out.println("Enter your pH: ");
        inputs[1] = Math.round(CalculatepHWQI(input.nextDouble()));
        System.out.println("pH WQI: " + inputs[1]);

        System.out.println("");
        System.out.println("Enter your total phosphate (ppm): ");
        inputs[2] = Math.round(CalculateTotalPhosphateWQI(input.nextDouble()));
        System.out.println("Total phosphate WQI: " + inputs[2]);

        System.out.println("");
        System.out.println("Enter your nitrate (ppm): ");
        inputs[3] = Math.round(CalculateNitrateWQI(input.nextDouble()));
        System.out.println("Nitrate WQI: " + inputs[3]);

        System.out.println("");
        System.out.println("Enter your turbidity (NTU): ");
        inputs[4] = Math.round(CalculateTurbidityWQI(input.nextDouble()));
        System.out.println("Turbidity WQI: " + inputs[4]);

        int i;
        double result = 0;
        for (i = 0; i < 5; i++) {
            result += (inputs[i] * (weights[i] / weightTotal));
        }
        System.out.println("");
        System.out.println("Your WQI Rating is: " + Math.round(result));

        System.out.println("");
        System.out.println("Press Enter key to close...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }

    public static double WQIFromData(double target, double[] xset, double[] yset, double nomatch) {
        int i;
        boolean found = false;

        for (i = 0; i < xset.length && !found; i++) {
            if (xset[i] <= target && xset[i + 1] >= target && !found)
                found = true;
        }
        if (found)
        {
            i--;
            return MatchXToY(target, xset[i], xset[i + 1], yset[i], yset[i + 1]);
        } else
            return nomatch;
    }

    public static double MatchXToY (double target, double x0, double x1, double y0, double y1) {
        double slope = (y1 - y0) / (x1 - x0);

        return (y0 + slope * (target - x0));
    }
    
    public static double DOPercentFromPPM(double ppm) {
        double  tempK = tempC + CtoK,
                pWV = Math.exp(11.8571 - (3840.7 / tempK) - (216961 / Math.pow(tempK, 2))),
                theta = A * Math.pow(tempC, 2) + B * tempC + C,
                equilibrium = Math.exp(7.7117 - 1.31403 * Math.log(tempC + 45.93)),
                percentSaturation,
                cP = equilibrium * pATM * (((1 - pWV / pATM) * (1 - theta * pATM)) / ((1 - pWV) * (1 - theta)));

        percentSaturation = (double)Math.round(10000 * ppm / cP) / 100;

        return percentSaturation;
    }

    public static double CalculateDOWQI(double percentSaturation) {
        if (percentSaturation > 140)
            return 50;

        return WQIFromData(percentSaturation, DOxset, DOyset, 50);
    }

    public static double CalculatepHWQI(double pH) {
        if (pH > 12 || pH < 2)
            return 0;
        
        return WQIFromData(pH, PHxset, PHyset, 0);
    }

    public static double CalculateTotalPhosphateWQI(double totalPhosphate) {
        if (totalPhosphate > 10)
            return 2;

        return WQIFromData(totalPhosphate, TPxset, TPyset, 2);
    }

    public static double CalculateNitrateWQI(double nitrate) {
        if (nitrate > 100)
            return 1;

        return WQIFromData(nitrate, NIxset, NIyset, 1);
    }

    public static double CalculateTurbidityWQI(double turbidity) {
        if (turbidity > 100)
            return 5;
        
        return WQIFromData(turbidity, TUxset, TUyset, 5);
    }
}
