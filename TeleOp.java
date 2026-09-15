package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    DcMotor ul, ur, ll, lr, intake;
    public void init(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");
        intake = hardwareMap.dcMotor.get("in");

        ur.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void loop(){
        double forward = gamepad1.left_stick_y*1;
        double strafe = gamepad1.left_stick_x*1;
        double turn = gamepad1.right_stick_x*1;

        hello();

        ul.setPower(forward-strafe-turn);
        ur.setPower(forward+strafe+turn);
        ll.setPower(forward+strafe-turn);
        lr.setPower(forward-strafe+turn);

        if(gamepad1.a){
            intake.setPower(1);
        }
        if(gamepad1.b){
            intake.setPower(-1);
        }
        if(gamepad1.y){
            intake.setPower(0);
        }





    }



    public void hello(){
        telemetry.addLine("Hello My name is verity");
        telemetry.addLine("Hello My name is verity");
    }

}
