/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.TeleOp;
import frc.robot.commands.WristCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SolenoidSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.commands.DriveToDistanceCommand;

public class Robot extends TimedRobot {

  public static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static WristSubsystem wristSubsystem = new WristSubsystem();
  public static SolenoidSubsystem solenoidSubsystem = new SolenoidSubsystem();
  public static OI m_oi;

  Command m_autonomousCommand;
  Command driveCommand = new DriveCommand();
  Command driveToDistanceCommand = new DriveToDistanceCommand(5, .5);
  Command wristCommand = new WristCommand();
  SendableChooser<Command> m_chooser = new SendableChooser<>();


  public static TeleOp teleOp = new TeleOp();
  @Override
  public void robotInit() {
    m_oi = new OI();
    CameraServer.getInstance().startAutomaticCapture();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    
    
  }


  @Override
  public void robotPeriodic() {

    //Outputs
    SmartDashboard.putString("DB/String 0", "NA");
    SmartDashboard.putString("DB/String 1", "NA");
    SmartDashboard.putString("DB/String 2", "NA");
    SmartDashboard.putString("DB/String 3", "NA");
    SmartDashboard.putString("DB/String 4", "NA");
    SmartDashboard.putString("DB/String 5", "NA");
    SmartDashboard.putString("DB/String 6", "NA");
    SmartDashboard.putString("DB/String 7", "NA");
    SmartDashboard.putString("DB/String 8", "NA");
    SmartDashboard.putString("DB/String 9", "NA");
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    //in autonomous, we need to run teleOp 
    teleOp.start();//run the main teleOp command group
    
  
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }


  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {

    teleOp.start();//run the main teleOp command group



    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void testPeriodic() {
  }
}
