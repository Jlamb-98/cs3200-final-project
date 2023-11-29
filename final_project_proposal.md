# CS3200 Final Project Proposal

## General Overview

I will create a workout training planner app.
This app can be used by any group (friends, coworkers, sports team) that wants to organize a training plan for an event.
As an example, I am planning to run a half marathon next year with a few of my siblings.
My sister has a 12 week training program that we will start in March.
We all live far apart, so frequent training together isn't practical.
She will probably send us a link to a training program online, and we'll check it each week to see what workouts are planned for that week.

This is where my app would come in.
Since we all live far apart, it's hard to know when everyone else works out, motivate each other, and stick with the plan.
My app will allow the group organizer (in my example, my sister) to create a training program in the app and invite others to the group.
In the app, everyone will see today's workout, who has completed it, and the schedule for each day leading up to the event.
The app will have a commenting/chat feature to motivate each other and comment on workouts.
This will help keep everyone in the group on track, in shape, and prepared for whatever event they are training for.
It will encourage the group to stay in touch, do their workouts, and know how long they have till their event.
This is similar to social exercise tracking apps, like Strava or Apple's Activity app, but my app will give people the flexibility to organize their workouts how they want and help a group plan for an event, rather than just post workouts.

## Feature List

In a training plan group there will be two types of users: organizers and members.
Organizers create the training plan and invite members to the group.
Members are part of an organizer's group.
Each will have different permissions and possibly different screens/actions.
Users have limited access, but can become an organizer or member.
Members can do everything a user can do, plus access their group's training plan.
Organizers can do everything a member can do, plus make changes to their training plan.
In a full app, a user could be a member of one group and an organizer of another, but for my project I will probably only have time to allow a user to be in one group.

### Must Have Features

* As a user, I should be able to log in.
* As a user, I should be able to join a training plan.
* As a member, I should be able to view each day's workout.
* As a member, I should be able to check off completion of today's or a previous day's workout.
* As a member, I should be able to see who else has completed each workout.
* As an organizer, I should be able to create a training plan.
* As an organizer, I should be able to invite others to join my training plan.
* As an organizer, I should be able to edit/update my training plan.

### Nice to Have Features

* As a member, I could add a profile picture.
* As a member, I could set a daily workout reminder notification.
* As a member, I could choose to get a notification when another member completes a workout.
* As a member, I could access a calendar to display the workout for a specific date.
* As a member, I could see how many days until the event.
* As a member, I could comment on workouts.
* As an organizer, I could send announcements to the group.
* As a user, I could join/create multiple training plans.

## Technical Challenges

Much of my application depends upon dates.
I'll need to check the date each time the user logs in so that today's workout is displayed first.
Each workout is associated with a specific date, so I'll need to save and access that information efficiently.
Adding a workout to each date might take a while, especially for longer training plans.
I'll want to simplify how the organizer enters workouts.

If the user wants to check workouts for a different day, I need a good way for them to access it.
A simple way would be to swipe or scroll to display more days.
A more satisfying option would be to have a calendar that is displayed, so they have an overall view of the training plan and can tap on a specific date.
This would take some research and designing in how to make the calendar look good as well as functional.

Another thing I will need to learn to do is how to share data between users.
The members all need access to the training plan that the organizer made.
I'll need to look more into Firebase and how to share information between accounts.
The same goes for any chat, commenting, and announcements if I implement those.

I will also need to find out how to send an SMS text or email on behalf of the organizer when they invite someone to the group.
That message will need a link of some sort to the organizer's group.

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
   * I will pick some new colors, probably some shades of blue or green.
   * I will also have a top app bar, and possibly a bottom app bar, if needed.