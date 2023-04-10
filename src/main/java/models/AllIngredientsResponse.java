package models;

public class AllIngredientsResponse {
    private boolean success;
    private Ingredient[] data;

    public AllIngredientsResponse(boolean success, Ingredient[] ingredients) {
        this.success = success;
        this.data = ingredients;
    }

    public AllIngredientsResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Ingredient[] getIngredients() {
        return data;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.data = ingredients;
    }
}
