package org.example.intuitetllapplication.util.Readers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;

@Service
public class CSVReadWriteUtilService implements ReaderUtil{
    public CSVParser readRecord(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        CSVParser csvParser = new CSVParser(br, CSVFormat.EXCEL.withFirstRecordAsHeader());
        return csvParser;
    }

    public void writeRecord(File createdOutputFile, String record) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(createdOutputFile), CSVFormat.EXCEL);
        csvPrinter.printRecords(record);
        csvPrinter.flush();
    }

    public CSVRecord next(CSVParser csvParser){
        Iterator<CSVRecord> iterator = csvParser.stream().iterator();
        return iterator.next();
    }

}
