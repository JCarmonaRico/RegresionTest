package com.acc.regresiontest.com.comparator.bussineslogic;



import java.util.Vector;

//buzon.import.xml.integracion.respuesta.queue

/* 
* Copyright (c) 2001-$Date: 2006-05-01 14:41:34 -0700 (Mon, 01 May 2006) $ TIBCO Software Inc. 
* All rights reserved.
* For more information, please contact:
* TIBCO Software Inc., Palo Alto, California, USA
* 
* $Id: tibjmsQueueSender.java 21731 2006-05-01 21:41:34Z rselph $
* 
*/

/*
* This is a simple sample of basic QueueSender.
*
* This sample publishes specified message(s) on a specified
* queue and quits.
* 
* Notice that specified queue should exist in your configuration
* or your queues configuration file should allow
* creation of the specified queue.
*
* This sample can send into dynamic queues thus it is
* using the QueueSession.createQueue() method
* to obtain the Queue object.
*
* Usage:  java tibjmsQueueSender  [options]
*                                  <message-text1>
*                                  ...
*                                  <message-textN>
*
*
*    where options are:
*
*      -server     Server URL.
*                  If not specified this sample assumes a
*                  serverUrl of "tcp://localhost:7222"
*
*      -user       User name. Default is null.
*      -password   User password. Default is null.
*      -queue      Queue name. Default is "queue.sample"
*
*
*/
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import com.acc.regresiontest.com.utils.tibjmsUtilities;



public class TibjmsQueueSender
{
  String      serverUrl       = "";
  String      userName        = "admin";
  String      password        = "admin123";

  String      queueName       = "";

  @SuppressWarnings("rawtypes")
	Vector      data            = new Vector();

  public TibjmsQueueSender(String[] args, String cola, String url) {

      parseArgs(args);
      serverUrl = url;
      queueName = cola;
      /* print parameters */
      System.out.println("\n------------------------------------------------------------------------");
      System.out.println("tibjmsQueueSender SAMPLE");
      System.out.println("------------------------------------------------------------------------");
      System.out.println("Server....................... "+(serverUrl!=null?serverUrl:"localhost"));
      System.out.println("User......................... "+(userName!=null?userName:"(null)"));
      System.out.println("Queue........................ "+queueName);
      System.out.println("------------------------------------------------------------------------\n");
      

      try
      {
          tibjmsUtilities.initSSLParams(serverUrl,args);
          System.out.println("iniciParams: " + serverUrl);
      }
      catch (JMSSecurityException e)
      {
          System.err.println("JMSSecurityException: "+e.getMessage()+", provider="+e.getErrorCode());
          e.printStackTrace();
          System.exit(0);
      }

      if (queueName == null)
      {
          System.err.println("Error: must specify queue name");
          usage();
      }
      
      

      if (data.size() == 0)
      {
          System.err.println("Error: must specify at least one message text");
          usage();
      }

      System.err.println("Publishing into queue: '"+queueName+"'\n");

      try
      {
      	
          QueueConnectionFactory factory = new com.tibco.tibjms.TibjmsQueueConnectionFactory(serverUrl);
        
          QueueConnection connection = factory.createQueueConnection(userName,password);
         
          QueueSession session = connection.createQueueSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
         
          /*
           * Use createQueue() to enable sending into dynamic queues.
           */
          javax.jms.Queue queue = session.createQueue(queueName);
      

          QueueSender sender = session.createSender(queue);
         
          /* publish messages */
         for (int i=0; i<data.size(); i++)
          {
          	
              javax.jms.TextMessage message = session.createTextMessage();
              String text = (String)data.elementAt(i);
              message.setText(text);
              message.setBooleanProperty("JMS_PRESERVE_UNDERIVERED", true);
              
              sender.send(message);
             
           //   System.err.println("Sent message: "+text);
         }
          
       

          connection.close();
         
      }
      catch(JMSException e)
      {
          e.printStackTrace();
          System.exit(0);
      }
  }

//  @SuppressWarnings("unused")
//	public static void main(String args[])
//  {
//      TibjmsQueueSender t = new TibjmsQueueSender(args, "");
//  }

  void usage()
  {
      System.err.println("\nUsage: java tibjmsQueueSender [options]");
      System.err.println("                                <message-text1 ... message-textN>");
      System.err.println("");
      System.err.println("   where options are:");
      System.err.println("");
      System.err.println(" -server    <server URL> - EMS server URL, default is local server");
      System.err.println(" -user      <user name>  - user name, default is null");
      System.err.println(" -password  <password>   - password, default is null");
      System.err.println(" -queue     <queue-name> - queue name, default is \"queue.sample\"");
      System.err.println(" -help-ssl               - help on ssl parameters\n");
      System.exit(0);
  }

  @SuppressWarnings("unchecked")
	void parseArgs(String[] args)
  {
      int i=0;

      while(i < args.length)
      {
          if (args[i].compareTo("-server")==0)
          {
              if ((i+1) >= args.length) usage();
              serverUrl = args[i+1];
              i += 2;
          }
          else
          if (args[i].compareTo("-queue")==0)
          {
              if ((i+1) >= args.length) usage();
              queueName = args[i+1];
              i += 2;
          }
          else
          if (args[i].compareTo("-user")==0)
          {
              if ((i+1) >= args.length) usage();
              userName = args[i+1];
              i += 2;
          }
          else
          if (args[i].compareTo("-password")==0)
          {
              if ((i+1) >= args.length) usage();
              password = args[i+1];
              i += 2;
          }
          else
          if (args[i].compareTo("-help")==0)
          {
              usage();
          }
          else
          if (args[i].compareTo("-help-ssl")==0)
          {
              tibjmsUtilities.sslUsage();
          }
          else
          if(args[i].startsWith("-ssl"))
          {
              i += 2;
          }
          else
          {
              data.addElement(args[i]);
              i++;
          }
      }
  }

}


