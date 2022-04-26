package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.MsgClass;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

//my imports

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class SimpleServer extends AbstractServer {

    private static Session session;

    private static List<Flower> getAllFlowers() throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Flower> query = builder.createQuery(Flower.class);
        query.from(Flower.class);
        List<Flower> data = session.createQuery(query).getResultList();
        return data;
    }

    private static void generateFlowers() {
        Flower flower1 = new Flower(50, "red","https://images.unsplash.com/photo-1569101315919-dafea4df33ff?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cmVkJTIwZmxvd2VyfGVufDB8fDB8fA%3D%3D&w=1000&q=80");
        session.save(flower1);
        session.flush();
        Flower flower2 = new Flower(45, "green","https://www.allaboutgardening.com/wp-content/uploads/2021/10/Carnation.jpg");
        session.save(flower2);
        session.flush();
        Flower flower3 = new Flower(30, "blue","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzjZZ9dDZzh6zb3fbq8g4MpK8ybBNNQ9TzEg&usqp=CAU");
        session.save(flower3);
        session.flush();
        Flower flower4 = new Flower(55, "purple","https://upload.wikimedia.org/wikipedia/commons/9/9c/Purple_Flower_%22Pensamiento%22_Viola_%C3%97_wittrockiana.JPG");
        session.save(flower4);
        session.flush();
        Flower flower5 = new Flower(20, "yellow","https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/yellow-flower-dahlia-1587061007.jpg?crop=0.557xw:1.00xh;0.0569xw,0&resize=480:*");
        session.save(flower5);
        session.flush();
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        SimpleServer.session = session;
    }


    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Flower.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }



    public SimpleServer(int port) {
        super(port);
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        generateFlowers();
        session.getTransaction().commit();
    }

    private static void updatePrice(Flower flower, int price) {
        System.out.println(price);
        System.out.println(flower);
        session.beginTransaction();
        flower.setPrice(price);
        session.update(flower);
        System.out.println(flower);
        session.getTransaction().commit();
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        String msgString = msg.toString();
        if (msg.getClass().equals(MsgClass.class)) {
            MsgClass myMsg = (MsgClass) msg;
            String msgtext=myMsg.getMsg();
            try {
                if (msgtext.startsWith("#warning")) {
                    Warning warning = new Warning("Warning from server!");
                    try {
                        client.sendToClient(warning);
                        System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (msgtext.equals("#get phots URL")) {
                    try {
                        MsgClass myMSg = new MsgClass("all flowers");
                        myMSg.setObj(getAllFlowers());
                        client.sendToClient(myMSg);
                    } catch (Exception e) {
                        System.out.println("eror hapend");
                        System.out.println(e);
                    }
                }
                if (msgtext.startsWith("#update")) {
                    try {
                        System.out.println("in update");
                        int id = Integer.parseInt(String.valueOf(msgtext.charAt(9)));
                        int price = Integer.parseInt(msgtext.substring(11));
                        updatePrice(session.get(Flower.class, id), price);
                        myMsg.setObj(getAllFlowers());
                        myMsg.setMsg("all flowers");
                        System.out.println("sent to update");
                        client.sendToClient(myMsg);
                        System.out.println("done sending update");
                    } catch (Exception e) {
                        System.out.println("in updete exception");
                        System.out.println(e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            if (msgString.startsWith("#close")) {
                session.close();
            }
    }
}



