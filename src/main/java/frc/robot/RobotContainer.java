// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Drive;
import frc.robot.subsystems.MecanumDriveSubsystem;
import frc.robot.subsystems.ShirtCannonSubsystem;
import frc.robot.subsystems.TiltSubsystem;
import frc.robot.subsystems.LEDSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final CommandXboxController commandXboxController;
    private final MecanumDriveSubsystem mecanumDriveSubsystem;
    private final ShirtCannonSubsystem shirtCannonSubsystem;
    private final TiltSubsystem tiltSubsystem;
    private final LEDSubsystem ledSubsystem;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        TalonSRX talonSRX = new TalonSRX(22);
        talonSRX.setNeutralMode(NeutralMode.Brake);
        DigitalInput sensor0 = new DigitalInput(0);
        DigitalInput sensor1 = new DigitalInput(1);
        tiltSubsystem = new TiltSubsystem(talonSRX, sensor0, sensor1);
        tiltSubsystem.setDefaultCommand(tiltSubsystem.stop());

        mecanumDriveSubsystem = new MecanumDriveSubsystem();
        commandXboxController = new CommandXboxController(0);
        mecanumDriveSubsystem.setDefaultCommand(
                new Drive(
                        mecanumDriveSubsystem,
                        commandXboxController::getLeftY,
                        commandXboxController::getLeftX,
                        commandXboxController::getRightX));

        Solenoid solenoid = new Solenoid(1, PneumaticsModuleType.CTREPCM, 1);
        Compressor compressor = new Compressor(1, PneumaticsModuleType.CTREPCM);
        shirtCannonSubsystem = new ShirtCannonSubsystem(solenoid, compressor);
        shirtCannonSubsystem.setDefaultCommand(shirtCannonSubsystem.createIdle());

        CANdle candle = new CANdle(13, "rio");
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = false;
        configAll.stripType = CANdle.LEDStripType.GRB;
        configAll.brightnessScalar = 0.1;
        configAll.vBatOutputMode = CANdle.VBatOutputMode.Modulated;
        candle.configAllSettings(configAll, 100);
        int ledCount = 400;
        this.ledSubsystem = new LEDSubsystem(candle, ledCount);


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

        commandXboxController.leftTrigger(0.05).whileTrue(tiltSubsystem.createTiltUp(commandXboxController::getLeftTriggerAxis));
        commandXboxController.rightTrigger(0.05).whileTrue(tiltSubsystem.createTiltDown(commandXboxController::getRightTriggerAxis));

        commandXboxController.a().whileTrue(shirtCannonSubsystem.createFire());

        shirtCannonSubsystem.readyToFire.onTrue(
                ledSubsystem.createReadyToFire());
        shirtCannonSubsystem.readyToFire.onFalse(
                ledSubsystem.createCharging());

        commandXboxController.x().onTrue(ledSubsystem.toggle());
    }
}
