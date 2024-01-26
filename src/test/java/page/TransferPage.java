package page;


import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement transferHead = $("[data-test-id='dashboard']");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorMassage = $("[data-test-id='error-notification']");
    private final SelenideElement cancel = $("[data-test-id='action-cancel']");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage doValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        doTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void doTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void findErrorMassage(String expectedText) {
        errorMassage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
