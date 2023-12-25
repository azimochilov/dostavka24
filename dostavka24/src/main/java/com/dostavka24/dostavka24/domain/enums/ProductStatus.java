package com.dostavka24.dostavka24.domain.enums;

public enum ProductStatus {
    APPETIZER("Appetizer"),
    MAIN_COURSE("Main Course"),
    DESSERT("Dessert"),
    BEVERAGE("Beverage"),
    PIZZA("Pizza"),
    BURGER("Burger"),
    SUSHI("Sushi"),
    SALAD("Salad"),
    PASTA("Pasta"),
    SANDWICH("Sandwich"),
    STEAK("Steak"),
    CHICKEN("Chicken"),
    VEGETARIAN("Vegetarian"),
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACK("Snack"),
    ICE_CREAM("Ice Cream"),
    COFFEE("Coffee");

    private final String typeName;

    ProductStatus(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
