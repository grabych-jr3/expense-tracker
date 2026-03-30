package com.ogidazepam.expense_tracker.util.converters;

import com.ogidazepam.expense_tracker.model.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category category) {
        return category != null ? category.getCode() : null;
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        return s != null ? Category.formCode(s) : null;
    }
}
