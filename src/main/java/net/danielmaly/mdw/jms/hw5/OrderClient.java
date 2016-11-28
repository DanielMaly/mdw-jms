package net.danielmaly.mdw.jms.hw5;

import net.danielmaly.mdw.jms.JMSProducer;

public class OrderClient {

    private static int idCounter = 0;

    public static void main(String[] args) throws Exception {
        JMSProducer orderProducer = new JMSProducer();

        while(true) {
            if(Math.random() < 0.5) {
                // Produce a trip
                OrderMessage trip = new OrderMessage(OrderMessage.Type.TRIP, "This is a fantastic trip", idCounter++);
                orderProducer.send(Config.ORDER_QUEUE_NAME, trip);
                System.out.println("Order Client sent order " + trip.getId() + ". It is a trip.");
            }

            else {
                // Produce a booking
                OrderMessage booking = new OrderMessage(OrderMessage.Type.BOOKING, "This is an awesome booking", idCounter++);
                orderProducer.send(Config.ORDER_QUEUE_NAME, booking);
                System.out.println("Order Client sent order " + booking.getId() + ". It is a booking.");
            }

            Thread.sleep(1000);
        }
    }


}
