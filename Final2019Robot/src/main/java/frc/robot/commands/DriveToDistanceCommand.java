/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
/** 
 * sadasdasd
 * 
 * 
 * **/ 
public class DriveToDistanceCommand extends Command {

  DriveCommand driveCommand = new DriveCommand();

  double distance = 0.0;
  double speed = 0.0;
  
  public DriveToDistanceCommand(double distance, double speed) {
    this.distance = distance;
    this.speed = speed;
    requires (Robot.driveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveSubsystem.stop();
    //Robot.driveSubsystem.encReset();
    driveCommand.cancel();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.driveSubsystem.drive(speed, 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return Math.abs(Robot.driveSubsystem.getAvgDistance()-distance)<=0.1;
    return false;
    

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveSubsystem.stop();
    driveCommand.start();
    DriverStation.reportWarning("DONE DRIVING", false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
