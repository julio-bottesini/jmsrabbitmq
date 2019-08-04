package br.com.bottesini.jms.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

import static br.com.bottesini.jms.util.StringUtils.getMessage;
import static com.rabbitmq.client.BuiltinExchangeType.TOPIC;

public class TopicEmitLog {

    protected static final String EXCHANGE_NAME = "directLogs";
    protected static final BuiltinExchangeType EXCHANGE_TYPE = TOPIC;

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

        String routingKey = "teste.a.a.final";
        String message = "Test";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());


        System.out.println(" [x] Sent '" + routingKey + ": " + message + "'");

        channel.close();
        connection.close();
    }

    private static String getSeverity(String[] strings) {

        return "lazy.orange.rabbit";
//        return "quick.blue.bird";
//        return "lazy.*.bird";
//        return "lazy.#";
    }
}
