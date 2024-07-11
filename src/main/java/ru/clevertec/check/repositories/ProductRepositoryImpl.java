package ru.clevertec.check.repositories;

import ru.clevertec.check.models.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.valueOf;

public class ProductRepositoryImpl implements ProductRepository {

    private final File productsFile = Path.of("./src/main/resources/products.csv").toFile();
    private final List<Product> productList = new ArrayList<>();
    private static ProductRepository productRepository;

    @Override
    public List<Product> readProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))) {
            reader.lines()
                    .skip(1)
                    .forEach((product) -> {
                        String[] productProperties = product.split(";");
                        productList.add(new Product(
                                valueOf(productProperties[0]),
                                productProperties[1],
                                new BigDecimal(productProperties[2]),
                                valueOf(productProperties[3]),
                                parseBoolean(productProperties[4])));
                    });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return productList;
    }

    private ProductRepositoryImpl() {
    }

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepositoryImpl();
        }
        return productRepository;
    }
}