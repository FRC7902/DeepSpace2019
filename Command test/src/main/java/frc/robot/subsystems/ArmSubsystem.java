/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class ArmSubsystem extends Subsystem {
  //TalonSRX myTalon = new TalonSRX(0);//set this to the correct port
  WPI_TalonSRX myTalon = new WPI_TalonSRX(0);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void moveArm(Joystick joystick, double speed) {
    myTalon.set(ControlMode.PercentOutput, joystick.getY()*speed);
    
  }

  public void stopArm(){
    myTalon.disable();
  }

  public double getArmSpeed(){
    return myTalon.get();

  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
