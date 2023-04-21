package org.eugenible.model.interaction;

import org.eugenible.model.game.Complexity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class RecordManager {
    private static final PlayerData defaultPlayerData = new PlayerData("Unknown", 999);
    private Path recordsFile;
    private Map<Complexity, PlayerData> recordsMap;

    public RecordManager(String recordsFilename) {
        recordsMap = new HashMap<>();
        recordsFile = Path.of(recordsFilename);
        if (!Files.exists(recordsFile)) {
            try {
                Files.createFile(recordsFile);
            } catch (IOException e) {
                System.out.println("Couldn't create records file!");
            }
        }
        setRecordMapToDefault();
        readRecordsFromFile();
    }

    private void setRecordMapToDefault() {
        recordsMap.put(Complexity.EASY, defaultPlayerData);
        recordsMap.put(Complexity.MEDIUM, defaultPlayerData);
        recordsMap.put(Complexity.HARD, defaultPlayerData);
    }

    private void readRecordsFromFile() {
        try (BufferedReader reader = Files.newBufferedReader(recordsFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                recordsMap.put(Complexity.valueOf(values[0]), new PlayerData(values[1], Integer.parseInt(values[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + recordsFile.getFileName() + " not found!");
        } catch (IOException e) {
            System.out.println("IOException occurred");
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println(
                    "Формат хранения рекордов в строке должен иметь вид <COMPLEXITY,NAME,DURATION>, где:\nCOMPLEXITY " +
                            "- [EASY,MEDIUM,HARD];\nNAME - имя;\nDURATION - рекорд в секундах.");
            setRecordMapToDefault();
        }
    }

    // Most important method #1
    public void updateRecord(Complexity complexity, PlayerData player) {
        recordsMap.put(complexity, player);
        writeRecordsToFile();
    }

    // Most important method #2
    public PlayerData getRecordPlayerData(Complexity complexity) {
        PlayerData data = recordsMap.get(complexity);
        if (data != null) return data;
        return defaultPlayerData;
    }

    private void writeRecordsToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(recordsFile, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Map.Entry<Complexity, PlayerData> entry : recordsMap.entrySet()) {
                writer.write(String.format("%s,%s,%s", entry.getKey().toString(), entry.getValue().getPlayerName(),
                        entry.getValue().getDuration()));
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("IOException occurred");
        }
    }

    public Map<Complexity, PlayerData> getRecordsMap() {
        return recordsMap;
    }
}
