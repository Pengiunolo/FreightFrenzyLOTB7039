
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Disabled
@Autonomous
public class BlueParkCloseToBridge extends LinearOpMode {

    private static final long SLEEP_10 = 10;
    private static final long SLEEP_25 = 25;
    private static final long SLEEP_50 = 50;


    OpenCvInternalCamera phoneCam;
    SkystoneDeterminationPipeline pipeline;
    Saketzanehardware robot = new Saketzanehardware();
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 537.6;//356.3 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH = 122.600924;
            //first hundred digits of pi fr more accuracy


    /**
     * Override this method and place your code here.
     * <p>
     * Please do not swallow the InterruptedException, as it is used in cases
     * where the op mode needs to be terminated early.
     *
     * @throws InterruptedException
     */
    @Override

    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.FRONT, cameraMonitorViewId);
        pipeline = new SkystoneDeterminationPipeline();
        phoneCam.setPipeline(pipeline);


        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
        });

        robot.init(hardwareMap);

        waitForStart();


        double FORWARD_SPEED = 0.5;



        while (opModeIsActive()) {




            robot.Slider.setPower(1);
            sleep(600);
            robot.Slider.setPower(0.2);
            sleep(200);
            encoderDriveWithoutTime(.5,-5,5,-5,5);
            sleep(100);
            encoderDriveWithoutTime(.8,50,50,50,50);
            stop();

            double moveLength = 28;
            int position = 1;
            robotMoveToShippingElement(1);
            sleep(5000);

            boolean positionShippingElement = scanShippingElement();
            telemetry.addData("Is the shipping element present",positionShippingElement);
            telemetry.update();
            sleep(1000);
            if (positionShippingElement){
                moveToShippingHub(moveLength, position);
            }

            else {

                moveToSecondDuck(6);
                positionShippingElement = scanShippingElement();
                telemetry.addData("Is the shipping element present",positionShippingElement);
                telemetry.update();
                sleep(1000);
                if (positionShippingElement){
                    position = 2;

                    moveToShippingHub(moveLength - 6, position);
                }
                else {
                    position = 3;

                    moveToShippingHub(moveLength - 6, position);
                }
            }

            placeFreightCorrectLocation(position);
            moveToWarehouse();
            stop();



            //switch - scan position one and two
            //if not at one, then go to position two  if not at two, then it is at three
            //if at one, then continue  if not at one, then go to position two
            //if at position two, then continue

            //Move forward/strafe toward shipping hub
            //Place freight in correct location
            //Finally go to warehouse

            //sleep(5000);




        }
    }

    private void moveToWarehouse() {
        //turn to the left for blue
        encoderDriveWithoutTime(0.5,4,4,4,4);
        //go forward
        encoderDriveWithoutTime(0.5, 48,48,48,48);
    }

    private void placeFreightCorrectLocation(int position) {

        switch (position) {
            case 1:
                //adjust arm position and drop cube
                break;
            case 2:
                //adjust arm position and drop cube
                break;
            default:
                //adjust arm position and drop cube
                break;

        }

    }

    private void moveToSecondDuck(double moveLength) {

        //encoderDriveWithoutTime(0.3, moveLength, moveLength, moveLength, moveLength);
        encoderDriveWithTime(0.3,1,1,1,1,2);
    }

    private void moveToShippingHub(double moveLength, int position) {

        encoderDriveWithoutTime(0.5, -moveLength, -moveLength, -moveLength, -moveLength);
        encoderDriveWithoutTime(.3, -6,6,-6,6);
        placeFreightCorrectLocation(position);
    }

    private boolean scanShippingElement() {

        sleep(1000);
        int avg1 = pipeline.getAnalysis();

        final int THRESHOLD = 25;//150;
        telemetry.addData("Scan Threshhold:",avg1);
        telemetry.update();


        if(avg1 > THRESHOLD){
            return true;
        }else{
            return false;
        }


    }

    private void robotMoveToShippingElement(double tmpmoveLength) {
        telemetry.addData("Going toward Shipping element", tmpmoveLength);
        telemetry.update();
        //encoderDriveWithoutTime(0.3, -tmpmoveLength, -tmpmoveLength, -tmpmoveLength, -tmpmoveLength);
        encoderDriveWithTime(1,-1,-1,-1,-1,10);
    }



    private void strafeLeft(double moveLength) {
        telemetry.addData("Strafe Left: ", moveLength);
        telemetry.update();
        //encoderDriveWithoutTime(0.3, -moveLength, moveLength, -moveLength, moveLength);
        encoderDriveWithTime(0.3,-1,1,-1,1,2);
    }

    private void strafeRight(double moveLength) {
        telemetry.addData("Strafe Right: ", moveLength);
        telemetry.update();
        encoderDriveWithoutTime(0.3, moveLength, -moveLength, moveLength, -moveLength);
    }

    private void moveTowards(double moveLength) {

        telemetry.addData("Move Length: ", moveLength);
        telemetry.update();
        encoderDriveWithoutTime(0.5, -moveLength, -moveLength, -moveLength, -moveLength);

    }

    public void encoderDriveWithoutTime ( double speed,
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
    public void encoderDriveWithTime(double speed,
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
    public void encoderDriveWithTimeForward(
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
    public void encoderDriveWithTimeBackward(
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
    public void encoderDriveWithTimeLeft(
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
    public void encoderDriveWithTimeRight(
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
    public void spinLeft(
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
    public void spinRight(
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












    public static class SkystoneDeterminationPipeline extends OpenCvPipeline
    {
        /*
         * An enum to define the skystone position
         */
        public boolean IS_SHIPPING_ELEMENT_PRESENT = false;

        /*
         * Some color constants
         */
        static final Scalar BLUE = new Scalar(0, 0, 255);
        static final Scalar GREEN = new Scalar(0, 255, 0);

        /*
         * The core values which define the location and size of the sample regions
         */
        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(75,58);

        static final int REGION_WIDTH = 140;
        static final int REGION_HEIGHT = 66;

        final int THRESHOLD = 25;//150;


        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        /*
         * Working variables
         */
        Mat region1_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int avg1;

        // Volatile since accessed by OpMode thread w/o synchronization
        //private volatile boolean EasyOpenCVExample.SkystoneDeterminationPipeline.IS

        /*
         * This function takes the RGB frame, converts to YCrCb,
         * and extracts the Cb channel to the 'Cb' variable
         */
        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 1);
        }

        @Override
        public void init(Mat firstFrame)
        {
            inputToCb(firstFrame);

            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        }

        @Override
        public Mat processFrame(Mat input)
        {
            inputToCb(input);

            avg1 = (int) Core.mean(region1_Cb).val[0];

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            //position = EasyOpenCVExample.SkystoneDeterminationPipeline.RingPosition.FOUR; // Record our analysis
            if(avg1 > THRESHOLD){
                IS_SHIPPING_ELEMENT_PRESENT = true;
            }else {
                IS_SHIPPING_ELEMENT_PRESENT = false;
            }

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill

            return input;
        }

        public int getAnalysis()
        {
            return avg1;
        }



    }

}
