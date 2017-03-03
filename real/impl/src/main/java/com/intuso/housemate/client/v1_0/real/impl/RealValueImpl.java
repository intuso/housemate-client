package com.intuso.housemate.client.v1_0.real.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.intuso.housemate.client.v1_0.api.object.Value;
import com.intuso.housemate.client.v1_0.messaging.api.Receiver;
import com.intuso.housemate.client.v1_0.messaging.api.Sender;
import com.intuso.housemate.client.v1_0.real.api.RealValue;
import com.intuso.utilities.collection.ManagedCollectionFactory;
import org.slf4j.Logger;

/**
 * @param <O> the type of this value's value
 */
public final class RealValueImpl<O>
        extends RealValueBaseImpl<O, Value.Data, Value.Listener<? super RealValueImpl<O>>, RealValueImpl<O>>
        implements RealValue<O, RealTypeImpl<O>, RealValueImpl<O>> {

    /**
     * @param logger {@inheritDoc}
     * @param managedCollectionFactory
     * @param type the type of the value's value
     */
    @Inject
    public RealValueImpl(@Assisted Logger logger,
                         @Assisted("id") String id,
                         @Assisted("name") String name,
                         @Assisted("description") String description,
                         @Assisted RealTypeImpl type,
                         @Assisted("min") int minValues,
                         @Assisted("max") int maxValues,
                         @Assisted Iterable values,
                         ManagedCollectionFactory managedCollectionFactory,
                         Receiver.Factory receiverFactory,
                         Sender.Factory senderFactory) {
        super(logger, new Value.Data(id, name, description, type.getId(), minValues, maxValues), managedCollectionFactory, receiverFactory, senderFactory, type, values);
    }

    public interface Factory {
        RealValueImpl<?> create(Logger logger,
                                @Assisted("id") String id,
                                @Assisted("name") String name,
                                @Assisted("description") String description,
                                RealTypeImpl type,
                                @Assisted("min") int minValues,
                                @Assisted("max") int maxValues,
                                Iterable values);
    }
}
