package org.libsdl.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

// Urho3D - suppress lint error
@TargetApi(Build.VERSION_CODES.O)
class SDLGenericMotionListener_API26 extends SDLGenericMotionListener_API24 {
    // Generic Motion (mouse hover, joystick...) events go here
    private boolean mRelativeModeEnabled;

    @Override
    public boolean onGenericMotion(View v, MotionEvent event) {
        float x, y;
        int action;

        switch ( event.getSource() ) {
            case InputDevice.SOURCE_JOYSTICK:
            case InputDevice.SOURCE_GAMEPAD:
            case InputDevice.SOURCE_DPAD:
                return SDLControllerManager.handleJoystickMotionEvent(event);

            case InputDevice.SOURCE_MOUSE:
            // DeX desktop mouse cursor is a separate non-standard input type.
            case InputDevice.SOURCE_MOUSE | InputDevice.SOURCE_TOUCHSCREEN:
                action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_SCROLL:
                        x = event.getAxisValue(MotionEvent.AXIS_HSCROLL, 0);
                        y = event.getAxisValue(MotionEvent.AXIS_VSCROLL, 0);
                        SDLActivity.onNativeMouse(0, action, x, y, false);
                        return true;

                    case MotionEvent.ACTION_HOVER_MOVE:
                        x = event.getX(0);
                        y = event.getY(0);
                        SDLActivity.onNativeMouse(0, action, x, y, false);
                        return true;

                    default:
                        break;
                }
                break;

            case InputDevice.SOURCE_MOUSE_RELATIVE:
                action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_SCROLL:
                        x = event.getAxisValue(MotionEvent.AXIS_HSCROLL, 0);
                        y = event.getAxisValue(MotionEvent.AXIS_VSCROLL, 0);
                        SDLActivity.onNativeMouse(0, action, x, y, false);
                        return true;

                    case MotionEvent.ACTION_HOVER_MOVE:
                        x = event.getX(0);
                        y = event.getY(0);
                        SDLActivity.onNativeMouse(0, action, x, y, true);
                        return true;

                    default:
                        break;
                }
                break;

            default:
                break;
        }

        // Event was not managed
        return false;
    }

    @Override
    public boolean supportsRelativeMouse() {
        return (!SDLActivity.isDeXMode() || (Build.VERSION.SDK_INT >= 27));
    }

    @Override
    public boolean inRelativeMode() {
        return mRelativeModeEnabled;
    }

    @Override
    public boolean setRelativeMouseEnabled(boolean enabled) {
        if (!SDLActivity.isDeXMode() || (Build.VERSION.SDK_INT >= 27)) {
            if (enabled) {
                SDLActivity.getContentView().requestPointerCapture();
            }
            else {
                SDLActivity.getContentView().releasePointerCapture();
            }
            mRelativeModeEnabled = enabled;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void reclaimRelativeMouseModeIfNeeded()
    {
        if (mRelativeModeEnabled && !SDLActivity.isDeXMode()) {
            SDLActivity.getContentView().requestPointerCapture();
        }
    }

    @Override
    public float getEventX(MotionEvent event) {
        // Relative mouse in capture mode will only have relative for X/Y
        return event.getX(0);
    }

    @Override
    public float getEventY(MotionEvent event) {
        // Relative mouse in capture mode will only have relative for X/Y
        return event.getY(0);
    }
}
