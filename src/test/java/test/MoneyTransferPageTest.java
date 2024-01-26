package test;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferPageTest {
    DashboardPage dashboardPage;

    CardInfo firstCardInfo;
    CardInfo secondCardInfo;

    @BeforeEach
    void setup() {
        var login = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = login.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.doValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance() {
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.doTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.findErrorMassage("Выполнена попытка перевода сунны, превышающей остаток на карте списания");
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondcard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondcard);
    }
}
//}
//    @Test
//    void shouldTransfer() {
//        open("http://localhost:9999/");
//        var authInfo = DataHelper.getAuthInfo();
//        $("[data-test-id='login'] input").setValue("vasya");
//        $("[data-test-id='password'] input").setValue("qwerty123");
//        $("button.button").click();
//        $(".input__control").setValue("12345");
//        $("span.button__text").click();
//        $("h1")
//                .shouldBe(Condition.visible, Duration.ofSeconds(2))
//                .shouldBe(Condition.exactText("Ваши карты"));
//
//
//    }
//}    @org.junit.jupiter.api.Test
//public void login() {
//    open("http://localhost:9999/");
//    $("[data-test-id='login'] input").setValue("vasya");
//    $("[data-test-id='password'] input").setValue("qwerty123");
//    $("button.button").click();
//    $(".input__control").setValue("12345");
//    $("span.button__text").click();
//    $("h1")
//            .shouldBe(Condition.visible, Duration.ofSeconds(2))
//            .shouldBe(Condition.exactText("Ваши карты"));
//
//}
//    @Test
//    public void transaction (){
//        open("http://localhost:9999/");
//        $("[data-test-id='login'] input").setValue("vasya");
//        $("[data-test-id='password'] input").setValue("qwerty123");
//        $("button.button").click();
//        $(".input__control").setValue("12345");
//        $("span.button__text").click();
//        $(".button__content").click();
//        $ ("[data-test-id='dashboard']")
//                .shouldBe(Condition.visible, Duration.ofSeconds(2))
//                .shouldBe(Condition.exactText("Ваши карты"));
//        $("[data-test-id='amount'] input").setValue("200");
//
//    };
//}
//
//        }
