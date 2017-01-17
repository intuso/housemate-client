package com.intuso.housemate.client.v1_0.proxy.api.object;

import com.intuso.housemate.client.v1_0.api.object.Node;
import com.intuso.housemate.client.v1_0.proxy.api.ChildUtil;
import com.intuso.utilities.listener.ListenersFactory;
import org.slf4j.Logger;

import javax.jms.Connection;
import javax.jms.JMSException;

/**
 * @param <COMMAND> the type of the command
 * @param <TYPES> the type of the types list
 * @param <HARDWARES> the type of the hardwares list
 * @param <NODE> the type of the node
 */
public abstract class ProxyNode<
        COMMAND extends ProxyCommand<?, ?, ?>,
        TYPES extends ProxyList<? extends ProxyType<?>, ?>,
        HARDWARES extends ProxyList<? extends ProxyHardware<?, ?, ?, ?, ?, ?, ?>, ?>,
        NODE extends ProxyNode<COMMAND, TYPES, HARDWARES, NODE>>
        extends ProxyObject<Node.Data, Node.Listener<? super NODE>>
        implements Node<COMMAND, TYPES, HARDWARES, NODE> {

    private final TYPES types;
    private final HARDWARES hardwares;
    private final COMMAND addHardwareCommand;

    public ProxyNode(Logger logger,
                     ListenersFactory listenersFactory,
                     Factory<COMMAND> commandFactory,
                     Factory<TYPES> typesFactory,
                     Factory<HARDWARES> hardwaresFactory) {
        super(logger, Node.Data.class, listenersFactory);
        types = typesFactory.create(ChildUtil.logger(logger, TYPES_ID));
        hardwares = hardwaresFactory.create(ChildUtil.logger(logger, HARDWARES_ID));
        addHardwareCommand = commandFactory.create(ChildUtil.logger(logger, ADD_HARDWARE_ID));
    }

    @Override
    protected void initChildren(String name, Connection connection) throws JMSException {
        super.initChildren(name, connection);
        types.init(ChildUtil.name(name, TYPES_ID), connection);
        hardwares.init(ChildUtil.name(name, HARDWARES_ID), connection);
        addHardwareCommand.init(ChildUtil.name(name, ADD_HARDWARE_ID), connection);
    }

    @Override
    protected void uninitChildren() {
        super.uninitChildren();
        types.uninit();
        hardwares.uninit();
        addHardwareCommand.uninit();
    }

    @Override
    public TYPES getTypes() {
        return types;
    }

    @Override
    public HARDWARES getHardwares() {
        return hardwares;
    }

    @Override
    public COMMAND getAddHardwareCommand() {
        return addHardwareCommand;
    }

    @Override
    public ProxyObject<?, ?> getChild(String id) {
        if(ADD_HARDWARE_ID.equals(id))
            return addHardwareCommand;
        else if(HARDWARES_ID.equals(id))
            return hardwares;
        else if(TYPES_ID.equals(id))
            return types;
        return null;
    }
}
