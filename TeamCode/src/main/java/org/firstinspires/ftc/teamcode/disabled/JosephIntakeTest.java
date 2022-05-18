package org.firstinspires.ftc.teamcode.disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Competition.zanehardware;

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
                //Outward
                robot.Intake1.setPower(0.8);
                robot.Intake2.setPower(-0.8);
            }
            if (gamepad1.y){
                //forward
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
