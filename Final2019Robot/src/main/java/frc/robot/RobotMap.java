/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
 
  //CAN IDs for motor controller outputs
  public static final int frontRight = 1;
  public static final int backRight = 2;
  public static final int frontLeft = 3;
  public static final int backLeft = 4;
  public static final int armMotor = 5;
  public static final int wristMotor = 6;
  public static final int wristMotor2 = 9;
  public static final int intakeTop = 7;
  public static final int intakeBottom = 8;

  public static final int motorHistLen = 50;
  public static final float brakeDurMult = 40;

  

  //a method for removing an element from an array
  public static float[] removeTheElement(float[] arr, int index){
    if (arr == null || index < 0 || index >= arr.length){
      return arr;
    }

    float[] anotherArray = new float[arr.length -1];

    for (int i = 0, k = 0; i < arr.length; i++) {
      if (i == index){
        continue;
      }

      anotherArray[k++] = arr[i];
    }

    return anotherArray;

  }

  //finding an average from array
 // public static float findTheAverage(float[] arr){
   // float sum = 0;

    //for(int i = 0; i < arr.length; i++){
      //sum = sum + arr[i];

    //}
    //sum = sum/arr.length;
    //return sum;

  }

