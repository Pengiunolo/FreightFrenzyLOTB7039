package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "servo test ")
public class servotest extends LinearOpMode {
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

           if(gamepad1.a){
               robot.Sweeper.setPosition(0.1);
           }//else(){}

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


