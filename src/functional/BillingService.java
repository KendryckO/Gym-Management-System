package functional;

import data.DataStore;
import model.Member;
import model.Payment;

public class BillingService
{
    public static double planPrice(String plan)
    {
        if (plan == null)
        {
            return 0;
        }
        if (plan.startsWith("basic"))
        {
            return 20;
        }
        if (plan.startsWith("standard"))
        {
            return 30;
        }
        if (plan.startsWith("premium"))
        {
            return 40;
        }
        return 0;
    }

    public static Payment recordPayment(String memberName, double amount)
    {
        Member member = MemberService.findByName(memberName);
        if (member == null || amount <= 0)
        {
            return null;
        }

        String id = "P" + (1001 + DataStore.payments.size());
        Payment payment = new Payment(id, member.name, member.plan, amount);
        DataStore.payments.add(payment);
        DataStore.save();
        return payment;
    }

    public static int paymentCount()
    {
        return DataStore.payments.size();
    }

    public static double totalRevenue()
    {
        double total = 0;
        for (Payment p : DataStore.payments)
        {
            total = total + p.amount;
        }
        return total;
    }
}
