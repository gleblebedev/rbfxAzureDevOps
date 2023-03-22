package org.libsdl.app;

import android.content.Context;
import android.os.Vibrator;
import android.view.InputDevice;

import java.util.ArrayList;

class SDLHapticHandler {

    static class SDLHaptic {
        public int device_id;
        public String name;
        public Vibrator vib;
    }

    private final ArrayList<SDLHaptic> mHaptics;

    public SDLHapticHandler() {
        mHaptics = new ArrayList<SDLHaptic>();
    }

    public void run(int device_id, float intensity, int length) {
        SDLHaptic haptic = getHaptic(device_id);
        if (haptic != null) {
            haptic.vib.vibrate(length);
        }
    }

    public void stop(int device_id) {
        SDLHaptic haptic = getHaptic(device_id);
        if (haptic != null) {
            haptic.vib.cancel();
        }
    }

    public void pollHapticDevices() {

        final int deviceId_VIBRATOR_SERVICE = 999999;
        boolean hasVibratorService = false;

        int[] deviceIds = InputDevice.getDeviceIds();
        // It helps processing the device ids in reverse order
        // For example, in the case of the XBox 360 wireless dongle,
        // so the first controller seen by SDL matches what the receiver
        // considers to be the first controller

        for (int i = deviceIds.length - 1; i > -1; i--) {
            SDLHaptic haptic = getHaptic(deviceIds[i]);
            if (haptic == null) {
                InputDevice device = InputDevice.getDevice(deviceIds[i]);
                Vibrator vib = device.getVibrator();
                if (vib.hasVibrator()) {
                    haptic = new SDLHaptic();
                    haptic.device_id = deviceIds[i];
                    haptic.name = device.getName();
                    haptic.vib = vib;
                    mHaptics.add(haptic);
                    SDLControllerManager.nativeAddHaptic(haptic.device_id, haptic.name);
                }
            }
        }

        /* Check VIBRATOR_SERVICE */
        Vibrator vib = (Vibrator) SDL.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vib != null) {
            hasVibratorService = vib.hasVibrator();

            if (hasVibratorService) {
                SDLHaptic haptic = getHaptic(deviceId_VIBRATOR_SERVICE);
                if (haptic == null) {
                    haptic = new SDLHaptic();
                    haptic.device_id = deviceId_VIBRATOR_SERVICE;
                    haptic.name = "VIBRATOR_SERVICE";
                    haptic.vib = vib;
                    mHaptics.add(haptic);
                    SDLControllerManager.nativeAddHaptic(haptic.device_id, haptic.name);
                }
            }
        }

        /* Check removed devices */
        ArrayList<Integer> removedDevices = null;
        for (SDLHaptic haptic : mHaptics) {
            int device_id = haptic.device_id;
            int i;
            for (i = 0; i < deviceIds.length; i++) {
                if (device_id == deviceIds[i]) break;
            }

            if (device_id != deviceId_VIBRATOR_SERVICE || !hasVibratorService) {
                if (i == deviceIds.length) {
                    if (removedDevices == null) {
                        removedDevices = new ArrayList<Integer>();
                    }
                    removedDevices.add(device_id);
                }
            }  // else: don't remove the vibrator if it is still present
        }

        if (removedDevices != null) {
            for (int device_id : removedDevices) {
                SDLControllerManager.nativeRemoveHaptic(device_id);
                for (int i = 0; i < mHaptics.size(); i++) {
                    if (mHaptics.get(i).device_id == device_id) {
                        mHaptics.remove(i);
                        break;
                    }
                }
            }
        }
    }

    protected SDLHaptic getHaptic(int device_id) {
        for (SDLHaptic haptic : mHaptics) {
            if (haptic.device_id == device_id) {
                return haptic;
            }
        }
        return null;
    }
}
