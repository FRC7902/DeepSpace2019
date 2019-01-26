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
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class DriveSubsystem extends Subsystem {
  
  //make a new Victor for each of the motors
  Victor frontRight = new Victor(RobotMap.frontRight); 
  Victor frontLeft = new Victor(RobotMap.frontLeft);
  Victor backRight = new Victor(RobotMap.backRight);
  Victor backLeft = new Victor(RobotMap.backLeft);

  //group these motors as speedControllers
  SpeedController leftSide = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedController rightSide = new SpeedControllerGroup(frontRight, backRight);

  float yOut = 0;

  public DifferentialDrive drive;

  //the contructor class
  public DriveSubsystem (){
    //if a motor is inverted, switch the boolean
    frontRight.setInverted(false);
    frontLeft.setInverted(false);
    backRight.setInverted(false);
    backLeft.setInverted(false);

    //drive is a new DifferentialDrive
    drive = new DifferentialDrive(leftSide, rightSide);

  }
  //this method is for Joystick driving
  public void driveJoystick(Joystick joystick, double speed) {
    
    

    if(joystick.getY() == 0){
      yOut = 0;
    }else{
      yOut = (float)joystick.getY();
    }



    drive.arcadeDrive((double)yOut*speed, joystick.getX()*speed);
  }
  //this method is just for normal driving
  public void drive(double speed, double rotationSpeed) {
    drive.arcadeDrive(speed, rotationSpeed);
  }
  //this will end the motor
  public void stop() {
    drive.stopMotor();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
