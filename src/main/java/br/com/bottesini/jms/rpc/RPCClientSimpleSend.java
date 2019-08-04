package br.com.bottesini.jms.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClientSimpleSend {

    private Connection connection;
    private Channel channel;
    private static String requestQueueName = "rpc_queue";
    public static String replyQueueName = "rpc_reply_queue";

    public RPCClientSimpleSend() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(replyQueueName, false, false, false, null);
    }

    public void call(String message) throws IOException, InterruptedException {

        final String corrId = UUID.randomUUID().toString();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        System.out.println("Message with id "+corrId+" sent.");
    }

    public void close() throws IOException {
        connection.close();
    }

    public static void main(String[] argv) {
        RPCClientSimpleSend rpcSender = null;

        try {
            rpcSender = new RPCClientSimpleSend();

            for (int i = 0; i < 5; i++) {
                System.out.println(" [x] Requesting msg (" + i + ")");
                rpcSender.call(String.valueOf(i));
            }
        }catch  (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (rpcSender!= null) {
                try {
                    rpcSender.close();
                }
                catch (IOException _ignore) {}
            }
        }
    }
}
