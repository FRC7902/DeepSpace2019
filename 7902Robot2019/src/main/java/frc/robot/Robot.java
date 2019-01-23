
package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends TimedRobot {

  //public float yOut = 0;
  //when first created robot,
  static float yOut = 0; //yOut is zero

  public static void accelerate (float incr, double spin){ //declare a method "accelerate"
    if(incr > -0.2 && incr < 0.2){ //if incr is inbetween -0.2 and 0.2, set yOut to 0
      yOut = 0;
      spin = 0;
    }else if(yOut < 1 && yOut > -1 ){ //if yOut is inbetween 1 and -1, add 3% of incr to yOut
      yOut = yOut + (incr * 0.03f);// the error should be fixed now
    }
  }
  public static void turn (){ //no Turn... yet

  }
  // driver joystick
  private Joystick driver;

  // speed controllers
  private VictorSP leftDrive1;
  private VictorSP leftDrive2;
  private VictorSP rightDrive1;
  private VictorSP rightDrive2;

  // autonomous timer
  long autoStartTime;
  
  @Override
  public void robotInit() { // called when robot is starting up
    this.driver = new Joystick(0); // JOYSTICK connected to INPUT 0

    this.leftDrive1 = new VictorSP(0); // LEFTDRIVE1 connected to PWM PORT 0
    this.leftDrive2 = new VictorSP(1); // LEFTDRIVE2 connected to PWM PORT 1

    this.rightDrive1 = new VictorSP(2); // RIGHTDRIVE1 connected to PWM PORT 2
    this.rightDrive2 = new VictorSP(3); // RIGHTDRIVE2 connected to PWM PORT 3
  }

  @Override
  public void robotPeriodic() { // called repeatedly when the robot is on
  }

  @Override
  public void autonomousInit() { // called at the start of autonomous
    this.autoStartTime = System.currentTimeMillis(); // assign the current milliseconds
  }

  @Override
  public void autonomousPeriodic() { // called repeatedly during autonomous
   /* 
    long timePassed = System.currentTimeMillis() - this.autoStartTime; // find the difference of when auto started and

    if (timePassed < 300) { // drive forward for 3 seconds
      
    } else if (timePassed < 5000) { // turn left for 5 seconds
      this.leftDrive1.set(0.3);
      this.leftDrive2.set(0.3);
      this.rightDrive1.set(0.3);
      this.rightDrive2.set(0.3);

    } else { // stop
      this.leftDrive1.set(0);
      this.leftDrive2.set(0);
      this.rightDrive1.set(0);
      this.rightDrive2.set(0);

    }
 */
  }

  @Override
  public void teleopPeriodic() { // called repeatedly during teleop
    /*
     * //Tank drive //getting our inputs double driverX = this.driver.getRawAxis(5);
     * //match to the right stick y axis double driverY =
     * -this.driver.getRawAxis(1); //match to the left stick y axis
     * 
     * //calculating our inputs
     * 
     * 
     * 
     * // setting the speed controllers this.leftDrive1.set(driverY);
     * this.leftDrive2.set(driverY); this.rightDrive1.set(driverX);
     * this.rightDrive2.set(driverX);
     * 
     * 
     */

    // Arcade drive (may not work)
    // getting our inputs
    /*
     * //logitech controller boolean bumper = this.driver.getRawButton(5); double
     * driverX = -this.driver.getRawAxis(4); //match to the stick x axis double
     * driverY = this.driver.getRawAxis(5); //match to the stick y axis
     */
    /* xtreme 3d pro
    boolean bumper = this.driver.getRawButton(1);
    boolean Turbo = this.driver.getRawButton(2);
    boolean Breaks = this.driver.getRawButton(12);
    boolean straight = this.driver.getRawButton(8);
    */
    double turnR = this.driver.getRawAxis(3) * 0.1; 
    double turnL = this.driver.getRawAxis(2) * 0.1;
    double driverX = -this.driver.getRawAxis(4) * 0.3; // match to the right stick x axis
    float driverY = this.driver.getRawAxis(1); // match to the left stick y axis
    /*
    double slider = -((this.driver.getRawAxis(3) - 1) / 2);

    // calculating our inputs

    System.out.println(driverX);
    System.out.println(driverY);
    */
  
  float inc = driverY * 0.03f; //declare inc as a 3% of driverY
  
  if(driverY < -0.2 && yOut >= driverY){ //if driverY is less than -0.2 and less or equal to yOut
    yOut = yOut + inc;
  }else if(driverY > 0.2 && yOut <= driverY){ // if driverY is greater than 0.2 and greater or equal to yOut
    yOut = yOut + inc;
  }else if(driverY < 0.3 && driverY > -0.3){// if driverY is inbetween 0.3 and -0.3
    yOut = 0;
    driverX = 0; //dont turn
  }
    


    double leftOut = (double)yOut * 0.5 + turnL + driverX - turnR; 
    double rightOut = (double)yOut * 0.5 + turnR - driverX - turnL;



    /*
    if (bumper == true) {

      leftOut = leftOut * 0.6;
      rightOut = rightOut * 0.6;

    } else if (Breaks == true) {

    } else if (Turbo == true) {

      leftOut = leftOut * 1;
      rightOut = rightOut * 1;

    } else if (straight == true) {
      leftOut = -1;
      rightOut = -1;
    } else {

      leftOut = leftOut * 0.75 * slider;
      rightOut = rightOut * 0.75 * slider;

    }
*/
    // setting the speed controllers
    this.leftDrive1.set(-leftOut);
    this.leftDrive2.set(-leftOut);
    this.rightDrive1.set(rightOut);
    this.rightDrive2.set(rightOut);

  }
}
