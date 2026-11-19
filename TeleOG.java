package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOG extends OpMode {
    DcMotor ul, ur, ll, lr, intake;
    DcMotor shooter;
    double Velocity = 1500;
    double P = 15.1;
    double F = 16.1;

    public void init(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");
        shooter = hardwareMap.dcMotor.get("st");
        intake = hardwareMap.dcMotor.get("in");

        shooter.setDirection(DcMotorSimple.Direction.FORWARD);
        ur.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);

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
            shooter.setPower(0.6);
        }
        else{
            shooter.setPower(0);
        }



    }



    public void hello(){
        telemetry.addLine("Hello My name is verity");
        telemetry.addLine("Hello My name is verity");
    }

}
