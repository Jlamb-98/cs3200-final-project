# Final Project Plan

## Features

In a training plan group there will be two types of users: organizers and members.
Organizers create the training plan and invite members to the group.
Members are part of an organizer's group.
Each will have different permissions and possibly different screens/actions.
Users have limited access, but can become an organizer or member.
Members can do everything a user can do, plus access their group's training plan.
Organizers can do everything a member can do, plus make changes to their training plan.
In a full app, a user could be a member of one group and an organizer of another, but for my project I will probably only have time to allow a user to be in one group.

### Must Have Features

* [x] As a user, I should be able to log in.
* [x] As a user, I should be able to join a training plan.
* [x] As a member, I should be able to view each day's workout.
* [ ] As a member, I should be able to check off completion of today's or a previous day's workout.
* [ ] As a member, I should be able to see who else has completed each workout.
* [ ] As an organizer, I should be able to create a training plan.
* [ ] As an organizer, I should be able to invite others to join my training plan.
* [ ] As an organizer, I should be able to edit/update my training plan.

### Nice to Have Features

* As a member, I could add a profile picture.
* As a member, I could set a daily workout reminder notification.
* As a member, I could choose to get a notification when another member completes a workout.
* As a member, I could access a calendar to display the workout for a specific date.
* As a member, I could see how many days until the event.
* As a member, I could comment on workouts.
* As a member, I could see other's stats on the workout (duration, speed, time).
* As an organizer, I could send announcements to the group.
* As a user, I could join/create multiple training plans.



## Requirements

1. I will use Firebase for my users to log in and store their training plan.
2. I will use a one-time purchase as a monetization strategy
   * The organizer will pay to have access to creating a training plan and inviting members.
   * If this turns out to be too complex, I will use ads.
3. I will use animation when the user is swiping or scrolling through dates.
   * If I use a calendar, the animation will occur when a date is selected.
4. I estimate this app will require at least 20 hours of work.
5. My app will be useful for groups of friends or athletes that want to follow a training plan together and keep each other motivated.
   * This will be especially useful for those groups that live far apart and cannot train together frequently.
6. My app will have a few different screens, the main ones being the training plan creation screens and screens for viewing workouts.
   * I will pick some new colors, probably some shades of blue or green and a **dark mode**.
   * I will also have a top app bar, and possibly a bottom app bar, if needed.



## Design

### Screens and Navigation

* Splash Screen
* Launch Screen
* Dashboard
* Create/Edit Training Plan
* Invite Members

#### Splash Screen

Same as in Assignment 4.
After a few seconds, directs to the Login screen or Dashboard, depending on if the user is logged in.
* [ ] Maybe create a cool logo for the Splash Screen.

#### Launch Screen

Same as in Assignment 4.
Screen shows **Login** and **Create Account** buttons.
**Login** button directs to **Login Screen** where user can input username and password.
After successful login, directs to **Dashboard Screen**.

**Create Account** button directs to **Account Creation Screen** where user can create an account.
Make sure to perform all necessary checks as done in Assignment 4.
After successful account creation, directs to **Dashboard Screen**.
Maybe add the logo to the Launch Screen.

#### Dashboard

This screen will be slightly different for a new user, member, and organizer.

* New User
  * A button to join a group, directs to screen to input group code.
  * A button to create a training plan, directs to payment screen.
  * Might want to make this a separate screen
* Member
  * Shows today's workout.
  * Can swipe through the next/previous days' workouts.
  * A button or checkbox to complete a workout.
  * Shows a list of other group members that have completed the workout.
  * Comments?
* Organizer
  * Same as Member
  * A button to edit a workout

If I have the time, I would love to add a calendar view!

#### Create/Edit Workout

An Organizer can edit workouts.

* Workout title
* Description
* Date
* What else???

#### Create Training Plan

The Organizer can input details about their training plan.

* How long it goes for
* How many workout days
* How many rest days
* What the event is
* Event date

#### Invite Members

The Organizer can invite friends to join the group.
Two ways I'm thinking of doing this:
1. Group has a unique code which can be entered on User's **Dashboard**.
2. Organizer sends invite via email/SMS with link which directs user to app.

Code seems an easier way to go.
Generate a random code when Organizer creates group, Organizer shares code, User enters code, app checks if code exists, then adds User to group.
I'd have to research email/SMS stuff and link generation if I wanted to go that route.

### Firebase

Firebase allows users to create accounts and login to the app.
It also allows storage of training plans and allows group Members to view information about training plans.

#### Account Setup/Login

Similar to Assignment 4.
Save user's email and password.
I think I will need a Users collection in Firestore.
This will allow me to store data about users: which training plan they are a part of, their role, user id.

#### Storing/Accessing Training Plan

Similar to Assignment 4.
Save and update a group's training plan.
Send training plan info to user upon login.

* Should I create 3 separate collections (Groups, TrainingPlan, and Workouts) or nest them inside each other?
* Collections:
  * Each Group is assigned an id which its TrainingPlan has saved and each TrainingPlan has an id which its Workouts have saved.
  * This would work very closely to how we learned to use Firebase, and how I think it should be used.
  * Each User would just add 10-100 Workouts to the collection? Is that an issue?
* Nesting:
  * Group has a variable `trainingPlan` that has TrainingPlan info, `trainingPlan` has List of Workouts.
  * Might be simpler to access variables, but nesting makes it a bit less robust.

### Monetization Strategy

#### Plan A: One-time Payment



#### Plan B: Ads

### Animation

#### Required: Swipe Between Dates

#### Extra Mile: Calendar View



## Implementation



## Testing??

Anything other than just running the app?
