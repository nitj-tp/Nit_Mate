# Nit_Mate

NitMate is a college information management system which includes Attendance updates, Placements management,Esteemed students (which helps to motivate the students and also these students represents the college excellence), complaints box, Lost Found item updates and Motivational Videos.


Attendance Management

This module is used to maintain the attendance of the subjects in a semester. In start of semester number of new subject can be added into the list. Each subject has data attached with calendar behind it. The data of this module is stired in the phone of user by SQLite.The calendar which opens after click on the subject will display the attendance as marked on the dates by the user. By tapping on particular date a drop down list appears from which user can select options- present, absent and holiday. And accordingly data is stored and the percentage of attendance and number of present and absent are displayed.
At any time the subject can be deleted by long pressing on that subject. At end of semester , user can delete all the subjects and make new list on start of next semester.


Placement Management

It contains placement updates of college .This is displayed in CardView and the updates are sent from     server regularly. Whenever any results come after recruitment pricess , the name of company , LPA and the name of students are updated from server.
For managing queries from server ,PHP is used . The data is fetched from server using Volley modules and that information is displayed to user in list of customised card view.


Complain Box

Common platform for lodging complains for all students and teachers to administration is required. Complain box provide an interface where  complains can be registered and if complains are valid then administrator (developer) can set the flag from server and it would be shown in grid view in the application.


Esteemed Students

The guidance from talented seniors is always required. The contact details, designation and achievements of the esteemed students are displayed in customized list view(containing image and text) using Recycler View. User can just tap the contact no. And it invokes the dial pad of phone. And on tapping email Id, user can compose a mail and for sending , it redirect to gmail.


Lost And Found

It provide interface for college students to provide details of the items lost by them or items found in college.The items along with all details and contact of person are received from user interface and then valid are displayed in CardView in application.


Motivational Video

The entertaining element of our application is that it provide selected funny and motivational videos. It contains youtube integration the video are shown directly in youtube by tapping thumbnail of the list.


You can download and install apk from Nit_Mate.apk file.
