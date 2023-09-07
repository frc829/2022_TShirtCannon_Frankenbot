// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive;
import frc.robot.commands.Tilt;
import frc.robot.subsystems.MecanumDriveSubsystem;
import frc.robot.subsystems.ShirtCannon;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final MecanumDriveSubsystem mecanumDriveSubsystem;
  private final XboxController xboxController;
  private final ShirtCannon shirtCannon;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    this.mecanumDriveSubsystem = new MecanumDriveSubsystem();
    this.xboxController = new XboxController(0);
    this.mecanumDriveSubsystem.setDefaultCommand(
      new Drive(
        mecanumDriveSubsystem, 
        () -> xboxController.getLeftY(), 
        () -> xboxController.getLeftX(),
        () -> xboxController.getRightX()));
    this.shirtCannon = new ShirtCannon();
    this.shirtCannon.setDefaultCommand(
      new Tilt(
        shirtCannon, 
        () -> xboxController.getLeftTriggerAxis(), 
        () -> xboxController.getRightTriggerAxis()));



    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(xboxController, 1).whenPressed(new InstantCommand(shirtCannon::Shirt, shirtCannon));
    new JoystickButton(xboxController, 2).whenPressed(new InstantCommand(shirtCannon::lightsOff, shirtCannon));
    
  }


}
