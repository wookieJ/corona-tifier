# 🦠 corona-tifier
[![CircleCI](https://img.shields.io/circleci/build/gh/wookieJ/corona-tifier?style=plastic)](https://app.circleci.com/pipelines/github/wookieJ/corona-tifier)
[![codecov](https://codecov.io/gh/wookieJ/corona-tifier/branch/master/graph/badge.svg)](https://codecov.io/gh/wookieJ/corona-tifier)

Create your own facebook messenger 💬 assistant which understand polish 🇵🇱  language.

<p align="center">
 <img width="400" src="../master/screenshots/layout.png">
</p>

## How to start

### Setup Facebook Page <img width="45" src="../master/screenshots/facebook.png">
Go to https://developers.facebook.com/ and sign with your Facebook credentials.
Click **Get Started** button and follow instructions to **Add a New App**.

After your app is created, go to **Dashboard** tab on the menu left. Find messenger and click **Set Up**.
Firstly you have to *"Create a new page"*. 

<p align="center">
 <img width="800" src="../master/screenshots/createPage.png">
</p>

This will take you to facebook.com where you have to
create your facebook page. Choose *Community or public figure*. 

Once you have your page created go back to [Facebook Developer Portal](https://developers.facebook.com/) and refresh page.
You should find **Token Generation** module. Select your page and generate your **page access token**.

<p align="center">
 <img width="800" src="../master/screenshots/token.png">
</p>

### Configure Dialogflow <img width="45" src="../master/screenshots/dialogflow.png">

Go to https://dialogflow.com/ and sign in with your google credentials. Click on **Go to console**
to create your first agent. Click **Create Agent** and fill in the details. Give your Agent a name. 
It can be the same name as your Facebook Page. 

When your agent is created, it will automatically add two Intents. Click on **Default Welcome Intent** to edit it

<p align="center">
 <img width="800" src="../master/screenshots/intent.png">
</p>

In the Events section, add the event **FACEBOOK_WELCOME**. This enables your bot to recognize user when he 
interacts with the bot for the first time.

<p align="center">
 <img width="800" src="../master/screenshots/fbWelcome.png">
</p>

Scroll down the page and delete the default responses by clicking the trash icon, as we will be responding to 
the user from our code.

Scroll down further and enable **Fulfillment**. This makes sure that all requests are routed through our chatbot function

<p align="center">
 <img width="800" src="../master/screenshots/fulfillment.png">
</p>

Next, we need to connect the Facebook Messenger to Dialogflow. Click on **Integrations**.

Turn on Messenger Integration. This will open a modal window. You need to enter two things here. 
First is the Verify Token which can be any text and second is the Page Access Token.

Switch to the [Facebook Developer Portal](https://developers.facebook.com/) and copy the Page Access Token.
Paste the Page Access Token in [Dialogflow](https://dialogflow.com/) modal window and enter your Verify Token. 
Click on **START button** to start the integration. Now click on the clipboard icon to copy the Callback URL.

Now switch to [Facebook Developer Portal](https://developers.facebook.com/). It’s time to setup the Webhooks integration. 
Select **Setup Webhooks** found below the Token Generation section. Paste the URL you copied into the Callback URL 
field and enter your Verify Token you specified in the previous step. Tick all Subscription Fields.

Click on **Verify and Save** and if you’ve setup everything correctly, you should see a green tick with Complete. 
Now select your page name from the dropdown and click on Subscribe.

There are a couple more things that you need to complete the setup. 
Go to **Settings > Basic** and add a **Privacy Policy URL** and choose a Category for your app. 
The Privacy Policy URL can be any valid URL (e.g. https://google.com/).

Save changes and Toggle the status button On to make your app go live.

<p align="center">
 <img width="500" src="../master/screenshots/live.png">
</p>

### Setup your App <img width="25" src="../master/screenshots/kotlin.png">

Deploy your app (e.g. https://heroku.com). Add your facebook **Page Access Token** to your server environment variable.

```bash
export ACCESS_TOKEN="<your_page_access_token>"
```

▶️ Run your app

Now we should point our app in [Dialogflow](https://dialogflow.com/) setup. 
Switch to [Dialogflow](https://dialogflow.com/) and click on Fulfillment and turn on the Webhook.
 
Append the copied URL with `/webhook` path and click on **SAVE** at the bottom of the page. 
Your webhook URL should look like:

```
https://your-app-host/webhook
```

We are almost done. All we need to do now, is to enable our bot on the [Facebook Developer Portal](https://developers.facebook.com/). 
Go to your Facebook page and click on Add a Button. Choose Send Message, complete the details and save.

### **Congratulations!** 🎉 🎉 🎉 You have a personal chatbot now 🤖
