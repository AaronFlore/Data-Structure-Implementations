// A record class for creating quaternion objects and performing various quaternion operations.

import java.util.List;

public record Quaternion(double a, double b, double c, double d) {

    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);
    
    public Quaternion {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d))
        {
            throw new IllegalArgumentException("Fields cannot be NaN");
        }
    }

    public Quaternion plus(Quaternion q1) {
        Quaternion q2 = this;
        return new Quaternion(q2.a+q1.a, q2.b+q1.b, q2.c+q1.c, q2.d+q1.d);
    }

    public Quaternion minus(Quaternion q1) {
        Quaternion q2 = this;
        return new Quaternion(q2.a-q1.a, q2.b-q1.b, q2.c-q1.c, q2.d-q1.d);
    }

    public Quaternion times(Quaternion q1) {
        Quaternion q2 = this;
        double aProduct = q2.a*q1.a - q2.b*q1.b - q2.c*q1.c - q2.d*q1.d;
        double bProduct = q2.a*q1.b + q2.b*q1.a + q2.c*q1.d - q2.d*q1.c;
        double cProduct = q2.a*q1.c - q2.b*q1.d + q2.c*q1.a + q2.d*q1.b;
        double dProduct = q2.a*q1.d + q2.b*q1.c - q2.c*q1.b + q2.d*q1.a;
        return new Quaternion(aProduct, bProduct, cProduct, dProduct);
    }

    public double norm() {
        return Math.sqrt(a*a + b*b + c*c + d*d);
    }

    public Quaternion normalized() {
        double norm = norm();
        return new Quaternion(a / norm, b / norm, c / norm, d / norm);
    }

    public Quaternion conjugate() {
        return new Quaternion(a, -b, -c, -d);
    }

    public Quaternion inverse() {
        double qSquared = a*a + b*b + c*c + d*d;
        return new Quaternion(a/qSquared, -b/qSquared, -c/qSquared, -d/qSquared);
    }
    
    public List coefficients() {
        return List.of(a, b, c, d);
    }
}