package br.com.bottesini.jms.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

import static br.com.bottesini.jms.util.StringUtils.getMessage;
import static br.com.bottesini.jms.util.StringUtils.joinStrings;
import static com.rabbitmq.client.BuiltinExchangeType.DIRECT;

public class EmitLog {

    protected static final String EXCHANGE_NAME = "directLogs";
    protected static final BuiltinExchangeType EXCHANGE_TYPE = DIRECT;

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

        String severityRouting = "info";
        String message = "Test msg info";

        channel.basicPublish(EXCHANGE_NAME, severityRouting, null, message.getBytes());
        System.out.println(" [x] Sent '" + severityRouting + ": " + message + "'");

        channel.close();
        connection.close();
    }

    private static String getSeverity(String[] strings) {
        return "info";
    }
}
