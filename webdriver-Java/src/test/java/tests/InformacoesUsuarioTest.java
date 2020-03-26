package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class InformacoesUsuarioTest {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){

        // Abrindo o Navegador
        System.setProperty("webdriver.chrome.driver", "/home/joaopgioio/Cursos/Udemy/TestesAutomatizados/Automacao-De-Testes-Com-Selenium-Webdriver-Em-Java/drivers/chromedriver");
        //System.setProperty("webdriver.gecko.driver", "/home/joaopgioio/Cursos/Udemy/TestesAutomatizados/Automacao-De-Testes-Com-Selenium-Webdriver-Em-Java/drivers/geckodriver");

        navegador = new ChromeDriver();
        //navegador = new FirefoxDriver();
        // Abrir a janela maximizada
        navegador.manage().window().maximize();
        // Definir o timeout - tempo de espera para carregar a página para um elemento ser encontrado
        navegador.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Navegando na pagina
        navegador.get("http://www.juliodelima.com.br/taskit");


        // Clicar no link que possui o texto "Sign in"
        //navegador.findElement(By.linkText() '*[@class="right hide-on-med-and-down"]//a[@data-target="signinbox"]');
        navegador.findElement(By.linkText("Sign in")).click();

        // Identificar o campo de formulario de login
        WebElement formularioSignin = navegador.findElement(By.id("signinbox"));

        // Digitar no campo com o nome "login" que está dentro do formulário do id "signinbox" o texto "joaopgioio"
        formularioSignin.findElement(By.name("login")).sendKeys("joaopgioio");

        // Digitar no campo com o nome "password" que está dentro do formulário do id "signinbox" o texto "200861"
        formularioSignin.findElement(By.name("password")).sendKeys("200861");

        // Clicar no link com o texto "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        // Clicar no link que possui a class "me"
        navegador.findElement(By.className("me")).click();
        //String textoNoElementoME = me.getText();
        //assertEquals( "Hi, Julio", textoNoElementoME);

        // Clicar no link que possui o texto "Hi, João Paulo Alves"
        //navegador.findElement(By.linkText("Hi, João Paulo Alves")).click();

        // Clicar no link que possui o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    // @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(){
        // Clicar no botao que possui o texto "+ Add more data"
        navegador.findElement(By.xpath("//div[@id='moredata']//button[@data-target='addmoredata']")).click();

        // Identificar a popup onde está o formulario do id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        // No combo de name "type" escolhe a opção "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText("Phone");
        //popupAddMoreData.findElement(By.xpath("//*[@value=\"phone\"]")).click();


        // No campo de name "contact" digitar "+5511931421337"
        popupAddMoreData.findElement(By.name("contact")).sendKeys("+5511931421337");

        // Clicar no link de text "SAVE" que está na popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        // Na mensagem de id "toast-container" validar que o texto é "Your contact has been added!"
        //navegador.findElement(By.id("toast-container"));
        WebElement  mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals( "Your contact has been added!", mensagem);
    }

    @Test
    public void removerUmContatoDeUmUsuario(){
        // Clicar no elemento pelo seu xpath  //span[text()="+5511931421337"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+5511931421337\"]/following-sibling::a")).click();

        // Confirmar a janela javascript
        Alert alert = navegador.switchTo().alert();
        alert.accept();

        // Validar que a mensagem "Rest in peace, dear phone!" apresentou
        WebElement  mensagemPopRemover = navegador.findElement(By.id("toast-container"));
        String mensagemRemover = mensagemPopRemover.getText();
        assertEquals( "Rest in peace, dear phone!", mensagemRemover);

        String screenshotArquivo = "/home/joaopgioio/Cursos/Udemy/TestesAutomatizados/Automacao-De-Testes-Com-Selenium-Webdriver-Em-Java/tests-report/taskit/" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, screenshotArquivo);


        // Aguardar até 10 segundos para que a janela desapareca
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPopRemover));

        // Clicar no link com o texto Logout
        navegador.findElement(By.linkText("Logout")).click();


    }

    @After
    public void tearDown(){
        // Fechar o navegador - close fecha o aba que foi aberta e o quit fecha todas as abas
        //navegador.close();
    }
}
