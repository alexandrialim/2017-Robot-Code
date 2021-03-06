package org.usfirst.frc.team1247.robot;

import org.usfirst.frc.team1247.robot.utilities.*;
import org.usfirst.frc.team1247.robot.commands.AutonomousMode;
import org.usfirst.frc.team1247.robot.commands.BaseCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static OI oi;
	AutonomousMode autonomousMode;
    ADIS16448_IMU imu;
	
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);		
		System.out.println("Robot Init!");
		
		oi = new OI();
		BaseCommand.init();
		autonomousMode = new AutonomousMode();
        imu = new ADIS16448_IMU();
        //System.out.println(imu.getAngleX());
        //System.out.println(imu.getAngleY());
        //System.out.println(imu.getAngleZ());
	}
	
	@Override
	public void robotPeriodic(){
        //System.out.println(imu.getAngleX());
        //System.out.println(imu.getAngleY());
        //System.out.println(imu.getAngleZ());
		SmartDashboard.putData("ADIS", imu);
        SmartDashboard.putNumber("AngleX", imu.getAngleX());
        SmartDashboard.getNumber("AngleY", imu.getAngleY());
        SmartDashboard.getNumber("AngleZ", imu.getAngleZ());
        SmartDashboard.putNumber("AccelX", imu.getAccelX());
        SmartDashboard.putNumber("AccelY", imu.getAccelY());
        SmartDashboard.putNumber("AccelZ", imu.getAccelZ());
        SmartDashboard.getNumber("MagX", imu.getMagX());
        SmartDashboard.getNumber("MagY", imu.getMagY());
        SmartDashboard.getNumber("MagZ", imu.getMagZ());
        Timer.delay(0.005);		// wait for a motor update time
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autonomousMode.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		//System.out.println("Do I has autonomous periodic even?");
	}

	@Override
	public void teleopInit() {
		if (autonomousMode != null) autonomousMode.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	@Override
	public void disabledInit() {

	}
	
	@Override
	public void teleopPeriodic() {
		LiveWindow.run();
		Scheduler.getInstance().run();
		//System.out.println("Do I has teleop periodic even?");
	}
	
	 public void operatorControl() {
	        while (isOperatorControl() && isEnabled()) {
	            SmartDashboard.putData("IMU", imu);
	            Timer.delay(0.005);		// wait for a motor update time
	        }
	    }

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		LiveWindow.run();
	}
	
}
