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

/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Victor frontRight = new Victor(RobotMap.frontRight);
  Victor frontLeft = new Victor(RobotMap.frontLeft);
  Victor backRight = new Victor(RobotMap.backRight);
  Victor backLeft = new Victor(RobotMap.backLeft);

  SpeedController leftSide = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedController rightSide = new SpeedControllerGroup(frontRight, backRight);

  public DifferentialDrive drive;


  public DriveSubsystem (){
    frontRight.setInverted(false);
    frontLeft.setInverted(false);
    backRight.setInverted(false);
    backLeft.setInverted(false);

    drive = new DifferentialDrive(leftSide, rightSide);

  }

  public void driveJoystick(Joystick joystick, double speed) {
    drive.arcadeDrive(joystick.getY()*speed, joystick.getX()*speed);
  }

  public void drive(double speed, double rotationSpeed) {
    drive.arcadeDrive(speed, rotationSpeed);
  }

  public void stop() {
    drive.stopMotor();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
