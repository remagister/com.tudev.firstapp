#Android Task (contacts list)

### Days elapsed: 43 (from initial commit 27.07).
Last branch - **'dialog'**

* [x] Part 1 - ListView and Activities.
* [x] Part 2 - SQLite and activity interaction.
* [x] Part 2.1 - Moved to ubuntu OS.
* [x] Part 2.2 - Data Layer remastered.
* [x] Part 2.3 - MVP injected (not tested/debugged).
* [x] Part 2.4 - MVP debugged.
* [x] Part 3 - List multiselection (with elements deletion).
* [x] Part 3.1 - MVP improved, base classes added. List multiselection debug and state saving introduced. 
* [x] Part 3.2 - Listview stability improved.
* [x] Part 4 - Image loading stabilized, saving and restoring routines added.
* [x] Part 4.1 - Images & thumbs saving, caching and viewing established.
* [x] Part 5 - EventBus injection.
* [x] Part 6 - Custom calendar view.
* [ ] Part 7 - Themes support. Bigender contact.

## Known issues:

1. Main list does not update properly on main activity resume.
2. PAUSE GC is thrown all the time.
3. ~~Main list multiple choice mode does not work :<~~
4. ~~Main list can be multiselected, but cannot return to normal mode.~~
5. ~~ContactDAO is closed in MainActivity/onDestroy -> access to a closed file exception.~~
6. ~~When editing contact, previously loaded image icon may eventually disappear on orientation change.~~
7. ~~Thumbnails are not well aligned enough.~~
8. ~~Contact and edit contact screens' layout ruins in landscape mode.~~
9. Not an actual bug, but images stretching is harsh.
10. Empty contact image thumbnail is somehow replaced by an another random pic.

### Images

![Date picker dialog](https://github.com/remagister/com.tudev.firstapp/blob/master/img/scroll.gif)
