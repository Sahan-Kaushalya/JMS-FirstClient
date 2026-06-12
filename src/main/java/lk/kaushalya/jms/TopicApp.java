package lk.kaushalya.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicApp {
    public static void main(String[] args) {
        try {
            InitialContext ic = new InitialContext();

            TopicConnectionFactory factory = (TopicConnectionFactory) ic.lookup("jms/myTopicConnectionFactory");
            TopicConnection connection = factory.createTopicConnection();

            System.out.println(connection);
            connection.start();

            TopicSession session = connection.createTopicSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            Topic topic = (Topic) ic.lookup("myTopic");
            TopicSubscriber subscriber = session.createSubscriber(topic);

//            Message message = subscriber.receive();
//            System.out.println(message);

            subscriber.setMessageListener(new MessageListener() {
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
        while (true){
        }
    }
}
