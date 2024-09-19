import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WordPressAutomationTest {
    private WebDriver driver;
    private Dotenv dotenv;

    @BeforeEach
    public void setUp() {
        dotenv = Dotenv.load();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get(dotenv.get("WORDPRESS_URL"));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testWordPressDarkMode() {
        try {
            login();
            System.out.println("Login successful.");
        } catch (Exception e) {
            System.out.println("Login failed.");
        }

        try {
            if (!isPluginActive("WP Dark Mode")) {
                installAndActivatePlugin("WP Dark Mode");
            }
            System.out.println("Plugin activation successful.");
        } catch (Exception e) {
            System.out.println("Plugin activation failed.");
        }

        try {
            enableAdminDashboardDarkMode();
            System.out.println("Admin Dashboard Dark Mode enabled.");
        } catch (Exception e) {
            System.out.println("Enabling Admin Dashboard Dark Mode failed: " + e.getMessage());
        }

        try {
            validateAdminDashboardDarkMode();
            System.out.println("Validation of Admin Dashboard Dark Mode successful.");
        } catch (Exception e) {
            System.out.println("Validation of Admin Dashboard Dark Mode failed.");
        }

        try {
            customizeSwitchStyle();
            System.out.println("Customizing Switch Style successful.");
        } catch (Exception e) {
            System.out.println("Customizing Switch Style failed.");
        }

        try {
            CustomizeSwitchSize();
            System.out.println("Customizing Switch Size successful.");
        } catch (Exception e) {
            System.out.println("Customizing Switch Size failed");
        }

        try {
            CustomizeSwitchPosition();
            System.out.println("Customizing Switch Position successful.");
        } catch (Exception e) {
            System.out.println("Customizing Switch Position failed");
        }

        try {
            disableKeyboardShortcut();
            System.out.println("Disabling Keyboard Shortcut successful.");
        } catch (Exception e) {
            System.out.println("Disabling Keyboard Shortcut failed");
        }

        try {
            enablePageTransitionAnimation();
            System.out.println("Enabling Page Transition Animation successful.");
        } catch (Exception e) {
            System.out.println("Enabling Page Transition Animation failed");
        }

        try {
            validateFrontEndDarkMode();
            System.out.println("Validation of Front End Dark Mode successful.");
        } catch (Exception e) {
            System.out.println("Validation of Front End Dark Mode failed.");
        }
    }

    private void login() {
        WebElement usernameField = driver.findElement(By.xpath("//input[@id='user_login']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@id='user_pass']"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='wp-submit']"));

        usernameField.sendKeys(dotenv.get("WORDPRESS_USERNAME"));
        passwordField.sendKeys(dotenv.get("WORDPRESS_PASSWORD"));
        loginButton.click();
    }

    private boolean isPluginActive(String pluginName) {
        WebElement pluginsMenu = driver.findElement(By.xpath("//li[@id='menu-plugins']/a"));
        pluginsMenu.click();
        try {
            WebElement WPDarkPlugin = driver.findElement(By.xpath("//tr[contains(@class, 'active') and .//strong[contains(text(), '" + pluginName + "')]]"));
            return WPDarkPlugin != null;
        } catch (Exception e) {
            return false;
        }
    }

    private void installAndActivatePlugin(String pluginName) {
        WebElement addNewButton = driver.findElement(By.xpath("//a[contains(@class, 'page-title-action')]"));
        addNewButton.click();
        WebElement searchPluginField = driver.findElement(By.xpath("//input[contains(@id, 'search-plugins')]"));
        searchPluginField.sendKeys(pluginName);
        WebElement InstallNowButton = driver.findElement(By.xpath("//a[@data-slug='wp-dark-mode']"));
        InstallNowButton.click();
        WebElement activatePluginButton = driver.findElement(By.xpath("//a[@class='button activate-now button-primary']"));
        activatePluginButton.click();
    }

    private void enableAdminDashboardDarkMode() {
        WebElement pluginsMenu = driver.findElement(By.id("menu-plugins"));
        pluginsMenu.click();
        WebElement WPDarkModeSettings = driver.findElement(By.xpath("//span[@class='0']/a"));
        WPDarkModeSettings.click();
        WebElement AdminPanelDarkMode = driver.findElement(By.xpath("//*[@class='nav-item-child focus:outline-none inactive']"));
        AdminPanelDarkMode.click();
        WebElement EnableDarkMode = driver.findElement(By.xpath("//*[text()='Enable Admin Dashboard Dark Mode']"));
        EnableDarkMode.click();
        WebElement SaveChanges = driver.findElement(By.xpath("//div[@class='save-buttons z-[10]']/button[2]"));
        SaveChanges.click();

    }

    private void validateAdminDashboardDarkMode() {
            WebElement AdminDashboard = driver.findElement(By.xpath("//li[@id='menu-dashboard']/a"));
            AdminDashboard.click();
            WebElement body = driver.findElement(By.tagName("html"));
            String bodyClass = body.getAttribute("class");
            Assertions.assertTrue(!bodyClass.contains("wp-dark-mode-active"), "Admin Dashboard Dark Mode is not enabled");
    }

    private void customizeSwitchStyle() {
        WebElement pluginsMenu = driver.findElement(By.id("menu-plugins"));
        pluginsMenu.click();
        WebElement WPDarkModeSettings = driver.findElement(By.xpath("//span[@class='0']/a"));
        WPDarkModeSettings.click();
        WebElement Customization = driver.findElement(By.xpath("//*[text()='Customization']"));
        Customization.click();
        WebElement SwitchSettings = driver.findElement(By.xpath("//*[text()='Switch Settings']"));
        SwitchSettings.click();
        WebElement SelectSwitch = driver.findElement(By.xpath("//div[@class='flex flex-wrap gap-3']/div[3]"));
        SelectSwitch.click();
        WebElement SaveChanges = driver.findElement(By.xpath("//div[@class='save-buttons z-[10]']/button[2]"));
        SaveChanges.click();
    }

    private void CustomizeSwitchSize(){
        WebElement pluginsMenu = driver.findElement(By.id("menu-plugins"));
        pluginsMenu.click();
        WebElement WPDarkModeSettings = driver.findElement(By.xpath("//span[@class='0']/a"));
        WPDarkModeSettings.click();
        WebElement Customization = driver.findElement(By.xpath("//*[text()='Customization']"));
        Customization.click();
        WebElement SwitchSettings = driver.findElement(By.xpath("//*[text()='Switch Settings']"));
        SwitchSettings.click();
        WebElement FloatingSwitchCustom = driver.findElement(By.xpath("//div[@class='flex flex-col w-fit gap-6']/div/div[2]/div[6]"));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(FloatingSwitchCustom)).click();
        WebElement FloatingSwitchCustomSize = driver.findElement((By.xpath("//div[@data-v-5ca4e721][2]/input")));
        FloatingSwitchCustomSize.clear();
        FloatingSwitchCustomSize.sendKeys("220");
        WebElement SaveChanges = driver.findElement(By.xpath("//div[@class='save-buttons z-[10]']/button[2]"));
        SaveChanges.click();
    }

    private void CustomizeSwitchPosition(){
        WebElement pluginsMenu = driver.findElement(By.id("menu-plugins"));
        pluginsMenu.click();
        WebElement WPDarkModeSettings = driver.findElement(By.xpath("//span[@class='0']/a"));
        WPDarkModeSettings.click();
        WebElement Customization = driver.findElement(By.xpath("//*[text()='Customization']"));
        Customization.click();
        WebElement SwitchSettings = driver.findElement(By.xpath("//*[text()='Switch Settings']"));
        SwitchSettings.click();
        WebElement SwitchPositionLeft = driver.findElement(By.xpath("//*[text()='Left']"));
        SwitchPositionLeft.click();
        WebElement SaveChanges = driver.findElement(By.xpath("//div[@class='save-buttons z-[10]']/button[2]"));
        SaveChanges.click();
    }

    private void disableKeyboardShortcut() {
        WebElement pluginsMenu = driver.findElement(By.id("menu-plugins"));
        pluginsMenu.click();
        WebElement WPDarkModeSettings = driver.findElement(By.xpath("//span[@class='0']/a"));
        WPDarkModeSettings.click();
        WebElement Advanced = driver.findElement(By.xpath("//*[@id=\"wp-dark-mode-admin\"]/div/div/div/div[1]/div[2]/div[3]/div/div"));
        Advanced.click();
        WebElement Accessibility = driver.findElement(By.xpath("//a[text()='Accessibility']"));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(Accessibility)).click();
        WebElement KeyboardShortcuts = driver.findElement(By.xpath("//*[text()='Keyboard Shortcut']"));
        KeyboardShortcuts.click();
        WebElement SaveChanges = driver.findElement(By.xpath("//div[@class='save-buttons z-[10]']/button[2]"));
        SaveChanges.click();
    }

    private void enablePageTransitionAnimation() {
        WebElement pluginsMenu = driver.findElement(By.id("menu-plugins"));
        pluginsMenu.click();
        WebElement WPDarkModeSettings = driver.findElement(By.xpath("//span[@class='0']/a"));
        WPDarkModeSettings.click();
        WebElement Customization = driver.findElement(By.xpath("//*[text()='Customization']"));
        Customization.click();
        WebElement SiteAnimation = driver.findElement(By.xpath("//a[text()='Site Animation']"));
        SiteAnimation.click();
        WebElement EnableSiteAnimation = driver.findElement(By.xpath("//*[text()='Enable Page Transition Animation']"));
        EnableSiteAnimation.click();
        WebElement SelectAnimation = driver.findElement(By.xpath("//*[text()='Pulse']"));
        SelectAnimation.click();
        WebElement SaveChanges = driver.findElement(By.xpath("//div[@class='save-buttons z-[10]']/button[2]"));
        SaveChanges.click();
    }

    private void validateFrontEndDarkMode() {
        WebElement viewSite = driver.findElement(By.xpath("//li[@id='wp-admin-bar-site-name']/a"));
        viewSite.click();
        WebElement body = driver.findElement(By.tagName("html"));
        String bodyClass = body.getAttribute("class");
        Assertions.assertTrue(bodyClass.contains("wp-dark-mode-active"), "Front End Dark Mode is not enabled");
    }
}