package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

public interface AutonomousFunc {
     long SLEEP_10 = 10;
     long SLEEP_25 = 25;
     long SLEEP_50 = 50;


    OpenCvInternalCamera phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.FRONT, cameraMonitorViewId);;
    AutonomousFreightFrenzyBlueB1.SkystoneDeterminationPipeline pipeline = new AutonomousFreightFrenzyBlueB1.SkystoneDeterminationPipeline();;
    zanehardware robot = new zanehardware();
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 537.6;//356.3 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH = 45;
    //first hundred digits of pi fr more accuracy


    phoneCam.setPipeline(pipeline);


    default void spinAndComeBack() {

        encoderDriveWithoutTime(.5,4,-4,4,-4);
        sleep(100);
        encoderDriveWithoutTime(.3,26,26,26,26);
        sleep(100);
        spinLeft(4,.4);
        sleep(100);
        encoderDriveWithoutTime(.5,-1,1,1,-1);
        sleep(100);
        encoderDriveWithoutTime(.5,5,-5,5,-5);
        sleep(100);
        encoderDriveWithoutTime(.5,-19,-19,-19,-19);
        sleep(100);
        turn90Left();
    }

    default void turn90Left() {

        encoderDriveWithoutTime(.5,-19.5,19.5,19.5,-19.5);
    }


    default void turn90LeftMore() {

        encoderDriveWithoutTime(.5,-20.5,20.5,20.5,-20.5);
    }
    default void turn90Right() {

        encoderDriveWithoutTime(.5,18.5,-18.5,-18.5,18.5);
    }
    default void moveToWarehouse() {
        //turn to the left for blue
        //encoderDriveWithoutTime(0.5,4,4,4,4);
        //go forward
        //encoderDriveWithoutTime(0.5, 48,48,48,48);
        encoderDriveWithoutTime(.8,5,5,5,5);
        sleep(100);
        turn90Right();
        telemetry.addData("Before final move to warehouse","");
        telemetry.update();
        encoderDriveWithoutTime(.8,65,65,65,65);

    }

    default void placeFreightCorrectLocation(int position) {

        switch (position) {
            case 1:
                double len = 2;
                robot.Slider.setPower(1);
                sleep(1300);
                robot.Slider.setPower(0.1);

                robot.Sweeper.setPosition(0);
                sleep(50);
                len = 2;
                encoderDriveWithoutTime(.5,-len,-len,-len,-len);
                sleep(100);
                //adjust arm position and drop cube
                break;

            case 2:
                robot.Slider.setPower(0.7);
                sleep(900);
                robot.Slider.setPower(0.2);


                robot.Sweeper.setPosition(0.4);
                sleep(50);
                len = 2;
                encoderDriveWithoutTime(.5,-len,-len,-len,-len);
                sleep(100);
                //adjust arm position and drop cube
                break;
            default:
                //adjust arm position and drop cube


                robot.Sweeper.setPosition(0.4);
                sleep(50);
                len = 2;
                encoderDriveWithoutTime(.5,-len,-len,-len,-len);
                sleep(100);
                break;


        }

    }

    default void moveToSecondDuck(double moveLength) {

        encoderDriveWithoutTime(0.3, moveLength, -moveLength, moveLength, -moveLength);
        //encoderDriveWithTime(0.3,1,1,1,1,2);
    }

    default void moveToShippingHub(double moveLength, int position) {

        encoderDriveWithoutTime(0.5, moveLength, -moveLength, moveLength, -moveLength);
        sleep(100);
        turn90LeftMore();
        telemetry.addData("Its working", "");
        telemetry.update();
        turn90LeftMore();
        sleep(100);
        //robot.Sweeper.setPosition(0);
        encoderDriveWithoutTime(0.5, -10.5, -10.5, -10.5, -10.5);

        //encoderDriveWithoutTime(.3, -6,6,-6,6);
        //placeFreightCorrectLocation(position);
    }

      default boolean scanShippingElement() {

        sleep(1000);
        int avg1 = pipeline.getAnalysis();

        final int THRESHOLD = 150;//150;

        sleep(1000);


        if(avg1 > THRESHOLD){
            return true;
        }else{
            return false;
        }


    }

    default void robotMoveToShippingElement(double tmpmoveLength) {
        telemetry.addData("Going toward Shipping element", tmpmoveLength);
        telemetry.update();
        encoderDriveWithoutTime(0.3, tmpmoveLength, tmpmoveLength, tmpmoveLength, tmpmoveLength);
        //encoderDriveWithTime(1,-1,-1,-1,-1,10);
    }


     default void encoderDriveWithoutTime ( double speed,
                                          double Back_Left_Inches,
                                          double Back_Right_Inches,
                                          double Front_Right_Inches,
                                          double Front_Left_Inches)
    {
        int newLeftBottomTarget;
        int newRightBottomTarget;
        int newRightTopTarget;
        int newLeftTopTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftBottomTarget = robot.Back_Left.getCurrentPosition() + (int)(Back_Left_Inches * COUNTS_PER_INCH);
            newRightBottomTarget = robot.Back_Right.getCurrentPosition() + (int)(Back_Right_Inches * (COUNTS_PER_INCH));
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
                    (robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
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
     default void encoderDriveWithTime(double speed,
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
            double COUNTS_PER_INCH = 122.600924;
            newLeftBottomTarget = robot.Back_Left.getCurrentPosition() + (int)(Back_Left_Inches * COUNTS_PER_INCH);
            newRightBottomTarget = robot.Back_Right.getCurrentPosition() + (int)(Back_Right_Inches * COUNTS_PER_INCH);
            newRightTopTarget = robot.Front_Right.getCurrentPosition() + (int) (Front_Right_Inches * COUNTS_PER_INCH);
            newLeftTopTarget = robot.Front_Left.getCurrentPosition() + (int) (Front_Left_Inches * COUNTS_PER_INCH);

            robot.Back_Left.setTargetPosition(newLeftBottomTarget);
            robot.Back_Right.setTargetPosition(newRightBottomTarget);
            robot.Front_Right.setTargetPosition(newRightTopTarget);
            robot.Front_Left.setTargetPosition(newLeftTopTarget);

            // Turn On RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Back_Left.setPower(Math.abs(speed));
            robot.Back_Right.setPower(Math.abs(speed));
            robot.Front_Left.setPower(Math.abs(speed));
            robot.Front_Right.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("testing","123");


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
     default void encoderDriveWithTimeForward(
            double timeoutS,
            double speed) {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            //robot.Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Back_Left.setDirection(DcMotorSimple.Direction.REVERSE);

            // Turn On RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Back_Left.setPower(-speed);
            robot.Back_Right.setPower(-speed);
            robot.Front_Left.setPower(-speed);
            robot.Front_Right.setPower(-speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("Going Forward for ", timeoutS - runtime.seconds());


                telemetry.update();
            }

            // Stop all motion;
            robot.Back_Left.setPower(0);
            robot.Back_Right.setPower(0);
            robot.Front_Left.setPower(0);
            robot.Front_Right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }
     default void encoderDriveWithTimeBackward(
            double timeoutS,
            double speed) {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            /*robot.Back_Right.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.Front_Left.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.Front_Right.setDirection(DcMotorSimple.Direction.FORWARD);*/


            // Turn On RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Back_Left.setPower(speed);
            robot.Back_Right.setPower(speed);
            robot.Front_Left.setPower(speed);
            robot.Front_Right.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("Going Backward for ", timeoutS - runtime.seconds());


                telemetry.update();
            }

            // Stop all motion;
            robot.Back_Left.setPower(0);
            robot.Back_Right.setPower(0);
            robot.Front_Left.setPower(0);
            robot.Front_Right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }
     default void encoderDriveWithTimeLeft(
            double timeoutS,
            double speed) {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            /*robot.Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.Front_Left.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.Back_Left.setDirection(DcMotorSimple.Direction.FORWARD);*/

            // Turn On RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Back_Left.setPower(-speed);
            robot.Back_Right.setPower(speed);
            robot.Front_Left.setPower(speed);
            robot.Front_Right.setPower(-speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("Going Left for ", timeoutS - runtime.seconds());


                telemetry.update();
            }

            // Stop all motion;
            robot.Back_Left.setPower(0);
            robot.Back_Right.setPower(0);
            robot.Front_Left.setPower(0);
            robot.Front_Right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }
    default void encoderDriveWithTimeRight(
            double timeoutS,
            double speed) {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            /*robot.Back_Right.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.Front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.Front_Right.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.Back_Left.setDirection(DcMotorSimple.Direction.REVERSE);*/

            // Turn On RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Back_Left.setPower(speed);
            robot.Back_Right.setPower(-speed);
            robot.Front_Left.setPower(-speed);
            robot.Front_Right.setPower(speed);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("Going Right for ", timeoutS - runtime.seconds());


                telemetry.update();
            }

            // Stop all motion;
            robot.Back_Left.setPower(0);
            robot.Back_Right.setPower(0);
            robot.Front_Left.setPower(0);
            robot.Front_Right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Back_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }
     default void spinLeft(
            double timeoutS,
            double speed) {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            //robot.Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Back_Left.setDirection(DcMotorSimple.Direction.REVERSE);

            // Turn On RUN_TO_POSITION
            robot.Spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Spinner.setPower(speed);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("Spinning for ", timeoutS - runtime.seconds());


                telemetry.update();
            }

            // Stop all motion;
            robot.Spinner.setPower(0);


            // Turn off RUN_TO_POSITION
            robot.Spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            //  sleep(250);   // optional pause after each move

        }
    }
    default void spinRight(
            double timeoutS,
            double speed) {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            //robot.Back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            //robot.Back_Left.setDirection(DcMotorSimple.Direction.REVERSE);

            // Turn On RUN_TO_POSITION
            robot.Spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Spinner.setPower(-speed);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                //&&
                //(robot.Back_Left.isBusy() && robot.Back_Right.isBusy() && robot.Front_Left.isBusy() && robot.Front_Right.isBusy())) {

                // Display it for the driver.
                /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Back_Left.getCurrentPosition(),
                        robot.Back_Right.getCurrentPosition());
                robot.Front_Left.getCurrentPosition();
                robot.Front_Right.getCurrentPosition();*/
                telemetry.addData("Spinning for ", timeoutS - runtime.seconds());


                telemetry.update();
            }

            // Stop all motion;
            robot.Spinner.setPower(0);


            // Turn off RUN_TO_POSITION
            robot.Spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            //  sleep(250);   // optional pause after each move

        }
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
