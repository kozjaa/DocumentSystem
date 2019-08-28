package pl.blueenergy.utils;

import pl.blueenergy.organization.User;

import java.lang.reflect.Field;

public class UserSalaryModifier {

    private final User user;

    public UserSalaryModifier(User user) {
        this.user = user;
    }

    public void changeSalary(double newSalaryValue){
        try {
            Field salaryField = user.getClass().getDeclaredField("salary");
            salaryField.setAccessible(true);
            salaryField.set(user, newSalaryValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
