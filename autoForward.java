package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous
public class autoForward extends LinearOpMode{
    private DcMotor ul, ll, ur, lr;
    private IMU imu;
    public static final double TICKS_PER_INCH = 45.26;



    public void runOpMode(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        lr = hardwareMap.dcMotor.get("lr");
        ll = hardwareMap.dcMotor.get("ll");

        ur.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);

        imu = hardwareMap.get(IMU.class, "imu");

        ul.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ur.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ll.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RevHubOrientationOnRobot.LogoFacingDirection logo = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usb = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logo, usb);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

        imu.resetYaw();

    waitForStart();
    imu.resetYaw();

    if(opModeIsActive()){
        drive(1,-5,0,0,false);
       // drive(1,22,0,0, false);
        sleep(100);
      //  drive(1,0,23,0, false);
        drive(1,0,0,90,true);


    }
}
public void drive(double power, double forwards, double strafes, double degrees, boolean turn) {
//The turn booleans is supposed to ask the user if you want to use the turn function.
    //This is to prevent errors such as:
    //The user wants to turn to the angle to the original 0 degrees,
    //but he doesn't want the whole drive function to run too.
    //To present that, we add a condition, turn, to prevent this issue.
    if (turn == false) {
        int forward = (int) Math.round(forwards*TICKS_PER_INCH);
        int strafe = (int) Math.round(strafes*TICKS_PER_INCH);

        ul.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ll.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ur.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ll.setTargetPosition(forward - strafe);
        ul.setTargetPosition(forward + strafe);
        lr.setTargetPosition(forward + strafe);
        ur.setTargetPosition(forward - strafe);

        ul.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ll.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ur.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ul.setPower(power);
        ur.setPower(power);
        lr.setPower(power);
        ll.setPower(power);

        while (opModeIsActive() && ul.isBusy() && lr.isBusy() && ur.isBusy() && ll.isBusy()) {
        }

       stopMotors();
        sleep(100);

    } else {
        turn(degrees);
    }
}
    public void turn(double Target){
        //Turn(80) does not add 80 degrees,
        // it merely just forces the robot to turn until
        // the IMU read around 78.5-81.5 degrees.(That's because of tolerence)

        ul.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ll.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ur.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double kp = 0.012; //controls how smoothly the robot slows down
        double minPower = 0.08; //Prevents stalling right before turning angle
        double maxPower = 0.75;//Caps turning speed
        double tolerance = 2; //Stops within 1.5 degrees of target



        while(opModeIsActive()){
            double error = AngleUnit.normalizeDegrees(Target-getYaw());

            error = AngleUnit.normalizeDegrees(Target-getYaw());

            if (Math.abs(error) <= tolerance) {
                break;
            }

            double power = error*kp;

            if (Math.abs(power)<minPower){
                power = Math.signum(error)*minPower;
                //Math.signum only returns
                // -1, 0, or 1 depending on the original sign
                //If error was -9.38902, it would return -1
                //if error was 82780, it would return 1
            }




            power = Math.max(-maxPower, Math.min(maxPower, power));

            //positive value will turn counterclockwise
            ul.setPower(-power);
            ll.setPower(-power);
            ur.setPower(power);
            lr.setPower(-power);
        }
       stopMotors();

    }
    private double getYaw(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public void stopMotors(){
        ul.setPower(0);
        ur.setPower(0);
        lr.setPower(0);
        ll.setPower(0);
    }
}