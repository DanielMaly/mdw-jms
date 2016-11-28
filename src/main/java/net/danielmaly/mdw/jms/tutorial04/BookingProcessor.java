package net.danielmaly.mdw.jms.tutorial04;

import net.danielmaly.mdw.jms.JMSConsumer;
import net.danielmaly.mdw.jms.JMSProducer;

import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BookingProcessor {
    public static void main(String[] args) throws Exception {
        Queue<Message> bookingQueueMessages = new ConcurrentLinkedQueue<>();
        JMSConsumer bookingConsumer = new JMSConsumer(Config.BOOKING_QUEUE_NAME, bookingQueueMessages);
        JMSProducer confirmationProducer = new JMSProducer();

        new Thread(bookingConsumer).start();

        while(true) {
            while(!bookingQueueMessages.isEmpty()) {
                String message = ((TextMessage) bookingQueueMessages.remove()).getText();
                String confirmation = message + " is confirmed.";
                System.out.println("Received booking message: " + message + ". Sending confirmation.");
                confirmationProducer.send(Config.CONFIRMATION_QUEUE_NAME, confirmation);
            }
            Thread.sleep(1000);
        }
    }
}
