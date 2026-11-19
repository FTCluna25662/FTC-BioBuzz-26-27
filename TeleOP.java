package org.firstinspires.ftc.teamcode;

import androidx.lifecycle.GenericLifecycleObserver;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOP extends OpMode {
    DcMotor ul, ur, ll, lr, intake;
    DcMotorEx shooter;
    VoltageSensor batteryVoltage;
    double Velocity = 1300;
    double baseP = 50;
    double baseF = 16.1;
    double nominal_voltage = 12.0;
    //in volts, and in terms of the GoBilda 12v battery
    double last_voltage = 0;
    boolean shootTrue;


    public void init(){
        ul = hardwareMap.dcMotor.get("ul");
        ur = hardwareMap.dcMotor.get("ur");
        ll = hardwareMap.dcMotor.get("ll");
        lr = hardwareMap.dcMotor.get("lr");
        shooter = hardwareMap.get(DcMotorEx.class, "st");
        intake = hardwareMap.dcMotor.get("in");
        batteryVoltage = hardwareMap.voltageSensor.iterator().next();

        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ur.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);

        voltageCompensation();

    }

    public void loop(){

        voltageCompensation();

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

        if (gamepad2.aWasPressed()){
            shootTrue = !shootTrue;
        }

        if (shootTrue){
            shooter.setVelocity(Velocity);
        }
        else{
            shooter.setVelocity(0);
        }

        double curVelocity = shooter.getVelocity();
        double curVoltage = batteryVoltage.getVoltage();
        telemetry.addData("Current Velocity", curVelocity);
        telemetry.addData("Voltage: ", curVoltage);
        telemetry.update();


    }

public void voltageCompensation(){
        //This function is to adjust the kp and kf values according to
    // the robot's battery voltage to make sure the flywheel's
    // velocity remains consistent.
        double currentVoltage = batteryVoltage.getVoltage();
        if(currentVoltage > 5.0 &&  Math.abs(currentVoltage-last_voltage) >= 0.05){
            //We find the multipler by dividing our battery's
            // assigned voltage and divinding it by the current voltage.
            //We can then multiply the multiplier if our kf and kp values to adjust them
            // whenever the battery is getting too low or high.
            double multiplier = nominal_voltage/currentVoltage;
            PIDFCoefficients compensatedVoltage = new PIDFCoefficients(
                    baseP * multiplier,
                    0,
                    0,
                    baseF * multiplier
            );
            shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, compensatedVoltage);
            last_voltage = currentVoltage;


        }



}
    public void hello(){
        telemetry.addLine("Hello My name is verity");
        telemetry.addLine("Hello My name is verity");
    }

}
