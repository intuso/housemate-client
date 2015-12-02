package com.intuso.housemate.client.v1_0.real.api.device.feature;

import com.intuso.housemate.client.v1_0.real.api.annotations.Feature;
import com.intuso.housemate.client.v1_0.real.api.annotations.TypeInfo;
import com.intuso.housemate.client.v1_0.real.api.annotations.Value;
import com.intuso.housemate.client.v1_0.real.api.annotations.Values;

/**
 * Interface to mark real devices that provide stateful volume control
 */
@Feature
@TypeInfo(id = "volume-stateful", name = "Volume", description = "Volume")
public interface StatefulVolumeControl extends VolumeControl {

    @Values
    interface VolumeValues {

        /**
         * Callback for when the current volume has changed
         * @param currentVolume the new current volume
         */
        @Value("integer")
        @TypeInfo(id = "current-volume", name = "Current Volume", description = "The device's current volume")
        void currentVolume(int currentVolume);
    }
}