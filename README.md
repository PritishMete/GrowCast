# 🌱 GrowCast — Gardening Companion for Smarter Plant Care

> An Android gardening application that combines plant knowledge, location-aware weather, reminders, user accounts and shopping touchpoints in one mobile experience.

## Overview

GrowCast was designed around a practical gardening problem: plant care information, weather context and recurring care tasks are usually scattered across different tools. The app brings those moments together so a user can discover a plant, understand its needs, check local conditions and remember the next action from one place.

## Product Idea

The product connects four everyday gardening needs:

- **Learn** — browse plant-specific information across flowers, fruits and vegetables.
- **Understand conditions** — use location and weather context to support care decisions.
- **Remember** — schedule reminders that can trigger outside the active app session.
- **Continue the journey** — use account/profile flows and a shop entry point as part of a broader gardening experience.

## Core Features

### Plant Knowledge
- Dedicated plant screens for multiple flowers, fruits and vegetables
- Category-based browsing
- Individual plant detail experiences

### Weather & Location
- Fine and coarse location support
- Weather-related networking layer
- Google Maps integration point
- Location-aware product behavior

### Care Reminders
- Android `AlarmReceiver`
- Notification permission support
- Vibration and wake-lock support
- Alarm audio asset for reminder behavior

### Accounts
- Login
- Signup
- Forgot-password flow
- User/profile screen

### Product Extensions
- Gardening shop route
- Notes / care-reference flow

## Engineering Depth

GrowCast is a native Android project rather than a static prototype. The codebase includes Android activities, location permissions, notification infrastructure, networking helpers, alarm handling and a structured collection of plant-specific screens.

```text
User
  │
  ├── Account / Profile
  ├── Plant Library
  │     ├── Flowers
  │     ├── Fruits
  │     └── Vegetables
  ├── Weather / Location
  ├── Notes / Reminders
  └── Shop
```

## Technology Stack

- Java
- Android SDK
- Gradle
- Google Maps integration
- Android Location APIs
- Android notifications / alarms
- Firebase integration

## Repository Structure

```text
GrowCast/
├── app/
│   ├── src/main/java/com/example/growcast/
│   ├── src/main/res/
│   └── src/main/assets/
├── build.gradle
├── settings.gradle
└── README.md
```

## Run Locally

1. Clone the repository:

```bash
git clone https://github.com/PritishMete/GrowCast.git
cd GrowCast
```

2. Open the project in Android Studio.
3. Configure required local API/Firebase values.
4. Sync Gradle.
5. Run on an Android emulator or device.

## Security Note

Do not commit production secrets, unrestricted API keys or private Firebase credentials. Restrict Maps/API keys by application/package and API scope.

## Portfolio Case Study

https://pritish-mete.onrender.com/projects/showcase/project.html?id=1

## Author

**Pritish Mete**

GitHub: https://github.com/PritishMete
