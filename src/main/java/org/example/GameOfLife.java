package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOfLife {

    private static final String LIVE_STATEMENT = "X";
    private static final String DEAD_STATEMENT = "O";
    private static final int QUANTITY_OF_LIVE_NEIGHBOURS_2 = 2;
    private static final int QUANTITY_OF_LIVE_NEIGHBOURS_3 = 3;

    private int fieldWidth;
    private int fieldHeight;
    private int numOfIterate;
    private String[][] field;
    private String[][] fieldForMarkLiveOrDead;
    private final String filePath;

    public GameOfLife() {
        filePath = new File("").getAbsolutePath() +
                File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    }

    public void game(String inputFileName, String outputFileName) {
        loadInputData(inputFileName);
        for (int i = 0; i < numOfIterate; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                for (int k = 0; k < fieldWidth; k++) {
                    if (willLive(k, j)) {
                        fieldForMarkLiveOrDead[j][k] = LIVE_STATEMENT;
                    } else {
                        fieldForMarkLiveOrDead[j][k] = DEAD_STATEMENT;
                    }
                }
            }
            field = fieldForMarkLiveOrDead;
            fieldForMarkLiveOrDead = new String[fieldHeight][fieldWidth];
        }
        unloadOutputData(outputFileName);
    }

    private void loadInputData(String inputFileName) {
        inputFileName = filePath + inputFileName;
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(inputFileName));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        setGameInitInformation(lines.get(0));
        field = new String[fieldWidth][];
        for (int i = 0; i < lines.size() - 1; i++) {
            field[i] = lines.get(i + 1).split(" ");
        }
        fieldForMarkLiveOrDead = new String[fieldHeight][fieldWidth];
    }

    private void unloadOutputData(String outputFileName) {
        outputFileName = filePath + outputFileName;
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < fieldHeight; i++) {
            String line = Arrays.toString(field[i]);
            strings.add(line.substring(1, line.length() - 1).replaceAll(",", ""));
        }
        try {
            Files.write(Paths.get(outputFileName), strings, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void setGameInitInformation(String gameInitInformation) {
        String[] gameInitInformationArray = gameInitInformation.split(",");
        fieldWidth = Integer.parseInt(gameInitInformationArray[1].trim());
        fieldHeight = Integer.parseInt(gameInitInformationArray[0].trim());
        numOfIterate = Integer.parseInt(gameInitInformationArray[2].trim());
    }

    private int getLeftXCoordinate(int currentCellX) {
        if (currentCellX == 0) {
            currentCellX = fieldWidth - 1;
        } else {
            currentCellX--;
        }
        return currentCellX;
    }

    private int getRightXCoordinate(int currentCellX) {
        if (currentCellX == fieldWidth - 1) {
            currentCellX = 0;
        } else {
            currentCellX++;
        }
        return currentCellX;
    }

    private int getUpYCoordinate(int currentCellY) {
        if (currentCellY == 0) {
            currentCellY = fieldHeight - 1;
        } else {
            currentCellY--;
        }
        return currentCellY;
    }

    private int getDownYCoordinate(int currentCellY) {
        if (currentCellY == fieldHeight - 1) {
            currentCellY = 0;
        } else {
            currentCellY++;
        }
        return currentCellY;
    }

    private boolean leftNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[currentCellY][getLeftXCoordinate(currentCellX)].equals(LIVE_STATEMENT);
    }

    private boolean rightNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[currentCellY][getRightXCoordinate(currentCellX)].equals(LIVE_STATEMENT);
    }

    private boolean upNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[getUpYCoordinate(currentCellY)][currentCellX].equals(LIVE_STATEMENT);
    }

    private boolean downNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[getDownYCoordinate(currentCellY)][currentCellX].equals(LIVE_STATEMENT);
    }

    private boolean rightDownNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[getDownYCoordinate(currentCellY)][getRightXCoordinate(currentCellX)].equals(LIVE_STATEMENT);
    }

    private boolean rightUpNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[getUpYCoordinate(currentCellY)][getRightXCoordinate(currentCellX)].equals(LIVE_STATEMENT);
    }

    private boolean leftUpNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[getUpYCoordinate(currentCellY)][getLeftXCoordinate(currentCellX)].equals(LIVE_STATEMENT);
    }

    private boolean leftDownNeighbourIsLive(int currentCellX, int currentCellY) {
        return field[getDownYCoordinate(currentCellY)][getLeftXCoordinate(currentCellX)].equals(LIVE_STATEMENT);
    }

    public int getQuantityOfLiveNeighbour(int currentCellX, int currentCellY) {
        int quantityOfLiveNeighbour = 0;
        if (leftNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (rightNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (upNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (downNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (leftDownNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (leftUpNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (rightDownNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        if (rightUpNeighbourIsLive(currentCellX, currentCellY)) {
            quantityOfLiveNeighbour++;
        }
        return quantityOfLiveNeighbour;
    }

    private boolean isLive(int currentCellX, int currentCellY) {
        return field[currentCellY][currentCellX].equals(LIVE_STATEMENT);
    }

    private boolean willLive(int currentCellX, int currentCellY) {
        boolean isLive = isLive(currentCellX, currentCellY);
        int quantityOfLiveNeighbours = getQuantityOfLiveNeighbour(currentCellX, currentCellY);
        if (isLive && (quantityOfLiveNeighbours == QUANTITY_OF_LIVE_NEIGHBOURS_2 ||
                quantityOfLiveNeighbours == QUANTITY_OF_LIVE_NEIGHBOURS_3)) {
            return true;
        }
        return !isLive && quantityOfLiveNeighbours == QUANTITY_OF_LIVE_NEIGHBOURS_3;
    }
}
