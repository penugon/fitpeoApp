import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class FitpeoTest {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Lenovo\\Downloads\\chromedriver-win32 (1)\\chromedriver-win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://www.fitpeo.com/");

        driver.navigate().to("https://fitpeo.com/revenue-calculator");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0,400)");

        WebElement slider = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/div/span[1]"));

        Actions action = new Actions(driver);
        action.dragAndDropBy(slider,0, 80).perform();

        WebElement inputElement = driver.findElement(By.className("MuiInputBase-input"));
        String inputValue = inputElement.getAttribute("value");
        System.out.println(inputValue);

        JavascriptExecutor jss = (JavascriptExecutor) driver;
        jss.executeScript("arguments[0].value='';", inputElement);

        // Use JavaScript to set a new value
        jss.executeScript("arguments[0].value='560';", inputElement);

        String newValue = inputElement.getAttribute("value");
        System.out.println("Updated input value: " + newValue);

        String value = "560";

        if(newValue.equals(value)){
            System.out.println("value is Updated");
        }else{
            System.out.println("value is not Updated");
        }

        JavascriptExecutor scroll = (JavascriptExecutor) driver;

        scroll.executeScript("window.scrollBy(0,500)");

        // Example CPT codes to select
        String[] cptCodes = {"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
        double totalReimbursement = 0.0;


        for (String code : cptCodes) {
            try {
                // Locate the checkbox by its associated class names (adjust the XPath as per the actual HTML structure)
                WebElement checkbox = driver.findElement(By.xpath("//div[@class='MuiBox-root css-4o8pys']//p[@class='MuiTypography-root MuiTypography-body1 inter css-1s3unkt' and contains(text(), '" + code + "')]/following::input[@type='checkbox'][1]"));

                if (!checkbox.isSelected()) {
                    checkbox.click();
                }

                JavascriptExecutor scrollup = (JavascriptExecutor) driver;

                scrollup.executeScript("window.scrollBy(0,-1400)");

                WebElement reimbursementElement = driver.findElement(By.xpath("//p[contains(text(), '" + code + "')]/following::span[contains(@class, 'css-1s3unkt')][1]"));
                String reimbursementText = reimbursementElement.getText().trim();
                double reimbursementValue = Double.parseDouble(reimbursementText);
                totalReimbursement += reimbursementValue;


            } catch (Exception e) {
                System.out.println("Checkbox or reimbursement value for " + code + " not found.");

            }
        }

        String expectedValue = "$134595";

        WebElement header = driver.findElement(By.xpath("//*[contains(text(), 'Total Recurring Reimbursement for all Patients Per Month:')]"));
        String headerText = header.getText().trim();
        if (headerText.contains(expectedValue)) {
            System.out.println("Header verification passed: " + headerText);
        } else {
            System.out.println("Header verification failed. Expected: " + expectedValue + " but found: " + headerText);
        }

        driver.close();





    }





}
