package io.testomat.e2e_tests_light;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProjectPageTests {

    @Test
    public void firstTest() {
        open("https://app.testomat.io/");

        //login user
        $("#content-desktop #user_email").setValue("strange.viking@gmail.com");
        $("#content-desktop #user_password").setValue("kY!mAr!9MGNG@Mj");
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=\"commit\"]").click();
        $(".common-flash-success").shouldBe(visible);

        //search project
        $("#search").setValue(("manufacture light"));

        //select project
        $(byText("Manufacture light")).click();

        //wait for project is loaded
        $("h2").shouldHave(text("Manufacture light"));
    }

}
