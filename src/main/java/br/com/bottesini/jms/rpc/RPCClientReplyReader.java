package br.com.bottesini.jms.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClientReplyReader {

    private Connection connection;
    private Channel channel;


    public static void main(String[] argv) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(RPCClientSimpleSend.replyQueueName, false, false, false, null);
        channel.basicConsume(RPCClientSimpleSend.replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Response for id "+ properties.getCorrelationId()+".");
            }
        });
    }
}
