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

    if(getArmPosition() >= 4000 ){//will need to be adjusted
      myTalon.set(ControlMode.PercentOutput, 0.1);
    }else if(getArmPosition() <= 10){
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


  public void setPreset(int position){

    int desPosition = 0;
    switch (position) {//will need to be set to arbitrary numbers
      case 1://lowest cargo
        desPosition = 1;
      case 2://middle cargo
        desPosition = 1;
      case 3://highest cargo
        desPosition = 1;
    }

    int currPosition = getArmPosition();

    

  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
