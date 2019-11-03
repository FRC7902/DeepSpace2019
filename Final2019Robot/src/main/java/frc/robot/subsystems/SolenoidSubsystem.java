/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The solenoid subsystem. I still don't know what we are going to do with this.
 */
public class SolenoidSubsystem extends Subsystem {

  // Declaration and Initiation
  private DoubleSolenoid doubleSolenoid = new DoubleSolenoid(RobotMap.frontValve, RobotMap.backValve);
  
  public SolenoidSubsystem() {
    // Close both channels
    doubleSolenoid.set(DoubleSolenoid.Value.kOff);
  }

  // Should extend if mechanic team connected the system up correctly
  public void openFrontValve() {
    doubleSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void closeValve() {
    doubleSolenoid.set(DoubleSolenoid.Value.kOff);
  }

  // Vice versa
  public void openBackValve() {
    doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
