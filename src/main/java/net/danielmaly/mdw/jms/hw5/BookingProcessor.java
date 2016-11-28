package net.danielmaly.mdw.jms.hw5;

import net.danielmaly.mdw.jms.JMSConsumer;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BookingProcessor {
    public static void main(String[] args) throws Exception {
        java.util.Queue<Message> bookingQueueMessages = new ConcurrentLinkedQueue<>();
        JMSConsumer bookingConsumer = new JMSConsumer(Config.BOOKING_QUEUE_NAME, bookingQueueMessages);

        new Thread(bookingConsumer).start();

        while(true) {
            while(!bookingQueueMessages.isEmpty()) {
                Message message = bookingQueueMessages.remove();
                OrderMessage orderMessage = (OrderMessage) ((ObjectMessage) message).getObject();

                System.out.println("Processing boooking " + orderMessage);

            }
            Thread.sleep(500);
        }
    }
}
