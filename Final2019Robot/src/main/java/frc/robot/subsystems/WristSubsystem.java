/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class WristSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  WPI_TalonSRX myTalon = new WPI_TalonSRX(RobotMap.wristMotor);

  public void moveWrist(Joystick joystick, double speed){
    myTalon.set(ControlMode.PercentOutput, joystick.getRawAxis(5));

    if(getWristPosition() >= 4000 ){//will need to be adjusted
      myTalon.set(ControlMode.PercentOutput, 0.1);
    }else if(getWristPosition() <= 10){
      myTalon.set(ControlMode.PercentOutput, 0.1);
    }
  }

  public void stopWrist(){
    myTalon.disable();
  }

  public int getWristPosition(){
    return myTalon.getSelectedSensorPosition() % 4096;

  }

  public int getWristVelocity(){
    return myTalon.getSelectedSensorVelocity();
  }
  
  public void setWristPosition(int desPosition){
    //to be filled by code from Experimental
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
