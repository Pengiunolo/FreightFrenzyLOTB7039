package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Joesph intake test")
public class JosephIntakeTest extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    zanehardware robot = new zanehardware();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.speak("TEST is working");

        //you can change what servo is referring to here

        waitForStart(); 

        while (opModeIsActive()) {
            if(gamepad1.a) {
                robot.Intake1.setPower(0.8);
                robot.Intake2.setPower(-0.8);
            }
            if (gamepad1.y){
                robot.Intake1.setPower(-0.8);
                robot.Intake2.setPower(0.8);
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
