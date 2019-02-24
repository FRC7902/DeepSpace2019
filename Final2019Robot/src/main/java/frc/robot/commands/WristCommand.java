/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristCommand extends Command {
  public WristCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.wristSubsystem);//this command requires the subsystem
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {//at the start...
    Robot.wristSubsystem.stopWrist();//stop the Wrist
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {//when the command is running
    Robot.wristSubsystem.moveWrist(Robot.m_oi.getOperatorStick(), 1);//take the operator's stick and a speed of 1
    if(Robot.m_oi.getOperatorStick().getRawButton(1)){//if "A" button is pressed
      Robot.wristSubsystem.setWristPosition(Robot.wristSubsystem.setPreset(1));
    }else if(Robot.m_oi.getOperatorStick().getRawButton(3)){//if "X" button is pressed
      Robot.wristSubsystem.setWristPosition(Robot.wristSubsystem.setPreset(2));
    }else if(Robot.m_oi.getOperatorStick().getRawButton(4)){//if "Y" button is pressed
      Robot.wristSubsystem.setWristPosition(Robot.wristSubsystem.setPreset(3));
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {//when is it finished?
    return false;//never!
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
