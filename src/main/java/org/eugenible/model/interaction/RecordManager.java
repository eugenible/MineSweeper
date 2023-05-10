package org.eugenible.model.interaction;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.eugenible.model.game.Complexity;
import org.eugenible.model.interaction.exceptions.InvalidInputException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class RecordManager {
    private static final PlayerData DEFAULT_PLAYER_DATA = new PlayerData("Unknown", 999);
    private final Path recordsFile;

    @Getter
    private final Map<Complexity, PlayerData> recordsMap;

    public RecordManager(String recordsFilename) {
        recordsMap = new EnumMap<>(Complexity.class);
        recordsFile = Path.of(recordsFilename);
        if (!Files.exists(recordsFile)) {
            try {
                Files.createFile(recordsFile);
            } catch (IOException e) {
                log.warn("Couldn't create records file!", e);
            }
        }
        setRecordMapToDefault();
        readRecordsFromFile();
    }

    private void setRecordMapToDefault() {
        recordsMap.put(Complexity.EASY, DEFAULT_PLAYER_DATA);
        recordsMap.put(Complexity.MEDIUM, DEFAULT_PLAYER_DATA);
        recordsMap.put(Complexity.HARD, DEFAULT_PLAYER_DATA);
    }

    // Most important method #1
    public void updateRecord(Complexity complexity, PlayerData player) {
        recordsMap.put(complexity, player);
        writeRecordsToFile();
    }

    // Most important method #2
    public PlayerData getRecordPlayerData(Complexity complexity) {
        PlayerData data = recordsMap.get(complexity);
        if (data != null) {
            return data;
        }
        return DEFAULT_PLAYER_DATA;
    }

    private void readRecordsFromFile() {
        try (BufferedReader reader = Files.newBufferedReader(recordsFile)) {
            List<String> recordLines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                recordLines.add(line);
            }

            fillRecords(recordLines);
        } catch (FileNotFoundException e) {
            log.warn("File '{}' not found!", recordsFile.getFileName(), e);
        } catch (IOException | InvalidInputException e) {
            log.warn(e);
        }
    }

    private void fillRecords(List<String> recordLines) throws InvalidInputException {
        for (String line : recordLines) {
            String[] recordData = line.split(",");
            if (recordData.length != 3) {
                throw new InvalidInputException(
                        "Формат хранения рекордов в строке должен иметь вид <COMPLEXITY,NAME,DURATION>, " +
                                "где:\nCOMPLEXITY " +
                                "- [EASY,MEDIUM,HARD];\nNAME - имя;\nDURATION - рекорд в секундах.");
            }

            Complexity complexity = getComplexity(recordData[0]);
            String playerName = recordData[1];
            int recordTime = getNumericRecordTime(recordData[2]);
            recordsMap.put(complexity, new PlayerData(playerName, recordTime));
        }
    }

    private Complexity getComplexity(String stringComplexity) throws InvalidInputException {
        Complexity complexity;
        try {
            complexity = Complexity.valueOf(stringComplexity);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Provided record complexity <" + stringComplexity + "> is not valid");
        }
        return complexity;
    }

    private int getNumericRecordTime(String stringRecordTime) throws InvalidInputException {
        int record;
        try {
            record = Integer.parseInt(stringRecordTime);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Provided game duration value is not valid: <" + stringRecordTime + ">");
        }
        return record;
    }

    private void writeRecordsToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(recordsFile, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Map.Entry<Complexity, PlayerData> entry : recordsMap.entrySet()) {
                writer.write(String.format("%s,%s,%s", entry.getKey().toString(), entry.getValue().getPlayerName(),
                        entry.getValue().getDuration()));
                writer.newLine();
            }
        } catch (IOException e) {
            log.warn("IOException occurred", e);
        }
    }
}
