package test;

import data.DataStore;
import functional.BillingService;
import functional.MemberService;
import middleware.AuthService;
import middleware.InputValidator;
import model.Member;
import model.Payment;

// headless functional tests for the core logic (run: java test.FunctionalTests)
public class FunctionalTests
{
    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args)
    {
        DataStore.folder = "testdata";
        DataStore.clear();

        check("TC-01 login accepted with valid credentials (admin/admin123)",
                AuthService.login("admin", "admin123") && AuthService.isLoggedIn());

        AuthService.logout();
        check("TC-02 logout clears the session",
                !AuthService.isLoggedIn() && AuthService.getCurrentUser() == null);

        check("TC-03 login rejected with wrong password",
                !AuthService.login("admin", "wrongpass"));

        check("TC-04 login rejected for unknown user",
                !AuthService.login("nobody", "admin123"));

        check("TC-05 email validation accepts valid and rejects invalid format",
                InputValidator.isValidEmail("kb@mail.com") && !InputValidator.isValidEmail("kbmail.com"));

        check("TC-06 phone validation accepts digits only, 7 to 15 long",
                InputValidator.isValidPhone("0123456789") && !InputValidator.isValidPhone("12ab56") && !InputValidator.isValidPhone("123"));

        Member m = MemberService.registerMember("test user", "test@mail.com", "0123456789", "basic - $20");
        check("TC-07 valid registration creates member with auto id",
                m != null && m.id == 1001 && MemberService.memberCount() == 1);

        check("TC-08 duplicate email is rejected",
                MemberService.registerMember("someone else", "test@mail.com", "0999999999", "basic - $20") == null
                && MemberService.memberCount() == 1);

        check("TC-09 plan prices map correctly (20/30/40)",
                BillingService.planPrice("basic - $20") == 20
                && BillingService.planPrice("standard - $30") == 30
                && BillingService.planPrice("premium - $40") == 40);

        Payment p = BillingService.recordPayment("test user", 20);
        check("TC-10 payment recorded with receipt number and revenue updated",
                p != null && p.id.equals("P1001") && BillingService.totalRevenue() == 20 && BillingService.paymentCount() == 1);

        check("TC-11 payment rejected for unregistered member",
                BillingService.recordPayment("ghost person", 20) == null && BillingService.paymentCount() == 1);

        check("TC-12 amount validation rejects zero, negative and text",
                !InputValidator.isValidAmount("0") && !InputValidator.isValidAmount("-5")
                && !InputValidator.isValidAmount("abc") && InputValidator.isValidAmount("20"));

        DataStore.members.clear();
        DataStore.payments.clear();
        DataStore.load();
        check("TC-13 members and payments persist to file and reload",
                MemberService.memberCount() == 1 && BillingService.paymentCount() == 1
                && MemberService.findByName("test user") != null);

        System.out.println("----------------------------------------");
        System.out.println("total: " + (passed + failed) + "  passed: " + passed + "  failed: " + failed);
        if (failed > 0)
        {
            System.exit(1);
        }
    }

    static void check(String name, boolean ok)
    {
        if (ok)
        {
            passed = passed + 1;
            System.out.println("PASS  " + name);
        }
        else
        {
            failed = failed + 1;
            System.out.println("FAIL  " + name);
        }
    }
}
