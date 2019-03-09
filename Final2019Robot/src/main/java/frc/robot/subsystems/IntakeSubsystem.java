/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



//Imports all the library necessary
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
  
/**
 * Add your docs here.
 */
public class IntakeSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  //Victor topMotor = new Victor(RobotMap.intakeTop);
  //Victor bottomMotor = new Victor(RobotMap.intakeBottom);
  DigitalInput limitSwitch = new DigitalInput(1);//channel 1
  public WPI_VictorSPX topMotor = new WPI_VictorSPX(RobotMap.intakeTop);
  public WPI_VictorSPX bottomMotor = new WPI_VictorSPX(RobotMap.intakeBottom);

  public IntakeSubsystem(){

    //will have to adjust
    topMotor.setInverted(false);
    bottomMotor.setInverted(true);

  }

  public void moveIntake(double trig1, double trig2, double speed){//moves the intake in and out
    
    SmartDashboard.putString("DB/String 0",  Double.toString(trig1));
    SmartDashboard.putString("DB/String 1",  Double.toString(trig2));
    SmartDashboard.putString("DB/String 2",  Double.toString(trig1-trig2));
    SmartDashboard.putString("DB/String 3",  Boolean.toString(limitSwitch.get()));

    topMotor.set((trig1-trig2)*speed);
    bottomMotor.set((trig1-trig2)*speed);


    // if(limitSwitch.get()){//if the ball is in the intake...

    //   if(trig1-trig2 < 0){
    //     topMotor.set(trig1-trig2);
    //     bottomMotor.set(trig1-trig2);
    //   }
    //   // topMotor.set((-trig2)*speed);//trig2 now can eject the ball for the topMotor,
    //   // bottomMotor.set((-trig2)*speed);//and the bottom motor

    // }else{//if the ball is not in the intake...
    //   if(Robot.m_oi.getDriverStick().getRawButton(2)){//only if the "B" is pressed
    //     topMotor.set(trig1-trig2);
    //     bottomMotor.set(trig1-trig2);
    //     // topMotor.set((trig1)*speed);//then the ball can be ejected from trig1
    //     // bottomMotor.set((trig1)*speed);
    //   }
      
    // }
  }

  

  public void stopIntake(){//stops the intake
    topMotor.stopMotor();
    bottomMotor.stopMotor();
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
