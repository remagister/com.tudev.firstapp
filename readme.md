#Android Task (contacts list)

### Days elapsed: __14__ (initial commit).
Current branch - **'mvp'**

* [x] Part 1 - ListView and Activities.
* [x] Part 2 - SQLite and activity interaction.
* [x] Part 2.1 - Moved to ubuntu OS.
* [x] Part 2.2 - Data Layer remastered.
* [x] Part 2.3 - MVP injected (not tested/debugged).
* [x] Part 2.4 - MVP debugged.
* [x] Part 3 - List multiselection (with elements deletion).
* [x] Part 3.1 - MVP improved, base classes added. List multiselection debug and state saving introduced. 
* [ ] Image caching, storing and processing. 

## Known issues:

1. Main list does not update properly on main activity resume.
2. PAUSE GC is thrown all the time.
3. ~~Main list multiple choice mode does not work :<~~
4. ~~Main list can be multiselected, but cannot return to normal mode.~~
5. ContactDAO is closed in MainActivity/onDestroy -> access to a closed file exception.
