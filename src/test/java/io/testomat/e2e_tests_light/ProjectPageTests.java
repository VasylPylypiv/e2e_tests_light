package io.testomat.e2e_tests_light;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.testomat.e2e_tests_light.utils.StringParsers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.testomat.e2e_tests_light.utils.StringParsers.parseIntegerFromString;

public class ProjectPageTests extends BaseTest {


    private static final Logger log = LoggerFactory.getLogger(ProjectPageTests.class);
    static String baseUrl = env.get("BASE_URL");
    static String username = env.get("USERNAME");
    static String password = env.get("PASSWORD");
    String targetProjectName = "Manufacture light";

    @BeforeAll
    static void openTestomatAndLogin() {
        open(baseUrl);

        loginUser(username, password);
    }

    @BeforeEach
    void openHomePage() {
        open(baseUrl);
    }


    @Test
    public void userCanFindProjectWithTests() {

        searchForProject(targetProjectName);

        selectProject(targetProjectName);

        waitForProjectPageIsLoaded(targetProjectName);
    }

    @Test
    public void anotherTest() {
        searchForProject(targetProjectName);

        SelenideElement targetProject = countOfProjectsShouldBeEqualTo(1).first();

        countOfTestCasesShouldBeEqualTo(targetProject, 0);

//        totalCountOfTestCasesGreaterThan (100);
    }


    @Test
    public void openSomeSettings() {


        searchForProject(targetProjectName);

        selectProject(targetProjectName);

        waitForProjectPageIsLoaded(targetProjectName);

        openProjectSettings();

        openJiraIntegrationPage();

        back();

        openProjectSettings();

        openEnvironmentsPage();

        $("h2").shouldHave(text("Environments"));

    }

    @Test
    public void workWithLabels() {

        searchForProject(targetProjectName);

        selectProject(targetProjectName);

        waitForProjectPageIsLoaded(targetProjectName);

        openProjectSettings();

        openLabelsAndFieldsPage();
        $("[list=\"tags\"]").setValue("SomeLabel").pressEnter();
        $(".ember-power-select-placeholder").click();
        $("[data-option-index=\"2\"]").click();
        $(byText("Save")).click();
        $(byText("SomeLabel")).shouldBe(visible);
        $(".md-icon-delete-outline").shouldBe(visible).click();
        switchTo().alert().accept();
    }


    @Test
    public void exampleAssertDouble() {
        var text = "15.4 coverage";
        Double actualDouble = StringParsers.parseDoubleFromString(text);

        Assertions.assertTrue(15.4 >= actualDouble);
    }

    @Test
    public void exampleParseInteger() {
        var text = "0 tests";
        Integer actual = parseIntegerFromString(text);

        Assertions.assertEquals(0, actual);
    }

    @Test
    public void exampleParseBoolean() {
        var text = "true";
        Boolean actual = Boolean.parseBoolean(text);

        Assertions.assertEquals(true, actual);
    }

    private void waitForProjectPageIsLoaded(String targetProjectName) {
        $("h2").shouldHave(text(targetProjectName));
        $(".first [href*='/readme']").shouldHave(text("Readme"));
    }

    private void selectProject(String targetProjectName) {
        $(byText(targetProjectName)).click();
    }

    private void searchForProject(String targetProjectName) {
        $("#search").setValue((targetProjectName));
    }

    public static void loginUser(String email, String password) {

        $("#content-desktop #user_email").setValue(email);
        $("#content-desktop #user_password").setValue(password);
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=\"commit\"]").click();
        $(".common-flash-success").shouldBe(visible);
    }

    private void totalCountOfTestCasesGreaterThan(int expectedTotalCount) {
        String totalProjects = $("#container kbd").getText();
        Integer actualCountOfTotalTests = parseIntegerFromString(totalProjects);
        Assertions.assertTrue(actualCountOfTotalTests > 100);
    }

    private void countOfTestCasesShouldBeEqualTo(SelenideElement targetProject, int expectedCount) {
        String countOfTests = targetProject.$("p").getText();
        Integer actualCountOfTests = parseIntegerFromString(countOfTests);
        Assertions.assertEquals(expectedCount, actualCountOfTests);
    }

    @NotNull
    private ElementsCollection countOfProjectsShouldBeEqualTo(int expectedSize) {
        return $$("#grid ul li").filter(visible).shouldHave(size(expectedSize));
    }

    private void openProjectSettings() {
        $(".mainnav-menu-body [aria-describedby=\"ember21-popper\"]").shouldBe(visible);
        sleep(50);
        $(".mainnav-menu-body [aria-describedby=\"ember21-popper\"]").click();
        $(".subnav-menu-header-first").shouldBe(visible).shouldHave(text("Settings"));
    }

    private void openJiraIntegrationPage() {
        $(".subnav-menu-list-wrapper [id=\"ember30\"]").click();
        $("h2").shouldHave(text("Jira Integration"));

    }

    private void openEnvironmentsPage() {
        $(".subnav-menu-list-wrapper [id=\"ember33\"]").click();
    }

    private void openLabelsAndFieldsPage() {
        $(".subnav-menu-list-wrapper [id=\"ember27\"]").click();
        $("h2").shouldHave(text("Labels"));
    }
}
