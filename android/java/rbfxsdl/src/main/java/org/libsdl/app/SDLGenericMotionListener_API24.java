package org.libsdl.app;

import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

class SDLGenericMotionListener_API24 extends SDLGenericMotionListener_API12 {
    // Generic Motion (mouse hover, joystick...) events go here

    private boolean mRelativeModeEnabled;

    @Override
    public boolean onGenericMotion(View v, MotionEvent event) {

        // Handle relative mouse mode
        if (mRelativeModeEnabled) {
            if (event.getSource() == InputDevice.SOURCE_MOUSE) {
                int action = event.getActionMasked();
                if (action == MotionEvent.ACTION_HOVER_MOVE) {
                    float x = event.getAxisValue(MotionEvent.AXIS_RELATIVE_X);
                    float y = event.getAxisValue(MotionEvent.AXIS_RELATIVE_Y);
                    SDLActivity.onNativeMouse(0, action, x, y, true);
                    return true;
                }
            }
        }

        // Event was not managed, call SDLGenericMotionListener_API12 method
        return super.onGenericMotion(v, event);
    }

    @Override
    public boolean supportsRelativeMouse() {
        return true;
    }

    @Override
    public boolean inRelativeMode() {
        return mRelativeModeEnabled;
    }

    @Override
    public boolean setRelativeMouseEnabled(boolean enabled) {
        mRelativeModeEnabled = enabled;
        return true;
    }

    @Override
    public float getEventX(MotionEvent event) {
        if (mRelativeModeEnabled) {
            return event.getAxisValue(MotionEvent.AXIS_RELATIVE_X);
        } else {
            return event.getX(0);
        }
    }

    @Override
    public float getEventY(MotionEvent event) {
        if (mRelativeModeEnabled) {
            return event.getAxisValue(MotionEvent.AXIS_RELATIVE_Y);
        } else {
            return event.getY(0);
        }
    }
}
