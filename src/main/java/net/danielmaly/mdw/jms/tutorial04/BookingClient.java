package net.danielmaly.mdw.jms.tutorial04;

import net.danielmaly.mdw.jms.JMSConsumer;
import net.danielmaly.mdw.jms.JMSProducer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BookingClient {
    public static void main(String[] args) throws Exception {
        Queue<String> confirmationQueueMessages = new ConcurrentLinkedQueue<>();
        JMSConsumer confirmationConsumer = new JMSConsumer(Config.CONFIRMATION_QUEUE_NAME, confirmationQueueMessages);
        JMSProducer bookingProducer = new JMSProducer();

        new Thread(confirmationConsumer).start();

        while(true) {
            if(Math.random() < 0.33) {
                Integer roomNumber = (int) (Math.random() * 1000);
                String booking = "Booking for room " + roomNumber;
                System.out.println("Sending booking for room " + roomNumber);
                bookingProducer.send(Config.BOOKING_QUEUE_NAME, booking);
            }


            while(!confirmationQueueMessages.isEmpty()) {
                String message = confirmationQueueMessages.remove();
                System.out.println("Received confirmation message: " + message);
            }
            Thread.sleep(1000);
        }
    }
}
