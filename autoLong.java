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
    private ElapsedTime timer = new ElapsedTime();
    private DcMotor ul, ll, ur, lr, intake;
    private DcMotorEx shooter;
    private IMU imu;
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

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logo = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usb = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logo, usb);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

        imu.resetYaw();

    waitForStart();
    if(opModeIsActive()){
      //  drive(1,4,0,0);
        sleep(100);
       // drive(1,0,-15,0);
       drive(1,0,0,48);
        shooter.setVelocity(velocity);
        timer.reset();
            while(timer.seconds() <5){
            }
        intake.setPower(-1);
            timer.reset();
        while(timer.seconds() <5){
        }


    }
}
public void drive(double power, double forwards, double strafes, double turns){
        forwards *=45.26;
        strafes *=45.26;
        turns *=45.26;

        Math.round(forwards);
        Math.round(strafes);
        Math.round(turns);
        int forward = (int)forwards;
        int strafe = (int)strafes;
        int turn = (int)turns;

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
        
        sleep(100);
    }
    public void turn(double Turn){
        //A negative turn value will turn it towards the right
        //A positive turn value will turn it towards the left
        double yawDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        if(Turn > 0){
            while(opModeIsActive() && yawDegrees < Turn) {
                drive(0.5, 0, 0, -1);
                yawDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            }
        }
        else if(Turn < 0){
            while(opModeIsActive() && yawDegrees > Turn) {
                drive(0.5, 0, 0, 1);
                yawDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            }
        }
    }

}