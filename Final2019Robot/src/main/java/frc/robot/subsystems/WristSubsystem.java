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

    if(getWristPosition() > 1024) {//if the position is greater than 2045 (or too forward)
      //Phase 1
      //stopWrist();
      //Phase 2
      //myTalon.set(ControlMode.PercentOutput, 0);
      //Phase 3
      //move arm outside of limit using setArmPosition
      //Phase 4
      myTalon.set(ControlMode.PercentOutput, -0.1);//move it back(counter clockwise)
      
    }else if (getWristPosition() < 4096){//if the position is less than 4096(or too back)
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
    int lim = 512;
    if(getWristPosition() > desPosition){//if the arm is too forward, move it back
      int diff = desPosition - getWristPosition();
      if(diff>lim){
        myTalon.set(ControlMode.PercentOutput, -1);
      }else{
        myTalon.set(ControlMode.PercentOutput, diff/lim);
      }
    }else if(getWristPosition() < desPosition){
      int diff = desPosition - getWristPosition();
      if(diff>lim){
        myTalon.set(ControlMode.PercentOutput, 1);
      }else{
        myTalon.set(ControlMode.PercentOutput, diff/lim);//otherwise move it forward
      }
    }
  }
  
  public double deccelerate(Joystick joystick){
    double outputSpeed = 0;
    outputSpeed = (getWristVelocity()+joystick.getRawAxis(5))/100;
    return outputSpeed;
  }

  public int setPreset(int position){

    int desPosition = 0;
    switch (position) {//will need to be set to arbitrary numbers
      case 1://lowest cargo
        desPosition = 1024;
      case 2://middle cargo
        desPosition = 512;
      case 3://highest cargo
        desPosition = 256;
    }
    return desPosition;

  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
