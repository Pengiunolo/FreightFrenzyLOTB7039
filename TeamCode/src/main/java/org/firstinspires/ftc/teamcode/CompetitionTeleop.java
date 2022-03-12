package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Teleop Tank Drive Competition")
public class CompetitionTeleop extends LinearOpMode {
    zanehardware robot = new zanehardware();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addLine(robot.Kicker.getConnectionInfo());
        telemetry.update();

        //telemetry.speak("LETS DO THIS");
        //wait for start
        waitForStart();
        while (opModeIsActive()) {
            //tank controls
            robot.Back_Right.setPower(gamepad1.left_stick_y*0.7);
            robot.Back_Left.setPower(gamepad1.right_stick_y*0.7);
            robot.Front_Right.setPower(gamepad1.left_stick_y*0.7);
            robot.Front_Left.setPower(gamepad1.right_stick_y*0.7);



            telemetry.addData("Right Stick x", gamepad1.right_stick_x);
            telemetry.addData("Left Stick x", gamepad1.left_stick_x);
            telemetry.update();
            //spinner controls
            if (gamepad1.right_trigger == 1) {
                robot.Spinner.setPower(-0.3);
            } else if (gamepad1.left_trigger == 1) {
                robot.Spinner.setPower(0.3);
            } else {
                robot.Spinner.setPower(0);
            }
            //slider controls
            if (gamepad1.a) {
                robot.Slider.setPower(-0.7);
            } else if (gamepad1.y) {
                robot.Slider.setPower(0.7);
            } else {
                robot.Slider.setPower(0.1);
                robot.Slider.setPower(0.1);
            }
            //Slider home
            if (gamepad1.dpad_up){
                robot.Slider.setPower(0);
                sleep(500);
                robot.Slider.setPower(0.5);
                sleep(250);
                robot.Slider.setPower(0.1);
            }
            //Sweeper controls
//            if (gamepad1.right_bumper) {
//                robot.Sweeper.setPosition(1);
//            } else if (gamepad1.left_bumper) {
//                robot.Sweeper.setPosition(0);
//            }
            //intake controls
            if(gamepad1.left_bumper) {
                //Outward
                robot.Intake1.setPower(0.8);
                robot.Intake2.setPower(-0.8);
            }
            if (gamepad1.right_bumper){
                //forward
                robot.Intake1.setPower(-0.8);
                robot.Intake2.setPower(0.8);
            }
            //Kicker controls
            if (gamepad1.dpad_right) {
                robot.Kicker.setPosition(0.8);
            } else if (gamepad1.dpad_left) {
                //out postion
                robot.Kicker.setPosition(0.1);
            }
            //stop all motors
            if (gamepad1.dpad_down) {

                robot.allMotorPower(0);
                robot.Slider.setPower(0);
                robot.Spinner.setPower(0);
            }
            //slider home
            if (gamepad1.dpad_up){
                robot.Slider.setPower(0);
                sleep(750);
                robot.Slider.setPower(0.7);
                sleep(450);
                robot.Slider.setPower(0.1);
            }
            //controls
//            while (gamepad1.right_stick_x == -1 && gamepad1.right_stick_y == 1) {
//                robot.Back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                robot.Front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                robot.Front_Left.setPower(.6);
//                robot.Back_Right.setPower(.6);
//            }
//            while (gamepad1.left_stick_x == 1 && gamepad1.left_stick_y == 1) {
//                robot.Front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                robot.Back_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                robot.Back_Left.setPower(-.6);
//                robot.Front_Right.setPower(-.6);
//        }
            //This is the Strafe
            //strafe right
            while (gamepad1.right_stick_x >= 0.5) {

                robot.Back_Left.setPower(0.6);
                robot.Back_Right.setPower(-0.6);
                robot.Front_Left.setPower(-0.6);
                robot.Front_Right.setPower(0.6);
            }

            //This is the Strafe
            // --Speeds changed by Coach 12/13/19
            //strafe left
            while (gamepad1.left_stick_x <= -0.5) {
                robot.Front_Right.setPower(-.6);
                robot.Back_Right.setPower(.6);

                robot.Front_Left.setPower(.6);
                robot.Back_Left.setPower(-.6);

            }
        }

    }
}

