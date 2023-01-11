package org.firstinspires.ftc.teamcode.Outreachbot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OutreachHardware {

    public DcMotor Front_Left;
    public DcMotor Front_Right;
    public DcMotor Back_Left;
    public DcMotor Back_Right;
    public DcMotor Arm;


    HardwareMap hwMap = null;

    public void init(HardwareMap ahwmap) {
        hwMap = ahwmap;
        Front_Left = hwMap.get(DcMotor.class,"Front_Left");
        Front_Right = hwMap.get(DcMotor.class,"Front_Right");
        Back_Left = hwMap.get(DcMotor.class,"Back_Left");
        Back_Right = hwMap.get(DcMotor.class,"Back_Right");
        Arm = hwMap.get(DcMotor.class,"arm");
    }
}
