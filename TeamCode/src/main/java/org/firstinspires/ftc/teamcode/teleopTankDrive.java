package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name = "Teleop Tank Drive")
public class teleopTankDrive extends LinearOpMode {
    zanehardware robot = new zanehardware();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.Front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
        //wait for start
        waitForStart();
        while (opModeIsActive()) {
            //tank controls
            robot.leftMotorPower(gamepad1.left_stick_y);


            robot.rightMotorPower(gamepad1.right_stick_y);

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

            if (gamepad1.y) {
                robot.Slider.setPower(-0.7);
            } else if (gamepad1.a) {
                robot.Slider.setPower(0.7);
            } else {
                robot.Slider.setPower(0.1);
            }

            if (gamepad1.x) {
                robot.Sweeper.setPosition(-0.3);
            } else if (gamepad1.b) {
                robot.Sweeper.setPosition(0.3);
            } else {
                robot.Sweeper.setPosition(0);
            }

//            if (gamepad1.dpad_left) {
//                robot.Grabber.setPosition(1);
//            } else if (gamepad1.dpad_right) {
//                robot.Grabber.setPosition(0);
//            }

            if (gamepad1.dpad_down) {

                robot.allMotorPower(0);
                robot.Slider.setPower(0);
                robot.Spinner.setPower(0);
            }

            //controls
            while (gamepad1.right_stick_x == -1 && gamepad1.right_stick_y == 1) {
                robot.Back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Front_Left.setPower(.6);
                robot.Back_Right.setPower(.6);
            }
            while (gamepad1.left_stick_x == 1 && gamepad1.left_stick_y == 1) {
                robot.Front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Back_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Back_Left.setPower(-.6);
                robot.Front_Right.setPower(-.6);
            }
            //This is the Strafe
            if (gamepad1.right_stick_x >= 0.5) {
//            robot.Front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.Back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.Back_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.Front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Front_Right.setPower(.6);
                robot.Back_Right.setPower(-.6);

                robot.Front_Left.setPower(-.6);
                robot.Back_Left.setPower(.6);
            }
            //This is the Strafe
            // --Speeds changed by Coach 12/13/19
            if (gamepad1.left_stick_x >= -0.5) {
//            robot.Front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.Back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.Back_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.Front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Front_Right.setPower(-.6);
                robot.Back_Right.setPower(.6);

                robot.Front_Left.setPower(.6);
                robot.Back_Left.setPower(-.6);

            }
        }

    }
}


