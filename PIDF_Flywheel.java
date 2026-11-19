package org.firstinspires.ftc.teamcode;

import androidx.lifecycle.GenericLifecycleObserver;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class PIDF_Flywheel extends OpMode {
    DcMotor ul, ur, ll, lr, intake;
    DcMotorEx shooter;

    double highVelocity = 1500;
    double lowVelocity = 900;

    double curTargetVelocity = highVelocity;

    double P = 0;
    double F = 0;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};

    int stepIndex = 1;
    public void init(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");
        shooter = hardwareMap.get(DcMotorEx.class, "st");
        intake = hardwareMap.dcMotor.get("in");


        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ur.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0,F);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);


    }

    public void loop(){
        double forward = gamepad1.left_stick_y*0.6;
        double strafe = gamepad1.left_stick_x*0.6;
        double turn = gamepad1.right_stick_x*0.6;

        hello();

        ul.setPower(forward-strafe-turn);
        ur.setPower(forward+strafe+turn);
        ll.setPower(forward+strafe-turn);
        lr.setPower(forward-strafe+turn);
        if (gamepad2.right_bumper){
            intake.setPower(-1);
        }
        else if(gamepad2.left_bumper){
            intake.setPower(1);
        }
        else{
            intake.setPower(0);
        }

        if (gamepad2.a){
            shooter.setPower(-0.78);
        }
        else{
            shooter.setPower(0);
        }


        if(gamepad2.yWasPressed()){
            if(curTargetVelocity == highVelocity){
                curTargetVelocity = lowVelocity;
            }
            else{
                curTargetVelocity = highVelocity;
            }
        }
        if (gamepad2.bWasPressed()){
            stepIndex = (stepIndex+1) % stepSizes.length;
        }

        if(gamepad2.dpadLeftWasPressed()){
            F -= stepSizes[stepIndex];
        }
        if(gamepad2.dpadRightWasPressed()){
            F+=stepSizes[stepIndex];

        }
        if (gamepad2.dpadDownWasPressed()){
            P-= stepSizes[stepIndex];
        }
        if (gamepad2.dpadUpWasPressed()){
            P+= stepSizes[stepIndex];
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0,F);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        shooter.setVelocity(curTargetVelocity);

        double curVelocity = shooter.getVelocity();
        double error = curTargetVelocity-curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("-------------------------------");
        telemetry.addData("Tuning F", "%.4f (LEFT/RIGHT DPAD)", F);
        telemetry.addData("Tuning P", "%.4f (UP/DOWN DPAD)", P);
        telemetry.addData("Step Size","%.4f (B button)", stepSizes[stepIndex]);

    }



    public void hello(){
        telemetry.addLine("Hello My name is verity");
        telemetry.addLine("Hello My name is verity");
    }

}
