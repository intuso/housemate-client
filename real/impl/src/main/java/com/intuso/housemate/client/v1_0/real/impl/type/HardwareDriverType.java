package com.intuso.housemate.client.v1_0.real.impl.type;

import com.google.inject.Inject;
import com.intuso.housemate.client.v1_0.real.impl.RealOptionImpl;
import com.intuso.housemate.client.v1_0.real.impl.ioc.Types;
import com.intuso.housemate.plugin.v1_0.api.driver.HardwareDriver;
import com.intuso.utilities.listener.ListenersFactory;
import org.slf4j.Logger;

/**
 * Created by tomc on 19/03/15.
 */
public class HardwareDriverType extends FactoryType<HardwareDriver.Factory<?>> {

    public final static String TYPE_ID = "hardware-factory";
    public final static String TYPE_NAME = "Hardware Factory";
    public final static String TYPE_DESCRIPTION = "Available types for new hardware";

    @Inject
    protected HardwareDriverType(@Types Logger logger, ListenersFactory listenersFactory, RealOptionImpl.Factory optionFactory) {
        super(logger, listenersFactory, TYPE_ID, TYPE_NAME, TYPE_DESCRIPTION, optionFactory);
    }
}
