package com.intuso.housemate.client.v1_0.real.api;

import com.intuso.housemate.client.v1_0.api.object.Device;
import com.intuso.housemate.plugin.v1_0.api.driver.DeviceDriver;
import com.intuso.housemate.plugin.v1_0.api.driver.PluginResource;

/**
 * Base class for all devices
 */
public interface RealDevice<COMMAND extends RealCommand<?, ?, ?>,
        BOOLEAN_VALUE extends RealValue<Boolean, ?, ?>,
        STRING_VALUE extends RealValue<String, ?, ?>,
        DRIVER_PROPERTY extends RealProperty<PluginResource<DeviceDriver.Factory<?>>, ?, ?, ?>,
        PROPERTIES extends RealList<? extends RealProperty<?, ?, ?, ?>, ?>,
        FEATURES extends RealList<? extends RealFeature<?, ?, ?>, ?>,
        DEVICE extends RealDevice<COMMAND, BOOLEAN_VALUE, STRING_VALUE, DRIVER_PROPERTY, PROPERTIES, FEATURES, DEVICE>>
        extends Device<
        COMMAND,
        COMMAND,
        COMMAND,
        BOOLEAN_VALUE,
        STRING_VALUE,
        DRIVER_PROPERTY,
        BOOLEAN_VALUE,
        PROPERTIES,
        FEATURES,
        DEVICE>,
        DeviceDriver.Callback {

    <DRIVER extends DeviceDriver> DRIVER getDriver();

    boolean isDriverLoaded();

    interface Container<DEVICE extends RealDevice<?, ?, ?, ?, ?, ?, ?>, DEVICES extends RealList<? extends DEVICE, ?>> extends Device.Container<DEVICES>, RemoveCallback<DEVICE> {
        void addDevice(DEVICE device);
    }

    interface RemoveCallback<DEVICE extends RealDevice<?, ?, ?, ?, ?, ?, ?>> {
        void removeDevice(DEVICE device);
    }
}
