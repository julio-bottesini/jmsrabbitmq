package br.com.bottesini.jms.exchange.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

import static br.com.bottesini.jms.exchange.topic.TopicEmitLog.EXCHANGE_NAME;
import static br.com.bottesini.jms.exchange.topic.TopicEmitLog.EXCHANGE_TYPE;

public class TopicReceiveLogs {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        String queueName = channel.queueDeclare().getQueue();

        String routingKey = "teste.#.final";
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + ": " + message + "'");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }

    private static void createBindWithRoute(Channel channel, String queueName, String severityRouting) throws Exception {
        channel.queueBind(queueName, EXCHANGE_NAME, severityRouting);
    }
}