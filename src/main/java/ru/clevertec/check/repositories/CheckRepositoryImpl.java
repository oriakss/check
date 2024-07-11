package ru.clevertec.check.repositories;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class CheckRepositoryImpl implements CheckRepository {

    private static CheckRepository checkRepository;

    @Override
    public void printCheck(String check) {
        File result = Path.of("./result.csv").toFile();
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(result, true))) {
            output.write(check.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private CheckRepositoryImpl() {
    }

    public static CheckRepository getInstance() {
        if (checkRepository == null) {
            checkRepository = new CheckRepositoryImpl();
        }
        return checkRepository;
    }
}