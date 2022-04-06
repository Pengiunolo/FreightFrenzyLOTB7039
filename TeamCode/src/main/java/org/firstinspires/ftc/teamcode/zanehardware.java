package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class zanehardware {

    public static final double COUNTS_PER_INCH = 46.2495654425;
    public DcMotor Front_Left;
    public DcMotor Front_Right;
    public DcMotor Back_Left;
    public DcMotor Back_Right;
    public DcMotor Slider;
    public DcMotor Spinner;
//    public DistanceSensor LeftDistance;
    public DistanceSensor RightDistance;
    public Servo Kicker;
    public Servo Sweeper;
    public CRServo Intake1;
    public CRServo Intake2;
    HardwareMap hwMap = null;
    private final ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap ahwmap){
        hwMap = ahwmap;
        Front_Left = hwMap.get(DcMotor.class,"Front_Left");
        Front_Right = hwMap.get(DcMotor.class,"Front_Right");
        Back_Left = hwMap.get(DcMotor.class,"Back_Left");
        Back_Right = hwMap.get(DcMotor.class,"Back_Right");
        Spinner = hwMap.get(DcMotor.class,"Spinner");
        Slider = hwMap.get(DcMotor.class,"Slider");
        Sweeper = hwMap.get(Servo.class,"Sweeper");
        Kicker = hwMap.get(Servo.class,"Kicker");
        Intake1 =hwMap.get(CRServo.class,"Intake1");
        Intake2 =hwMap.get(CRServo.class,"Intake2");
//        LeftDistance = hwMap.get(DistanceSensor.class,"ldistance");
        RightDistance = hwMap.get(DistanceSensor.class,"rdistance");




        //reverse motors
//        Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        Front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
//        Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        Back_Left.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void allMotorPower(double power){
        Front_Left.setPower(power);
        Front_Right.setPower(power);
        Back_Left.setPower(power);
        Back_Right.setPower(power);

    }
    public void leftMotorPower(double power){
        Front_Left.setPower(power);
        Back_Left.setPower(power);

    }
    public void rightMotorPower(double power){
        Front_Right.setPower(power);
        Back_Right.setPower(power);
    }


    public void driveByTime(double FrontLeftPower, double FrontRightPower, double BackLeftPower, double BackRightPower, int time) {
        Front_Left.setPower(FrontLeftPower);
        Front_Right.setPower(FrontRightPower);
        Back_Left.setPower(BackLeftPower);
        Back_Right.setPower(BackRightPower);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void sliderMove(Double power,Long timeMS){
     Slider.setPower(power);
     try {
         Thread.sleep(timeMS);
     } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
     }
     Slider.setPower(0);
    }
}
