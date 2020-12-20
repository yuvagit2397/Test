package testCases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import preRequisite.MyTripSetup;
import singleton.PageObject;

public class MakeMyTrip extends MyTripSetup {
	//public static WebDriver driver;
	
	List<Integer> conPrice1=new ArrayList<Integer>();
	List<Integer> conPrice2=new ArrayList<Integer>();
	List<Integer> totalPrice=new ArrayList<Integer>();

	@Test(priority = 1)
	public void searchFlights() throws AWTException {
		try {
			WebElement loginPopup = PageObject.getHp().getLoginPopup();
			if (loginPopup.isDisplayed() == true) {
				PageObject.getHp().getPopupReturn().click();
			}
		} catch (Exception e) {
		}
		PageObject.getHp().getFromCity().click();
		PageObject.getHp().getFromCityBox().sendKeys("Delhi");
		PageObject.getHp().getToCity().click();
		PageObject.getHp().getToCityBox().sendKeys("Bangalore");
		PageObject.getHp().getDeparture().click();
		PageObject.getHp().gettodayDate().click();
		PageObject.getHp().getReturnArea().click();
		PageObject.getHp().getreturnDate().click();
		PageObject.getHp().getSearchBtn().click();

		/*List<WebElement> depatureFlights = PageObject.getHp().getDepatureFlights();
		System.out.println("Total no of Depature Filghts " + depatureFlights.size());
		for (WebElement allDepFlights : depatureFlights) {
			System.out.println(allDepFlights.getText());
		}

		List<WebElement> returnFlights = PageObject.getHp().getReturnFlights();
		System.out.println("Total no of Return Filghts " + returnFlights.size());
		for (WebElement allRetFlights : returnFlights) {
			System.out.println(allRetFlights.getText());
		}*/
	} 

	@Test(priority = 2)
	public void nonStopAndOneStopFlights() throws InterruptedException {
		List<WebElement> nonStopCheckBox = PageObject.getNsOs().getNonStopCheckBox();
		nonStopCheckBox.get(1).click();
		Thread.sleep(5000);
		List<WebElement> nonStopDepFlights = PageObject.getNsOs().getNonStopDepFlights();
		System.out.println("Total NonStop Fligts " + nonStopDepFlights.size());
		for (WebElement nonStopDepFlight : nonStopDepFlights) {
			System.out.println(nonStopDepFlight.getText());
		}

		List<WebElement> nonStopRetFlights = PageObject.getNsOs().getNonStopRetFlights();
		System.out.println("Total no of NonStop Return Flights " + nonStopRetFlights.size());

		for (WebElement nonStopRetFlight : nonStopRetFlights) {
			System.out.println(nonStopRetFlight.getText());
		}
	}

	@Test(priority = 3, enabled=false)
	public void clickingRadioBtnOfFlights() throws InterruptedException {
		List<WebElement> depFlightRadio = PageObject.getFd().getdepFlightRadio();
		List<WebElement> depFlightName = PageObject.getFd().getdepFlightName();
		WebElement depCoName = PageObject.getFd().getContainerFlightName();
		
		System.out.println("Depature flight verification");
		for (int i = 0; i < depFlightRadio.size(); i++) {
			if (i<5) {
				WebElement radio = depFlightRadio.get(i);
				JavascriptExecutor js=(JavascriptExecutor) driver;
				radio.click();
				
				String text = depFlightName.get(i).getText();
				text=text.replace(" ", "");
				Thread.sleep(2000);
				
				String text1 = depCoName.getText();
				text1=text1.replace(" ", "");
				
				Assert.assertEquals(text, text1);
				System.out.println("actual--->"+text+" expected--->"+text1);
				
				
				js.executeScript("arguments[0].scrollIntoView();",radio);
				Thread.sleep(1000);
			}
		}
		
		List<WebElement> returnFlightRadio = PageObject.getFd().getReturnFlightRadio();
		List<WebElement> returnFlightName = PageObject.getFd().getReturnFlightName();
		WebElement returnCoName = PageObject.getFd().getContainerReturnFlightName();
		
		System.out.println("Return flights verification");
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-1300)");
		Thread.sleep(2000);
		
		for (int i = 0; i < returnFlightRadio.size(); i++) {
			if (i<10) {
				WebElement radio1 = returnFlightRadio.get(i);
				JavascriptExecutor js1=(JavascriptExecutor) driver;
				radio1.click();
				
				String text = returnFlightName.get(i).getText();
				text=text.replace(" ", "");
				Thread.sleep(3000);
				
				String text1 = returnCoName.getText();
				text1=text1.replace(" ", "");
				
				Assert.assertEquals(text, text1);
				System.out.println("actual--->"+text+" expected--->"+text1);
				js1.executeScript("arguments[0].scrollIntoView();",radio1);
				Thread.sleep(1000);

			}
			
		}
	}
	
	@Test(priority=4)
	public void verifyTotalPrice() throws InterruptedException {
		JavascriptExecutor j=(JavascriptExecutor) driver;
		j.executeScript("window.scrollBy(0,-1300)");
		Thread.sleep(2000);
		List<WebElement> depFlightRadio = PageObject.getFd().getdepFlightRadio();
		List<WebElement> returnFlightRadio = PageObject.getFd().getReturnFlightRadio();
		
		WebElement depConPrice = PageObject.getPp().getDepConPrice();
		WebElement retConPrice = PageObject.getPp().getRetConPrice();
		WebElement price = PageObject.getPp().getTotalPrice();
		
		for (int i = 0; i < 10; i++) {
			WebElement radio = depFlightRadio.get(i);
			JavascriptExecutor js=(JavascriptExecutor) driver;
			radio.click();
			Thread.sleep(2000);
			
			WebElement radio1 = returnFlightRadio.get(i);
			JavascriptExecutor js1=(JavascriptExecutor) driver;
			radio1.click();
			Thread.sleep(2000);
			js1.executeScript("arguments[0].scrollIntoView();",radio1);
			Thread.sleep(1000);
			
			String text1 = depConPrice.getText();
			text1=text1.replaceAll("[\\W]", "");
			conPrice1.add(Integer.parseInt(text1));
			String text2 = retConPrice.getText().replaceAll("[\\W]", "");
			conPrice2.add(Integer.parseInt(text2));
			String text3 = price.getText().replaceAll("[\\W]", "");
			totalPrice.add(Integer.parseInt(text3));
		}
		
		for (int i = 0; i < 10; i++) {
			/*System.out.print(" "+conPrice1.get(i));
			System.out.println(" "+conPrice2.get(i));
			System.out.println(" "+totalPrice.get(i));*/
			Integer amont1 = conPrice1.get(i);
			Integer amont2 = conPrice2.get(i);
			Integer t=amont1+amont2;
			
			Assert.assertEquals(totalPrice.get(i), t);
			System.out.println("total price verified-->departure price = "+amont1+" return price = "+
			amont2 +" total = "+t);

		}


	}

}
