package org.firstinspires.ftc.teamcode.Outreachbot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "DemoRobot Teleop",group = "Demo Robot")
public class DemoTeleop extends LinearOpMode {
    OutreachHardware robot = new OutreachHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            robot.Back_Right.setPower(gamepad1.left_stick_y*0.7);
            robot.Back_Left.setPower(gamepad1.right_stick_y*0.7);
            robot.Front_Right.setPower(gamepad1.left_stick_y*0.7);
            robot.Front_Left.setPower(gamepad1.right_stick_y*0.7);
        }
    }
}
