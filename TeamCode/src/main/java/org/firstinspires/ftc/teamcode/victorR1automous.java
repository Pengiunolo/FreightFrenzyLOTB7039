package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.zanehardware.COUNTS_PER_INCH;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Victors R1 automous")
public class victorR1automous extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    zanehardware robot = new zanehardware();
    int Location = 0;
    int distanceToHub;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        //revese the motor direction for this program
        //robot.Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
//        robot.Front_Left.setDirection(DcMotorSimple.Direction.FORWARD);
        //robot.Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
//        robot.Back_Left.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.sliderMove(0.8, 200l);
        robot.Slider.setPower(0.1);
        waitForStart();

// make sure to have the encoder statements reversed

        for (int i = 0; i < 3; i++) {

            if (robot.RightDistance.getDistance(DistanceUnit.INCH) <= 18) {
                Location = i + 1;
                break;
            } else {

                encoderDrive(0.5,-8,8,-8,8,1);

            }
        }
        if(Location != 0){
            telemetry.speak("Location equals" + String.valueOf(Location));
        }
        encoderDrive(0.5,1,1,1,1,500);
        switch (Location) {
            case 1:
                telemetry.addLine("location 1");
                telemetry.update();
                distanceToHub = 36;
                encoderDrive(0.6,-36,36,-36,36,4);
                spinAndPlace();
                break;
            case 2:
                telemetry.addLine("location 2");
                telemetry.update();
                distanceToHub -= 8;
                encoderDrive(0.6,28,-28,28,-28,3);
                spinAndPlace();
                break;
            case 3:
                telemetry.addLine("location 3");
                telemetry.update();
                distanceToHub -= 8;
                encoderDrive(0.6,24,-24,24,-24,2);
                spinAndPlace();
                break;
            default:
                telemetry.addLine("no object found");
                telemetry.speak("no OBJECT found");
                telemetry.update();
                //encoderDrive(0.6,1,1,1,1,0.5);
                encoderDrive(0.5,-20,20,20,-20,2);

                break;
        }
        encoderDrive(0.8,60,60,60,60,2);

    }

     void spinAndPlace(){
        encoderDrive(0.5,4,4,4,4,2);
        encoderDrive(0.5,38,-38,-38,38,4);

        switch (Location){
             case 1:
                 robot.sliderMove(1d,3500l);
//                 robot.Sweeper.setPosition(0);
//                 encoderDrive(1,0.5,0.5,0.5,0.5,100);
                 break;
             case 2:
                 robot.sliderMove(0.9,1500l);
//                 robot.Sweeper.setPosition(0);
//                 encoderDrive(1,0.5,0.5,0.5,0.5,100);
                 break;

             case 3:
                 robot.sliderMove(0.9,1000l);
//
                 break;
             default:
                 break;
         }
         encoderDrive(0.3,-15,-15,-15,-15,3);
         robot.Sweeper.setPosition(0);
         encoderDrive(0.5,20,-20,-20,20,2);
     }


    public void encoderDrive(double speed,
                             double Back_Left_Inches,
                             double Back_Right_Inches,
                             double Front_Right_Inches,
                             double Front_Left_Inches,
                             double timeoutS) {
        int newLeftBottomTarget;
        int newRightBottomTarget;
        int newRightTopTarget;
        int newLeftTopTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller

            newLeftBottomTarget = robot.Back_Left.getCurrentPosition() + (int) (Back_Left_Inches * COUNTS_PER_INCH);
            newRightBottomTarget = robot.Back_Right.getCurrentPosition() + (int) (Back_Right_Inches * COUNTS_PER_INCH);
            newRightTopTarget = robot.Front_Right.getCurrentPosition() + (int) (Front_Right_Inches * COUNTS_PER_INCH);
            newLeftTopTarget = robot.Front_Left.getCurrentPosition() + (int) (Front_Left_Inches * COUNTS_PER_INCH);

            robot.Back_Left.setTargetPosition(newLeftBottomTarget);
            robot.Back_Right.setTargetPosition(newRightBottomTarget);
            robot.Front_Right.setTargetPosition(newRightTopTarget);
            robot.Front_Left.setTargetPosition(newLeftTopTarget);

            // Turn On RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Back_Left.setPower(Math.abs(speed));
            robot.Back_Right.setPower(Math.abs(speed));
            robot.Front_Left.setPower(Math.abs(speed));
            robot.Front_Right.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftBottomTarget, newRightBottomTarget, newLeftTopTarget, newRightTopTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();


                telemetry.update();
            }

            // Stop all motion;
            robot.Back_Left.setPower(0);
            robot.Back_Right.setPower(0);
            robot.Front_Left.setPower(0);
            robot.Front_Right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }
    void printToPhone(String Text){
        telemetry.addLine(Text);
        telemetry.update();
    }
}

