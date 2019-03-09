/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
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
  WPI_TalonSRX myTalon2 = new WPI_TalonSRX(RobotMap.wristMotor2);

  public WristSubsystem() {
    myTalon2.setInverted(true);
    myTalon.setInverted(false);
    
  }
  
  double i = 0;
  int time = 0;

  public void moveWrist(Joystick joystick, double speed){//the method on how to move the wrist
    myTalon.set(ControlMode.PercentOutput, joystick.getRawAxis(5)*speed);//the second joystick's Y-axis is the motor
    myTalon2.set(ControlMode.PercentOutput, joystick.getRawAxis(5)*speed);
    //checkOutOfRange();//always make sure it is in range
  }

  public void checkOutOfRange(){

    if(getWristPosition() > RobotMap.wristFrontLimit) {//if the wrist is more forward than the frontmost wrist position
      
      myTalon.set(ControlMode.PercentOutput, -0.1);//move it back(counter clockwise)
      myTalon2.set(ControlMode.PercentOutput, -0.1);//move it back(counter clockwise)
      
    }else if (getWristPosition() < RobotMap.wristBackLimit && getWristPosition() > RobotMap.wristBottomPos){//if the wrist is more back than the backmost wrist position
      
      myTalon.set(ControlMode.PercentOutput, 0.1);//move it forward
      myTalon2.set(ControlMode.PercentOutput, 0.1);//move it forward
    }
  }

  public void stopWrist(){//disable the wrist
    myTalon.disable();
  }

  public int getWristPosition(){//get the position
    return myTalon.getSelectedSensorPosition() % 4096;

  }

  public void displayInfo(){
    SmartDashboard.putString("DB/String 4",  Integer.toString(getWristPosition()));
    //SmartDashboard.putString("DB/String 5",  Integer.toString(desPosition));
  }
  
  public void setWristPosition(int desPosition){//to move the desired position

    
    /*
    //calculate error
    int error = 0;
    if(getWristPosition() > RobotMap.wristBottomPos && getWristPosition() < 4096){
      error = 4096-getWristPosition();
      error += desPosition;
    }else{
      error = desPosition - getWristPosition();
    }
    

    //P
    double p = error/RobotMap.PIDthreshold;

    //I
    i = i*time;
    time++;
    i = i + p;
    i = i/time;

    //D
    double d = 0;
    
    
    //Output
    double power = RobotMap.kP * p + RobotMap.kI * i + RobotMap.kD * d;
    
    myTalon.set(ControlMode.PercentOutput, power);//in between -1 and 1
    
    */
    
    //FOR A BACKUP
    int lim = 256;
    if(getWristPosition() > desPosition){//if the arm is too forward, move it back
          
      int diff = desPosition - getWristPosition();
      if(diff>lim){
        myTalon.set(ControlMode.PercentOutput, -1);
        myTalon2.set(ControlMode.PercentOutput, -1);
      }else{
        myTalon.set(ControlMode.PercentOutput, diff/lim);
        myTalon2.set(ControlMode.PercentOutput, diff/lim);
      }
    }else if(getWristPosition() < desPosition){
      int diff = desPosition - getWristPosition();
      if(diff>lim){
        myTalon.set(ControlMode.PercentOutput, 1);
        myTalon2.set(ControlMode.PercentOutput, 1);
      }else{
        myTalon.set(ControlMode.PercentOutput, diff/lim);//otherwise move it forward
        myTalon2.set(ControlMode.PercentOutput, diff/lim);
      }
    }
  }
  

  public void detectPresetButton(){
    if(Robot.m_oi.getDriverStick().getRawButton(1)){//if "A" button is pressed
      setWristPosition(RobotMap.groundPos);
    }else if(Robot.m_oi.getDriverStick().getRawButton(3)){//if "X" button is pressed
      setWristPosition(RobotMap.lowPos);
    }else if(Robot.m_oi.getDriverStick().getRawButton(4)){//if "Y" button is pressed
      setWristPosition(RobotMap.midPos);
    }
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
