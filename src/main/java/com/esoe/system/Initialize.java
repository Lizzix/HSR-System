package com.esoe.system;

import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static com.esoe.util.JsonUtils.parseOriginalData;

@Slf4j
public class Initialize {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        parseOriginalData();
    }
}
