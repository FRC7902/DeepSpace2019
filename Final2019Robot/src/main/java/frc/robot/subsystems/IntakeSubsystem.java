/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
  
/**
 * Add your docs here.
 */
public class IntakeSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  Victor topMotor = new Victor(RobotMap.intakeTop);
  Victor bottomMotor = new Victor(RobotMap.intakeBottom);
  
  public IntakeSubsystem(){

    //will have to adjust
    topMotor.setInverted(false);
    bottomMotor.setInverted(true);

  }

  public void moveIntake(double trig1, double trig2, double speed){
    topMotor.set((trig1-trig2)*speed);
    bottomMotor.set((trig1-trig2)*speed);
  }

  public void stopIntake(){
    topMotor.stopMotor();
    bottomMotor.stopMotor();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
