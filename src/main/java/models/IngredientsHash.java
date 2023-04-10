package models;

public class IngredientsHash {
    private String[] ingredients;

    public IngredientsHash() {
    }

    public IngredientsHash(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] inggredients) {
        this.ingredients = inggredients;
    }
}
