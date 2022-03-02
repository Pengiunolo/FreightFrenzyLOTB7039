package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name ="Co-Op TeleOp")
public class coopTelop extends LinearOpMode {
    zanehardware robot = new zanehardware();
    //sets varible names to the gamepads
    driver1 driver1 = new driver1(robot,gamepad1);
    driver2 driver2 = new driver2(robot,gamepad2);
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();
        //inits movement thread
        driver1.start();
        //inits scoring thread
        driver2.start();
        //this makes sure that the op mode does not prematurely end also has emergency stop command
        while (driver1.isAlive() || driver2.isAlive()){
            if (gamepad1.dpad_up && gamepad2.dpad_down){
                stop();
            }
        }
    }

}
//movement thread
class driver1 extends Thread{
    zanehardware robot;
    Gamepad gp1;
    timer timer = new timer(150000);
    driver1(zanehardware robot, Gamepad gp1){
        this.robot = robot;
        this.gp1 = gp1;
    }
    @Override
    public void run(){
        timer.countdown();
        while (timer.isTimerActive()){
            robot.rightMotorPower(gp1.left_stick_y * 0.7);
            robot.leftMotorPower(gp1.right_stick_y * 0.7);
            strafeLeft(gp1.left_bumper);
            strafeRight(gp1.right_bumper);
        }
    }
    public void strafeRight(boolean condition){
        while (condition = true) {
            robot.Back_Left.setPower(0.6);
            robot.Back_Right.setPower(-0.6);
            robot.Front_Left.setPower(-0.6);
            robot.Front_Right.setPower(0.6);
        }
    }
    public void strafeLeft(boolean condition){
        while (condition = true) {
            robot.Back_Left.setPower(-0.6);
            robot.Back_Right.setPower(0.6);
            robot.Front_Left.setPower(0.6);
            robot.Front_Right.setPower(-0.6);
        }
    }
}
//scoring thread
class driver2 extends Thread{
    zanehardware robot;
    Gamepad gp2;
    timer timer = new timer(150000);
    driver2(zanehardware robot, Gamepad gp2){
        this.robot = robot;
        this.gp2 = gp2;
    }
    @Override
    public void run(){
        timer.countdown();
        while (true) {
            if (gp2.right_trigger == 1) {
                robot.Spinner.setPower(-0.3);
            } else if (gp2.left_trigger == 1) {
                robot.Spinner.setPower(0.3);
            } else {
                robot.Spinner.setPower(0);
            }
            //slider controls
            if (gp2.a) {
                robot.Slider.setPower(-0.7);
            } else if (gp2.y) {
                robot.Slider.setPower(0.7);
            } else {
                robot.Slider.setPower(0.1);
                robot.Slider.setPower(0.1);
            }
            //Slider controls
            if (gp2.dpad_up) {
                robot.Slider.setPower(0);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                }
                robot.Slider.setPower(0.5);
                try {
                    sleep(250);
                } catch (InterruptedException e) {
                }
                robot.Slider.setPower(0.1);
            }
            //Sweeper controls
            if (gp2.right_bumper) {
                robot.Sweeper.setPosition(1);
            } else if (gp2.left_bumper) {
                robot.Sweeper.setPosition(0);
            }
            //Kicker controls
            if (gp2.dpad_right) {
                robot.Kicker.setPosition(0.8);
            } else if (gp2.dpad_left) {
                //out postion
                robot.Kicker.setPosition(0.1);
            }
            if (gp2.dpad_up) {
                robot.Slider.setPower(0);
                try {
                    sleep(750);
                } catch (InterruptedException e) {
                }
                robot.Slider.setPower(0.7);
                try {
                    sleep(450);
                } catch (InterruptedException e) {
                }
                robot.Slider.setPower(0.1);
            }
        }
    }
}
class timer {
    int time;
    boolean timerActive;
    timer(int time){
        this.time = time;
    }
    public void countdown(){
        while (time > 0){
            try{Thread.sleep(1000);}catch (InterruptedException e){}
            time --;
            timerActive = true;
        }
        timerActive = false;
    }

    public boolean isTimerActive() {
        return timerActive;
    }

    public int getTime() {
        return time;
    }
}