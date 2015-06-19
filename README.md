# GoogleTest

Two test classess:
 - GmailTest (log in to gmail/logoff from gmail)
 - DriveTest (Log in to gmail, change to Google Drive, add folder, remove folder)

 No dynamic browser support implemented yet.

GmailTest.java is hard-coded to run on Firefox
DriveTest.java is hard-coded to tun on Google Chrome.


Usage:
 1. git clone https://github.com/thenin/GoogleTest.git
 2. Edit testng.xml (at least provide correct path for ChromeDrover executable)
 3. mvn test
