package lk.kaushalya.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueApp {
    public static void main(String[] args) {

        try {
            InitialContext ic = new InitialContext();

            QueueConnectionFactory factory = (QueueConnectionFactory) ic.lookup("jms/myQueueConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Queue queue = (Queue) ic.lookup("myQueue");
            QueueReceiver receiver = session.createReceiver(queue);

            receiver.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    String msg = null;
                    try {
                        msg = message.getBody(String.class);
                        System.out.println(msg);
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
        while (true){}
    }
}
