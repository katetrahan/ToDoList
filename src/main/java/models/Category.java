package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Category {

    private String category;
    private int id;

    public Category(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category1 = (Category) o;

        if (id != category1.id) return false;
        return category.equals(category1.category);
    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + id;
        return result;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
