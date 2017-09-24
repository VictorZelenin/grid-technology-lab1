package ua.kpi.cad.gridtech.lab1.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static java.lang.Math.*;

public class Main {
    private static final double H = 0.25;
    private static final double p1 = 500;
    private static final double ALPHA = 30;
    private static final double p2 = 1000;
    private static final double f = 15;
    private static final double g = 9.8;
    private static final double V0 = 0;
    private static final double START_TIME = 0;
    private static final double END_TIME = 20;
    private static double TIME_STEP = 0.001;

    private static final double m = PI * p1 / 3.0 * pow(tan(ALPHA* PI / 180), 2) * pow(H, 3);
    private static final double h0 = H * pow(p1 / p2, 1.0 / 3.0);

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        double k1, k2, k3, k4;
        double q1, q2, q3, q4;

        double x = 0.05;
        double velocity = V0;

        if (args.length > 0) {
            try {
                x = Double.parseDouble(args[0]);
            } catch (Exception e) {
                System.err.println("Wrong initial rejection value");
            }

            try {
                TIME_STEP = Double.parseDouble(args[1]);
            } catch (Exception e) {
                System.err.println("Wrong time step value.");
            }
        }

        PrintWriter printWriter = new PrintWriter("data.csv", "UTF-8");

        printWriter.write("Time,Rejection,Velocity\n");
        System.out.print("Time,Rejection,Velocity\n");


        for (double time = START_TIME; time <= END_TIME; time += TIME_STEP) {
            k1 = TIME_STEP * velocity;
            q1 = TIME_STEP * function(x, velocity);

            k2 = TIME_STEP * (velocity + q1 / 2.0);
            q2 = TIME_STEP * function(x + k1 / 2.0, velocity + q1 / 2.0);

            k3 = TIME_STEP * (velocity + q2 / 2.0);
            q3 = TIME_STEP * function(x + k2 / 2.0, velocity + q2 / 2.0);

            k4 = TIME_STEP * (velocity + q3);
            q4 = TIME_STEP * function(x + k3, velocity + q3);

            double newRejectionValue = x + (k1 + 2 * k2 + 2 * k3 + k4) / 6.0;
            double newVelocityValue = velocity + (q1 + 2 * q2 + 2 * q3 + q4) / 6.0;

            printWriter.write(time + "," + x + "," + velocity + "\n");
            System.out.println(time + "," + x + "," + velocity);

            x = newRejectionValue;
            velocity = newVelocityValue;
        }
    }

    private static double function(double x, double v) {
        return -g + PI * pow(tan(ALPHA * PI / 180), 2) * pow(h0 - x, 2) / m * (p2 * g / 3.0 * (h0 - x) -
                f / sin(ALPHA * PI / 180) * v);
    }
}
