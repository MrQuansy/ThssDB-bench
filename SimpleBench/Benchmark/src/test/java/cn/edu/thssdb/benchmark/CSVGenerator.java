package cn.edu.thssdb.benchmark;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVGenerator {
  public static void main(String[] args) {
    String csvFilePath = "path_to_your_csv_file.csv";

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));

      // Write header
      writer.write(
          "group_number,RC-CRUDTest,RC-ConcurrentTest,RC-LostUpdate,RC-DirtyRead,S-CRUDTest,S-ConcurrentTest,S-LostUpdate,S-DirtyRead,S-RepeatRead,S-PhantomRead,50-Time,100-Time,500-Time");
      writer.newLine();

      // Write rows
      for (int groupNumber = 1; groupNumber <= 40; groupNumber++) {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(groupNumber).append(",1,1,1,1,1,1,1,1,1,1,1,1,1");
        writer.write(rowBuilder.toString());
        writer.newLine();
      }

      writer.close();
      System.out.println("CSV file generated successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
