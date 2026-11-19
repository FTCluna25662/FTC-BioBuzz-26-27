package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class ShooterAdjust extends OpMode {
    DcMotor ul, ur, ll, lr, intake, shoot;

    boolean lastLeftState = false;
    boolean lastRightState = false;
    double shooter_power = 0.9;

    public void init(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");
        intake = hardwareMap.dcMotor.get("in");
        shoot = hardwareMap.dcMotor.get("shoot");


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

        if(gamepad2.right_bumper){
            intake.setPower(1);
        }
        else if(gamepad2.left_bumper){
            intake.setPower(-1);
        }
        else{
            intake.setPower(0);
        }


        if(gamepad2.x){
            shoot.setPower(shooter_power);
        }
        else{
            shoot.setPower(0);
        }

        //shooter_power = adjust_power(gamepad2.dpad_left, gamepad2.dpad_right, shooter_power);

    }



    public void hello(){
        telemetry.addLine("Hello My name is verity");
        telemetry.addLine("Hello My name is verity");
    }
    /*public double adjust_power(Boolean pressedLeft, Boolean pressedRight, double power){

        if(pressedRight && !lastRightState){
            if(power < 1){
                power +=0.1;

                power = Math.round(power * 10.0) / 10.0;
            }
        }
        if(power >= 1){
            power = 1;
            telemetry.addLine("Power is at 1(max)");
        }

        if(pressedLeft && !lastLeftState){
            if(power > -1){
                power -=0.1;
                power = Math.round(power * 10.0) / 10.0;

            }
        }
        if(power  <= -1){
            power = -1;
            telemetry.addLine("Power is at -1(min)");
        }
        telemetry.addData("Shooter Power: ", power);
        telemetry.update();

        lastLeftState = pressedLeft;
        lastRightState = pressedRight;

        return power;

    }*/

}
