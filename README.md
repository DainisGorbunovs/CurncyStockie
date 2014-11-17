CurncyStockie
==============
Instructions:

Add it to your tomcat apache client
clone folders into /webpages/sms/WEB-INF

===============
We wanted to create a mobile enquiry centre, which provides the latest financial data to the users through SMS.

Since finding stock prices and currency exchange rates without internet might be hard on the move, we created a way for users to do it by sending an SMS message and they would receive a response back.

We get live financial feed data from Bloomberg API, and receive and send SMS through Twilio. We used Java for the logic and the server.

This program was intended for businessmen who are always on the move and not always have access to the internet; and people interested in following their stock prices.

Examples of use:
"&lt;stock name&gt; stocks"
- GOOG stocks
=&gt; GOOG's current price is USD 544.4

"&lt;currency from&gt;&lt;currency to&gt; currency"
- XAUGBP currency
=&gt; The exchange rate XAU -&gt; GBP is 758.58

"&lt;amount&gt; &lt;currency from&gt;&lt;currency to&gt; currency"
- 13.37 XAUGBP
=&gt; 13.37 XAU = 1042.21 GBP. The exchange rate XAU -&gt; GBP is 758.58
===================================

Created:
By 
  Dainis Gorbunovs: https://github.com/hackath
& Sami Alabed: https://github.com/SamiAlabed

During WarwickHACK in 15th November 2014.
