package ru.xpendence.development.gimstopwatch.foodstuffs;

import android.content.Context;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by promoscow on 26.05.17.
 * Temporary class to fill goods data from excell file.
 */

public class ExcelParser {

    private Context context;

    public ExcelParser(Context context) {
        this.context = context;
    }

    public Map<String, Good> fill(InputStream iStream) {

        Map<String, Good> map = new TreeMap<>();

        InputStream inputStream;
        HSSFWorkbook workBook = null;
        try {
            inputStream = iStream;
            workBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workBook != null) {
            Sheet sheet = workBook.getSheetAt(0);
            for (Row row : sheet) {
                Iterator<Cell> cells = row.iterator();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    if (cell.toString().equals("Name")) break;

//                    Good good = new Good();
//                    if (cell.toString().equals("Name")) break;
//                    else good.setName(cell.getStringCellValue());
//                    good.setProteins(cells.next().getNumericCellValue());
//                    good.setFats(cells.next().getNumericCellValue());
//                    good.setCarbohydrates(cells.next().getNumericCellValue());
//                    int x = (int) Double.parseDouble(String.valueOf(cells.next().getNumericCellValue()));
//                    good.setCalories(x);
//                    good.setCategory(cells.next().getStringCellValue());
//                    map.put(good.getName(), good);
                }
            }
        }
        return map;
    }
}
