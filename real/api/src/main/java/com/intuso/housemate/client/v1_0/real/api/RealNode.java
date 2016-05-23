package com.intuso.housemate.client.v1_0.real.api;

import com.intuso.housemate.client.v1_0.api.object.Node;

public interface RealNode<COMMAND extends RealCommand<?, ?, ?>,
        HARDWARE extends RealHardware<?, ?, ?, ?, ?, ?>,
        HARDWARES extends RealList<? extends HARDWARE, ?>,
        NODE extends RealNode<COMMAND, HARDWARE, HARDWARES, NODE>>
        extends Node<COMMAND, HARDWARES, NODE>,
        RealHardware.Container<HARDWARE, HARDWARES> {
    COMMAND getAddHardwareCommand();

    interface Container<NODE extends RealNode<?, ?, ?, ?>, NODES extends RealList<? extends NODE, ?>> extends Node.Container<NODES> {
        void addNode(NODE node);
        void removeNode(NODE node);
    }
}
