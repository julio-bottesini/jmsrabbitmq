package br.com.bottesini.jms.oneWorker.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

public class Send {

    public final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws java.io.IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false,false,false, null);
        String msg = "Hello, Rabbit";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("[x] Sent: '"+msg+"'");

        channel.close();
        connection.close();
    }
}
