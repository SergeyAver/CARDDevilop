package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MeetingDateChange {

    @Test
    public void shouldSuccessfulFormSubmission() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        FormInfo formInfo = DataGenerator.Registration.generateUser("Ru");
        $("[data-test-id=city] input").setValue(formInfo.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        String scheduledDate = DataGenerator.generateDate(4);
        $("[data-test-id=date] input").setValue(scheduledDate);
        $("[data-test-id=name] input").setValue(formInfo.getName());
        $("[data-test-id=phone] input").setValue(formInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").shouldHave(Condition.text("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Успешно! Встреча успешно запланирована на " + scheduledDate),
                        Duration.ofSeconds(15));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        String rescheduledDate = DataGenerator.generateDate(14);
        $("[data-test-id=date] input").setValue(rescheduledDate);
        $(".button").shouldHave(Condition.text("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Необходимо подтверждение" +
                                " У вас уже запланирована встреча на другую дату. Перепланировать?"),
                        Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .button").shouldHave(Condition.text("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Успешно! Встреча успешно запланирована на " + rescheduledDate),
                        Duration.ofSeconds(15));
    }

}
