package model;

public class Payment
{
    public String id;
    public String memberName;
    public String plan;
    public double amount;

    public Payment(String id, String memberName, String plan, double amount)
    {
        this.id = id;
        this.memberName = memberName;
        this.plan = plan;
        this.amount = amount;
    }
}
