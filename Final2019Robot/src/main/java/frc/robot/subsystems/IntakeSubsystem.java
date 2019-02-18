/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
  
/**
 * Add your docs here.
 */
public class IntakeSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  Victor topMotor = new Victor(RobotMap.intakeTop);
  Victor bottomMotor = new Victor(RobotMap.intakeBottom);
  DigitalInput limitSwitch = new DigitalInput(1);//channel 1
  Timer timer = new Timer();

  boolean delayLimSwitch = false;

  public IntakeSubsystem(){

    //will have to adjust
    topMotor.setInverted(false);
    bottomMotor.setInverted(true);

  }

  public void moveIntake(double trig1, double trig2, double speed){

    if(limitSwitch.get()){//if limit switch is pressed
      if(delayLimSwitch == false){//the instance the switch is flipped
        timer.start();//start the timer
      }
      if(timer.get() >= 180){//if past the delay
        topMotor.set((-trig2)*speed);
        bottomMotor.set((-trig2)*speed);
      }
      delayLimSwitch = true;
    }else{//if limit switch is not pressed
      if(Robot.m_oi.getOperatorStick().getRawButton(2)){
        topMotor.set((trig1)*speed);
        bottomMotor.set((trig1)*speed);
      }
      delayLimSwitch = false;
    }
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
