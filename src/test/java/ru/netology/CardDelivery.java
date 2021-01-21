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
                .shouldHave(text("Встреча успешно забронирована на"));
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
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestNotCorrectPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+7987654321");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone] .input_invalid .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldTestNotCorrectData() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }
    @Test
    void shouldTestNotCorrectName() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Victoria Patrina");
        $("[data-test-id='phone'] input").setValue("+7987654321");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldTestNotAgreement() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement] .input_invalid .checkbox__text")
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestEmptyName() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name] .input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldTestEmptyPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone] .input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldTestEmptyData() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);


        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(text("Неверно введена дата"));
    }
    @Test
    void shouldTestEmptyForm() {

        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldTestEmptyCity() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);

        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Виктория Патрина");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city] .input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }
}
