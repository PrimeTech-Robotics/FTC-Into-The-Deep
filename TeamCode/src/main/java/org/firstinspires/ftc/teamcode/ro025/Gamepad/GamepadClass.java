package org.firstinspires.ftc.teamcode.ro025.Gamepad;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadClass {
    private static GamepadClass instance = null;
    Gamepad currentGamepad = null;
    Gamepad previousGamepad = null;

    public static synchronized GamepadClass getInstance() { //creezi o instanta
        if (instance == null) {
            instance = new GamepadClass();
        }
        return instance;
    }

    public void init() {
        currentGamepad = new Gamepad();
        currentGamepad.copy(gamepad1);

        previousGamepad = new Gamepad();
    }

    public void loop() {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }

    public boolean triangle() { return (currentGamepad.triangle && !previousGamepad.triangle); }

    public boolean square() { return (currentGamepad.square && !previousGamepad.square); }

    public boolean dpad_up() { return currentGamepad.dpad_up; }

    public boolean dpad_down() { return currentGamepad.dpad_down; }

    public double right_trigger() { return currentGamepad.right_trigger; }

    public double left_trigger() { return currentGamepad.left_trigger; }

    public boolean dpad_left() { return currentGamepad.dpad_left; }

    public boolean dpad_right() { return currentGamepad.dpad_right; }
}
