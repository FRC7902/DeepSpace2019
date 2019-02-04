/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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

  /** Used to create string thoughout loop */
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	
    /** Track button state for single press event */
	boolean _lastButton1 = false;

	/** Save the target position to servo to */
	double targetPositionRotations;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    myTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, //something i dont know
    0,//PID ID, which is basically 0
    30); //the timeout, which is 30 ms
    

    //make sure the sensor makes a positive, when the sensors is positive. I really don't know why this is here
    myTalon.setSensorPhase(true);

    //we do not want the motor to be inverted, therefor it is false
    myTalon.setInverted(false); 

    // I honestly don't know what this does. Something to do with 12V 
    myTalon.configNominalOutputForward(0, 30);
		myTalon.configNominalOutputReverse(0, 30);
		myTalon.configPeakOutputForward(1, 30);
    myTalon.configPeakOutputReverse(-1, 30);
    
    //configure the error... something something
    myTalon.configAllowableClosedloopError(0, 0, 30);


    myTalon.config_kF(0, 0.0, 30);
		myTalon.config_kP(0, 0.15, 30);
		myTalon.config_kI(0, 0.0, 30);
		myTalon.config_kD(0, 1.0, 30);

    //get the starting position, and make it absolutePosition
    int absolutePosition = myTalon.getSensorCollection().getPulseWidthPosition();

    /* Mask out overflows, keep bottom 12 bits */
		absolutePosition &= 0xFFF;
		if (true) { absolutePosition *= -1; }
		if (false) {absolutePosition *= -1; }
		
		/* Set the quadrature (relative) sensor to match absolute */
		myTalon.setSelectedSensorPosition(absolutePosition, 0, 30);
  }

  void commonLoop() {
		/* Gamepad processing */
		double leftYstick = myJoystick.getY();
		boolean button1 = myJoystick.getRawButton(1);	// X-Button
		boolean button2 = myJoystick.getRawButton(2);	// A-Button

		/* Get Talon/Victor's current output percentage */
		double motorOutput = myTalon.getMotorOutputPercent();

		/* Deadband gamepad */
		if (Math.abs(leftYstick) < 0.10) {
			/* Within 10% of zero */
			leftYstick = 0;
		}

		/* Prepare line to print */
		_sb.append("\tout:");
		/* Cast to int to remove decimal places */
		_sb.append((int) (motorOutput * 100));
		_sb.append("%");	// Percent

		_sb.append("\tpos:");
		_sb.append(myTalon.getSelectedSensorPosition(0));
		_sb.append("u"); 	// Native units

		/**
		 * When button 1 is pressed, perform Position Closed Loop to selected position,
		 * indicated by Joystick position x10, [-10, 10] rotations
		 */
		if (!_lastButton1 && button1) {
			/* Position Closed Loop */

			/* 10 Rotations * 4096 u/rev in either direction */
			targetPositionRotations = leftYstick * 10.0 * 4096;
			myTalon.set(ControlMode.Position, targetPositionRotations);
		}

		/* When button 2 is held, just straight drive */
		if (button2) {
			/* Percent Output */

			myTalon.set(ControlMode.PercentOutput, leftYstick);
		}

		/* If Talon is in position closed-loop, print some more info */
		if (myTalon.getControlMode() == ControlMode.Position) {
			/* ppend more signals to print when in speed mode. */
			_sb.append("\terr:");
			_sb.append(myTalon.getClosedLoopError(0));
			_sb.append("u");	// Native Units

			_sb.append("\ttrg:");
			_sb.append(targetPositionRotations);
			_sb.append("u");	/// Native Units
		}

		/**
		 * Print every ten loops, printing too much too fast is generally bad
		 * for performance.
		 */
		if (++_loops >= 10) {
			_loops = 0;
			System.out.println(_sb.toString());
		}

		/* Reset built string for next loop */
		_sb.setLength(0);
		
		/* Save button state for on press detect */
		_lastButton1 = button1;
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

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    commonLoop();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
