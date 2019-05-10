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
    myTalon2.setInverted(false);
    myTalon.setInverted(false);
    
  }
  
  double i = 0;
  int time = 0;
  int startPos = 0;
  int top = -500;

  //Satellite
  public double counterGrav = 0;

  public void moveWrist(Joystick joystick, double speed, double gravMult){//the method on how to move the wrist
    myTalon.set(ControlMode.PercentOutput, (joystick.getRawAxis(3)-joystick.getRawAxis(2))*speed+ counterGrav(gravMult));//the second joystick's Y-axis is the motor
    myTalon2.set(ControlMode.PercentOutput, (joystick.getRawAxis(3)-joystick.getRawAxis(2))*speed+counterGrav(gravMult));
    //SmartDashboard.putString("DB/String 6",  Double.toString(counterGrav(gravMult)));
    counterGrav = counterGrav(gravMult);
    // myTalon.set(ControlMode.PercentOutput, (joystick.getRawAxis(3)-joystick.getRawAxis(2))*speed+ 0.08);//the second joystick's Y-axis is the motor
    // myTalon2.set(ControlMode.PercentOutput, (joystick.getRawAxis(3)-joystick.getRawAxis(2))*speed+0.08);
    //SmartDashboard.putString("DB/String 6",  Double.toString(0.08));
    //checkOutOfRange();//always make sure it is in range
  }

  // public void checkOutOfRange(){

  //   if(getWristPosition() > RobotMap.wristFrontLimit) {//if the wrist is more forward than the frontmost wrist position
      
  //     myTalon.set(ControlMode.PercentOutput, -0.1);//move it back(counter clockwise)
  //     myTalon2.set(ControlMode.PercentOutput, -0.1);//move it back(counter clockwise)
      
  //   }else if (getWristPosition() < RobotMap.wristBackLimit && getWristPosition() > RobotMap.wristBottomPos){//if the wrist is more back than the backmost wrist position
      
  //     myTalon.set(ControlMode.PercentOutput, 0.1);//move it forward
  //     myTalon2.set(ControlMode.PercentOutput, 0.1);//move it forward
  //   }
  // }

  public void stopWrist(){//disable the wrist
    myTalon.disable();
  }

  public int getWristPosition(){//get the position
    return (myTalon.getSelectedSensorPosition() % 4096)-startPos;

  }
  public void resetPos(){
    startPos += getWristPosition();

  }
  public double counterGrav(double mult){
    return (top - getWristPosition())*mult;
  }
  public void displayInfo(){
    SmartDashboard.putString("DB/String 2", "WristPos: " + Integer.toString(getWristPosition()));
    SmartDashboard.putString("DB/String 3", "Bpressed: " + Boolean.toString(Robot.m_oi.getDriverStick().getRawButton(2)));
    SmartDashboard.putString("DB/String 4", "cGrav: " + counterGrav);
  }
  
  // public void setWrist(int despos){
  //   if(getWristPosition()>despos){
  //     myTalon.set(-0.1);
  //   }else if(getWristPosition()<despos){
  //     myTalon.set(0.1);
  //   }else{
  //     myTalon.set(0);
  //   }

  // }

  public void setWristPosition(int desPosition, double speed, double grav){//to move the desired position

    
    
    //calculate error
    // int error = 0;
    
    // error = desPosition - getWristPosition();
    
    

    // //P
    // double p = error/RobotMap.PIDthreshold;

    // //I

    // // i = i*time;
    // // time++;
    // // i = i + p;
    // // i = i/time;
    // double i = 0;

    // //D
    // double d = 0;
    
    
    // //Output
    // double power = RobotMap.kP * p + RobotMap.kI * i + RobotMap.kD * d;
    
    // myTalon.set(ControlMode.PercentOutput, power);//in between -1 and 1
    
    
    
    //FOR A BACKUP
    int lim = 256;
    if(getWristPosition() > desPosition){//if the arm is too forward, move it back
          
      int diff = desPosition - getWristPosition();
      if(diff>lim){
        myTalon.set(ControlMode.PercentOutput, -1*speed+grav);
        myTalon2.set(ControlMode.PercentOutput, -1*speed+grav);
      }else{
        myTalon.set(ControlMode.PercentOutput, (diff/lim)*speed+grav);
        myTalon2.set(ControlMode.PercentOutput, (diff/lim)*speed+grav);
      }
    }else if(getWristPosition() < desPosition){
      int diff = desPosition - getWristPosition();
      if(diff>lim){
        myTalon.set(ControlMode.PercentOutput, 1*speed+grav);
        myTalon2.set(ControlMode.PercentOutput, 1*speed+grav);
      }else{
        myTalon.set(ControlMode.PercentOutput, (diff/lim)*speed+grav);//otherwise move it forward
        myTalon2.set(ControlMode.PercentOutput, (diff/lim)*speed+grav);
      }
    }
  }
  

  // public void detectPresetButton(){
  //   // if(Robot.m_oi.getDriverStick().getRawButton(1)){//if "A" button is pressed
  //   //   setWristPosition(RobotMap.groundPos);
  //   // }else if(Robot.m_oi.getDriverStick().getRawButton(3)){//if "X" button is pressed
  //   //   setWristPosition(RobotMap.lowPos);
  //   // }else if(Robot.m_oi.getDriverStick().getRawButton(4)){//if "Y" button is pressed
  //   //   setWristPosition(RobotMap.midPos);
  //   // }
  // }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
