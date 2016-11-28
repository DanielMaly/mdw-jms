package net.danielmaly.mdw.jms.hw5;

import net.danielmaly.mdw.jms.JMSConsumer;
import net.danielmaly.mdw.jms.JMSProducer;

import javax.jms.*;
import javax.jms.Message;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderProcessor {
    public static void main(String[] args) throws Exception {
        java.util.Queue<Message> orderQueueMessages = new ConcurrentLinkedQueue<>();
        JMSConsumer bookingConsumer = new JMSConsumer(Config.ORDER_QUEUE_NAME, orderQueueMessages);
        JMSProducer jmsProducer = new JMSProducer();

        new Thread(bookingConsumer).start();

        while(true) {
            while(!orderQueueMessages.isEmpty()) {
                Message message = orderQueueMessages.remove();
                OrderMessage orderMessage = (OrderMessage) ((ObjectMessage) message).getObject();

                if(orderMessage.getType().equals(OrderMessage.Type.BOOKING)) {
                    jmsProducer.send(Config.BOOKING_QUEUE_NAME, orderMessage);
                    System.out.println("Forwarded message " + orderMessage + " to the booking queue");
                }
                else if(orderMessage.getType().equals(OrderMessage.Type.TRIP)) {
                    jmsProducer.send(Config.TRIP_QUEUE_NAME, orderMessage);
                    System.out.println("Forwarded message " + orderMessage + " to the trip queue");
                }

            }
            Thread.sleep(500);
        }
    }
}
