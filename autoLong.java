package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Timer;

@Autonomous
public class autoLong extends LinearOpMode{
    public static final double TICKS_PER_INCH = 45.26;

    private ElapsedTime timer = new ElapsedTime();
    private DcMotor ul, ll, ur, lr, intake;
    private DcMotorEx shooter;
    double velocity = 1200;
    double P = 50;
    double F = 16.1;



    public void runOpMode(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        lr = hardwareMap.dcMotor.get("lr");
        ll = hardwareMap.dcMotor.get("ll");
        intake = hardwareMap.dcMotor.get("in");

        shooter = hardwareMap.get(DcMotorEx.class,("st"));


        ur.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0 ,0, F);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        ul.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ur.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ll.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



    waitForStart();
    if(opModeIsActive()){
        //This Autonomous starts one mat away from the flower
        // closest to the right of the loading zone. It will simply park.
        drive(1,4,0,0);
        drive(1,0,-15,0);
        /*shooter.setVelocity(velocity);
        timer.reset();
            while(timer.seconds() <5){
            }
        intake.setPower(-1);
            timer.reset();
        while(timer.seconds() <5){
        }*/


    }
}
public void drive(double power, double forwards, double strafes, double turns){
    //When turns is set to 24, it turns a perfect 90 degrees. Use that as reference.

    int forward = (int) Math.round(forwards*TICKS_PER_INCH);
    int strafe = (int) Math.round(strafes*TICKS_PER_INCH);
    int turn = (int) Math.round(turns*TICKS_PER_INCH);

        ul.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ll.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ur.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ll.setTargetPosition(forward-strafe+turn);
        ul.setTargetPosition(forward+strafe+turn);
        lr.setTargetPosition(forward+strafe-turn);
        ur.setTargetPosition(forward-strafe-turn);

        ul.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ll.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ur.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ul.setPower(power);
        ur.setPower(power);
        lr.setPower(power);
        ll.setPower(power);

        while(ul.isBusy() && lr.isBusy() &&ur.isBusy() &&ll.isBusy()){
        }

        stopMotors();
    }
    public void stopMotors(){
        ul.setPower(0);
        ur.setPower(0);
        lr.setPower(0);
        ll.setPower(0);
    }

}