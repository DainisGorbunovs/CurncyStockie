package com.twilio;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;

public class TwilioServlet extends HttpServlet {

  // service() responds to both GET and POST requests.
  // You can also use doGet() or doPost()
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    TwiMLResponse twiml = new TwiMLResponse();
    String userInput = request.getParameter("Body"); 
    Message message = new Message(HackWix.interpretSMS(userInput));


    twiml.append(message);

    response.setContentType("application/xml");
    response.getWriter().print(twiml.toXML());
  }
}
