package model;

public class Member
{
    public int id;
    public String name;
    public String email;
    public String phone;
    public String plan;

    public Member(int id, String name, String email, String phone, String plan)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.plan = plan;
    }
}
