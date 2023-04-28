# shared_card
Project developers: Danil Trofimov, Mikhail Kuznetsov

The "Shared Card" application

Goal: to create an application in which a group of people (a family, a young couple, employees of one office/enterprise, a student group, etc.) will be able to track and make changes in real time on products and goods that need to be purchased by any deadline. This application will help to avoid duplicate purchases of the same goods, calculate the approximate cost of the receipt, check and adjust the current list of products and compare with the previous one.
Tasks:
1. Create a mobile application for android.
2. Make a server that will contain groups and not overlap with each other. The server must store information about groups so that they do not overlap with each other, but information within the same group can be updated once an hour and updated by a button and when entering the application.
3. Organize a group in which there will be users who did not intersect with each other and did not know about each other. A user can manage data (from a database) on an equal basis with other users in the same group:
• Enter the data of purchased goods (manually):
o Place of purchase (generalized, without exact address);
Selection by the selector.
o Product price;
Automatically:
o Time of purchase;
o Product name;
o Quantity of goods;
o Total purchase amount;
o Product category;
• Enter the data of the goods to be purchased:
o The name of the products;
o Quantity of goods;
Automatically:
o Request time;
o Total purchase amount;
o Product category;
• Tag data:
o Purchased \not purchased goods – the purchased goods are marked and lowered down the list until the end of the day, then goes to the archive.
o Urgent\long-term.
• Group products:
o By product category;
• Predict spending:
o For a specific product;
o By product category;
o Shopping;
o On the expenses of a specific user;
• View your purchase history.
• Shopping list for the next day:
o Sorting;
4. Organize the creation of a group based on the unique identifier of the group and the unique identifiers of users in the group (by analogy with a telegram).
5. Create an analogue of user and group registration, where a unique identifier of the client and group will be automatically issued. Create access by QR code:
• Name;
• Avatar (optional);
6. In the group we will show:
• Name + Avatar;
• Role in the group (creator and users);
• Add/remove a member to a group;
• Disband the group;
Initially, a local user account is created that will work with SQLite and not send a request to the server.
When creating a group, the user who created the group will be marked as an admin and all requests will be sent to the server.
