/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class DriveSubsystem extends Subsystem {

  // make a new Victor for each of the motors
  public WPI_VictorSPX frontRight = new WPI_VictorSPX(RobotMap.frontRight);
  public WPI_VictorSPX frontLeft = new WPI_VictorSPX(RobotMap.frontLeft);
  public WPI_VictorSPX backRight = new WPI_VictorSPX(RobotMap.backRight);
  public WPI_VictorSPX backLeft = new WPI_VictorSPX(RobotMap.backLeft);

  // group these motors as speedControllers
  SpeedController leftSide = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedController rightSide = new SpeedControllerGroup(frontRight, backRight);

  Encoder leftEnc = new Encoder(RobotMap.leftEnc1, RobotMap.leftEnc2, false, Encoder.EncodingType.k4X);
  Encoder rightEnc = new Encoder(RobotMap.rightEnc1, RobotMap.rightEnc2, false, Encoder.EncodingType.k4X);

  float yOut = 0;
  boolean brake = false;
  float[] motorHist;
  float averagePower = 0;
  int brakeTime = 0;
  int currbrakeTime = 0;

  public DifferentialDrive drive;

  // the contructor class
  public DriveSubsystem() {
    // if a motor is inverted, switch the boolean
    frontRight.setInverted(false);
    frontLeft.setInverted(false);
    backRight.setInverted(false);
    backLeft.setInverted(false);

    // drive is a new DifferentialDrive
    drive = new DifferentialDrive(leftSide, rightSide);

    rightEnc.setReverseDirection(true);
  }

  // this method is for Joystick driving
  public void driveJoystick(Joystick joystick, double speed) {

    if (joystick.getY() > (double) yOut && joystick.getY() > 0.1) {// if joystict.getY is bigger than yOut and 0.1
      yOut = yOut + (float) joystick.getY() * 0.01f; // increment yOut by a bit of joystick.getY
      brake = false; // brake is set to false
      currbrakeTime = 0; // clean currbrakeTime
      brakeTime = 0; // clean brakeTime
      if (motorHist.length == RobotMap.motorHistLen) { // if motorHist.length reached RobotMap.motorHistLen
        motorHist[50] = yOut * (float) speed; // add yOut*speed to the end of motorHist
        RobotMap.removeTheElement(motorHist, 0); // remove the first item of motorHist
      }
    } /*
       * else if(joystick.getY() < (double)yOut && joystick.getY() < -0.1){ //if
       * joystick.getY is smaller than yOut and -0.1 yOut = yOut +
       * (float)joystick.getY() * 0.01f; //increment yOut by a bit of joystick.getY }
       */else if (joystick.getY() > -0.2 && joystick.getY() < 0.2) { // if joystick.getY is in between -0.2 and 0.2
      yOut = 0f; // yOut is now 0
    }

    if (joystick.getY() == 0) {// when you want the robot to stop
      averagePower = RobotMap.findTheAverage(motorHist); // find the average power of the motorHist
      brakeTime = (int) Math.round(averagePower * RobotMap.brakeDurMult);// calculate the brakeTime
      brake = true; // brake is now true
      if (brake == true) { // when brake is true
        yOut = -1 / (float) speed; // set yOut to -1 in the output
        currbrakeTime++; // add one to currBrakeTime

      }
      if (currbrakeTime == brakeTime) {// when currBrakeTime has reached brakeTime
        yOut = 0; // yOut is 0
        brake = false; // brake is false
        currbrakeTime = 0; // clean currBrakeTime
        brakeTime = 0;// and brakeTime
      }

    }

    drive.arcadeDrive((double) yOut * speed, joystick.getX() * speed);
  }

  // this method is just for normal driving
  public void drive(double speed, double rotationSpeed) {
    drive.arcadeDrive(speed, rotationSpeed);
  }

  // this will end the motor
  public void stop() {
    drive.stopMotor();
  }

  public double getLeftRaw() {
    return leftEnc.getRaw();
  }

  public double getRightRaw() {
    return rightEnc.getRaw();
  }

  public double getRawAvg() {
    return (leftEnc.getRaw() + rightEnc.getRaw()) / 2;
  }

  public double getAvgDistance() {
    return getRawAvg() * 0.0008;
  }

  // Resets the encoders so that they read from 0 again
  public void encReset() {
    leftEnc.reset();
    rightEnc.reset();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
