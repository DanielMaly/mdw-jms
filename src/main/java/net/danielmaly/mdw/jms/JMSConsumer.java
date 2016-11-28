package net.danielmaly.mdw.jms;

import net.danielmaly.mdw.jms.hw5.Config;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class JMSConsumer implements MessageListener, Runnable {
    // connection factory
    private QueueConnectionFactory qconFactory;

    // connection to a queue
    private QueueConnection qcon;

    // session within a connection
    private QueueSession qsession;

    // queue receiver that receives a message to the queue
    private QueueReceiver qreceiver;

    // queue where the message will be sent to
    private Queue queue;

    private boolean stopped = false;

    private java.util.Queue<Message> messageQueue;

    // callback when the message exist in the queue
    @Override
    public void onMessage(Message msg) {
        this.messageQueue.add(msg);
    }

    // create a connection to the WLS using a JNDI context
    public JMSConsumer(String queueName, java.util.Queue<Message> messageQueue)
            throws NamingException, JMSException {

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Config.JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, Config.PROVIDER_URL);
        this.messageQueue = messageQueue;

        try {
            InitialContext ctx = new InitialContext(env);
            qconFactory = (QueueConnectionFactory) ctx.lookup(Config.JMS_FACTORY);
            qcon = qconFactory.createQueueConnection();
            qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = (Queue) ctx.lookup(queueName);

            qreceiver = qsession.createReceiver(queue);
            qreceiver.setMessageListener(this);

            qcon.start();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    // close sender, connection and the session
    private void close() throws JMSException {
        qreceiver.close();
        qsession.close();
        qcon.close();
    }

    // start receiving messages from the queue
    public void stop() {
        this.stopped = true;
    }

    @Override
    public void run() {

        System.out.println("Connected to " + queue.toString() + ", receiving messages...");
        try {
            synchronized (this) {
                while (!this.stopped) {
                    this.wait(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (JMSException ignored) {}
            finally {
                System.out.println("Finished.");
            }
        }
    }
}
