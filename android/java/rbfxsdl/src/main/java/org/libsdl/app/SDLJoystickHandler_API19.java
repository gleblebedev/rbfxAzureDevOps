package org.libsdl.app;

import android.view.InputDevice;
import android.view.KeyEvent;

class SDLJoystickHandler_API19 extends SDLJoystickHandler_API16 {

    @Override
    public int getProductId(InputDevice joystickDevice) {
        return joystickDevice.getProductId();
    }

    @Override
    public int getVendorId(InputDevice joystickDevice) {
        return joystickDevice.getVendorId();
    }

    @Override
    public int getButtonMask(InputDevice joystickDevice) {
        int button_mask = 0;
        int[] keys = new int[]{
                KeyEvent.KEYCODE_BUTTON_A,
                KeyEvent.KEYCODE_BUTTON_B,
                KeyEvent.KEYCODE_BUTTON_X,
                KeyEvent.KEYCODE_BUTTON_Y,
                KeyEvent.KEYCODE_BACK,
                KeyEvent.KEYCODE_MENU,
                KeyEvent.KEYCODE_BUTTON_MODE,
                KeyEvent.KEYCODE_BUTTON_START,
                KeyEvent.KEYCODE_BUTTON_THUMBL,
                KeyEvent.KEYCODE_BUTTON_THUMBR,
                KeyEvent.KEYCODE_BUTTON_L1,
                KeyEvent.KEYCODE_BUTTON_R1,
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_DPAD_DOWN,
                KeyEvent.KEYCODE_DPAD_LEFT,
                KeyEvent.KEYCODE_DPAD_RIGHT,
                KeyEvent.KEYCODE_BUTTON_SELECT,
                KeyEvent.KEYCODE_DPAD_CENTER,

                // These don't map into any SDL controller buttons directly
                KeyEvent.KEYCODE_BUTTON_L2,
                KeyEvent.KEYCODE_BUTTON_R2,
                KeyEvent.KEYCODE_BUTTON_C,
                KeyEvent.KEYCODE_BUTTON_Z,
                KeyEvent.KEYCODE_BUTTON_1,
                KeyEvent.KEYCODE_BUTTON_2,
                KeyEvent.KEYCODE_BUTTON_3,
                KeyEvent.KEYCODE_BUTTON_4,
                KeyEvent.KEYCODE_BUTTON_5,
                KeyEvent.KEYCODE_BUTTON_6,
                KeyEvent.KEYCODE_BUTTON_7,
                KeyEvent.KEYCODE_BUTTON_8,
                KeyEvent.KEYCODE_BUTTON_9,
                KeyEvent.KEYCODE_BUTTON_10,
                KeyEvent.KEYCODE_BUTTON_11,
                KeyEvent.KEYCODE_BUTTON_12,
                KeyEvent.KEYCODE_BUTTON_13,
                KeyEvent.KEYCODE_BUTTON_14,
                KeyEvent.KEYCODE_BUTTON_15,
                KeyEvent.KEYCODE_BUTTON_16,
        };
        int[] masks = new int[]{
                (1 << 0),   // A -> A
                (1 << 1),   // B -> B
                (1 << 2),   // X -> X
                (1 << 3),   // Y -> Y
                (1 << 4),   // BACK -> BACK
                (1 << 6),   // MENU -> START
                (1 << 5),   // MODE -> GUIDE
                (1 << 6),   // START -> START
                (1 << 7),   // THUMBL -> LEFTSTICK
                (1 << 8),   // THUMBR -> RIGHTSTICK
                (1 << 9),   // L1 -> LEFTSHOULDER
                (1 << 10),  // R1 -> RIGHTSHOULDER
                (1 << 11),  // DPAD_UP -> DPAD_UP
                (1 << 12),  // DPAD_DOWN -> DPAD_DOWN
                (1 << 13),  // DPAD_LEFT -> DPAD_LEFT
                (1 << 14),  // DPAD_RIGHT -> DPAD_RIGHT
                (1 << 4),   // SELECT -> BACK
                (1 << 0),   // DPAD_CENTER -> A
                (1 << 15),  // L2 -> ??
                (1 << 16),  // R2 -> ??
                (1 << 17),  // C -> ??
                (1 << 18),  // Z -> ??
                (1 << 20),  // 1 -> ??
                (1 << 21),  // 2 -> ??
                (1 << 22),  // 3 -> ??
                (1 << 23),  // 4 -> ??
                (1 << 24),  // 5 -> ??
                (1 << 25),  // 6 -> ??
                (1 << 26),  // 7 -> ??
                (1 << 27),  // 8 -> ??
                (1 << 28),  // 9 -> ??
                (1 << 29),  // 10 -> ??
                (1 << 30),  // 11 -> ??
                (1 << 31),  // 12 -> ??
                // We're out of room...
                0xFFFFFFFF,  // 13 -> ??
                0xFFFFFFFF,  // 14 -> ??
                0xFFFFFFFF,  // 15 -> ??
                0xFFFFFFFF,  // 16 -> ??
        };
        boolean[] has_keys = joystickDevice.hasKeys(keys);
        for (int i = 0; i < keys.length; ++i) {
            if (has_keys[i]) {
                button_mask |= masks[i];
            }
        }
        return button_mask;
    }
}
