package net.danielmaly.mdw.jms.hw5;

import net.danielmaly.mdw.jms.JMSConsumer;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TripProcessor {
    public static void main(String[] args) throws Exception {
        java.util.Queue<Message> tripQueueMessages = new ConcurrentLinkedQueue<>();
        JMSConsumer bookingConsumer = new JMSConsumer(Config.TRIP_QUEUE_NAME, tripQueueMessages);

        new Thread(bookingConsumer).start();

        while(true) {
            while(!tripQueueMessages.isEmpty()) {
                Message message = tripQueueMessages.remove();
                OrderMessage orderMessage = (OrderMessage) ((ObjectMessage) message).getObject();

                System.out.println("Processing trip " + orderMessage);

            }
            Thread.sleep(500);
        }
    }
}
