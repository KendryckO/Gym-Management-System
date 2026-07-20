# Gym Management System — Deliverable 5 Prototype

Java Swing prototype for a gym management system, structured into the component
layers required by the assignment (UI, Middleware, Functional, Data).

## How to run

Requires JDK 17+ (tested on JDK 21). From the project root:

    javac -d out $(find src -name "*.java")
    java -cp out app.Main

Or open the project in IntelliJ and run `app.Main`.

Login credentials: `admin` / `admin123` (or `staff` / `staff123`)

## Complete user journey implemented

login -> dashboard -> add member -> select plan -> billing (prefilled) -> receipt -> dashboard -> logout

Members and payments are saved to text files in `gymdata/` and reload on restart.

## Project structure

    src/app         Main entry point (loads data, opens login)
    src/ui          UI components: LoginScreen, Dashboard, MemberRegistration, MembershipPlan, Billing
    src/middleware  Middleware components: AuthService (login/session), InputValidator (form rules)
    src/functional  Functional components: MemberService, BillingService (business logic)
    src/model       Data models: Member, Payment
    src/data        DataStore (file persistence: members.txt, payments.txt)
    src/test        FunctionalTests (logic), UiJourneyTest (full UI walk-through + screenshots)

## Running the tests

    java -cp out test.FunctionalTests     # 13 headless functional tests
    java -cp out test.UiJourneyTest       # 12 UI journey tests (needs a display)

`UiJourneyTest` clicks through the real screens, verifies each navigation step,
and saves screenshots to `screenshots/`.
