package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Disabled
@TeleOp(name = "ServoTest")
public class ServoTester extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    zanehardware robot = new zanehardware();
    Servo servo = null;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.speak("TEST is working");

        //you can change what servo is referring to here
        servo = robot.Sweeper;

        waitForStart(); 

        while (opModeIsActive()) {
            if (gamepad1.right_trigger == 1) {
                servo.setPosition(0.1);
                telemetry.addData("position:", "0.1");
                telemetry.update();
            }

            if (gamepad1.left_trigger == 1) {
                servo.setPosition(0.2);
                telemetry.addData("position:", "0.2");
                telemetry.update();
            }
            if (gamepad1.left_bumper) {
                servo.setPosition(0.3);
                telemetry.addData("position:", "0.3");
                telemetry.update();
            }
            if (gamepad1.right_bumper) {
                servo.setPosition(0.4);
                telemetry.addData("position:", "0.4");
                telemetry.update();
            }
            if (gamepad1.b) {
                servo.setPosition(0.5);
                telemetry.addData("position:", "0.5");
                telemetry.update();
            }
            if (gamepad1.a) {
                servo.setPosition(0.6);
                telemetry.addData("position:", "0.6");
                telemetry.update();
            }
            if (gamepad1.x) {
                servo.setPosition(0.7);
                telemetry.addData("position:", "0.7");
                telemetry.update();
            }
            if (gamepad1.y) {
                servo.setPosition(0.8);
                telemetry.addData("position:", "0.8");
                telemetry.update();
            }
            if (gamepad1.left_stick_button) {
                servo.setPosition(0.9);
                telemetry.addData("position:", "0.9");
                telemetry.update();
            }


        }


//    public int barcodeDetect(){
//        int count = 0;
//        int Location = 0;
//        for (int i = 0;  i < 4; i++ ){
//            count ++;
//            if (robot.RightDistance.getDistance(DistanceUnit.INCH) <= 18 && seenObject == false){
//                Location = count;
//                seenObject = true;
//                encoderDrive(0.5,8,-8,8,-8,3);
//            }else if(seenObject == true){
//                break;
//            }else{
//
//            }
//        }


    }
}
