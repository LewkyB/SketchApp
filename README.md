# SketchApp

SketchApp is an android application that lets you browse Reddit for cute image inspiration then you can use the canvas tool to create your own cute animal sketches. When you submit your sketch, it goes through a tensorflow model that was trained with google doodles to identify which animal was drawn. After pushing the upload button it will show which animal it thinks you drew. That newly created user image is available for viewing and discussion via the Sketch Browser. The Reddit browser uses the JRAW API wrapper. For the database, it uses Firebase for users, comments, and storing images/urls. Images are shown in a recycler view using Glide. There is one main activity that serves up the different fragments. 

<p align="center">
    <img width="250" alt="login screen" src="https://i.imgur.com/93DWAoQ.jpg">
    <img width="250" alt="create your own image for the model to guess" src="https://i.imgur.com/IIpOGy1.jpg">
    <img width="250" alt="images created by user saved on profile" src="https://i.imgur.com/ka6je3Y.jpg">

</p>

Register and login, then you can start adding your own sketches to the database with the canvas tool. User created images get posted to a public browser and to their own profile page.

<p align="center">
    <img width="250" alt="all user created images get displayed on the sketch browser" src="https://i.imgur.com/uZZHw2l.jpg">
    <img width="250" alt="comment on other user's sketches" src="https://i.imgur.com/P6C6F7X.jpg">
</p>

Views of the Sketch Browser where you can see all the user created images. You can also click images and leave comments.

<p align="center">
    <img width="250" alt="comment on image from Reddit" src="https://i.imgur.com/0s8bKXx.jpg">
    <img width="250" alt="reddit browser view" src="https://i.imgur.com/LJun09b.jpg">
</p>

Reddit Browser pulls in cute images of animals for inspiration. You can also select images and leave comments. 


## TODO ##

### general
- [ ] splash screen
- [ ] settings page - theme choice
- [ ] dependency injection w/ dagger 2
- [ ] replace hide fragment buttons w/ refresh method

### User Management
- [ ] post management
- [ ] admin users for post management
- [ ] reporting system

### browsers
- [ ] sort by date/score/controversial
- [ ] redditbrowser - sort by subreddit
- [ ] drawingbrowser - sort by type
= [ ] upvote/downvote
- [ ] redditbrowser - implement endless scrolling (using JRAW to pull more reddit images)

### Database
- [ ] how many urls stored is too many?

### comments
- [ ] upvote/downvote

### login
- [ ] improved login - banner and resize on keyboard
- [ ] friends/followers

### social
- [ ] friends/followers

### Complete
- [x] reddit browser
- [x] login/registration fragments
- [x] implement firebase
- [x] add ML files
- [x] blow up view when selecting RecyclerView item
- [x] comment system using firebase
- [x] show comments in recyclerview selection
- [x] implement canvas
- [x] save canvas as bitmap
- [x] add doodle browser to bottom navigation bar
- [x] RecyclerView for showing doodles saved on firebase database
- [x] profile page

## Sources ##
* https://github.com/mattbdean/JRAW
* https://github.com/bumptech/glide
* https://developer.android.com/docs
