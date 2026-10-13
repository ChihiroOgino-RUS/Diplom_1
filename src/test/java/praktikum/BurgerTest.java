package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun bun;

    Burger burger;

    @Before
    public void init() {
        burger = new Burger();
    }

    @Test
    public void getPriceBunOnlyTest() {
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        float expected = 200f;
        float actual = burger.getPrice();

        assertEquals(expected, actual, 0.001f);
    }

    @Test
    public void addIngredientTest() {
        IngredientType expectedType = IngredientType.FILLING;
        String expectedName = "cutlet";
        float expectedPrice = 100f;

        Ingredient newIngredient = new Ingredient(expectedType, expectedName, expectedPrice);
        burger.addIngredient(newIngredient);
        Ingredient actualIngredient = burger.ingredients.get(0);

        assertEquals(expectedType, actualIngredient.getType());
        assertEquals(expectedName, actualIngredient.getName());
        assertEquals(expectedPrice, actualIngredient.getPrice(), 0.001f);
    }

    @Test
    public void removeIngredientTest() {
        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100f);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200f);

        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);
        burger.removeIngredient(0);

        Ingredient actualIngredient = burger.ingredients.get(0);

        int expectedSize = 1;
        String expectedName = "sour cream";

        assertEquals(expectedSize, burger.ingredients.size());
        assertEquals(expectedName, actualIngredient.getName());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientInvalidIndexTest() {
        burger.removeIngredient(0);
    }

    @Test
    public void moveIngredientTest() {
        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100f);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200f);

        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);

        assertTrue("Некорректный порядок", burger.ingredients.indexOf(sourCream) > burger.ingredients.indexOf(hotSauce));

        burger.moveIngredient(0, 1);

        assertTrue("Прямое перемещение: ингредиент не перемещён", burger.ingredients.indexOf(sourCream) < burger.ingredients.indexOf(hotSauce));
    }

    @Test
    public void moveIngredientBackTest() {
        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100f);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200f);

        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);

        assertTrue("Некорректный порядок", burger.ingredients.indexOf(sourCream) > burger.ingredients.indexOf(hotSauce));

        burger.moveIngredient(1, 0);

        assertTrue("Обратное перемещение: ингредиент не перемещён", burger.ingredients.indexOf(sourCream) < burger.ingredients.indexOf(hotSauce));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientInvalidIndexTest() {
        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100f);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200f);

        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);

        burger.moveIngredient(10, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientInvalidNewIndexTest() {
        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100f);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200f);

        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);

        burger.moveIngredient(0, 10);
    }

    @Test
    public void getReceiptBunOnlyTest() {
        String bunName = "black bun";

        when(bun.getName()).thenReturn(bunName);
        when(bun.getPrice()).thenReturn(100f);

        burger.setBuns(bun);

        StringBuilder receiptBuilder = new StringBuilder()
                .append(String.format("(==== %s ====)%n", bunName))
                .append(String.format("(==== %s ====)%n%n", bunName))
                .append(String.format("Price: %f%n", 200f));

        String expected = receiptBuilder.toString();

        assertEquals(expected, burger.getReceipt());
    }

    @Test
    public void getReceiptWithIngredientsTest() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);

        burger.setBuns(bun);

        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100f);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 200f);

        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);

        StringBuilder receiptBuilder = new StringBuilder()
                .append(String.format("(==== %s ====)%n", bun.getName()))
                .append(String.format("= %s %s =%n", hotSauce.getType().toString().toLowerCase(), hotSauce.getName()))
                .append(String.format("= %s %s =%n", sourCream.getType().toString().toLowerCase(), sourCream.getName()))
                .append(String.format("(==== %s ====)%n%n", bun.getName()))
                .append(String.format("Price: %f%n", 500f));

        String expected = receiptBuilder.toString();

        assertEquals(expected, burger.getReceipt());
    }

}
