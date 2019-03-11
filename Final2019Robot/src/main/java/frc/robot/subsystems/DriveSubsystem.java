/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

  /*
   * Encoder leftEnc = new Encoder(RobotMap.leftEnc1, RobotMap.leftEnc2, false,
   * Encoder.EncodingType.k4X); Encoder rightEnc = new Encoder(RobotMap.rightEnc1,
   * RobotMap.rightEnc2, false, Encoder.EncodingType.k4X);
   */
  float yOut = 0;
  

  public DifferentialDrive drive;

  // the contructor class
  public DriveSubsystem() {
    // if a motor is inverted, switch the boolean
    frontRight.setInverted(false);
    frontLeft.setInverted(true);
    backRight.setInverted(false);
    backLeft.setInverted(true);

    // drive is a new DifferentialDrive
    drive = new DifferentialDrive(leftSide, rightSide);

    // rightEnc.setReverseDirection(true);
  }

  // this method is for Joystick driving
  public void driveJoystick(Joystick joystick, double speed) {

    
    if((joystick.getRawAxis(1) > (double) yOut && joystick.getRawAxis(1) > RobotMap.driveStopRange) || (joystick.getRawAxis(1) < (double)yOut && joystick.getRawAxis(1) < -RobotMap.driveStopRange) ) {
      //if joystick.getRawAxis is bigger than yOut and driveStopRange OR if joystick.getRawAxis is smaller than yOut and -driveStopRange
      yOut = yOut + (float) joystick.getRawAxis(1) * 0.01f; // increment yOut by a bit of joystick.getRawAxis
    
    }else if (joystick.getRawAxis(1) > -RobotMap.driveStopRange && joystick.getRawAxis(1) < RobotMap.driveStopRange) { // if joystick.getRawAxis is in between driveStopRange and -driveStopRange
      yOut = 0f; // yOut is now 0
    }
    
    leftSide.set((-joystick.getRawAxis(1) - joystick.getRawAxis(4)*0.5)*speed);
    rightSide.set((-joystick.getRawAxis(1) + joystick.getRawAxis(4)*0.5)*speed);
    //drive.arcadeDrive((double) yOut * speed, joystick.getX() * speed);
  }

  // this method is just for normal driving
  public void drive(double speed, double rotationSpeed) {
    drive.arcadeDrive(speed, rotationSpeed);
  }

  public void overDrive(Joystick joystick){
    leftSide.set((-joystick.getRawAxis(1) - joystick.getRawAxis(4)*0.5));
    rightSide.set((-joystick.getRawAxis(1) + joystick.getRawAxis(4)*0.5));
  }

  public void microDrive(Joystick joystick, double speed){
    leftSide.set((-joystick.getRawAxis(1) - joystick.getRawAxis(4)*0.5)*speed);
    rightSide.set((-joystick.getRawAxis(1) + joystick.getRawAxis(4)*0.5)*speed);
  }

  // this will end the motor
  public void stop() {
    drive.stopMotor();
  }

  @Override
  protected void initDefaultCommand() {

  }
}
