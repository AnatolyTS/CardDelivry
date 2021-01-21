package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDelivery {
    @BeforeEach
    void setup () {
        open("http://localhost:9999/");
    }

    @Test
    void shouldCorrectForm() {
        $("[data-test-id='city'] input").setValue("Москва");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 11000);
        $("[data-test-id=notification] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на "+date));
    }

    @Test
    void shouldTestNotCorrectCity() {
        $("[data-test-id='city'] input").setValue("Лыткарино");

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Доставка в выбранный город недоступна "+date));
    }
}
