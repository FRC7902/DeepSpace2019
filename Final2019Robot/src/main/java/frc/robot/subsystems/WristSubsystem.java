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

  public void moveWrist(Joystick joystick, double speed){//the method on how to move the wrist
    myTalon.set(ControlMode.PercentOutput, joystick.getRawAxis(5));//the second joystick's Y-axis is the motor

    checkOutOfRange();//always make sure it is in range
  }

  public void checkOutOfRange(){

    if(getWristPosition() > 0) {//if the position is greater than 0 (or too forward)
      //Phase 1
      //stopWrist();
      //Phase 2
      //myTalon.set(ControlMode.PercentOutput, 0);
      //Phase 3
      //move arm outside of limit using setArmPosition
      //Phase 4
      myTalon.set(ControlMode.PercentOutput, -0.1);//move it back
      
    }else if (getWristPosition() < 2048){//if the position is less that 0(or too back)
      //Phase 1
      //stopWrist();
      //Phase 2
      //myTalon.set(ControlMode.PercentOutput, 0);
      //Phase 3
      //move arm outside of limit using setArmPosition
      //Phase 4
      myTalon.set(ControlMode.PercentOutput, 0.1);//move it forward
    }
  }

  public void stopWrist(){//disable the wrist
    myTalon.disable();
  }

  public int getWristPosition(){//get the position
    return myTalon.getSelectedSensorPosition() % 4096;

  }

  public int getWristVelocity(){//get the velocity
    return myTalon.getSelectedSensorVelocity();
  }
  
  public void setWristPosition(int desPosition){//to move the desired position
    //to be filled by code from Experimental
  }
  
  public int setPreset(int position){

    int desPosition = 0;
    switch (position) {//will need to be set to arbitrary numbers
      case 1://lowest cargo
        desPosition = 1;
      case 2://middle cargo
        desPosition = 1;
      case 3://highest cargo
        desPosition = 1;
    }
    return desPosition;

  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
