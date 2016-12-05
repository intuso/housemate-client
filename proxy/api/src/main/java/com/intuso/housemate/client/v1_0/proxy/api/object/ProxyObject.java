package com.intuso.housemate.client.v1_0.proxy.api.object;

import com.intuso.housemate.client.v1_0.api.object.Object;
import com.intuso.utilities.listener.ListenerRegistration;
import com.intuso.utilities.listener.Listeners;
import com.intuso.utilities.listener.ListenersFactory;
import org.slf4j.Logger;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @param <DATA> the type of the data
 * @param <LISTENER> the type of the listener
 */
public abstract class ProxyObject<
            DATA extends Object.Data,
            LISTENER extends Object.Listener> implements Object<LISTENER> {

    public final static String PROXY = "proxy";

    protected final Logger logger;
    protected final Listeners<LISTENER> listeners;
    private final Class<DATA> dataClass;

    protected DATA data;
    private Session session;
    private JMSUtil.Receiver<DATA> receiver;

    /**
     * @param logger the log
     */
    protected ProxyObject(Logger logger, Class<DATA> dataClass, ListenersFactory listenersFactory) {
        logger.debug("Creating");
        this.logger = logger;
        this.dataClass = dataClass;
        this.listeners = listenersFactory.create();
    }

    @Override
    public String getId() {
        return data == null ? null : data.getId();
    }

    @Override
    public String getName() {
        return data == null ? null : data.getName();
    }

    @Override
    public String getDescription() {
        return data == null ? null : data.getDescription();
    }

    @Override
    public ListenerRegistration addObjectListener(LISTENER listener) {
        return listeners.addListener(listener);
    }

    protected final void init(String name, Connection connection) throws JMSException {
        logger.debug("Init");
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        receiver = new JMSUtil.Receiver<>(logger,
                session.createConsumer(session.createTopic(name + "?consumer.retroactive=true")),
                dataClass,
                new JMSUtil.Receiver.Listener<DATA>() {
                    @Override
                    public void onMessage(DATA data, boolean wasPersisted) {
                        ProxyObject.this.data = data;
                        dataUpdated();
                    }
                });
        initChildren(name, connection);
    }

    protected void initChildren(String name, Connection connection) throws JMSException {}

    protected final void uninit() {
        logger.debug("Uninit");
        uninitChildren();
        if(receiver != null) {
            try {
                receiver.close();
            } catch (JMSException e) {
                logger.error("Failed to close receiver");
            }
            receiver = null;
        }
        if(session != null) {
            try {
                session.close();
            } catch(JMSException e) {
                logger.error("Failed to close session");
            }
            session = null;
        }
    }

    protected void uninitChildren() {}

    protected void dataUpdated() {}

    public interface Factory<OBJECT extends ProxyObject<?, ?>> {
        OBJECT create(Logger logger);
    }
}