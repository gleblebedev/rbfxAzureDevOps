package org.libsdl.app;

import android.view.InputDevice;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* Actual joystick functionality available for API >= 12 devices */
public class SDLJoystickHandler_API16 extends SDLJoystickHandler {

    static class SDLJoystick {
        public int device_id;
        public String name;
        public String desc;
        public ArrayList<InputDevice.MotionRange> axes;
        public ArrayList<InputDevice.MotionRange> hats;
    }

    static class RangeComparator implements Comparator<InputDevice.MotionRange> {
        @Override
        public int compare(InputDevice.MotionRange arg0, InputDevice.MotionRange arg1) {
            // Some controllers, like the Moga Pro 2, return AXIS_GAS (22) for right trigger and AXIS_BRAKE (23) for left trigger - swap them so they're sorted in the right order for SDL
            int arg0Axis = arg0.getAxis();
            int arg1Axis = arg1.getAxis();
            if (arg0Axis == MotionEvent.AXIS_GAS) {
                arg0Axis = MotionEvent.AXIS_BRAKE;
            } else if (arg0Axis == MotionEvent.AXIS_BRAKE) {
                arg0Axis = MotionEvent.AXIS_GAS;
            }
            if (arg1Axis == MotionEvent.AXIS_GAS) {
                arg1Axis = MotionEvent.AXIS_BRAKE;
            } else if (arg1Axis == MotionEvent.AXIS_BRAKE) {
                arg1Axis = MotionEvent.AXIS_GAS;
            }

            return arg0Axis - arg1Axis;
        }
    }

    private final ArrayList<SDLJoystick> mJoysticks;

    public SDLJoystickHandler_API16() {

        mJoysticks = new ArrayList<SDLJoystick>();
    }

    @Override
    public void pollInputDevices() {
        int[] deviceIds = InputDevice.getDeviceIds();

        for (int device_id : deviceIds) {
            if (SDLControllerManager.isDeviceSDLJoystick(device_id)) {
                SDLJoystick joystick = getJoystick(device_id);
                if (joystick == null) {
                    InputDevice joystickDevice = InputDevice.getDevice(device_id);
                    joystick = new SDLJoystick();
                    joystick.device_id = device_id;
                    joystick.name = joystickDevice.getName();
                    joystick.desc = getJoystickDescriptor(joystickDevice);
                    joystick.axes = new ArrayList<InputDevice.MotionRange>();
                    joystick.hats = new ArrayList<InputDevice.MotionRange>();

                    List<InputDevice.MotionRange> ranges = joystickDevice.getMotionRanges();
                    Collections.sort(ranges, new RangeComparator());
                    for (InputDevice.MotionRange range : ranges) {
                        if ((range.getSource() & InputDevice.SOURCE_CLASS_JOYSTICK) != 0) {
                            if (range.getAxis() == MotionEvent.AXIS_HAT_X || range.getAxis() == MotionEvent.AXIS_HAT_Y) {
                                joystick.hats.add(range);
                            } else {
                                joystick.axes.add(range);
                            }
                        }
                    }

                    mJoysticks.add(joystick);
                    SDLControllerManager.nativeAddJoystick(joystick.device_id, joystick.name, joystick.desc,
                            getVendorId(joystickDevice), getProductId(joystickDevice), false,
                            getButtonMask(joystickDevice), joystick.axes.size(), joystick.hats.size() / 2, 0);
                }
            }
        }

        /* Check removed devices */
        ArrayList<Integer> removedDevices = null;
        for (SDLJoystick joystick : mJoysticks) {
            int device_id = joystick.device_id;
            int i;
            for (i = 0; i < deviceIds.length; i++) {
                if (device_id == deviceIds[i]) break;
            }
            if (i == deviceIds.length) {
                if (removedDevices == null) {
                    removedDevices = new ArrayList<Integer>();
                }
                removedDevices.add(device_id);
            }
        }

        if (removedDevices != null) {
            for (int device_id : removedDevices) {
                SDLControllerManager.nativeRemoveJoystick(device_id);
                for (int i = 0; i < mJoysticks.size(); i++) {
                    if (mJoysticks.get(i).device_id == device_id) {
                        mJoysticks.remove(i);
                        break;
                    }
                }
            }
        }
    }

    protected SDLJoystick getJoystick(int device_id) {
        for (SDLJoystick joystick : mJoysticks) {
            if (joystick.device_id == device_id) {
                return joystick;
            }
        }
        return null;
    }

    @Override
    public boolean handleMotionEvent(MotionEvent event) {
        int actionPointerIndex = event.getActionIndex();
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_MOVE) {
            SDLJoystick joystick = getJoystick(event.getDeviceId());
            if (joystick != null) {
                for (int i = 0; i < joystick.axes.size(); i++) {
                    InputDevice.MotionRange range = joystick.axes.get(i);
                    /* Normalize the value to -1...1 */
                    float value = (event.getAxisValue(range.getAxis(), actionPointerIndex) - range.getMin()) / range.getRange() * 2.0f - 1.0f;
                    SDLControllerManager.onNativeJoy(joystick.device_id, i, value);
                }
                for (int i = 0; i < joystick.hats.size() / 2; i++) {
                    int hatX = Math.round(event.getAxisValue(joystick.hats.get(2 * i).getAxis(), actionPointerIndex));
                    int hatY = Math.round(event.getAxisValue(joystick.hats.get(2 * i + 1).getAxis(), actionPointerIndex));
                    SDLControllerManager.onNativeHat(joystick.device_id, i, hatX, hatY);
                }
            }
        }
        return true;
    }

    public String getJoystickDescriptor(InputDevice joystickDevice) {
        String desc = joystickDevice.getDescriptor();

        if (desc != null && !desc.isEmpty()) {
            return desc;
        }

        return joystickDevice.getName();
    }

    public int getProductId(InputDevice joystickDevice) {
        return 0;
    }

    public int getVendorId(InputDevice joystickDevice) {
        return 0;
    }

    public int getButtonMask(InputDevice joystickDevice) {
        return -1;
    }
}
