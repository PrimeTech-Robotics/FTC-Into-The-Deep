package org.firstinspires.ftc.teamcode.ro025.Components.Modes;

import org.firstinspires.ftc.teamcode.ro025.Components.Gamepad.GamepadClass;

public class FSM_modes {
    private static FSM_modes instance = null;
    public Modes modes = Modes.GENERAL;
    public Init init = Init.NOT_OVER;

    public static synchronized FSM_modes getInstance() {
        if (instance == null) {
            instance = new FSM_modes();
        }
        return instance;
    }

    public void FSM() {
        switch (modes) {
            case GENERAL:
                if (GamepadClass.getInstance().dpad_right()) {
                    modes = Modes.INTAKE_OUTTAKE_SPECIMEN;
                    All_modes.intake_outtake_specimen_init();
                }
                if (GamepadClass.getInstance().dpad_left()) {
                    modes = Modes.OUTTAKE_SAMPLE;
                    All_modes.outtake_sample_init();
                }
                if (GamepadClass.getInstance().dpad_down()) {
                    modes = Modes.INTAKE_SAMPLE;
                    init = Init.NOT_OVER;
                }
                All_modes.general();
                break;
            case INTAKE_SAMPLE:
                switch (init) {
                    case NOT_OVER:
                        All_modes.intake_sample_init();
                        break;
                    case OVER:
                        if (GamepadClass.getInstance().dpad_down()) {
                            modes = Modes.GENERAL;
                        }
                        All_modes.intake_sample_loop();
                        break;
                }
                break;
            case INTAKE_OUTTAKE_SPECIMEN:
                if (GamepadClass.getInstance().dpad_right()) {
                    modes = Modes.GENERAL;
                }
                All_modes.intake_outtake_specimen_loop();
                break;
            case OUTTAKE_SAMPLE:
                if (GamepadClass.getInstance().dpad_left()) {
                    modes = Modes.GENERAL;
                }
                All_modes.outtake_sample_loop();
                break;
        }
    }

    enum Modes {
        GENERAL, INTAKE_SAMPLE, INTAKE_OUTTAKE_SPECIMEN, OUTTAKE_SAMPLE
    }

    enum Init {
        OVER, NOT_OVER
    }
}
