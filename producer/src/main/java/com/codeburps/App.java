package com.codeburps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

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


        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // produce 10 messages
        for (int i = 0; i < 10; i++) {
            String message = "{\"oderId\":" + ThreadLocalRandom.current().nextInt(
                    1000, 2000) + ", \"items\":[{\"id\":" + ThreadLocalRandom.current().nextInt(
                    1000, 2000) + "},{\"id\":" + ThreadLocalRandom.current().nextInt(
                    1, 10) + "}]}";

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Produced: " + message);
        }
        channel.close();
        connection.close();
    }
}
