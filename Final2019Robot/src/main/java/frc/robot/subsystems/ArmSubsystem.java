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
public class ArmSubsystem extends Subsystem {
  //TalonSRX myTalon = new TalonSRX(0);//set this to the correct port
  WPI_TalonSRX myTalon = new WPI_TalonSRX(RobotMap.armMotor);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.


  public void moveArm(Joystick joystick, double speed) {
    myTalon.set(ControlMode.PercentOutput, joystick.getY()*speed);

    checkOutOfRange();

    /*
    if(getArmPosition() >= 4000 ){//will need to be adjusted
      myTalon.set(ControlMode.PercentOutput, 0.1);
    }else if(getArmPosition() <= 10){
      myTalon.set(ControlMode.PercentOutput, 0.1);
    }
    */
  }

  public void checkOutOfRange(){
    if(getArmPosition() > 3072) {//example to be changed later
      //Phase 1
      stopArm();
      //Phase 2
      myTalon.set(ControlMode.PercentOutput, 0);
      //Phase 3
      //move arm outside of limit using setArmPosition
      //Phase 4
      myTalon.set(ControlMode.PercentOutput, -0.1);
      
    }else if (getArmPosition() < 1024){
      //Phase 1
      stopArm();
      //Phase 2
      myTalon.set(ControlMode.PercentOutput, 0);
      //Phase 3
      //move arm outside of limit using setArmPosition
      //Phase 4
      myTalon.set(ControlMode.PercentOutput, 0.1);
    }
  }

  public void stopArm(){
    myTalon.disable();
  }

  public int getArmPosition(){
    return myTalon.getSelectedSensorPosition() % 4096;

  }

  public int getArmVelocity(){
    return myTalon.getSelectedSensorVelocity();
  }

  public void setArmPosition(int desPosition){
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
      case 4://lowest hatch
        desPosition = 1;
      case 5://middle hatch
        desPosition = 1;
      case 6://highest hatch
        desPosition = 1;
      case 7://lowest back hatch
        desPosition = 1;
      case 8://middle back hatch
        desPosition = 1;  
      case 9://highest back hatch
        desPosition = 1;
      case 10://lowest back cargo
        desPosition = 1;
      case 11://middle back cargo
        desPosition = 1;
      case 12://highest back cargo
        desPosition = 1;
      case 13://ground position
        desPosition = 1;
    }
    return desPosition;

    

  }
  //1024 2048 3072 4096
  public int parallelMode(){ //to be run constantly
    int armPos = myTalon.getSelectedSensorPosition();
    int desWristPos = 0;
    if(armPos > 512 && armPos < 3584){//in the lower section
      if(armPos > 512 && armPos < 2048){//on the right side
        desWristPos = 1024 - armPos;
      }else{//on the left side
        desWristPos = 3072 - armPos;
      }

    }else{//other stuff
      
    }
    
    return desWristPos;
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
