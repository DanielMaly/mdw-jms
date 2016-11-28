package net.danielmaly.mdw.jms;

import net.danielmaly.mdw.jms.tutorial04.Config;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Hashtable;

public class JMSProducer {
    // connection factory
    private QueueConnectionFactory qconFactory;

    // connection to a queue
    private QueueConnection qcon;

    // session within a connection
    private QueueSession qsession;

    // queue sender that sends a message to the queue
    private QueueSender qsender;

    // queue where the message will be sent to
    private Queue queue;

    // create a connection to the WLS using a JNDI context
    public void init(String queueName)
            throws NamingException, JMSException {

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Config.JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, Config.PROVIDER_URL);

        InitialContext ctx = new InitialContext(env);

        // create connection factory based on JNDI and a connection
        qconFactory = (QueueConnectionFactory) ctx.lookup(Config.JMS_FACTORY);
        qcon = qconFactory.createQueueConnection();

        // create a session within a connection
        qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        // lookups the queue using the JNDI context
        queue = (Queue) ctx.lookup(queueName);

        // create sender and message
        qsender = qsession.createSender(queue);
    }

    // close sender, connection and the session
    public void close() throws JMSException {
        qsender.close();
        qsession.close();
        qcon.close();
    }

    public void send(String queueName, Message message) throws Exception {

        init(queueName);
        try {
            qsender.send(message, DeliveryMode.PERSISTENT, 8, 0);
            System.out.println("The message was sent to the destination " +
                    qsender.getDestination().toString());
        } finally {
            close();
        }
    }

    // sends a string message to the queue
    public void send(String queueName, String message) throws Exception {
        init(queueName);
        TextMessage msg = qsession.createTextMessage(message);
        send(queueName, msg);
    }

    // sends an object message to the queue
    public void send(String queueName, Serializable object) throws Exception {
        init(queueName);
        ObjectMessage msg = qsession.createObjectMessage(object);
        send(queueName, msg);
    }
}
