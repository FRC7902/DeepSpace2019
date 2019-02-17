/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */


public class Robot extends TimedRobot {
  WPI_TalonSRX myTalon = new WPI_TalonSRX(5);
  Joystick myJoystick = new Joystick(0);
  public final int desPosition = 2048;
  Timer timer = new Timer();
  boolean flipflop = false;
  
  @Override
  public void robotInit() {
    myTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
  }
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {

    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    double speed = 1;

    
    //Constants
    if(!myJoystick.getRawButton(6)){
      myTalon.set(ControlMode.PercentOutput, myJoystick.getY());
    }
    SmartDashboard.putString("DB/String 0", "Position: " + Integer.toString(myTalon.getSelectedSensorPosition()));
    SmartDashboard.putString("DB/String 1",  "Speed: " + Double.toString(myTalon.getSelectedSensorVelocity()));

    //Testing the timer
    if(myJoystick.getRawButton(8)){
      if(flipflop == false){//if just switched over
        timer.start();
      }
      SmartDashboard.putString("DB/String 2",  Double.toString(timer.get()));
      flipflop = true;
    }else{
      if(flipflop == true){
        timer.stop();
      }
      flipflop = false;
    }


    if(myJoystick.getRawButton(6)){
      //Phase 1
      if(myTalon.getSelectedSensorPosition() >= (4096/2)+desPosition){
        myTalon.set(ControlMode.PercentOutput, 0.01);
      }else{
        myTalon.set(ControlMode.PercentOutput, -0.01);
      }

      //Phase 2
      if(myTalon.getSelectedSensorPosition() + myTalon.getSelectedSensorVelocity()*35 == desPosition){
        myTalon.set(ControlMode.PercentOutput, 0);
      }
    



    }


  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
