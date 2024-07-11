package ru.clevertec.check.repositories;

import ru.clevertec.check.models.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> readProducts();
}