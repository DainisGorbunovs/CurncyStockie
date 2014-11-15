package com.twilio;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

// Program which finds prices for stocks using Bloomberg API
public class HackWix {
  //        public String receivedSMS;
  //      public HackWix(String a)
  //    {
  //    receivedSMS = a;
  //};

  // Twilio API SID and token
  public static final String ACCOUNT_SID = "***EDIT***"; 
  public static final String AUTH_TOKEN = "***EDIT***"; 

  // This method returns the price of equity, e.g. "IBM US Equity"
  private static double getEquityPrice(String equityName) throws Exception
  {
    String[] s = new String[] {"-s",equityName};
    RefDataExample example = new RefDataExample();
    return example.run(s);
  }

  // This method allows user to find price of stock, e.g. "IBM"
  // Basically it just appends "Equity" and uses getEquityPrice method
  private static double getStockPrice(String stockName) throws Exception
  {
    return getEquityPrice(stockName + " Equity");
  }

  // This method allows user to find price of stock, e.g. "IBM"
  // Basically it just appends "Equity" and uses getEquityPrice method
  private static double getCurrencyRate(String fromTo) throws Exception
  {
    return getEquityPrice(fromTo + " Curncy");
  }

  // This method gets a list of equities
  /*private static void getEquities(String stockName)
    {
    String[] s = new String[] {"-s",stockName};
    SecurityLookupExample example = new SecurityLookupExample();
    example.run(s);
    }*/

  // This method sends an SMS to a selected phone number
  private static void sendSMS(String phoneNo, String message) throws Exception
  {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 

    // Build the parameters 
    List<NameValuePair> params = new ArrayList<NameValuePair>(); 
    params.add(new BasicNameValuePair("To", phoneNo)); 
    params.add(new BasicNameValuePair("From", "+44***EDIT***")); 
    params.add(new BasicNameValuePair("Body", message));   

    MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
    Message msg = messageFactory.create(params); 
    System.out.println(msg.getSid()); 
  }

  public static String interpretSMS(String receivedSMS) throws Exception
  {
    String[] split = receivedSMS.split(" ");
    // codeWord is "stock" for finding stock price
    // and "currency" for finding currency rate
    String codeWord = split[split.length-1].toUpperCase();
    String answerSMS = "";

    switch (codeWord)
    {
      case "STOCK":
        //System.out.println(split[0]+"'s current price is USD "+getStockPrice(split[0]));
        answerSMS = split[0]+"'s current price is USD "+getStockPrice(split[0]);
        break;
      case "CURRENCY":
        if (split.length < 2)
          break;

        String currencyFromTo = split[split.length-2];
        double exchangeRate = getCurrencyRate(currencyFromTo);
        String currencyFrom = currencyFromTo.substring(0,currencyFromTo.length()/2);
        String currencyTo = currencyFromTo.substring(currencyFromTo.length()/2,currencyFromTo.length());
        currencyFromTo = currencyFrom + " -> " + currencyTo;

        if (split.length > 2)
        {
          double moneyAmountFrom = Double.parseDouble(split[0]);
          double moneyAmountTo = moneyAmountFrom*exchangeRate;
          //System.out.println(moneyAmountFrom + " " + currencyFrom + " = " + moneyAmountTo + " " + currencyTo);
          //System.out.printf("%.2f " + currencyFrom + " = %.2f " + currencyTo, moneyAmountFrom, moneyAmountTo);
          answerSMS = String.format("%.2f " + currencyFrom + " = %.2f " + currencyTo + ". ", moneyAmountFrom, moneyAmountTo);
        }

        //System.out.println(currencyFromTo+" currency exchange rate is " + exchangeRate);
        answerSMS += "The exchange rate "+currencyFromTo+" is " + exchangeRate;
        break;
      default: break;
    }
    return answerSMS;
  }

  // This program uses RefDataExample for checking stock prices, currency exchange rates
  // It is ready to use SecurityLookupExample for showing a list of all stocks a company has
  // But not used in the program.
  //	public static void main(String[] args) throws Exception
  //	{
  // Some possible ways of using this program
  //String receivedSMS = "GOOG stock";
  //	String receivedSMS = "EURGBP currency";
  //	String receivedSMS = "13.37 EURGBP currency";

  // We have to interpret the received SMS message
  //	System.out.print(interpretSMS(receivedSMS));

  // Finding all equities of Apple company
  //getEquities("AAPL");

  // Sending SMS text message
  //sendSMS("<phone-number>", "Thanks, man! You are awesome!");


  //`	}
}
