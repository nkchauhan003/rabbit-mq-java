package com.codeburps;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 */
public class App {

    private final static String QUEUE_NAME = "order_queue";
    private final static String HOST = "localhost";
    private final static int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println("Consumed: " + message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
