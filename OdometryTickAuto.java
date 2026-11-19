package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

@Autonomous(name = "AutoTickOdom")
public class OdometryTickAuto extends LinearOpMode {
    //creating all four motors of our chassis
    private DcMotor ul, ll, ur, lr;
    //Creating our imu/ control hub orientation tracker for rotation
    private IMU imu;
    //Creating our two odometrry pods
    private DcMotorEx forwardPod, lateralPod;

    //To calculate the inches per tick, we must use the equation
    //(Diameter*PI)/ ticks per revolution
    //These ticks per revolution apply for a standard GoBilda 312RPM motor
    private static final double INCHES_PER_TICK = (3.78*Math.PI)/ 537.6;

    //Here are the variables representing the offsets
    // of the odometry pods from the center, but these aren't the official ones until stated so.
    private static final double FORWARD_POD_LATERAL_OFFSET = -4.5;
    private static final double LATERAL_POD_FORWARD_OFFSET = 3.2;

    public void runOpMode(){

    }
}
